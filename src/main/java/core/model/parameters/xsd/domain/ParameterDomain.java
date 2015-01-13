package core.model.parameters.xsd.domain;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
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