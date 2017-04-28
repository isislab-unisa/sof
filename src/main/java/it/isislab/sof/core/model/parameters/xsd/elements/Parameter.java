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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Parameter implements Serializable{
	
	
	private String type_ParameterDouble = "ParameterDouble";
	private String type_ParameterLong = "ParameterLong";
	private String type_ParameterString = "ParameterString";
	
	private Object param;
	private String variable_name;
	
	
	@XmlElements(value = { 
            @XmlElement(required = true, nillable = false,name="string", 
                        type=ParameterString.class),
            @XmlElement(required = true, nillable = false,name="double", 
                        type=ParameterDouble.class),
            @XmlElement(required = true, nillable = false,name="long", 
                        type=ParameterLong.class)
    })
	public Object getparam() {return param;}
	public void setparam(Object param) {this.param = param;}
	
	@XmlAttribute(required = true)
	public String getvariable_name() {return variable_name;}
	
	public void setvariable_name(String variable_name) {this.variable_name = variable_name;}
	
	
	@XmlTransient
	public String gettype_ParameterDouble() {return type_ParameterDouble;}
	
	@XmlTransient
	public String gettype_ParameterLong() {return type_ParameterLong;}
	
	@XmlTransient
	public String gettype_ParameterString() {return type_ParameterString;}
	
	@Override
	public String toString(){
		return variable_name+" -> "+param;
	}
} 