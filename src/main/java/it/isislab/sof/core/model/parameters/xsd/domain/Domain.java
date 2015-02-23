/**
 * 
 * Copyright ISISLab, 2015 Università degli Studi di Salerno.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License. You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 * @author Michele Carillo michelecarillo@gmail.com
 * @author Flavio Serrapica flavioserrapica@gmail.com
 * @author Carmine Spagnuolo spagnuolocarmine@gmail.com
 *
 */
package it.isislab.sof.core.model.parameters.xsd.domain;

import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
		});
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
        JAXBContext context= JAXBContext.newInstance(it.isislab.sof.core.model.parameters.xsd.domain.Domain.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(d, f);
 
		Unmarshaller unmarshall=context.createUnmarshaller();
		Domain dd= (Domain) unmarshall.unmarshal(new File("domain.xml"));
		System.out.println(dd.param);*/
	}
}
