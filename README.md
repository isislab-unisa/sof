# SCUD
SCUD is a Simulation exploration and optimization framework on the CloUD, designed in [ISISLab](http://www.isislab.it), that allows to run and collect results for two kinds of scenario parameter space exploration (PSE) and simulation optimization (SO) considering the computational resources as available for a not fixed time and subjects to failure. 

SCUD was designed to manage three kinds of simulation engine: [MASON](http://cs.gmu.edu/~eclab/projects/mason/), [NetLogo](https://ccl.northwestern.edu/netlogo/) and a generic simulator. SCUD provides some software facilities for the first simulators like the automatic simulation input setting and automatic output generating (that does not provide for the generic simulator, for obvious reasons). The generic simulator must be an executable compliant with the cluster machine used.

SCUD is a framework to exploit simulation optimization on Hadoop cluster. SCUD is divided in two main functional blocks the core and the client. The core component provides all functionality to write out Java based client application. The client is a command line Java application that shown the features of the core component and allows to execute PSE and SO process on a [Apache Hadoop](http://hadoop.apache.org/) cluster.

The SCUD system presents two main entities: the SCUD client and the remote host machine on which is installed Hadoop, also named the Hadoop master node. Respectively on the left and on the right of the above figure.

![alt tag](https://raw.githubusercontent.com/spagnuolocarmine/scud/master/architecture/ascud.png)


SCUD architecture is divided in three main software block: a user frontend that is the SCUD application for running and managing the simulation on the Hadoop infrastructure, used only on the client side; the Hadoop layer that encloses softwares and libraries provided from Hadoop infrastructure, used on the remote side; and the SCUD core that is the main software block composed of six functional blocks, that are used on the client and on the remote side.

##System Requirements
* [Apache Hadoop](http://hadoop.apache.org/) on Linux based Cluster version 2.4.0 or greater.
* Java Runtime Environment version 7 or greater.
* An account on the cluster over SSH.

-

## Example client code 

Here's a minimum example of defining a client application using the SCUD core:

```java
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Fonts");

        Group g = new Group();
        Scene scene = new Scene(g, 550, 250);

        AudioClip plonkSound = new AudioClip("http://somehost/p.aiff");
        plonkSound.play();
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

```

## Compiling the SCUD core library from src/ to build/
If you would like to add features to the library, you will have to change the code in `src/` and then compile the library into the `build/` directory. The compilation script simply concatenates files in `src/` and then minifies the result.

The compilation is done using an ant task: it compiles `build/convnet.js` by concatenating the source files in `src/` and then minifies the result into `build/convnet-min.js`. Make sure you have **ant** installed (on Ubuntu you can simply *sudo apt-get install* it), then cd into `compile/` directory and run:

    $ ant -lib yuicompressor-2.4.8.jar -f build.xml

The output files will be in `build/`

## Compiling the SCUD client from src/ to build/

## Getting Started SCUD Client
SCUD framework provides a Java command line client available in the release (SCUD-Client.jar):

* Execute the command: java -jar SCUD-Client.jar. This client application use SSH to connect to the Hadoop cluster. The application parameters are the following:
    - `-h HOST NAME` cluster master node IP address. The default value is `localhost (127.0.0.1)`;
    - `-port PORT NUMBER` listening port for SHH process on cluster. The default value is `22`;
    - `-bindir PATH BIN DIRECTORY` the bin installation path (absolute) of Hadoop. The default value is `/bin`;
    - `-homediR PATH DIRECTORY` the home directory of the user on the master node. The default value  is `~/temp`;
    - `-javabindir PATH JAVA BIN DIRECTORY` the bin installation path of the Java Virtual Machine. The default value is `/usr/bin`;
    - `-scudhomedir USER SCUD HOME DIRECTORY` the Hadoop distributed File system directory which will be the root directory for the SCUD application. The default value is `/`.

---

#### SCUD Client commands overview
* **`help`** shows the name and a brief use description of SCUD commands. 
* **`exit`** exits from SCUD application and disconnects the user.  
* **`createsimulation`** creates a simple simulation in parameter space exploration mode.  This command has following parameters input:  
    - ``model`` mason-netlogo-generic
    - ``simulation name``
    - ``input XML absolute path``
    - ``output XML absolute path``
    - ``brief simualtion description``
    - ``absolute path of bin file for simulation executable model``
        * usage ``createsimulation netlogo mysim /home/pippo/input.xml /home/pippo/output.xml "the description" /home/pippo/mysim.nlogo``
*  **`createsimulationloop`**  creates a simulation in simulation optimization mode.  This command has following parameters input:
    - ``model`` mason-netlogo-generic
    - ``simulation name``
    - ``domain XML absolute path``
    - ``absolute path for bin command to exec the selection and evaluation``
    - ``output XML absolute path``
    -  ``absolute path for the selection function file``
    -  ``absolute path for the evaluate function file``
    - ``brief simualtion description``
    - ``absolute path of bin file for simulation executable model``
        * usage ``createsimulationloop mason mysim /home/pippo/domain.xml /bin/java  /home/pippo/output.xml /home/pippo/selection_function.jar /home/pippo/evaluate_function.jar my description /home/pippo/mysim.jar``
* **``getsimulations``** prints states and data for all simulations. For each simulation returns the following information:   
    - `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation.  
    - ``simulation name``
    - ``simulation author`` 
    - ``creation time of simulation``
    - ``the simulation description``
    - ``loop status list``:
        * ``created``, the simulation has been created but not running yet.
        * ``running``, the simulation are running.
        * ``finished``, the simulation has been finished correctly.
        * ``aborted``, the simulation has been finished not correctly: the process was aborted from the system or the user.
* **`list`** prints a list of all simulations. For each simulation returns the following information:
    - `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation. 
    - `simulation name`
    - ``status of simulation``:
        * ``created``, the simulation has been created but not running yet.
        * ``running``, the simulation are running.
        * ``finished``, the simulation has been finished correctly.
        * ``aborted``, the simulation has been finished not correctly: the process was aborted from the system or the user.
    - `creation time of simulation`
    - `simulation identifier on hadoop file system`
* **`submit`** submit a new simulation execution. This command takes the following input parameters: 
    - `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation (given in the list command). 
        * usage `submit x` where x is your simulation identifier.
* **`getsimulation`** shows all data information of a simulation and loops. This command takes the following input parameters: 
- `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation (given in the list command). 
Command return the following information:  
- `id of simulation` 
- `simulation name`
- `simulation author` 
- `creation time of simulation`
- `description`
- `status of simulation(created, running, finished, aborted)`
- `number of loops of simulation`
  
## License
Copyright ISISLab, 2015 Università degli Studi di Salerno

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
