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
import javax.xml.bind.annotation.XmlAttribute;


public class ParameterDomainContinuous {
	
	private double min;
	private double max;
	private double increment;
	
	@XmlAttribute(required=true)
	public double getmin() {return min;}
	@XmlAttribute(required=true)
	public double getmax() {return max;}
	@XmlAttribute
	public double getincrement() {return increment;}
	
	public void setmin(double min) {this.min = min;}
	public void setmax(double max) {this.max = max;}
	public void setincrement(double increment) {this.increment = increment;}
	
	@Override
	public String toString() {
		return "\t\tmin: "+min+"\n"
				+ "\t\tmax: "+max+"\n"
				+ "\t\tincrement: "+increment+"\n";
	}
	
	
} 