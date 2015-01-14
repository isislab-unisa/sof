/**
 * Copyright 2014 Universit?? degli Studi di Salerno


   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   @author Michele Carillo, Serrapica Flavio, Raia Francesco
   */
package it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation;


import it.isislab.scud.core.exception.ParameterException;
import it.isislab.scud.core.model.parameters.xsd.input.Inputs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SimulationParser {
	/**
	 * convert Simulation object into xml file (simulations.xml) 
	 * @param s
	 * @param destinationPathFileName local filename destination
	 */
	public static void convertSimulationsToXML(Simulations s, String destinationPathFileName){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Simulations.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(s, new File(destinationPathFileName));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Simulations convertXMLToSimulations(String xmlFilePathName){
		JAXBContext context;
		Simulations s=null;
		try {
			context = JAXBContext.newInstance(Simulations.class);
			Unmarshaller um = context.createUnmarshaller();
			s = (Simulations) um.unmarshal(new FileReader(xmlFilePathName));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public static Simulations convertXmlListToSimulations(String xmlListDir){
		File dir = new File(xmlListDir);
		if(!dir.isDirectory()){
			System.err.println(xmlListDir+" is not a directory");
			return null;
		}
		Simulations sims = new Simulations();
		File[] ls = dir.listFiles();
		//for more files you have to parallelize it
		Arrays.sort(ls);
		for(File f: ls)
			sims.addSimulation(convertXMLToSimulation(f.getAbsolutePath()));
		
		return sims;
	}
	
	
	public static void convertSimulationToXML(Simulation s, String destinationPathFileName){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Simulation.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(s, new File(destinationPathFileName));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Simulation convertXMLToSimulation(String xmlFilename){
		JAXBContext context;
		Simulation s=null;
		try {
			context = JAXBContext.newInstance(Simulation.class);
			Unmarshaller um = context.createUnmarshaller();
			s = (Simulation) um.unmarshal(new FileReader(xmlFilename));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public static void main(String[] args){
		Simulation s = new Simulation(1+"");
		s.setAuthor("pasquale");
		RunnableFile f = new RunnableFile();
	
		f.setSimulation("/sof/sim-1/flock.jar");
		f.setRating("/sof/sim-1/description/rating/mamm.exe");
		f.setSelection("/sof/sim-1/description/selection/mammt.exe");
		s.setRunnableFile(f);
		s.setCreationTime();
		s.setState(Simulation.SUBMITTED);
		s.setDescription("oleeeeeeeeeeeeeeeeeeee");
		Runs r = new Runs();
		Loop l = new Loop();
		l.setId(1);
		l.setStartTime();
		l.setStatus(Loop.SUBMITTED);
		Inputs is = new Inputs();
		is = l.convertXMLInputToInput("/home/flavio/Scrivania/TestSSH/provaSim/mason_input.xml");
		l.setInputs(is);
		l.setStopTime();
		r.addLoop(l);
		s.setRuns(r);
		
		Simulations ls = new Simulations();
		ls.addSimulation(s);
		
		convertSimulationsToXML(ls, "simulations.xml");
		
		Simulations ls2 = convertXMLToSimulations("simulations.xml");
		for(Simulation ss : ls2.getSimulations()){	
			ss.setState(Simulation.FINISHED);
			ss.getRuns().getLoops().get(0).setStatus(Loop.FINISHED);
			System.out.println(ss);
		}
		convertSimulationsToXML(ls2, "simulations2.xml");
	}

}
