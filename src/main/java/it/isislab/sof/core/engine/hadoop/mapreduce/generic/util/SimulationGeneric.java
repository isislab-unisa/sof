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
package it.isislab.sof.core.engine.hadoop.mapreduce.generic.util;

import it.isislab.sof.core.engine.hadoop.utils.XmlToText;
import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.sof.core.model.parameters.xsd.output.Output;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;

public class SimulationGeneric {

	private Configuration conf=null;

	/**
	 * Method that run a NetLogo Simulation
	 * 
	 * @param program_path
	 * @param input
	 * @param sim_input_path
	 * @param sim_output_path
	 * @param sim_home
	 * @param output
	 * @param configuration
	 * @throws Exception
	 */
	public void run(String program_path,String input,
			String sim_input_path, String sim_output_path, String sim_home,
			OutputCollector<Text, Text> output,
			Configuration configuration) throws Exception{


		this.conf=configuration;
		String SIMULATION_NAME=conf.get("simulation.name");
		String SIMULATION_HOME=conf.get("simulation.home");
		String SIM_OUTPUT_MAPPER=conf.get("simulation.executable.output");
		String AUTHOR=conf.get("simulation.executable.author");
		String DESCRIPTION=conf.get("simulation.executable.description");


		/*HeadlessWorkspace workspace =
				HeadlessWorkspace.newInstance() ;
		workspace.open(program_path);*/


		//HashMap<String,String> inputSimulation = new HashMap<String,String>();

		String line = input;


		String[] aparam = line.split(";");
		String[] inputSimulation = new String[aparam.length-2];
		String[] couple=aparam[0].split(":");
		int idInputSimulation=Integer.parseInt(couple[1]);
		couple=aparam[1].split(":");
		int rounds = Integer.parseInt(couple[1]);
		for(int i=0; i<inputSimulation.length;i++){
			couple = aparam[i+2].split(":");
			inputSimulation[i]=couple[1];
		}

		
		String output_template=conf.get("simulation.description.output.domain");
		//converte il file output.xml con i soli campi in un'unica stringa da processare 
		String output_string_vars=XmlToText.convertOutputXmlIntoText(conf, output_template, idInputSimulation);


		ArrayList<String> outputSimulation =new ArrayList<String>();
		String aparam1 []= output_string_vars.split(";");
		for( int i=0; i<aparam1.length;i++){
			String[] couple2 = aparam1[i].split(":");
			outputSimulation.add(couple2[0]);
		}
		
		
		File f = new File(program_path);
		f.setExecutable(true);

		Process process;
         
		String sim_interpreter_path=conf.get("simulation.interpreter.genericsim");
		
		String command=""+sim_interpreter_path+" ";
		//command +=program_path.endsWith(".jar")?System.getProperty("java.home")+"/bin/java -jar "+program_path:program_path;
		if(program_path.endsWith(".jar"))
		command+="-jar ";
		
		command+=program_path;
		
		for(int i=0;i<inputSimulation.length;i++ )
			command+=" "+inputSimulation[i];

		HashMap<String, ArrayList<String>> output_collection = new HashMap<String, ArrayList<String>>();
		HashMap<String,String> ovs = new HashMap<String,String>();
		String[] outValues = null;
		String s;
		String tmp;
		BufferedReader stdInput;
		for(int r =0; r<rounds; r++){
			process = Runtime.getRuntime().exec(command);
			process.waitFor();

			stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			// read the output from the command
			s = "";
			tmp = null;
			while ((tmp = stdInput.readLine()) != null) {

				s+=tmp;
			}
			stdInput.close();

			ovs = new HashMap<String,String>();
			outValues = s.split(";");

			for(int i=0; i<outValues.length;i++){
				couple = outValues[i].split(":");
				ovs.put(couple[0], couple[1]);
			}
			
			//Collect OUTPUTs
			for(String field : outputSimulation){
				if(output_collection.containsKey(field))
					output_collection.get(field).add(ovs.get(field));
				else{
					ArrayList<String> l = new ArrayList<String>();
					l.add(ovs.get(field));
					output_collection.put(field, l);
				}
			}

		}		
		
		String inOutput="";
		/*for( int i=0; i<aparam1.length;i++){
			String[] couple2 = aparam1[i].split(":");
			inOutput+=couple2[0]+":"+outputSimulation.get(couple2[0])+";";
		}*/
		
		for(String field : output_collection.keySet()){
				inOutput+=field+":"+getAVG(output_collection.get(field),rounds)+";";
		}
		

		Path file_output=null;
		int id = (new String(inOutput+""+System.currentTimeMillis())).hashCode();

		//generate an output file from input field of simulation, that contains input parameters  : format --> input(param:param.val;...;) and 
		// output parameters of simulations:  format--> inOutput  (param:var.val;...;)
		file_output=generateOutput(input, inOutput, SIM_OUTPUT_MAPPER, id, idInputSimulation, SIMULATION_NAME, AUTHOR, DESCRIPTION, SIMULATION_HOME);

		output.collect(new Text(file_output.toString()), new Text(""));
		//output.collect(new Text(input), new Text(inOutput));




	}


