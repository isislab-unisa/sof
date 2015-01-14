package it.isislab.scud.core.model.parameters.xsd.rating;

import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement(namespace="it.isislab.sof")
@XmlType(propOrder = { "input","output" ,"rates" })
public class Rating {
	
	@XmlElement(required=true)
	public Input input;
	@XmlElement(required=true)
	public Output output;
	@XmlElement(required=true)
	public List<Parameter> rates;
	@XmlAttribute(required=true)
	public String id;

//public static void main(String[] args) throws Exception {
//		
//		JAXBContext domain = JAXBContext.newInstance(Rating.class);
//
//		domain.generateSchema(new SchemaOutputResolver() {
//			@Override
//			public Result createOutput(String namespaceUri, String suggestedFileName)
//					throws IOException {
//				FileOutputStream out=new FileOutputStream(new File("xsd/rating.xsd"));
//				StreamResult result = new StreamResult(out);
//				result.setSystemId(suggestedFileName);
//				return result;
//			}
//		});
//	}	
}
