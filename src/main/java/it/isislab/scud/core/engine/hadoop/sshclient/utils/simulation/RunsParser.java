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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class RunsParser {

	public static void convertRunsToXML(Runs s, String destinationPathFileName){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Runs.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(s, new File(destinationPathFileName));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Runs convertXMLToRuns(String xmlFilePathName){
		JAXBContext context;
		Runs s=null;
		try {
			context = JAXBContext.newInstance(Runs.class);
			Unmarshaller um = context.createUnmarshaller();
			s = (Runs) um.unmarshal(new FileReader(xmlFilePathName));
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
		
		Runs r = new Runs();
		Loop l = new Loop();
		l.setId(r.getLoops().size()+1);
		l.setStatus(Loop.RUNNING);
		l.setStartTime();
		l.setStopTime();
		r.addLoop(l);
		
		RunsParser.convertRunsToXML(r,"runs.xml");
		
		Runs r2 =RunsParser.convertXMLToRuns("runs.xml");
		for(Loop ll: r2.getLoops()){
			System.out.println(ll.getId()+ " "
					+ll.getStatus()+" "+ll.getStartTime()+" "
					+ll.getStopTime());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ll.setStopTime();
		}
		
		RunsParser.convertRunsToXML(r2, "runs2.xml");
	}
	
}
