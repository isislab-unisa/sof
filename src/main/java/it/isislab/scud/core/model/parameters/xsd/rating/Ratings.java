package it.isislab.scud.core.model.parameters.xsd.rating;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement(namespace="it.isislab.sof")
@XmlType(propOrder = { "ratings" })
public class Ratings {
	
	@XmlElement(required=true)
	public List<Rating> ratings;
	@XmlAttribute(required=true)
	public String id;

public static void main(String[] args) throws Exception {
		
		JAXBContext domain = JAXBContext.newInstance(Ratings.class);

		domain.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName)
					throws IOException {
				FileOutputStream out=new FileOutputStream(new File("xsd/ratings.xsd"));
				StreamResult result = new StreamResult(out);
				result.setSystemId(suggestedFileName);
				return result;
			}
		});
	}	
}
