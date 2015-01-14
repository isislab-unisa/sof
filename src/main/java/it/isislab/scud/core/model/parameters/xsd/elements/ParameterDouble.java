package it.isislab.scud.core.model.parameters.xsd.elements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
public class ParameterDouble implements ParameterType{
	
	private double value;

	@XmlValue
	public double getvalue() {return value;}

	public void setvalue(double value) {this.value = value;}
	
	public String toString()
	{
		return ""+value;
	}
	public String getType()
	{
		return "double";
	}
} 