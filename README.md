# SCUD
SCUD is a Simulation exploration and optimization framework on the CloUD, designed in [ISISLab](http://www.isislab.it), that allows to run and collect results for two kinds of scenario parameter space exploration (PSE) and simulation optimization (SO) considering the computational resources as available for a not fixed time and subjects to failure. 

SCUD was designed to manage three kinds of simulation engine: [MASON](http://cs.gmu.edu/~eclab/projects/mason/), [NetLogo](https://ccl.northwestern.edu/netlogo/) and a generic simulator. SCUD provides some software facilities for the first simulators like the automatic simulation input setting and automatic output generating (that does not provide for the generic simulator, for obvious reasons). The generic simulator must be an executable compliant with the cluster machine used.

SCUD is a framework to exploit simulation optimization on Hadoop cluster. SCUD is divided in two main functional blocks the core and the client. The core component provides all functionality to write out Java based client application. The client is a command line Java application that shown the features of the core component and allows to execute PSE and SO process on a [Apache Hadoop](http://hadoop.apache.org/) cluster.

The SCUD system presents two main entities: the SCUD client and the remote host machine on which is installed Hadoop, also named the Hadoop master node. Respectively on the left and on the right of the above figure.

![alt tag](https://raw.githubusercontent.com/spagnuolocarmine/scud/master/architecture/ascud.png)
![alt tag](https://raw.githubusercontent.com/spagnuolocarmine/scud/master/architecture/ascudworkflow.png

SCUD architecture is divided in three main software block: a user frontend that is the SCUD application for running and managing the simulation on the Hadoop infrastructure, used only on the client side; the Hadoop layer that encloses softwares and libraries provided from Hadoop infrastructure, used on the remote side; and the SCUD core that is the main software block composed of six functional blocks, that are used on the client and on the remote side.
####SCUD System workflow
![alt tag](https://raw.githubusercontent.com/spagnuolocarmine/scud/master/architecture/ascudworkflow.png)
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

- - -

* **`exit`** exits from SCUD application and disconnects the user.  

- - -

* **`createsimulation`** creates a simple simulation in parameter space exploration mode.  This command takes following parameters input:  
    - ``model`` mason-netlogo-generic
    - ``simulation name``
    - ``input XML absolute path``
    - ``output XML absolute path``
    - ``brief simualtion description``
    - ``absolute path of bin file for simulation executable model``
        * usage ``createsimulation netlogo mysim /home/pippo/input.xml /home/pippo/output.xml "the description" /home/pippo/mysim.nlogo``

- - -

*  **`createsimulationloop`**  creates a simulation in simulation optimization mode. This command takes following parameters input:
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

- - -

* **``getsimulations``** prints states and data for all simulations.

Returns for each simulation the following information:   
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

- - -

* **`list`** prints a list of all simulations.  

Returns for each simulation the following information:
- `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation. 
- `simulation name`
- ``status of simulation``:
    * ``created``, the simulation has been created but not running yet.
    * ``running``, the simulation are running.
    * ``finished``, the simulation has been finished correctly.
    * ``aborted``, the simulation has been finished not correctly: the process was aborted from the system or the user.
- `creation time of simulation`
- `simulation identifier on hadoop file system`

- - -

* **`submit`** submit a new simulation execution. This command takes the following input parameters: 
    - `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation (given in the list command). 
        * usage `submit x` where x is your simulation identifier.

- - -

* **`getsimulation`** shows all data information of a simulation and loops. This command takes the following input parameters: 
    - `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation (given in the list command). 
    
Command returns the following information:  
* `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation. 
* `simulation name`
* `simulation author` 
* `creation time of simulation`
* `the simulation description`
* ``status of simulation``:
    - ``created``, the simulation has been created but not running yet.
    - `running`, the simulation are running.
    - ``finished``, the simulation has been finished correctly.
    - ``aborted``, the simulation has been finished not correctly: the process was aborted from the system or the user.
* `number of loops executed by the process`

- - -

* **`getresult`** download all data of a simulation in GNU tar archive, the command works also when the simulation is running. This command takes the following input parameters: 
    - `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation (given in the list command).
    - `localpath`, absolute local path where you will save the tar file.
     * usage ``getresult x /home`` download the data of simulation with identifier 2 in the `/home` directory.
    
- - -

* **`kill`** stop the SCUD process of particular simulation. This command takes the following input parameters: 
    - `simulation identifier` an integer number associated to the simulation. Note: this is the simulation identifier to use for all command to refer a simulation (given in the list command).
    - `localpath`, absolute local path where you will save the tar file.
     * usage ``kill x `` interrupt the process of simulation with identifier x.
    
- - -

* **`usage`** prints informations about the commands.
    
- - -

* **`makexml`** this command start a tool to generate the XML files for the SCUD process.
     - `todo` todo.
- - -

#### SCUD XML schemas
SCUD support two executions modes, as mentioned above, PSE and SO.
In PSE mode the input to the simulation and the output must be in XML, compliant with input/output schemas. In the SO mode the user must not provide the input files but must declare the parameters domain in XML using the domain schema.

In the following there are shown the SCUD parameters XML schemas.


* **XML Domain**
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" targetNamespace="it.isislab.sof" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:import schemaLocation="schema3.xsd"/>

  <xs:element name="domain" type="domain"/>

</xs:schema>

<xs:complexType name="simulation">
    <xs:sequence>
      <xs:element name="name" type="xs:string"/>
      <xs:element name="toolkit" type="xs:string"/>
      <xs:element name="date" type="xs:string" minOccurs="0"/>
      <xs:element name="state" type="xs:string"/>
      <xs:element name="description" type="xs:string" minOccurs="0"/>
      <xs:element ref="runs" minOccurs="0"/>
      <xs:element ref="runnable" minOccurs="0"/>
      <xs:element name="loop" type="xs:boolean"/>
      <xs:element name="processName" type="xs:string" minOccurs="0"/>
    </xs:sequence>
    <xs:attribute name="id" type="xs:string" use="required"/>
    <xs:attribute name="author" type="xs:string"/>
  </xs:complexType>
</xs:schema>

</xs:complexType>


  <xs:complexType name="runs">
    <xs:sequence>
      <xs:element ref="loop" minOccurs="0" maxOccurs="unbounded"/>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="loop">
    <xs:sequence>
      <xs:element name="status" type="xs:string" minOccurs="0"/>
      <xs:element ref="time" minOccurs="0"/>
      <xs:element ref="inputs" minOccurs="0"/>
      <xs:element ref="outputs" minOccurs="0"/>
    </xs:sequence>
    <xs:attribute name="id" type="xs:int" use="required"/>
  </xs:complexType>

  <xs:complexType name="time">
    <xs:sequence>
      <xs:element name="start" type="xs:string" minOccurs="0"/>
      <xs:element name="stop" type="xs:string" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="inputs">
    <xs:sequence>
      <xs:element ref="simulation"/>
      <xs:element ref="input" maxOccurs="unbounded"/>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="input">
    <xs:sequence>
      <xs:element name="element" type="parameter" maxOccurs="unbounded"/>
    </xs:sequence>
    <xs:attribute name="id" type="xs:int" use="required"/>
  </xs:complexType>

  <xs:complexType name="parameter">
    <xs:choice>
      <xs:element name="string" type="parameterString"/>
      <xs:element name="double" type="parameterDouble"/>
      <xs:element name="long" type="parameterLong"/>
    </xs:choice>
    <xs:attribute name="variableName" type="xs:string" use="required"/>
  </xs:complexType>

  <xs:simpleType name="parameterString">
    <xs:restriction base="xs:string"/>
  </xs:simpleType>

  <xs:simpleType name="parameterDouble">
    <xs:restriction base="xs:double"/>
  </xs:simpleType>

  <xs:simpleType name="parameterLong">
    <xs:restriction base="xs:long"/>
  </xs:simpleType>

  <xs:complexType name="outputs">
    <xs:sequence>
      <xs:element name="output_list" type="output" nillable="true" minOccurs="0" maxOccurs="unbounded"/>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="output">
    <xs:sequence>
      <xs:element name="param" type="parameter" maxOccurs="unbounded"/>
    </xs:sequence>
    <xs:attribute name="inputID" type="xs:int" use="required"/>
  </xs:complexType>

  <xs:complexType name="runnableFile">
    <xs:sequence>
      <xs:element name="simulation" type="xs:string" minOccurs="0"/>
      <xs:element name="selection" type="xs:string" minOccurs="0"/>
      <xs:element name="rating" type="xs:string" minOccurs="0"/>
      <xs:element name="bashCommandForRunnableFunction" type="xs:string" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="parameterDomain">
    <xs:choice>
      <xs:element name="discrete" type="parameterDomainDiscrete"/>
      <xs:element name="continuous" type="parameterDomainContinuous"/>
      <xs:element name="list_string" type="parameterDomainListString"/>
      <xs:element name="list_values" type="parameterDomainListValues"/>
    </xs:choice>
    <xs:attribute name="variableName" type="xs:string" use="required"/>
  </xs:complexType>

  <xs:complexType name="parameterDomainDiscrete">
    <xs:sequence/>
    <xs:attribute name="increment" type="xs:long" use="required"/>
    <xs:attribute name="max" type="xs:long" use="required"/>
    <xs:attribute name="min" type="xs:long" use="required"/>
  </xs:complexType>

  <xs:complexType name="parameterDomainContinuous">
    <xs:sequence/>
    <xs:attribute name="increment" type="xs:double" use="required"/>
    <xs:attribute name="max" type="xs:double" use="required"/>
    <xs:attribute name="min" type="xs:double" use="required"/>
  </xs:complexType>

  <xs:complexType name="parameterDomainListString">
    <xs:sequence>
      <xs:element name="list" type="xs:string" maxOccurs="unbounded"/>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="parameterDomainListValues">
    <xs:sequence>
      <xs:element name="list" type="xs:double" maxOccurs="unbounded"/>
    </xs:sequence>
  </xs:complexType>
</xs:schema>
```
- - -

* **XML Input**
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="input" type="input"/>

  <xs:element name="parameter" type="parameter"/>

  <xs:complexType name="input">
    <xs:sequence>
      <xs:element name="element" type="parameter" maxOccurs="unbounded"/>
    </xs:sequence>
    <xs:attribute name="id" type="xs:int" use="required"/>
  </xs:complexType>

  <xs:complexType name="parameter">
    <xs:choice>
      <xs:element name="string" type="parameterString"/>
      <xs:element name="double" type="parameterDouble"/>
      <xs:element name="long" type="parameterLong"/>
    </xs:choice>
    <xs:attribute name="variableName" type="xs:string" use="required"/>
  </xs:complexType>

  <xs:simpleType name="parameterString">
    <xs:restriction base="xs:string"/>
  </xs:simpleType>

  <xs:simpleType name="parameterDouble">
    <xs:restriction base="xs:double"/>
  </xs:simpleType>

  <xs:simpleType name="parameterLong">
    <xs:restriction base="xs:long"/>
  </xs:simpleType>
</xs:schema>
```
- - -

* **XML Output**
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="output" type="output"/>

  <xs:element name="parameter" type="parameter"/>

  <xs:complexType name="output">
    <xs:sequence>
      <xs:element name="param" type="parameter" maxOccurs="unbounded"/>
    </xs:sequence>
    <xs:attribute name="inputID" type="xs:int" use="required"/>
  </xs:complexType>

  <xs:complexType name="parameter">
    <xs:choice>
      <xs:element name="string" type="parameterString"/>
      <xs:element name="double" type="parameterDouble"/>
      <xs:element name="long" type="parameterLong"/>
    </xs:choice>
    <xs:attribute name="variableName" type="xs:string" use="required"/>
  </xs:complexType>

  <xs:simpleType name="parameterString">
    <xs:restriction base="xs:string"/>
  </xs:simpleType>

  <xs:simpleType name="parameterDouble">
    <xs:restriction base="xs:double"/>
  </xs:simpleType>

  <xs:simpleType name="parameterLong">
    <xs:restriction base="xs:long"/>
  </xs:simpleType>
</xs:schema>
```
- - -


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
