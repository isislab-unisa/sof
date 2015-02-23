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

package it.isislab.sof.core.model.parameters.xsd.elements;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "name","one_shot", "note","toolkit" })
public class Simulation_old {
	
	private boolean one_shot;
	private String name;
	private String description;
	private String author;
	private String toolkit;
	
	private static String type_toolkit_mason = "mason";
	private static String type_toolkit_netlogo = "netlogo";
	private static String type_toolkit_other = "other";
	
	@XmlElement(required=true, name="loop")
	public Boolean isone_shot() {return one_shot;}
	public void modifyone_shot(Boolean one_shot) {this.one_shot = one_shot;}
	
	@XmlElement(required=true)
	public String getname() {return name;}
	public void setname(String name) {this.name = name;}
	
	@XmlElement(required=true)
	public String getdescription() {return description;}
	public void setnote(String note) {this.description = note;}
	
	@XmlAttribute
	public String getauthor() {return author;}
	public void setauthor(String author) {this.author = author;}
	
	@XmlElement(required=true)
	public String gettoolkit() {return toolkit;}
	public void settoolkit(String toolkit) {this.toolkit = toolkit;}
	
	@XmlTransient
	public String gettype_toolkit_mason() {return type_toolkit_mason;}
	
	@XmlTransient
	public String gettype_toolkit_netlogo() {return type_toolkit_netlogo;}
	
	@XmlTransient
	public String gettype_toolkit_other() {return type_toolkit_other;}
		
}	
