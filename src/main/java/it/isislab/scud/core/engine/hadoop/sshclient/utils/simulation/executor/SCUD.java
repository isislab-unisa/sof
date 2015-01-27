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
package it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.executor;


import it.isislab.scud.core.engine.hadoop.mapreduce.mason.SCUDMapperMason;
import it.isislab.scud.core.engine.hadoop.mapreduce.mason.SCUDReducerMason;
import it.isislab.scud.core.engine.hadoop.mapreduce.netlogo.SCUDMapperNetLogo;
import it.isislab.scud.core.engine.hadoop.mapreduce.netlogo.SCUDReducerNetLogo;
import it.isislab.scud.core.engine.hadoop.mapreduce.generic.SCUDReducerGeneric;
import it.isislab.scud.core.engine.hadoop.mapreduce.generic.SCUDMapperGeneric;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.RunningJob;


/**
 * 
 *
 * 
 * Exec Map/Reduce process for SCUD 
 * 
 *
 */
public class SCUD {

	
	public static void main(String[] args)
	{

		
		/**
		 * aids /home/michele/Scrivania/aids netlogo /home/michele/Scrivania/aids/aids.nlogo /home/michele/Scrivania/aids/input.tmp /home/michele/Scrivania/aids/output /home/michele/Scrivania/aids/output.xml false pepp ciao  
		 *  
		 */
		

	/*			try {//Runtime.getRuntime().exec("rm -r /home/lizard87/Desktop/mason_test/output");
				    Runtime.getRuntime().exec("rm -r /home/michele/Scrivania/aids/output");
		         } catch (IOException e) {e.printStackTrace();}*/

		if(args.length <9 || args.length==12 || args.length==13 || args.length>15)
		{

			System.out.println("Usage:");
			System.out.println("java -jar SCUD.jar "
					+ "<simulation_name> "
					+ "<simulation_path_home> "
					+ "<simulation_type[mason |netlogo |generic]>"
					+"<simulation_generic_interpreter_path>"
					+ "<simultion_program_path> "
					+ "<simulation_mapper_input_path> "
					+ "<simulation_mapper_output_path> "
					+ "<simulation_output_domain_xmlfile> "
					+ "<simulation_input_path> "
					+"<<simulation_rating_path>>"
					+ "<oneshot[one|loop]> "
					+ "<author_name> "
					+ "<simulation_description> "
					+ "<path_interpreter_evaluate_file> "
					+ "<evaluate_file_path>" );
			System.exit(-1);
		}

		Configuration conf=null;
		JobConf job =null;
		
		
		String AUTHOR=null;/*author name*/
		String SIMULATION_NAME=null;/*simulation name*/ 
		String SIMULATION_HOME=null;/*path simulation*/ 
		String SIM_TYPE=null;/*mason, netlogo, generic*/
		String SIM_EXECUTABLE_SIMULATION_INTERPRETER_PATH=null;
		String SIM_EXECUTABLE_SIMULATION_PROGRAM=null; /*executable program *.jar | *.nlogo*/
		String SIM_EXECUTION_INPUT_DATA_MAPPER=null;/*input.data path */
		String SIM_EXECUTION_OUTPUT_MAPPER=null;/*output loop(i) path*/
		String SIM_DESCRIPTION_OUTPUT_XML_DOMAIN= null;/*path of domain file */
		String SIM_EXECUTION_INPUT_XML=null;/*execution input path*/
		boolean ISLOOP=false;/*false[one] | true[loop]*/
		String DESCRIPTION=null;/*simulations' description*/
		String INTERPRETER_REMOTE_PATH_EVALUATION=null;/*remote program bin path for executing EvalFoo*/
		String EXECUTABLE_RATING_FILE=null;/*path of rating file*/
		String SIM_RATING_PATH=null;

		// aids /home/michele/Scrivania/aids netlogo /home/michele/Scrivania/aids/aids.nlogo /home/michele/Scrivania/aids/input.tmp /home/michele/Scrivania/aids/output /home/michele/Scrivania/aids/domain.xml /home/michele/Scrivania/aids/input loop pepp ciao /usr/bin/python /home/michele/Scrivania/aids/evaluate.py 
		
		
		
		
		
		
		
		
		if(args.length==14){
			SIMULATION_NAME=args[0]; 
			SIMULATION_HOME= args[1];
			SIM_TYPE=args[2];
			SIM_EXECUTABLE_SIMULATION_PROGRAM=args[3];
			SIM_EXECUTION_INPUT_DATA_MAPPER=args[4];
			SIM_EXECUTION_OUTPUT_MAPPER=args[5];
			SIM_DESCRIPTION_OUTPUT_XML_DOMAIN= args[6];
			SIM_EXECUTION_INPUT_XML=args[7];
			SIM_RATING_PATH=args[8];
			ISLOOP=Boolean.parseBoolean(args[9]);
			AUTHOR=args[10];
			DESCRIPTION=args[11];
			INTERPRETER_REMOTE_PATH_EVALUATION=args[12];
			EXECUTABLE_RATING_FILE=args[13];
			
		}


		else if(args.length==10){
			SIMULATION_NAME=args[0];
			SIMULATION_HOME=args[1];
			SIM_TYPE=args[2];
			SIM_EXECUTABLE_SIMULATION_PROGRAM=args[3];
			SIM_EXECUTION_INPUT_DATA_MAPPER=args[4];
			SIM_EXECUTION_OUTPUT_MAPPER=args[5];
			SIM_DESCRIPTION_OUTPUT_XML_DOMAIN= args[6];
			ISLOOP=Boolean.parseBoolean(args[7]);
			AUTHOR=args[8];
			DESCRIPTION=args[9];
		}


		
		else if(args.length==15){
			SIMULATION_NAME=args[0]; 
			SIMULATION_HOME= args[1];
			SIM_TYPE=args[2];
			SIM_EXECUTABLE_SIMULATION_INTERPRETER_PATH=args[3];
			SIM_EXECUTABLE_SIMULATION_PROGRAM=args[4];
			SIM_EXECUTION_INPUT_DATA_MAPPER=args[5];
			SIM_EXECUTION_OUTPUT_MAPPER=args[6];
			SIM_DESCRIPTION_OUTPUT_XML_DOMAIN= args[7];
			SIM_EXECUTION_INPUT_XML=args[8];
			SIM_RATING_PATH=args[9];
			ISLOOP=Boolean.parseBoolean(args[10]);
			AUTHOR=args[11];
			DESCRIPTION=args[12];
			INTERPRETER_REMOTE_PATH_EVALUATION=args[13];
			EXECUTABLE_RATING_FILE=args[14];
			
			
			
		}
		
		else if(args.length==11){
			SIMULATION_NAME=args[0];
			SIMULATION_HOME=args[1];
			SIM_TYPE=args[2];
			SIM_EXECUTABLE_SIMULATION_INTERPRETER_PATH=args[3];
			SIM_EXECUTABLE_SIMULATION_PROGRAM=args[4];
			SIM_EXECUTION_INPUT_DATA_MAPPER=args[5];
			SIM_EXECUTION_OUTPUT_MAPPER=args[6];
			SIM_DESCRIPTION_OUTPUT_XML_DOMAIN= args[7];
			ISLOOP=Boolean.parseBoolean(args[8]);
			AUTHOR=args[9];
			DESCRIPTION=args[10];
		}
		
		
		
		if(
				!(SIM_TYPE.equalsIgnoreCase("mason")
						||
						SIM_TYPE.equalsIgnoreCase("netlogo")
						||
						SIM_TYPE.equalsIgnoreCase("generic")))
		{
			System.exit(-2);
		}

        
		conf=new Configuration();
		job = new JobConf(conf,SCUD.class);
		job.setJobName(SIMULATION_NAME/*SIMULATION NAME*/);
		job.set("simulation.home", SIMULATION_HOME);
		job.set("simulation.name", SIMULATION_NAME);
		job.set("simulation.type", SIM_TYPE);
		
		
		
		
		if(SIM_TYPE.equalsIgnoreCase("generic")){
		   job.set("simulation.interpreter.genericsim", SIM_EXECUTABLE_SIMULATION_INTERPRETER_PATH);	
		}
		
		
		
		
		job.set("simulation.program.simulation", SIM_EXECUTABLE_SIMULATION_PROGRAM);
		job.set("simulation.executable.input", SIM_EXECUTION_INPUT_DATA_MAPPER);
		job.set("simulation.executable.output", SIM_EXECUTION_OUTPUT_MAPPER);
		job.setBoolean("simulation.executable.mode", ISLOOP);
		//job.set("simulation.executable.mode", ISLOOP);
		job.set("simulation.executable.author", AUTHOR);
		job.set("simulation.executable.description", DESCRIPTION);
		job.set("simulation.description.output.domain",SIM_DESCRIPTION_OUTPUT_XML_DOMAIN);
		


		/**
		 * GENERA IL .TMP
		 * COMMENTA LA LINEA 
		 * TEST IN LOCALE 
		 * SOLO PER IL LOCALE
		 */
		//XmlToText.convertXmlFileToFileText(conf,"/home/lizard87/Desktop/mason_test/input.xml");
		//XmlToText.convertXmlFileToFileText(conf,"/home/lizard87/Desktop/input.xml");
		//XmlToText.convertXmlFileToFileText(conf,"/home/lizard87/Desktop/aids/input.xml");
		
		if(ISLOOP){
			job.set("simulation.description.input", SIM_EXECUTION_INPUT_XML);
			job.set("simulation.program.rating", EXECUTABLE_RATING_FILE);
			//job.set("simulation.interpreter.selection", INTERPRETER_REMOTE_PATH_SELECTION);
			job.set("simulation.interpreter.rating", INTERPRETER_REMOTE_PATH_EVALUATION);
			job.set("simulation.executable.loop.rating", SIM_RATING_PATH);
		}



		FileInputFormat.addInputPath(job,new Path(SIM_EXECUTION_INPUT_DATA_MAPPER)/*DIRECTORY INPUT*/);
		FileOutputFormat.setOutputPath(job, new Path(SIM_EXECUTION_OUTPUT_MAPPER));


		if(SIM_TYPE.equalsIgnoreCase("mason"))
		{
			job.setMapperClass(SCUDMapperMason.class);
			job.setReducerClass(SCUDReducerMason.class);

		}else
			if(SIM_TYPE.equalsIgnoreCase("netlogo"))
			{
				job.setMapperClass(SCUDMapperNetLogo.class);
				job.setReducerClass(SCUDReducerNetLogo.class);
			}else
				if(SIM_TYPE.equalsIgnoreCase("generic"))
				{
					job.setMapperClass(SCUDMapperGeneric.class);
					job.setReducerClass(SCUDReducerGeneric.class);
				}

		job.setOutputKeyClass(org.apache.hadoop.io.Text.class);  
		job.setOutputValueClass(org.apache.hadoop.io.Text.class);

		JobClient jobc;

		try {
			jobc = new JobClient(job);
			System.out.println(jobc+" "+job);
			RunningJob runjob;
			runjob=JobClient.runJob(job);
			while(runjob.getJobStatus().equals(JobStatus.SUCCEEDED)){}
			System.exit(0);
		} catch (IOException e) {

			e.printStackTrace();
		}


	}

	
}
