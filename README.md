# SCUD
SCUD is a Simulation exploration and optimization framework on the CloUD, designed in [ISISLab](http://www.isislab.it), that allows to run and collect results for two kinds of scenario parameter space exploration (PSE) and simulation optimization (SO) considering the computational resources as available for a not fixed time and subjects to failure. 

SCUD was designed to manage three kinds of simulation engine: [MASON](http://cs.gmu.edu/~eclab/projects/mason/), [NetLogo](https://ccl.northwestern.edu/netlogo/) and a generic simulator. SCUD provides some software facilities for the first simulators like the automatic simulation input setting and automatic output generating (that does not provide for the generic simulator, for obvious reasons). The generic simulator must be an executable compliant with the cluster machine used.

SCUD is a framework to exploit simulation optimization on Hadoop cluster. SCUD is divided in two main functional blocks the core and the client. The core component provides all functionality to write out Java based client application. The client is a command line Java application that shown the features of the core component and allows to execute PSE and SO process on a Hadoop cluster.

The SCUD system presents two main entities: the SCUD client and the remote host machine on which is installed Hadoop, also named the Hadoop master node. Respectively on the left and on the right of the above figure.

![alt tag](https://raw.githubusercontent.com/spagnuolocarmine/scud/master/architecture/ascud.png)


SCUD architecture is divided in three main software block: a user frontend that is the SCUD application for running and managing the simulation on the Hadoop infrastructure, used only on the client side; the Hadoop layer that encloses softwares and libraries provided from Hadoop infrastructure, used on the remote side; and the SCUD core that is the main software block composed of six functional blocks, that are used on the client and on the remote side.

##System Requirements
..Java Runtime Environment version 7 or greater.
..Hadoop on Linux based Cluster version 2.4.0 or greater.
..A account on the cluster over SSH.

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

## Getting Started
A [Getting Started](link) tutorial is available on the Wiki page main page.

## Compiling the SCUD core library from src/ to build/
If you would like to add features to the library, you will have to change the code in `src/` and then compile the library into the `build/` directory. The compilation script simply concatenates files in `src/` and then minifies the result.

The compilation is done using an ant task: it compiles `build/convnet.js` by concatenating the source files in `src/` and then minifies the result into `build/convnet-min.js`. Make sure you have **ant** installed (on Ubuntu you can simply *sudo apt-get install* it), then cd into `compile/` directory and run:

    $ ant -lib yuicompressor-2.4.8.jar -f build.xml

The output files will be in `build/`

## Compiling the SCUD client from src/ to build/

## License
MIT
