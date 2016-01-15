package it.isislab.sof.example.function.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An example class to generate evaluation file for SOF with Java language
 * At the end of a step of simulations, the evaluator extracts and prints 
 * all parameters contained into output.xml file with associated value 
 * from simulation 
 * 
 * @author Michele Carillo
 * @author Flavio Serrapica
 * @author Carmine Spagnuolo
 */
public class EvaluationFunctionExample{


	public static void main(String[] args) throws SAXException, ParserConfigurationException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc;

		/******************************************************************************************************************
		 **
		 * This code inserts in a HashTable<VariableName,VariableValue> all output parameters of simulation
		 *
		/******************************************************************************************************************/		
		try {
			doc = dBuilder.parse(new File(args[0]));

			NodeList params=doc.getElementsByTagName("param");
			Hashtable<String/*variableName*/, String/*variableValue*/> simulationOutputValues=new Hashtable<String, String>();
			int paramSize=params.getLength();
			
			for(int j=0; j<paramSize; j++){

				Node d= params.item(j);
				NamedNodeMap attrbsj=d.getAttributes();

				int attrbsSize= attrbsj.getLength();
				for(int k=0; k<attrbsSize; k++){
					Node f=attrbsj.item(k);
					//System.out.println(f.getNodeValue());
					NodeList list=d.getChildNodes();
					simulationOutputValues.put(f.getNodeValue(), list.item(1).getTextContent()); 

				}
			}
			/*********************************************************************************************************************/
			/*********************************************************************************************************************/			
			
			
			/**
			 * PRINT  OUTPUTS OF SIMULATION
			 * FORMAT  variableName:variableValue;
			 */
			for(String key : simulationOutputValues.keySet()){
				System.out.println(key+":"+simulationOutputValues.get(key)+";");
			}


/*****************************************************************************************************************************/

			/**
			 * You can define new output parameters by using value of simulation contained in   
			 * HashTable<nameVariable,valueVariable>
			 * calling simulationValue.get("nameVariable"); where nameVariable is the name of variabl
			 * You can find nameVariabile and its type into output.xml that you created
			 *
			 * 
			 * Example
			 * At the end of simulation (Fire of NetLogo) i want to calculate percentage of burned trees. 
			 * You must:  
			 *   -Define Name of new parameter. For Example BurnedPercentage 
			 * 	 -Calculate new parameter  
			 *   -Print new parameter into file in a correct SOF Evaluator File Format "ParameterName:ParameterValue;" 
			 */  

			/*For example, i want to calculate burned trees percentage at the end of a step of simulation, 
			 * i called this parameter burnedPercentage */
			
			/*Calculate new parameter burnedPercentage*/
			double percentage= (Double.parseDouble(simulationOutputValues.get("burned-trees"))*100)/Double.parseDouble(simulationOutputValues.get("initial-trees"));
			
			/*Print 'burnedPercentage:percentage;' in a correct format where percentage is the value */
			System.out.printf("burnedPercentage:%.2f;",percentage);	

		} catch (IOException e) {
			System.err.println("Error occurred "+e.getMessage());
		}

	}   
/*****************************************************************************************************************************/

}
