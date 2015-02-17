import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




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
			System.out.print("id:"+id+";");
			System.out.print("AlberiIniziali:"+iniz+";");
			System.out.print("AlberiBruciati:"+burned+";" );
			System.out.print("PercentualeBruciati:"+percentage+";");
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	} 
}
