package core.model.parameters.xsd.elements;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement
public class Parameter {
	
	
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