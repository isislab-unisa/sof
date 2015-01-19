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
package it.isislab.scud.core.engine.hadoop.mapreduce.generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public class SCUDReducerGeneric extends MapReduceBase
implements Reducer<Text,Text, Text, Text> {
	private Configuration conf=null;
	String SIM_OUTPUT_MAPPER="";//conf.get("simulation.executable.output");
	boolean ISLOOP=false;//conf.get("simulation.executable.mode");
	String RATING_PROGRAM="";//conf.get("simulation.program.evaluation");
	String RATING_INTERPRETER="";
	 String RATING_PATH="";

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



		String EVALUATION_PROGRAM_THREAD="evaluation"+Thread.currentThread().getId();
		FileSystem fs=FileSystem.get(conf);

		if(ISLOOP)
		{
			Path eprogram=new Path(EVALUATION_PROGRAM_THREAD);
			fs.copyToLocalFile(new Path(RATING_PROGRAM),eprogram);
			try{
				fs.mkdirs(new Path(this.RATING_PATH));
			}catch(Exception e){}

		}

		if(ISLOOP)
		{   
			Random r=new Random(System.currentTimeMillis());
			String id=MD5(key.toString()+r.nextDouble());
			String tmpEvalXml = "tmpEval"+id+".xml";
			Path ptemp=new Path(tmpEvalXml);
			Path file_output=new Path(key.toString());
			fs.copyToLocalFile(file_output, ptemp);
			String xmlOutput =key.toString().substring(key.toString().lastIndexOf("/")+1);
			//generateEvaluation(tmpEvalXml,id,EVALUATION_PROGRAM_THREAD);
			generateEvaluation(tmpEvalXml,xmlOutput,EVALUATION_PROGRAM_THREAD);

			File f=new File(System.getProperty("user.dir")+"/"+EVALUATION_PROGRAM_THREAD);
			f.delete();
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
	private boolean generateEvaluation(String toevaluate,String id,String EVALUATION_PROGRAM) throws IOException {

		FileSystem fs=FileSystem.get(conf);
		File f=new File(EVALUATION_PROGRAM);
		f.setExecutable(true);
		String cmd=this.RATING_INTERPRETER;
		if(RATING_INTERPRETER.contains("java"))
			cmd+=" -jar";
		cmd+=" "+System.getProperty("user.dir")+"/"+EVALUATION_PROGRAM+" "+System.getProperty("user.dir")+"/"+toevaluate;
		Process process = Runtime.getRuntime().exec(cmd);
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		String evaluationFile="";
		while ((line = br.readLine()) != null) {
			evaluationFile+=line;
		}
		FSDataOutputStream out=fs.create(new Path(this.RATING_PATH+"/EVALUATE"+id));
		PrintWriter printer=new PrintWriter(out);
		printer.write(evaluationFile);
		printer.close();
		out.close();

		f=new File(System.getProperty("user.dir")+"/"+toevaluate);


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
	}
}