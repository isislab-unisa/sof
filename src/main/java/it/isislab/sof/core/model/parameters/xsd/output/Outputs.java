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


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement
public class Outputs{

	private List<Output> output_list;
	
	public List<Output> getOutput_list() {
		return output_list;
	}

	public void setOutput_list(List<Output> output_list) {
		this.output_list = output_list;
	}
	
	@Override
	public String toString(){
		String line ="";
		if(output_list==null)
			return line;
		for(Output i: output_list)
			line+=i;
		return line;
	}
	
}
