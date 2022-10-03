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
package it.isislab.sof.core.engine.hadoop.mapreduce.mason.util;

import it.isislab.sof.core.engine.hadoop.utils.XmlToText;
import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.sof.core.model.parameters.xsd.output.Output;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.jar.Manifest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;

import sim.engine.SimState;


public class SimulationMASON {

	Configuration conf=null;

	public static final Pattern MATCH_ANY = Pattern.compile(".*");

	public static void unJar(File jarFile, File toDir) throws IOException {
		unJar(jarFile, toDir, MATCH_ANY);
	}

	public static void unJar(File jarFile, File toDir, Pattern unpackRegex)
			throws IOException {
		JarFile jar = new JarFile(jarFile);
		try {
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = (JarEntry)entries.nextElement();
				if (!entry.isDirectory() &&
						unpackRegex.matcher(entry.getName()).matches()) {
					InputStream in = jar.getInputStream(entry);
					try {
						File file = new File(toDir, entry.getName());
						if(!file.toPath().normalize().startsWith(toDir.toPath().normalize())) {
							throw new IOException("Bad zip entry");
						}
						ensureDirectory(file.getParentFile());
						OutputStream out = new FileOutputStream(file);
						try {
							IOUtils.copyBytes(in, out, 8192, false);
						} finally {
							out.close();
						}
					} finally {
						in.close();
					}
				}
			}
		} finally {
			jar.close();
		}
	}


	private static void ensureDirectory(File dir) throws IOException {
		if (!dir.mkdirs() && !dir.isDirectory()) {
			throw new IOException("Mkdirs failed to create " +
					dir.toString());
		}
	}

	/**
	 * Method that run a Mason Simulation
	 * 
	 * @param program_path
	 * @param input
	 * @param sim_input_path
	 * @param sim_output_path
	 * @param sim_home
	 * @param output
	 * @param conf1
	 * @throws Exception
	 */
	public void run(String program_path,String input,
			String sim_input_path, String sim_output_path, String sim_home,
			OutputCollector<Text, Text> output,
			Configuration conf1) throws Exception
	{

		conf=conf1;
		String SIMULATION_NAME=conf.get("simulation.name");
		String SIMULATION_HOME=conf.get("simulation.home");
		String SIM_OUTPUT_MAPPER=conf.get("simulation.executable.output");
		String AUTHOR=conf.get("simulation.executable.author");
		String DESCRIPTION=conf.get("simulation.executable.description");



		String fileName = program_path;
		File file = new File(fileName);
		String mainClassName = null;

		JarFile jarFile;

		jarFile = new JarFile(fileName);


		Manifest manifest = jarFile.getManifest();
		if (manifest != null) {
			mainClassName = manifest.getMainAttributes().getValue(Attributes.Name.MAIN_CLASS);
		}
		jarFile.close();

		mainClassName = mainClassName.replaceAll("/", ".");

		File tmpDir = new File(new Configuration().get("hadoop.tmp.dir"));
		ensureDirectory(tmpDir);

		final File workDir = File.createTempFile("hadoop-unjar", "", tmpDir);
		if (!workDir.delete()) {
			System.err.println("Delete failed for " + workDir);
			System.exit(-1);
		}
		ensureDirectory(workDir);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				FileUtil.fullyDelete(workDir);
			}
		});

		unJar(file, workDir);

		ArrayList<URL> classPath = new ArrayList<URL>();
		classPath.add(new File(workDir+"/").toURI().toURL());
		classPath.add(file.toURI().toURL());
		classPath.add(new File(workDir, "classes/").toURI().toURL());
		File[] libs = new File(workDir, "lib").listFiles();
		File fileSimState=new File("sim.engine.SimState");
		classPath.add(fileSimState.toURI().toURL());


		if (libs != null) {

			for (int i = 0; i < libs.length; i++) {
				classPath.add(libs[i].toURI().toURL());

			}
		}

		ClassLoader loader =
				new URLClassLoader(classPath.toArray(new URL[0]));

		Thread.currentThread().setContextClassLoader(loader);

		Class<?> mainClass = Class.forName(mainClassName, true, loader);


		Constructor c = mainClass.getConstructor(long.class);
		Object obj = c.newInstance(new Object[]{System.currentTimeMillis()});

		HashMap<String,String> inputSimulation = new HashMap<String,String>();




		//stringa di .tmp
		String line = input;

		//System.out.println(line);

		String[] aparam = line.split(";");
		String[] couple=aparam[0].split(":");
		int idInputSimulation=Integer.parseInt(couple[1]);
		couple=aparam[1].split(":");
		int rounds = Integer.parseInt(couple[1]);
		for(int i=2; i<aparam.length;i++){
			couple = aparam[i].split(":");
			inputSimulation.put(couple[0], couple[1]);
		}


		String output_template=conf.get("simulation.description.output.domain");


		//converte il file output.xml con i soli campi in un'unica stringa da processare 
		String output_string_vars=XmlToText.convertOutputXmlIntoText(conf, output_template, idInputSimulation);


		ArrayList<String> outputSimulation =new ArrayList<String>();
		line=output_string_vars;
		aparam = line.split(";");
		for(int i=0; i<aparam.length;i++){
			String[] couple2 = aparam[i].split(":");
			outputSimulation.add(couple2[0]);

		}

		Method[] methods = mainClass.getMethods();
		String param ="";
		Object[] toSend={};
		for(String field : inputSimulation.keySet()){
			for (Method toInvoke: methods){
				if(toInvoke.getName().equalsIgnoreCase("set"+field)){
					toInvoke.setAccessible(true);
					//toInvoke.invoke(obj,inputSimulation.get(field));
					param=toInvoke.getGenericParameterTypes()[0].toString();
					if(param.equalsIgnoreCase(int.class.getName()))
						toSend=new Object[]{Integer.valueOf(inputSimulation.get(field))};
					else
						if(param.equalsIgnoreCase(double.class.getName()))
							toSend=new Object[]{Double.valueOf(inputSimulation.get(field))};
						else
							if(param.equalsIgnoreCase(long.class.getName()))
								toSend=new Object[]{Long.valueOf(inputSimulation.get(field))};
							else
								if(param.equalsIgnoreCase(short.class.getName()))
									toSend=new Object[]{Short.valueOf(inputSimulation.get(field))};
								else
									if(param.equalsIgnoreCase(float.class.getName()))
										toSend=new Object[]{Float.valueOf(inputSimulation.get(field))};
					toInvoke.invoke(obj,toSend);
					break;
				}
			}
		}
		SimState ss = null;
		HashMap<String, ArrayList<String>> output_collection = new HashMap<String, ArrayList<String>>();

		for(int i =0; i<rounds; i++){
			ss = (SimState) obj;
			ss.setSeed(System.currentTimeMillis());
			int j=0;
			ss.start();
			int step = Integer.valueOf(inputSimulation.get("step"));
			while(j<step){
				ss.schedule.step(ss);
				j++;

			}

			//Collect OUTPUTs
			for(String field : outputSimulation){
				for (Method toInvoke : methods){
					if(toInvoke.getName().equalsIgnoreCase("get"+field)){
						toInvoke.setAccessible(true);
						if(output_collection.containsKey(field))
							output_collection.get(field).add(""+toInvoke.invoke(obj, new Object[]{}).toString());
						else{
							ArrayList<String> l = new ArrayList<String>();
							l.add(""+toInvoke.invoke(obj, new Object[]{}).toString());
							output_collection.put(field, l);
						}
					}
				}
			}
		}

		String inOutput="";		

		/*for(String field : outputSimulation){
			for (Method toInvoke : methods){
				if(toInvoke.getName().equalsIgnoreCase("get"+field)){
					toInvoke.setAccessible(true);
					inOutput+=field+":"+toInvoke.invoke(obj, new Object[]{}).toString()+";";
				}
			}
		}*/

		for(String field : output_collection.keySet()){
			inOutput+=field+":"+getAVG(output_collection.get(field),rounds)+";";

		}

		//Random r=new Random(System.currentTimeMillis());
		//String id=MD5(line+r.nextDouble());

		Path file_output=null;
		int id = (new String(inOutput+""+System.currentTimeMillis())).hashCode();
		file_output=generateOutput(input, inOutput, SIM_OUTPUT_MAPPER, id, idInputSimulation, SIMULATION_NAME, AUTHOR, DESCRIPTION, SIMULATION_HOME);

		output.collect(new Text(file_output.toString()), new Text(""));

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
	 * Generate output resume of simulation 
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


		//Simulation sim =new Simulation();
		//sim.setauthor(AUTHOR);
		//sim.setname(SIMULATION_NAME);
		//sim.setnote(NOTE);
		//sim.settoolkit("MASON");


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

				Parameter paramOut=new Parameter();
				paramOut.setparam(valobjOutp);
				paramOut.setvariable_name(couple[0]);
				paramsOutput.add(paramOut);
			}
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
