/**
 * 
 * Copyright ISISLab, 2015 Università degli Studi di Salerno.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License. You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 * @author Michele Carillo michelecarillo@gmail.com
 * @author Flavio Serrapica flavioserrapica@gmail.com
 * @author Carmine Spagnuolo spagnuolocarmine@gmail.com
 *
 */
package it.isislab.scud.core.model.parameters.xsd.test;

import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Loop;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.RunnableFile;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Runs;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.input.Inputs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GenerateXMLInput {
	public static void main(String[] args) throws JAXBException, IOException 
    {
		List<Input> input_list = new ArrayList<Input>();
        
        Input i1 =new Input();
        i1.id=1;
        
        ParameterLong step_p=new ParameterLong();
        step_p.setvalue(1000);
        Parameter step=new Parameter();
        step.setparam(step_p);
        step.setvariable_name("step");
        
        ParameterLong numFlockers_p=new ParameterLong();
        numFlockers_p.setvalue(2000);
        Parameter numFlockers=new Parameter();
        numFlockers.setparam(numFlockers_p);
        numFlockers.setvariable_name("numFlockers");
        
        ParameterDouble cohesion_p=new ParameterDouble();
        cohesion_p.setvalue(1.0);
        Parameter cohesion=new Parameter();
        cohesion.setparam(cohesion_p);
        cohesion.setvariable_name("cohesion");
        
        
        ParameterDouble avoidance_p=new ParameterDouble();
        avoidance_p.setvalue(1.0);
        Parameter avoidance=new Parameter();
        avoidance.setparam(avoidance_p);
        avoidance.setvariable_name("avoidance");
        
        ParameterDouble randomness_p=new ParameterDouble();
        randomness_p.setvalue(1.0);
        Parameter randomness=new Parameter();
        randomness.setparam(randomness_p);
        randomness.setvariable_name("randomness");
        
        ParameterDouble consistency_p=new ParameterDouble();
        consistency_p.setvalue(1.0);
        Parameter consistency=new Parameter();
        consistency.setparam(consistency_p);
        consistency.setvariable_name("consistency");
        
        ParameterDouble momentum_p=new ParameterDouble();
        momentum_p.setvalue(1.0);
        Parameter momentum=new Parameter();
        momentum.setparam(momentum_p);
        momentum.setvariable_name("momentum");
        
        ParameterDouble deadFlockerProbability_p=new ParameterDouble();
        deadFlockerProbability_p.setvalue(1.0);
        Parameter deadFlockerProbability=new Parameter();
        deadFlockerProbability.setparam(deadFlockerProbability_p);
        deadFlockerProbability.setvariable_name("deadFlockerProbability");
        
        ParameterDouble neighborhood_p=new ParameterDouble();
        neighborhood_p.setvalue(1.0);
        Parameter neighborhood=new Parameter();
        neighborhood.setparam(neighborhood_p);
        neighborhood.setvariable_name("neighborhood");
        
        
        ParameterDouble jump_p=new ParameterDouble();
        jump_p.setvalue(1.0);
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
        
        
        

        i1.param_element=parainput1;
        input_list.add(i1);
        
        
        Input i2 =new Input();
        i1.id=2;
        
        step_p=new ParameterLong();
        step_p.setvalue(1000);
        step=new Parameter();
        step.setparam(step_p);
        step.setvariable_name("step");
        
        numFlockers_p=new ParameterLong();
        numFlockers_p.setvalue(2000);
        numFlockers=new Parameter();
        numFlockers.setparam(numFlockers_p);
        numFlockers.setvariable_name("numFlockers");
        
        cohesion_p=new ParameterDouble();
        cohesion_p.setvalue(1.0);
        cohesion=new Parameter();
        cohesion.setparam(cohesion_p);
        cohesion.setvariable_name("cohesion");
        
        
        avoidance_p=new ParameterDouble();
        avoidance_p.setvalue(1.0);
        avoidance=new Parameter();
        avoidance.setparam(avoidance_p);
        avoidance.setvariable_name("avoidance");
        
        randomness_p=new ParameterDouble();
        randomness_p.setvalue(1.0);
        randomness=new Parameter();
        randomness.setparam(randomness_p);
        randomness.setvariable_name("randomness");
        
        consistency_p=new ParameterDouble();
        consistency_p.setvalue(1.0);
        consistency=new Parameter();
        consistency.setparam(consistency_p);
        consistency.setvariable_name("consistency");
        
        momentum_p=new ParameterDouble();
        momentum_p.setvalue(1.0);
        momentum=new Parameter();
        momentum.setparam(momentum_p);
        momentum.setvariable_name("momentum");
        
        deadFlockerProbability_p=new ParameterDouble();
        deadFlockerProbability_p.setvalue(1.0);
        deadFlockerProbability=new Parameter();
        deadFlockerProbability.setparam(deadFlockerProbability_p);
        deadFlockerProbability.setvariable_name("deadFlockerProbability");
        
        neighborhood_p=new ParameterDouble();
        neighborhood_p.setvalue(1.0);
        neighborhood=new Parameter();
        neighborhood.setparam(neighborhood_p);
        neighborhood.setvariable_name("neighborhood");
        
        
        jump_p=new ParameterDouble();
        jump_p.setvalue(1.0);
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
        
        i2.param_element=parainput1;
        
        input_list.add(i2);
        
        Simulation s=new Simulation();
        s.setId(1+"");
        s.setName("flockers");
        s.setAuthor("pippo");
        s.setDescription("test descrizione");
        s.setToolkit("mason");
        s.setState(s.CREATED);
        s.setCreationTime();
        RunnableFile rf = new RunnableFile();
        rf.setBashCommandForRunnableFunction("aaaaaa");
        rf.setRating("bbbbb");
        rf.setSelection("ccccccc");
        rf.setSimulation(s.getName());
        s.setRunnableFile(new RunnableFile());
        s.setLoop(true);
        Runs r = new Runs();
        r.addLoop(new Loop());
        s.setRuns(r);
        
        
        Inputs inputs = new Inputs();
        inputs.setinput_list(input_list);
        inputs.setsimulation(s);
        

        File f = new File("test/input_mason.xml");
        f.createNewFile();
        JAXBContext context= JAXBContext.newInstance(it.isislab.scud.core.model.parameters.xsd.input.Inputs.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(inputs, f);
        jaxbMarshaller.marshal(inputs, System.out);
    }
}
