package it.isislab.scud.core.model.parameters.xsd.test;

import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
public class GenerateXMLOutput {
	public static void main(String[] args) throws JAXBException, IOException 
    {
	        
		int inputId =1;
		Output output=new Output();
		output.setIdInput(0);
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
		
	      

        File f = new File("mason_output.xml");
        JAXBContext context= JAXBContext.newInstance(it.isislab.scud.core.model.parameters.xsd.output.Output.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(output, f);
        jaxbMarshaller.marshal(output, System.out);
    }
}
