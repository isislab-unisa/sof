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
package it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	/*
	public static void main(String[] args) {
	
		String pt="/home/michele/Scaricati/SIM-d11937858d67fbce8c3af3e186a51799.xml";
		Simulation s=SimulationParser.convertXMLToSimulation(pt);
		System.out.println(s);
		
	}*/
	

}
