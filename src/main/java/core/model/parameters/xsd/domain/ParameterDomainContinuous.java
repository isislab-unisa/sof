package core.model.parameters.xsd.domain;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
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