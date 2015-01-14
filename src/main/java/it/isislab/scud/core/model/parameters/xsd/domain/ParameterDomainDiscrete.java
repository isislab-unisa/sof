package it.isislab.scud.core.model.parameters.xsd.domain;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
public class ParameterDomainDiscrete {

	private long min;
	private long max;
	private long increment;
	
	@XmlAttribute(required=true)
	public long getmin() {return min;}
	@XmlAttribute(required=true)
	public long getmax() {return max;}
	@XmlAttribute
	public long getincrement() {return increment;}
	
	public void setmin(long min) {this.min = min;}
	public void setmax(long max) {this.max = max;}
	public void setincrement(long increment) {this.increment = increment;}
	
	@Override
	public String toString(){
		return "\t\tmin: "+min+"\n"
				+ "\t\tmax: "+max+"\n"
				+ "\t\tincrement: "+increment+"\n";
	}
}