	private String getAVG(ArrayList<String> arrayList, int rounds) {

		try{
			long a = Long.parseLong(arrayList.get(0));
			for (int i = 1; i < arrayList.size(); i++) {
				a+=Long.parseLong(arrayList.get(i));
			}
			return ""+(long)Math.ceil(a/rounds);

		}catch(Exception e1){
			try{
				double a = Double.parseDouble(arrayList.get(0));
				for (int i = 1; i < arrayList.size(); i++) {
					a+=Double.parseDouble(arrayList.get(i));
				}
				return ""+a/rounds;
			}catch(Exception e2){
				return getMaxOccurenceString(arrayList);

			}

		}

	}


	public static String getMaxOccurenceString(List<String> myList){

		Map<String, AtomicInteger> dictionary = new HashMap<String, AtomicInteger>();
		int max=0;	   
		String maxKey="";

		for(String x: myList){
			if(dictionary.containsKey(x))
				dictionary.get(x).incrementAndGet();
			else
				dictionary.put(x, new AtomicInteger(1));
		}

		for(Entry<String, AtomicInteger> x :dictionary.entrySet()) {
			if(x.getValue().get()>=max){
				max=x.getValue().get();
				maxKey=x.getKey();
			}
		}

		return maxKey;
	}


	/**
	 * * Generate output resume of simulation 
	 * in a file Xml  
	 * 
	 * @param inputSimulation
	 * @param outputSimulation
	 * @param SIM_OUTPUT_MAPPER
	 * @param id
	 * @param SIMULATION_NAME
	 * @param AUTHOR
	 * @param NOTE
	 * @param SIMULATION_HOME
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	private Path generateOutput(String inputSimulation, 
			String outputSimulation,
			String SIM_OUTPUT_MAPPER,
			int id,
			int idInputSimulation,
			String SIMULATION_NAME,
			String AUTHOR, 
			String NOTE,
			String SIMULATION_HOME) throws JAXBException, IOException {


		/*	Simulation sim =new Simulation();
		sim.setauthor(AUTHOR);
		sim.setname(SIMULATION_NAME);
		sim.setnote(NOTE);
		sim.settoolkit("NETLOGO");
		 */

		Output output=new Output();
		output.setIdInput(idInputSimulation);

		ArrayList<Parameter> paramsOutput=new ArrayList<Parameter>();

		String valOutp=outputSimulation;

		Object valobjOutp=null;
		String[] parametri=valOutp.split(";");
		for(String st:  parametri){
			String[] couple=st.split(":");
			try{
				ParameterLong dvalOutLong=new ParameterLong();
				dvalOutLong.setvalue(Long.parseLong(couple[1]));
				valobjOutp=dvalOutLong;
			}catch(Exception e1){
				try{
					ParameterDouble dvalOutDouble=new ParameterDouble();
					dvalOutDouble.setvalue(Double.parseDouble(couple[1]));
					valobjOutp=dvalOutDouble;

				}catch(Exception e2){
					ParameterString dvalOutString=new ParameterString();
					dvalOutString.setvalue(couple[1]);
					valobjOutp=dvalOutString;

				}				
			}
			Parameter paramOut=new Parameter();
			paramOut.setparam(valobjOutp);
			paramOut.setvariable_name(couple[0]);
			paramsOutput.add(paramOut);
		}




		output.output_params=paramsOutput;

		FileSystem fs=FileSystem.get(conf);
		FSDataOutputStream out=fs.create(new Path(SIM_OUTPUT_MAPPER+"/OUTPUT"+id+".xml"));

		JAXBContext context= JAXBContext.newInstance(Output.class);
		Marshaller jaxbMarshaller = context.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(output, out);
		out.close();

		return new Path(SIM_OUTPUT_MAPPER+"/OUTPUT"+id+".xml");	}

	/**
	 * Metodo supporto
	 * Create an id 

	private String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}*/


}
