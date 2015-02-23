Tutorial: Optimization of NetLogo Fire simulation
==============================

Let’s learn by example.

Throughout this tutorial, we will walk you through the creation of a basic set-up of simulation optimization through SOF Client GUI.
We will assume you have already downloaded SOF and compiled using Apache Maven. 

###We will cover:

1. How to start a HDP 2.2 with Apache Hadoop pre-installed.
2. How to create the Domain, Input/Output and Ratings XML definitions.
*  How to create a simple selection and evaluate functions.
*  How to start the optimization process through SOF GUI.
*  How to download the results of the optimization process.

####Simulation Model Description
The Fire NetLogo model available in NetLogo examples library (shown in the following figure), _simulates the spread of a fire through a forest_.
It shows that the fire’s chance of reaching the right edge of the forest depends critically on the density of trees. This is an example of a common feature of
complex systems, the presence of a non-linear threshold or critical parameter.

![fire model image](https://github.com/isislab-unisa/SOF/raw/master/examples/netlogo-fire/images/fire.png)

In this tutorial we refer to an simulation optimization example described in _OpenMOLE_. We will tackle to the problem as described for the OpenMOLE framework but we adopt the evolutionary strategy that distinguish our framework.

* [OpenMOLE](http://www.openmole.org/) _Romain Reuillon, Mathieu Leclaire, and Sebastien Rey-Coyrehourcq. 2013. OpenMOLE, a workflow engine specifically tailored for the distributed exploration of simulation models. Future Gener. Comput. Syst. 29, 8 (October 2013)._

###1. How to start a HDP 2.2 with Apache Hadoop pre-installed

- Download the virtual machine at Hortonworks [download page](http://hortonworks.com/products/hortonworks-sandbox/), and install your prefered available virtual machine in order to set up the environment.
- Start the virtual machine in your virtual machine manager.
- After login, take the ip address of your machine by running the command:


```
[root@server]# ifconfig
eth0      Link encap:Ethernet  HWaddr 00:80:C8:F8:4A:51
          inet addr:192.168.99.35  Bcast:192.168.99.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:190312 errors:0 dropped:0 overruns:0 frame:0
          TX packets:86955 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:100 
          RX bytes:30701229 (29.2 Mb)  TX bytes:7878951 (7.5 Mb)
          Interrupt:9 Base address:0x5000 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:16436  Metric:1
          RX packets:306 errors:0 dropped:0 overruns:0 frame:0
          TX packets:306 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0 
          RX bytes:29504 (28.8 Kb)  TX bytes:29504 (28.8 Kb)
          
```

###2. How to create new simulation optimization process

1. Execute the SOF GUI Client.
```
java -jar SOF-1.0-client-GUI.jar
```
2. Connect to the Master of your Apache Hadoop cluster.

###3. How to create a simple selection and evaluate functions
In this tutorial we are interested to explore the parameters space in order to study the simulated fire propagation.
The parameter space is defined by

* the **seed**, `random-seed`, an integer to initialize the pseudo-random number generator of the model: 
* the **input value**,`density `, of the model representing the _densities of trees_, 
* the **output value** of the model representing the number of burned trees.

######Selection function

------------------------------------------------

The selection function generate each time _500_ new XML input with random seed and fixed density of _59_.

```
import random
print "<inputs>"
print "<simulation>"
print "<name>fire</name>"
print "<loop>true</loop>"
print "<description>nota</description>"
print "<toolkit>netlogo</toolkit>"
print "</simulation>"
for i in range(500):
    print "<input id=\""+str(i)+"\""+ " rounds=\"1\"> <element variableName=\"step\"><long>5000</long></element>"
    value=random.uniform(-2147483648,2147483647);
    valueint=int(value)
    print "<element variableName=\"random-seed\"><long>"+str(valueint)+"</long></element>"
    print "<element variableName=\"density\"><double>59</double></element>"
    print "</input>"  
print "</inputs>"
```

######Evaluate function

------------------------------------------------
The evaluation function given the initial number of trees and the burned trees at the end of simulation returns the percentage of the burned trees.

```
public class Evaluation {

	public static void main(String[] args) throws SAXException, ParserConfigurationException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc;
		try {
			doc = dBuilder.parse(new File(args[0]));
			NodeList output = doc.getElementsByTagName("output");
			int id = Integer.parseInt( output.item(0).getAttributes().item(0).getNodeValue());
			NodeList c=doc.getElementsByTagName("param");
			Element ccc1=(Element) c.item(0);
			Element ccc2=(Element) c.item(1);
			Element ccc3=(Element) c.item(2);
			float iniz=Float.parseFloat(ccc1.getChildNodes().item(1).getTextContent());
			float density=Float.parseFloat(ccc2.getChildNodes().item(1).getTextContent());
			float burned=Float.parseFloat(ccc3.getChildNodes().item(1).getTextContent());
			float percentage=(burned/iniz)*100;
		/*
			System.out.print("id:"+id+";");
			System.out.print("Initial Tree:"+iniz+";");
			System.out.print("Burned tree:"+burned+";" );
			System.out.print("Burned percentage:"+percentage+";");
		 */
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	} 
}
```


###4. How to start the optimization process through SOF GUI
###5. How to download the results of the optimization process