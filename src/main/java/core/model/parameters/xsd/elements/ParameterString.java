package core.model.parameters.xsd.elements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
public class ParameterString implements ParameterType{
	
	private String value;

	@XmlValue
	public String getvalue() {return value;}

	public void setvalue(String value) {this.value = value;}
	
	public String toString()
	{
		return value;
	}
	public String getType()
	{
		return "string";
	}

} 