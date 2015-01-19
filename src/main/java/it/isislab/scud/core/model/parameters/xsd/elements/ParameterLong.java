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
package it.isislab.scud.core.model.parameters.xsd.elements;
import javax.xml.bind.annotation.XmlValue;


public class ParameterLong implements ParameterType{
	
	private long value;

	@XmlValue
	public long getvalue() {return value;}

	public void setvalue(long value) {this.value = value;}
	
	public String toString()
	{
		return value+"";
	}
	public String getType()
	{
		return "long";
	}

} 