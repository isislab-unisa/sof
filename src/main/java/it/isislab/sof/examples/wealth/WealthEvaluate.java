package it.isislab.sof.examples.wealth;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WealthEvaluate {

	public static void main(String[] args) throws SAXException, ParserConfigurationException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc;
		try {
			doc = dBuilder.parse(new File(args[0]));
			NodeList output = doc.getElementsByTagName("output");
			int id = Integer.parseInt( output.item(0).getAttributes().item(0).getNodeValue());
			NodeList c=doc.getElementsByTagName("param");

			HashMap<String, ArrayList<String>> values = new HashMap<>();
			for(int i = 0; i<c.getLength(); i++){
				Element e =(Element) c.item(i);
				String output_key = e.getAttribute("variableName");
				if(values.containsKey(output_key))
					values.get(output_key).add(e.getChildNodes().item(1).getTextContent());
				else{
					ArrayList<String> vals = new ArrayList<>();
					vals.add(e.getChildNodes().item(1).getTextContent());
					values.put(output_key, vals);	
				}
			}
			System.out.print("id:"+id+";");
			String vals="";

			for (String key : values.keySet()) {
				for (String val : values.get(key)) {
					vals+=val+",";
				}
				vals=vals.substring(0,vals.lastIndexOf(",")) ;
				System.out.print(key+":"+vals+";");
			}



			//Element ccc1=(Element) c.item(0);
			/*float gindex=Float.parseFloat(ccc1.getChildNodes().item(1).getTextContent());
			System.out.print("id:"+id+";");
			System.out.println("giniinxed:"+gindex+";");*/

		} catch (IOException e) {
			e.printStackTrace();
		}


	} 

}
