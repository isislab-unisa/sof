package core.model.parameters.xsd.input;



import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import core.engine.hadoop.sshclient.utils.simulation.Simulation;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlType(propOrder = { "simulation", "input_list" })
@XmlRootElement
public class Inputs {

	
	private List<Input> input_list;
	private Simulation simulation;
	
	@XmlElement(required=true)
	public Simulation getsimulation() {return simulation;}
	public void setsimulation(Simulation simulation) {this.simulation = simulation;}
	
	@XmlElement(required=true, name="input")
	public List<Input> getinput_list() {return input_list;}
	public void setinput_list(List<Input> input_list) {this.input_list = input_list;}
	
	@Override
	public String toString(){
		String line ="";
		for(Input i: input_list)
			line+=i;
		return line;
	}
	
	
	public static void main(String[] args){
		
	}
	
}
