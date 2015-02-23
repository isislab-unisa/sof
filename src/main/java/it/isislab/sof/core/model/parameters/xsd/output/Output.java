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
package it.isislab.sof.core.model.parameters.xsd.output;

import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;

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

@XmlType(propOrder = { "output_params" })
@XmlRootElement
public class Output {
	private int idInput=1; 
	
	/*@XmlElement(required=true)
	public Input inputparameters;*/
	@XmlElement(required=true, name="param")
	public List<Parameter> output_params;

	


    @XmlAttribute(name="inputID")
	public int getIdInput() {
		return idInput;
	}



	public void setIdInput(int id) {
		this.idInput = id;
	}
	
	
	@Override
	public String toString(){
		String line = "\t\t\t\tid: "+idInput+"\n";
		for(Parameter p : output_params)
			line+="\t\t\t\t"+p+"\n";
		return line;
	}

	public static void main(String[] args) throws Exception {

		JAXBContext domain = JAXBContext.newInstance(Output.class);

		domain.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName)
					throws IOException {
				FileOutputStream out=new FileOutputStream(new File("xsd/output.xsd"));
				StreamResult result = new StreamResult(out);
				result.setSystemId(suggestedFileName);
				return result;
			}
		});
	}	
}
