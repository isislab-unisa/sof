package it.isislab.scud.core.model.parameters.xsd.elements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
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