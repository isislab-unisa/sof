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
package core.engine.hadoop.sshclient.utils.simulation;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import core.model.parameters.xsd.elements.ParameterDouble;
import core.model.parameters.xsd.elements.ParameterLong;
import core.model.parameters.xsd.elements.ParameterString;


@XmlRootElement(name = "simulation")
@XmlType(propOrder = {"id","name","author","toolkit", "creationTime", "state", "description", "runs","runnableFile","loop","processName"},
namespace="it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation")
public class Simulation {
	
	public static final String CREATED="created";
	public static final String SUBMITTED="submitted";
	public static final String RUNNING="running";
	public static final String FINISHED="finished";
	public static final String KILLED="killed";
	public static final String ABORTED="aborted";
	
	
	private static final String type_toolkit_mason = "mason";
	private static final String type_toolkit_netlogo = "netlogo";
	private static final String type_toolkit_generic = "generic";
	
//	public static RGB CREATED_COLOR=new RGB(205,255,51);
//	public static RGB SUBMITTED_COLOR=new RGB(30,144,255);
//	public static RGB RUNNING_COLOR=new RGB(255,255,0);
//	public static RGB FINISCED_COLOR=new RGB(127,255,0);


	
	private String id;
	
	
	private String name;
	
	
	private String toolkit;
	
	@XmlElement(name="date")
	private String creationTime;
	
	
	
	
	private String state;
	
	
	private String description;
	
	private Runs runs;
	
	private RunnableFile runnableFile;
	
	
	private String author;
	
	
	private boolean loop;
	
	private String processName;
	

	public Simulation(String id, String date, String state, String description,boolean isLoop){
		this.id = id;
		this.creationTime = date;
		this.state = state;
		this.description = description;
		this.runs = new Runs();
		this.loop=isLoop;
	
	}
	
	public Simulation(String id, String name, String toolkit, String creationTime,
			String state, String description, String author, boolean isLoop) {
		super();
		this.id = id;
		this.name = name;
		this.toolkit = toolkit;
		this.creationTime = creationTime;
		this.state = state;
		this.description = description;
		this.author = author;
		this.runs = new Runs();
		this.loop=isLoop;

	}
	public String[] toarray()
	{
		String[] a=new String[8];
		a[0]=id;
		a[1]=name+"";
		a[2]=toolkit+"";
		a[3]=creationTime+"";
		a[4]=state+"";
		a[5]=description+"";
		a[6]=author+"";
		a[7]=runs+"";
		return a;
	}
	public String[] toarray_table()
	{
		String[] a=new String[5];
		a[0]=id;
		a[1]=name+"";
		a[2]=toolkit+"";
		a[3]=author+"";
		a[4]=state+"";
		
		return a;
	}

	public Simulation(){}

	public Simulation(String simID){
		this.id = simID;
		this.runs = new Runs();
	}
	
	@XmlAttribute(required=true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime() {
		DateFormat d = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.creationTime = d.format(new Date());
	}


	@XmlElement(required = true, nillable = false,name="state",type=String.class)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		
		this.state = state;
	}

	@XmlElement(required=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Runs getRuns() {
		return runs;
	}

	public void setRuns(Runs loop) {
		this.runs = loop;
	}
	
	@XmlElement(required=true, name="loop")
	public boolean getLoop() {
		return loop;
	}

	public void setLoop(boolean isLoop) {
		this.loop = isLoop;
	}

	@XmlAttribute(required=false)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@XmlElement(name="runnable")
	public RunnableFile getRunnableFile() {
		return runnableFile;
	}

	public void setRunnableFile(RunnableFile runnableFile) {
		this.runnableFile = runnableFile;
	}
	
    @XmlElement(required = true, nillable = false,name="toolkit", type=String.class)
	public String getToolkit() {
		return toolkit;
	}

	
	public void setToolkit(String toolkit) {
		this.toolkit = toolkit;
	}

	@XmlElement(required=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	@Override
	public String toString(){
		String s="\tid: "+id+"\n"
				+"\tname: "+name+"\n"
				+ "\tauthor: "+author+"\n"
				+runnableFile+"\n"
				+ "\tstart: "+creationTime+"\n"
				+ "\tdescription: "+description+"\n"
				+ "\tstate: "+state+"\n";
		s+="\t\tLoops:\n";
		if(runs != null)
			for (Loop l : runs.getLoops()) {
				s+="\t\t\t"+l.toString()+" ";
			}
		s+="\tprocessName: "+processName;
		return s;
	}
	
	
	@XmlTransient
	public String gettype_toolkit_mason() {return type_toolkit_mason;}
	
	@XmlTransient
	public String gettype_toolkit_netlogo() {return type_toolkit_netlogo;}
	
	@XmlTransient
	public String gettype_toolkit_generic() {return type_toolkit_generic;}
}
