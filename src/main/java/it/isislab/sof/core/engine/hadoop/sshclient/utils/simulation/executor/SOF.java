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
package it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.executor;



import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.RunningJob;
import it.isislab.sof.core.engine.hadoop.mapreduce.generic.SOFMapperGeneric;
import it.isislab.sof.core.engine.hadoop.mapreduce.generic.SOFReducerGeneric;
import it.isislab.sof.core.engine.hadoop.mapreduce.mason.SOFMapperMason;
import it.isislab.sof.core.engine.hadoop.mapreduce.mason.SOFReducerMason;
import it.isislab.sof.core.engine.hadoop.mapreduce.netlogo.SOFMapperNetLogo;
import it.isislab.sof.core.engine.hadoop.mapreduce.netlogo.SOFReducerNetLogo;





/**
 * 
 *
 * 
 * Exec Map/Reduce process for SCUD 
 * 
 *
 */
public class SOF {

	
	public static void main(String[] args)
	{

		
		/**
		 * aids /home/michele/Scrivania/aids netlogo /home/michele/Scrivania/aids/aids.nlogo /home/michele/Scrivania/aids/input.tmp /home/michele/Scrivania/aids/output /home/michele/Scrivania/aids/output.xml false pepp ciao  
		 *  
		 */

      /*  
        if(new File("/Users/flaser/Desktop/ACTOR/Magellano-Sof/hadoop_hdfs/output/").exists() ){
		try {//Runtime.getRuntime().exec("rm -r /home/miccar/Desktop/mason_test/output");
		    Runtime.getRuntime().exec("rm -r /Users/flaser/Desktop/ACTOR/Magellano-Sof/hadoop_hdfs/output/");
        } catch (IOException e) {e.printStackTrace();}
        }
		

	/*			try {//Runtime.getRuntime().exec("rm -r /home/lizard87/Desktop/mason_test/output");
				    Runtime.getRuntime().exec("rm -r /home/michele/Scrivania/aids/output");
		         } catch (IOException e) {e.printStackTrace();}*/

		if(args.length <9 || args.length==10 || args.length==12 || args.length>=16 )
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
		String SIM_DESCRIPTION_CONFIG_FILE= null;/*path of optional configuration file */
		String SIM_EXECUTION_INPUT_XML=null;/*execution input path*/
		boolean ISLOOP=false;/*false[one] | true[loop]*/
		//String DESCRIPTION=null;/*simulations' description*/
		String INTERPRETER_REMOTE_PATH_EVALUATION=null;/*remote program bin path for executing EvalFoo*/
		String EXECUTABLE_RATING_FILE=null;/*path of rating file*/
		String SIM_RATING_PATH=null;

		// aids /home/michele/Scrivania/aids netlogo /home/michele/Scrivania/aids/aids.nlogo /home/michele/Scrivania/aids/input.tmp /home/michele/Scrivania/aids/output /home/michele/Scrivania/aids/domain.xml /home/michele/Scrivania/aids/input loop pepp ciao /usr/bin/python /home/michele/Scrivania/aids/evaluate.py 
		
		
		
		
		
		
		
		
		if(args.length==13){
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
			INTERPRETER_REMOTE_PATH_EVALUATION=args[11];
			EXECUTABLE_RATING_FILE=args[12];
			
		}


		else if(args.length==9){
			SIMULATION_NAME=args[0];
			SIMULATION_HOME=args[1];
			SIM_TYPE=args[2];
			SIM_EXECUTABLE_SIMULATION_PROGRAM=args[3];
			SIM_EXECUTION_INPUT_DATA_MAPPER=args[4];
			SIM_EXECUTION_OUTPUT_MAPPER=args[5];
			SIM_DESCRIPTION_OUTPUT_XML_DOMAIN= args[6];
			ISLOOP=Boolean.parseBoolean(args[7]);
			AUTHOR=args[8];
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
			INTERPRETER_REMOTE_PATH_EVALUATION=args[12];
			EXECUTABLE_RATING_FILE=args[13];
			SIM_DESCRIPTION_CONFIG_FILE = args[14];
			
			
			
			
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
			SIM_DESCRIPTION_CONFIG_FILE = args[10];
		}
		
		
		
		if(	!(SIM_TYPE.equalsIgnoreCase("mason") || SIM_TYPE.equalsIgnoreCase("netlogo") ||	SIM_TYPE.equalsIgnoreCase("generic")))
		{
			System.err.println("no sym_type");
			System.exit(-2);
		}

        
		conf=new Configuration();
		conf.set("mapred.task.timeout", "0");
		job = new JobConf(conf,SOF.class);
		job.setJobName(SIMULATION_NAME/*SIMULATION NAME*/);
		job.set("simulation.home", SIMULATION_HOME);
		job.set("simulation.name", SIMULATION_NAME);
		job.set("simulation.type", SIM_TYPE);
		
		
		
		
		if(SIM_TYPE.equalsIgnoreCase("generic")){
		   job.set("simulation.interpreter.genericsim", SIM_EXECUTABLE_SIMULATION_INTERPRETER_PATH);	
		   String xm=SIM_EXECUTION_INPUT_DATA_MAPPER.substring(0, SIM_EXECUTION_INPUT_DATA_MAPPER.lastIndexOf("/"));
		   job.set("simulation.input", xm);
		   
		   if(SIM_DESCRIPTION_CONFIG_FILE!=null)
			   job.set("simulation.conf",SIM_DESCRIPTION_CONFIG_FILE);
		      
		   
		}
		
		
		
		job.set("simulation.program.simulation", SIM_EXECUTABLE_SIMULATION_PROGRAM);
		job.set("simulation.executable.input", SIM_EXECUTION_INPUT_DATA_MAPPER);
		job.set("simulation.executable.output", SIM_EXECUTION_OUTPUT_MAPPER);
		job.setBoolean("simulation.executable.mode", ISLOOP);
		job.set("simulation.executable.author", AUTHOR);
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
			job.set("simulation.interpreter.rating", INTERPRETER_REMOTE_PATH_EVALUATION);
			job.set("simulation.executable.loop.rating", SIM_RATING_PATH);
		}



		FileInputFormat.addInputPath(job,new Path(SIM_EXECUTION_INPUT_DATA_MAPPER)/*DIRECTORY INPUT*/);
		FileOutputFormat.setOutputPath(job, new Path(SIM_EXECUTION_OUTPUT_MAPPER));

				
		if(SIM_TYPE.equalsIgnoreCase("mason"))
		{
			job.setMapperClass(SOFMapperMason.class);
			job.setReducerClass(SOFReducerMason.class);

		}else
			if(SIM_TYPE.equalsIgnoreCase("netlogo"))
			{
			
				job.setMapperClass(SOFMapperNetLogo.class);
				job.setReducerClass(SOFReducerNetLogo.class);
			}else
				if(SIM_TYPE.equalsIgnoreCase("generic"))
				{
					job.setMapperClass(SOFMapperGeneric.class);
					job.setReducerClass(SOFReducerGeneric.class);
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
