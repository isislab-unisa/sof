package it.isislab.scud.core.model.parameters.xsd.domain;



import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.hadoop.fs.Path;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement
@XmlType(propOrder = { "simulation", "param" })
public class Domain {

	@XmlElement(required=true)
	public List<ParameterDomain> param;
	@XmlElement(required=true)
	public Simulation simulation;

	public static void main(String[] args) throws Exception {
	/*	
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
		});*/
		Simulation s = new Simulation();
		s.setAuthor("Flavio");
		s.setToolkit("generic");
		s.setDescription("Simulated annealing java example");
		s.setName("SimAnnealing");
		
		Domain d = new Domain();
		d.simulation = s;
		ArrayList<ParameterDomain> pd = new ArrayList<>();
		ParameterDomainContinuous tmp = new ParameterDomainContinuous();
		tmp.setmin(1000);
		tmp.setmax(100000);
		tmp.setincrement(10);
		ParameterDomain p = new ParameterDomain();
		p.setvariable_name("temp");
		p.setparameter(tmp);
		pd.add(p);
		
		ParameterDomainContinuous coolingRate = new ParameterDomainContinuous();
		tmp.setmin(0.0001);
		tmp.setmax(100000);
		tmp.setincrement(10);
		p = new ParameterDomain();
		p.setvariable_name("coolingRate");
		p.setparameter(tmp);
		pd.add(p);
		
		d.param = pd;
		
		JAXBContext domain = JAXBContext.newInstance(Domain.class);
		File f = new File("domain.xml");
        JAXBContext context= JAXBContext.newInstance(it.isislab.scud.core.model.parameters.xsd.domain.Domain.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(d, f);
 
		Unmarshaller unmarshall=context.createUnmarshaller();
		Domain dd= (Domain) unmarshall.unmarshal(new File("domain.xml"));
		System.out.println(dd.param);
	}
}
