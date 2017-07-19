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
package it.isislab.sof.core.engine.hadoop.mapreduce.generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class SOFReducerGeneric extends MapReduceBase implements Reducer<Text,Text, Text, Text> {
	private Configuration conf=null;
	String SIM_OUTPUT_MAPPER="";//conf.get("simulation.executable.output");
	boolean ISLOOP=false;//conf.get("simulation.executable.mode");
	String RATING_PROGRAM="";//conf.get("simulation.program.evaluation");
	String RATING_INTERPRETER="";
	String RATING_PATH="";
	String CONF="";
	String TMP_FOLDER="";

	/*	public void reduce(Text key, Iterator<Text> values,
			OutputCollector<Text, Text> output, Reporter reporter)
					throws IOException {



		String EVALUATION_PROGRAM_THREAD="evaluation"+Thread.currentThread().getId();

		FileSystem fs=FileSystem.get(conf);

		if(ISLOOP)
		{
			Path eprogram=new Path(EVALUATION_PROGRAM_THREAD);
			fs.copyToLocalFile(new Path(EVALUATION_PROGRAM),eprogram);
			try{
				fs.mkdirs(new Path(this.SIM_OUTPUT_MAPPER+"/rating"));
			}catch(Exception e){}

		}


		Random r=new Random(System.currentTimeMillis());
		String id=MD5(key.toString()+r.nextDouble());

		if(ISLOOP)
		{
			Path ptemp=new Path("tmpEval"+id+".xml");
			Path file_output=new Path(key.toString());
			fs.copyToLocalFile(file_output, ptemp);

			generateEvaluation("tmpEval"+id+".xml",id,EVALUATION_PROGRAM_THREAD);
		}

		if(ISLOOP)
		{
			File f=new File(System.getProperty("user.dir")+"/"+EVALUATION_PROGRAM_THREAD);
			f.delete();

		}


	}*/

	public void reduce(Text key, Iterator<Text> values,
			OutputCollector<Text, Text> output, Reporter reporter)
					throws IOException {


		File tmpFolder = new File(TMP_FOLDER);
		if(!tmpFolder.exists()) tmpFolder.mkdirs();

		//String EVALUATION_PROGRAM_THREAD="evaluation"+Thread.currentThread().getId();
		String EVALUATION_PROGRAM=(new Path(RATING_PROGRAM)).getName();
		final FileSystem fs=FileSystem.get(conf);

		if(ISLOOP)
		{
			Path eprogram=new Path(TMP_FOLDER+File.separator+EVALUATION_PROGRAM);

			fs.copyToLocalFile(new Path(RATING_PROGRAM),eprogram);
			try{
				fs.mkdirs(new Path(this.RATING_PATH));
			}catch(Exception e){
				e.printStackTrace();
			}

		}

		if(ISLOOP)
		{   
			Random r=new Random(System.currentTimeMillis());
			String id=MD5(key.toString()+r.nextDouble());
			String tmpEvalXml = TMP_FOLDER+File.separator+"tmpEval"+id;
			Path ptemp=new Path(tmpEvalXml);

			Path file_output=new Path(key.toString());
			System.out.println("copying "+file_output+" to "+ptemp);
			fs.copyToLocalFile(file_output, ptemp);//output folder
			fs.copyToLocalFile(new Path(CONF), new Path(TMP_FOLDER));//conf.ini


			File lau_out = new File(TMP_FOLDER+File.separator+"SolveOutputs"+("SolveOutputs").hashCode());
			if(!lau_out.exists()) 
				lau_out.mkdir();

			
			while(values.hasNext()){
				
				String filesOutput = values.next().toString();
				System.out.println(filesOutput.toString());

				String[] fileCouple = null;
				for(String f : filesOutput.split(";")){

					fileCouple = f.split(":");
					if(fileCouple[0].equalsIgnoreCase("file")){

						String fileName=fileCouple[1];
						System.out.println(fileName);

						System.out.println("copying "+SIM_OUTPUT_MAPPER+fileName+" to "+" "+lau_out.getAbsolutePath() );


						fs.copyToLocalFile(new Path(SIM_OUTPUT_MAPPER+fileName), new Path( lau_out.getAbsolutePath()));

					}
				}
				
			}
			
			/*if(values.hasNext()){

				String filesOutput = values.next().toString();
				System.out.println(filesOutput.toString());

				String[] fileCouple = null;
				for(String f : filesOutput.split(";")){

					fileCouple = f.split(":");
					if(fileCouple[0].equalsIgnoreCase("file")){

						String fileName=fileCouple[1];
						System.out.println(fileName);

						System.out.println("copying "+SIM_OUTPUT_MAPPER+"/"+fileName+" to "+" "+lau_out.getAbsolutePath() );


						fs.copyToLocalFile(new Path(SIM_OUTPUT_MAPPER+"/"+fileName), new Path( lau_out.getAbsolutePath()));

					}
				}

			}

			*/
			
			
			String conf_file_name = (new Path(CONF).getName());
			String solution_folder = TMP_FOLDER+File.separator+"finalSolution"+("finalSolution").hashCode();
			File sol_folder = new File(solution_folder);
			File con_file = new File(TMP_FOLDER+File.separator+conf_file_name);
			File eval_exe_file = new File(TMP_FOLDER+File.separator+EVALUATION_PROGRAM);
			sol_folder.mkdirs();
			String xmlOutput =key.toString().substring(key.toString().lastIndexOf("/")+1);
			//generateEvaluation(tmpEvalXml,id,EVALUATION_PROGRAM_THREAD);
			generateEvaluation(tmpEvalXml,xmlOutput,eval_exe_file.getAbsolutePath(),lau_out.getAbsolutePath(),sol_folder.getAbsolutePath(),con_file.getAbsolutePath());

			//File f=new File(TMP_FOLDER+"/"+EVALUATION_PROGRAM);
			//f.delete();
		}


	}

	/**
	 * Exec an evaluation 
	 * 
	 * @param toevaluate
	 * @param SIM_OUTPUT_MAPPER
	 * @param id
	 * @param EVALUATION_PROGRAM
	 * @return
	 * @throws IOException
	 */
	private boolean generateEvaluation(String toevaluate, String id,String EVALUATION_PROGRAM,String input_folder,String output_folder, String conf_file) throws IOException {

		FileSystem fs=FileSystem.get(conf);
		File f=new File(EVALUATION_PROGRAM);
		f.setExecutable(true);
		ArrayList<String> commands=new ArrayList<String>();
		commands.add(RATING_INTERPRETER);
		if(EVALUATION_PROGRAM.endsWith(".jar"))
			commands.add("-jar");

		commands.add(EVALUATION_PROGRAM);
		commands.add(input_folder);




		commands.add(output_folder);
		commands.add(conf_file);

		/*for (String string : commands) {
			System.out.println(string);
		}*/
		String stringone="";
		for (String string : commands) {
			stringone+=string+" ";
		}

		stringone=stringone.substring(0, stringone.length()-1);



		Process cat = Runtime.getRuntime().exec(stringone);
		InputStream stderr = cat.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		String tmp = null;
		String prova=null;

		BufferedReader br = new BufferedReader(isr);
		tmp = null;
		// 
		while ((tmp = br.readLine()) != null) {

			if(tmp.trim().toLowerCase().contains(new String("Evaluating fitness").toLowerCase()) ){

				System.out.println(tmp);

				String [] linee =tmp.split(" ");

				prova=linee[linee.length-1];
			}

		}
		try {
			cat.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br.close();




		/*
		ProcessBuilder pb=new ProcessBuilder(commands);
		pb.redirectErrorStream(true);
		final Process process = pb.start();
		InputStream stderr = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String tmp = null;
		String prova=null;
		while ((tmp = br.readLine()) != null) {




			if(tmp.trim().toLowerCase().contains(new String("Evaluating fitness").toLowerCase()) ){

				System.out.println(tmp);

				String [] linee =tmp.split(" ");

				prova=linee[linee.length-1];
				System.out.println("the best sol "+prova);
			}

		}
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br.close();
		 */






		/*	FileSystem fs=FileSystem.get(conf);
		File f=new File(EVALUATION_PROGRAM);
		f.setExecutable(true);
		String cmd=this.RATING_INTERPRETER;
		if(RATING_INTERPRETER.contains("java"))
			cmd+=" -jar";
		cmd+=" "+System.getProperty("user.dir")+"/"+EVALUATION_PROGRAM;
		cmd+=" evaluate";
		cmd+=" "+System.getProperty("user.dir")+"/"+toevaluate;


		Process process = Runtime.getRuntime().exec(cmd);
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		String evaluationFile="";


		while ((line = br.readLine()) != null) {
			evaluationFile+=line;
		}
		 */
		FSDataOutputStream out=fs.create(new Path(this.RATING_PATH+"/EVALUATE"+id));
		PrintWriter printer=new PrintWriter(out);

		//		
		//		File lau_out = new File("launcher_output");
		//		if(!lau_out.exists()) lau_out.mkdir();


		File final_solutions = new File(output_folder);

		System.out.println("fs "+final_solutions.getAbsolutePath());
		String files="";
		for(File fsols : final_solutions.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".xml");
			}
		})){
			System.out.println("The best sol is "+fsols.getName());
			System.out.println("copying "+fsols.getAbsolutePath()+" to "+this.RATING_PATH );
			fs.copyFromLocalFile(new Path(fsols.getAbsolutePath()), new Path(this.RATING_PATH));
			files+="file:"+fsols.getName()+";";
		}


		printer.write(files);
		printer.close();
		out.close();

		//f=new File(System.getProperty("user.dir")+"/"+toevaluate);


		return true; 
	}

	/**
	 * Metodo supporto
	 * Create an id 
	 */
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
	}
	@Override
	public void configure(JobConf job) {
		super.configure(job);
		conf=job;
		this.SIM_OUTPUT_MAPPER=conf.get("simulation.executable.output");
		this.ISLOOP=conf.getBoolean("simulation.executable.mode",false);
		this.RATING_PROGRAM=conf.get("simulation.program.rating");
		this.RATING_INTERPRETER=conf.get("simulation.interpreter.rating");
		this.RATING_PATH=conf.get("simulation.executable.loop.rating");
		this.CONF=conf.get("simulation.conf");
		this.TMP_FOLDER=""+Thread.currentThread().getId();
	}
}