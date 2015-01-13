package core.model.parameters.xsd.domain;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import core.engine.hadoop.sshclient.utils.simulation.Simulation;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement(namespace="it.isislab.sof")
@XmlType(propOrder = { "simulation", "param" })
public class Domain {

	@XmlElement(required=true)
	public List<ParameterDomain> param;
	@XmlElement(required=true)
	public Simulation simulation;

	public static void main(String[] args) throws Exception {
		
		JAXBContext domain = JAXBContext.newInstance(Domain.class);

		domain.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName)
					throws IOException {
				FileOutputStream out=new FileOutputStream(new File("xsd/domain.xsd"));
				StreamResult result = new StreamResult(out);
				result.setSystemId(suggestedFileName);
				return result;
			}
		});
	}	
}
