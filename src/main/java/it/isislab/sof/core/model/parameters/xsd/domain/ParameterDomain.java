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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

public class ParameterDomain {

	
	
	private Object parameter;
	private String variable_name;
	
	private String type_ParameterDomainContinuous = "ParameterDomainContinuous";
	private String type_ParameterDomainDiscrete = "ParameterDomainDiscrete";
	private String type_ParameterDomainListString = "ParameterDomainListString";
	private String type_ParameterDomainListValues = "ParameterDomainListValues";

	@XmlElements(value = { 
            @XmlElement(required = true, nillable = false,name="discrete", 
                        type=ParameterDomainDiscrete.class),
            @XmlElement(required = true, nillable = false,name="continuous", 
                        type=ParameterDomainContinuous.class),
            @XmlElement(required = true, nillable = false,name="list_string", 
                        type=ParameterDomainListString.class),
            @XmlElement(required = true, nillable = false,name="list_values", 
                        type=ParameterDomainListValues.class),
    })
	public Object getparameter() {return parameter;}
	public void setparameter(Object parameter) {this.parameter = parameter;}

	@XmlAttribute(required = true)
	public String getvariable_name() {return variable_name;}
	public void setvariable_name(String variable_name) {this.variable_name = variable_name;}
	
	@XmlTransient
	public String gettype_ParameterDomainContinuous() {return type_ParameterDomainContinuous;}
	
	@XmlTransient
	public String gettype_ParameterDomainDiscrete() {return type_ParameterDomainDiscrete;}
	
	@XmlTransient
	public String gettype_ParameterDomainListString() {return type_ParameterDomainListString;}
	
	@XmlTransient
	public String gettype_ParameterDomainListValues() {return type_ParameterDomainListValues;}	
	
	@Override
	public String toString(){
		return "name: "+variable_name +"\n"+parameter.toString();
	}
	
} 