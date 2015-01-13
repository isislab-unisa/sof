package core.model.parameters.xsd.domain;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
public class ParameterDomainListString {

	private List<String> list;
	
	@XmlElement(required=true)
	public List<String> getlist(){return list;}
	public void setlist(List<String> list){this.list = list;}
	
	@Override
	public String toString() {
		String toPrint="";
		for(String s: list)
			toPrint+="\t\t"+s+"\n";		
		return toPrint;
	}
	
	
} 