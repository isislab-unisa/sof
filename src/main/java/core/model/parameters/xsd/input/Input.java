package core.model.parameters.xsd.input;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import core.model.parameters.xsd.elements.Parameter;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement
public class Input {
	
	@XmlElement(required=true, name="element")
	public List<Parameter> param_element;
	@XmlAttribute(required=true)
	public int id;

	@Override
	public String toString(){
		String line = "\t\t\t\tid: "+id+"\n";
		for(Parameter p : param_element)
			line+="\t\t\t\t"+p+"\n";
		return line;
	}
	
	public static void main(String[] args) throws Exception {
		
		JAXBContext domain = JAXBContext.newInstance(Input.class);
//		Domain d=domain.createUnmarshaller().unmarshal(oggetto_to_unmarshall);
		domain.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName)
					throws IOException {
				FileOutputStream out=new FileOutputStream(new File("xsd/input.xsd"));
				StreamResult result = new StreamResult(out);
				result.setSystemId(suggestedFileName);
				return result;
			}
		});
	}	
}
