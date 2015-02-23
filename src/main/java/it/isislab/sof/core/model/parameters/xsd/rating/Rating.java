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
package it.isislab.sof.core.model.parameters.xsd.rating;

import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.input.Input;
import it.isislab.sof.core.model.parameters.xsd.output.Output;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
