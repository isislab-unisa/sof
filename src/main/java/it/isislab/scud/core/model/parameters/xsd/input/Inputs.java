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
package it.isislab.scud.core.model.parameters.xsd.input;



import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.exception.ParameterException;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "simulation", "input_list" })
@XmlRootElement
public class Inputs {

	
	private List<Input> input_list;
	private Simulation simulation;
	
	@XmlElement(required=true)
	public Simulation getsimulation() {return simulation;}
	public void setsimulation(Simulation simulation) {this.simulation = simulation;}
	
	@XmlElement(required=true, name="input")
	public List<Input> getinput_list() {return input_list;}
	public void setinput_list(List<Input> input_list) {this.input_list = input_list;}
	
	@Override
	public String toString(){
		String line ="";
		for(Input i: input_list)
			line+=i;
		return line;
	}
	
	
	public static void main(String[] args){
		JAXBContext context;
		Inputs i = new Inputs();

		try {
			 context = JAXBContext.newInstance(Inputs.class);

			Unmarshaller unmarshal = context.createUnmarshaller();
			i = (Inputs) unmarshal.unmarshal(new File("/home/michele/Scrivania/aids/input.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
}
