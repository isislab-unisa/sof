# scud
SCUD is a Simulation exploration and optimization framework on the CloUD, designed in ISISLab, that allows to run and collect results for two kinds of scenario parameter space exploration (PSE) and simulation optimization (SO) considering the computational resources as available for a not fixed time and subjects to failure. 

SCUD was designed to manage three kinds of simulation engine: MASON, NetLogo and a generic simulator. SCUD provides some software facilities for [MASON](http://cs.gmu.edu/~eclab/projects/mason/) and [NetLogo](https://ccl.northwestern.edu/netlogo/) like the automatic simulation input setting and automatic output generating (that does not provide for the generic simulator, for obvious reasons). The generic simulator must be an executable compliant with the cluster machine used.

SCUD provides a command line user application see the Wiki.


## Example code

Here's a minimum example of defining a **2-layer neural network** and training
it on a single data point:

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
A [Getting Started](http://cs.stanford.edu/people/karpathy/convnetjs/started.html) tutorial is available on main page.

The full [Documentation](http://cs.stanford.edu/people/karpathy/convnetjs/docs.html) can also be found there.

See the **releases** page for this project to get the minified, compiled library, and a direct link to is also available below for convenience (but please host your own copy)

- [convnet.js](http://cs.stanford.edu/people/karpathy/convnetjs/build/convnet.js)
- [convnet-min.js](http://cs.stanford.edu/people/karpathy/convnetjs/build/convnet-min.js)

## Compiling the library from src/ to build/
If you would like to add features to the library, you will have to change the code in `src/` and then compile the library into the `build/` directory. The compilation script simply concatenates files in `src/` and then minifies the result.

The compilation is done using an ant task: it compiles `build/convnet.js` by concatenating the source files in `src/` and then minifies the result into `build/convnet-min.js`. Make sure you have **ant** installed (on Ubuntu you can simply *sudo apt-get install* it), then cd into `compile/` directory and run:

    $ ant -lib yuicompressor-2.4.8.jar -f build.xml

The output files will be in `build/`
## Use in Node
The library is also available on *node.js*:

1. install it: `$ npm install convnetjs`
2. use it: `var convnetjs = require("convnetjs");`

## License
MIT
