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
package it.isislab.scud.core.model.parameters.xsd.domain;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


public class ParameterDomainListValues {
	
	private List<Double> list;

	@XmlElement(required=true)
	public List<Double> getlist(){return list;}
	public void setlist(List<Double> list){this.list = list;}
	
	@Override
	public String toString() {
		String toPrint="";
		for(double d: list)
			toPrint+="\t\t"+d+"\n";	
		return toPrint;
	}
	
	
	
} 