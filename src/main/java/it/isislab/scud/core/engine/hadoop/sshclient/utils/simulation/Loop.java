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
package it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation;

import it.isislab.scud.core.model.parameters.xsd.input.Inputs;
import it.isislab.scud.core.model.parameters.xsd.output.Output;
import it.isislab.scud.core.model.parameters.xsd.output.Outputs;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement
@XmlType(propOrder = {"status", "loopTime", "inputs", "outputs"})
public class Loop {

	public static String SUBMITTED="submitted";
	public static String RUNNING="running";
	public static String FINISHED="finished";


	private int id;

	private String status;

	private Time loopTime;

	private Inputs inputs;

	private Outputs outputs;

	public Loop(){this.loopTime = new Time();}

	public Loop(int id, String status, String initTime){
		this.id = id;
		this.status = status;
		this.loopTime = new Time(initTime);
	}

	public Loop(int id){
		this.id = id;
		this.loopTime = new Time();
	}

	@XmlAttribute
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	@XmlElement(name="time")
	public Time getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(Time loopTime) {
		this.loopTime = loopTime;
	}

	public String getStartTime(){
		return loopTime.getStartTime();
	}

	public String getStopTime(){
		return loopTime.getStopTime();
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStopTime(){
		this.loopTime.setStopTime();
	}

	public void setStartTime(){
		this.loopTime.setStartTime();
	}

	@XmlElement
	public Inputs getInputs() {
		return inputs;
	}

	public void setInputs(Inputs inputs) {
		this.inputs = inputs;
	}

	@XmlElement
	public Outputs getOutputs() {
		return outputs;
	}

	public void setOutputs(Outputs outputs) {
		this.outputs = outputs;
	}

	@Override
	public String toString(){
		String toReturn = 
				"id: "+id+"\n"
						+"\t\t\tstatus: "+status +"\n"
						+"\t\t\tstart: "+loopTime.getStartTime()+"\n"
						+"\t\t\tstop: "+loopTime.getStopTime()+"\n"
						+"\t\t\tInputs:\n"+inputs
						+"\t\t\tOutputs:\n";
		if(outputs==null)
			return toReturn;
		else
			return toReturn+=outputs+"\n";
	}



	public Inputs convertXMLInputToInput(String fileName){
		Inputs i = new Inputs();
		try {
			JAXBContext context = JAXBContext.newInstance(Inputs.class);
			Unmarshaller unmarshal = context.createUnmarshaller();
			i = (Inputs) unmarshal.unmarshal(new File(fileName));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		return i;
	}


	public Output convertXMLOutputToOutput(File outputFile){
		Output out = new Output();
		try {
			JAXBContext context = JAXBContext.newInstance(Output.class);
			Unmarshaller unmarshal = context.createUnmarshaller();
			out = (Output) unmarshal.unmarshal(outputFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		return out;
	}

}





@XmlRootElement(name="time")
@XmlType(propOrder = {"startTime", "stopTime"})
class Time{

	@XmlElement(name="start")
	private String startTime;

	@XmlElement(name="stop")
	private String stopTime;

	private long startlong,stoplong;

	public Time(){}

	public Time(String initTime){
		this.startTime = initTime;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime() {
		DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.startTime = date.format(new Date());
		this.startlong=System.currentTimeMillis();
	}


	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime() {
		DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.stopTime = date.format(new Date());
		this.stoplong=System.currentTimeMillis();
	}

	public String toString()
	{
		return (stoplong-startlong)+"";
	}

}