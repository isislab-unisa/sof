package core.model.parameters.xsd.output;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import core.model.parameters.xsd.input.Input;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement
public class Outputs{

	private List<Output> output_list;
	
	public List<Output> getOutput_list() {
		return output_list;
	}

	public void setOutput_list(List<Output> output_list) {
		this.output_list = output_list;
	}
	
	@Override
	public String toString(){
		String line ="";
		for(Output i: output_list)
			line+=i;
		return line;
	}
	
	/*public static void main(String[] args){
		Outputs os = new Outputs();
		
		ArrayList<Output> out_list = new ArrayList<Output>();
		
		int inputId =1;
		Output output=new Output();
		
		ParameterLong step_p=new ParameterLong();
       
        Parameter step=new Parameter();
        step.setparam(step_p);
        step.setvariable_name("step");
        
        ParameterLong numFlockers_p=new ParameterLong();
        
        Parameter numFlockers=new Parameter();
        numFlockers.setparam(numFlockers_p);
        numFlockers.setvariable_name("numFlockers");
        
        ParameterDouble cohesion_p=new ParameterDouble();
       
        Parameter cohesion=new Parameter();
        cohesion.setparam(cohesion_p);
        cohesion.setvariable_name("cohesion");
        
        
        ParameterDouble avoidance_p=new ParameterDouble();
     
        Parameter avoidance=new Parameter();
        avoidance.setparam(avoidance_p);
        avoidance.setvariable_name("avoidance");
        
        ParameterDouble randomness_p=new ParameterDouble();
    
        Parameter randomness=new Parameter();
        randomness.setparam(randomness_p);
        randomness.setvariable_name("randomness");
        
        ParameterDouble consistency_p=new ParameterDouble();
      
        Parameter consistency=new Parameter();
        consistency.setparam(consistency_p);
        consistency.setvariable_name("consistency");
        
        ParameterDouble momentum_p=new ParameterDouble();
     
        Parameter momentum=new Parameter();
        momentum.setparam(momentum_p);
        momentum.setvariable_name("momentum");
        
        ParameterDouble deadFlockerProbability_p=new ParameterDouble();
        
        Parameter deadFlockerProbability=new Parameter();
        deadFlockerProbability.setparam(deadFlockerProbability_p);
        deadFlockerProbability.setvariable_name("deadFlockerProbability");
        
        ParameterDouble neighborhood_p=new ParameterDouble();
       
        Parameter neighborhood=new Parameter();
        neighborhood.setparam(neighborhood_p);
        neighborhood.setvariable_name("neighborhood");
        
        
        ParameterDouble jump_p=new ParameterDouble();
      
        Parameter jump=new Parameter();
        jump.setparam(jump_p);
        jump.setvariable_name("jump");
        
        ArrayList<Parameter> parainput1=new ArrayList<Parameter>();
        parainput1.add(step);
        parainput1.add(numFlockers);
        parainput1.add(cohesion);
        parainput1.add(avoidance);
        parainput1.add(randomness);
        parainput1.add(consistency);
        parainput1.add(momentum);
        parainput1.add(deadFlockerProbability);
        parainput1.add(neighborhood);
        parainput1.add(jump);
        
		output.output_params = parainput1;
		
		out_list.add(output);
		
		
		inputId =2;
		output=new Output();
		output.setIdInput(inputId);
		step_p=new ParameterLong();
       
		step=new Parameter();
        step.setparam(step_p);
        step.setvariable_name("step");
        
        numFlockers_p=new ParameterLong();
        
        numFlockers=new Parameter();
        numFlockers.setparam(numFlockers_p);
        numFlockers.setvariable_name("numFlockers");
        
        cohesion_p=new ParameterDouble();
       
        cohesion=new Parameter();
        cohesion.setparam(cohesion_p);
        cohesion.setvariable_name("cohesion");
        
        
        avoidance_p=new ParameterDouble();
     
        avoidance=new Parameter();
        avoidance.setparam(avoidance_p);
        avoidance.setvariable_name("avoidance");
        
        randomness_p=new ParameterDouble();
    
        randomness=new Parameter();
        randomness.setparam(randomness_p);
        randomness.setvariable_name("randomness");
        
        consistency_p=new ParameterDouble();
      
        consistency=new Parameter();
        consistency.setparam(consistency_p);
        consistency.setvariable_name("consistency");
        
        momentum_p=new ParameterDouble();
     
        momentum=new Parameter();
        momentum.setparam(momentum_p);
        momentum.setvariable_name("momentum");
        
        deadFlockerProbability_p=new ParameterDouble();
        
        deadFlockerProbability=new Parameter();
        deadFlockerProbability.setparam(deadFlockerProbability_p);
        deadFlockerProbability.setvariable_name("deadFlockerProbability");
        
        neighborhood_p=new ParameterDouble();
       
        neighborhood=new Parameter();
        neighborhood.setparam(neighborhood_p);
        neighborhood.setvariable_name("neighborhood");
        
        
        jump_p=new ParameterDouble();
      
        jump=new Parameter();
        jump.setparam(jump_p);
        jump.setvariable_name("jump");
        
        parainput1=new ArrayList<Parameter>();
        parainput1.add(step);
        parainput1.add(numFlockers);
        parainput1.add(cohesion);
        parainput1.add(avoidance);
        parainput1.add(randomness);
        parainput1.add(consistency);
        parainput1.add(momentum);
        parainput1.add(deadFlockerProbability);
        parainput1.add(neighborhood);
        parainput1.add(jump);
        
		
		output.output_params = parainput1;
		
		out_list.add(output);
		
		os.setOutput_list(out_list);
		
		File f = new File("mason_outputs.xml");
        JAXBContext context;
		try {
			context = JAXBContext.newInstance(Outputs.class);
			Marshaller jaxbMarshaller = context.createMarshaller();
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        jaxbMarshaller.marshal(os, f);
	        jaxbMarshaller.marshal(os, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}*/
	
}
