package core.model.parameters.xsd.domain;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
public class ParameterDomainListValues {
	
	private List<Double> list;

	@XmlElement(required=true)
	public List<Double> getlist(){return list;}
	public void setlist(List<Double> list){this.list = list;}
	
	@Override
	public String toString() {
		String toPrint="";
		for(double d: list)
			toPrint+="\t\t"+d+"\n";	
		return toPrint;
	}
	
	
	
} 