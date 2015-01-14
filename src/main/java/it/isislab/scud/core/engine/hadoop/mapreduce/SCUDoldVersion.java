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
   @author  Michele Carillo, Flavio Serrapica, Carmine Spagnuolo, Francesco Raia.
 */
package it.isislab.scud.core.engine.hadoop.mapreduce;

import java.io.IOException;
import it.isislab.scud.core.engine.hadoop.mapreduce.mason.SCUDMapperMason;
import it.isislab.scud.core.engine.hadoop.mapreduce.mason.SCUDReducerMason;
import it.isislab.scud.core.engine.hadoop.mapreduce.netlogo.SCUDMapperNetLogo;
import it.isislab.scud.core.engine.hadoop.mapreduce.netlogo.SCUDReducerNetLogo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.RunningJob;


/**
 * MAIN CLASS OF SCUD   
 * 
 * @author Michele Carillo, Flavio Serrapica, Franceco Raia
 *
 */ 
//use  it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.executor.SCUD.java
@Deprecated
public class SCUDoldVersion {
//	@Option(name="-u",usage="ssh user name (string)",required=true)
//	private String user;
//
//	@Option(name="-p",usage="ssh password (string)",required=true)
//	private String password;
//
//	@Option(name="-h",usage="hadoop home directory (string path)",required=true)
//	private String hadoop;
//
//	@Option(name="-id",usage="simulation id (integer)"/*,required=true*/)
//	private int id;
//
//	@Option(name="-loop",usage="to run the simulation ID in loop mode")
//	private boolean loop;
//
//	@Option(name="-type",usage="simulation type (M)ason, (N)etlogo and (G)eneric (string M-N-G)",required=true)
//	private String type;
//
//	@Option(name="-action", usage="running mode, specifics if you (M)ake the simulation folders or if you (R)un a simulation", required=true)
//	private String action;
//
//	@Option(name="-simName", usage="name of the simulation. Used if you want make the simulation folders")
//	private String simulationName;
//
//	@Option(name="-domain", usage="xml file of the domain. Used for loop mode")
//	private String domainFilename;
//
//	@Option(name="-input", usage="xml file of the inputs. Used for loop mode")
//	private String inputFilename;
//
//	@Option(name="-selectionfoo", usage="selection function to use for loop mode")
//	private String selectionFunctionExecFileName;
//
//	@Option(name="-output", usage="xml file of the outputs selected", required=true)
//	private String outputFilename;
//
//	@Option(name="-descr", usage="simulation description.")
//	private String simulationDescription;
//
//	@Option(name="-ratingfoo", usage="rating function to use for loop mode")
//	private String ratingFunctionExecFileName;
//
//	@Option(name="-exec", usage="pathname of the bash command for running selection/evaluation function")
//	private String bashCommandForRunnableFunction;
//
//	@Option(name="-sim", usage="simulation filename")
//	private String simulationExecProgramFileName;
//
//	@Option(name="-port", usage="port to connect ssh on hadoop")
//	private int port;
//
//	@Argument
//	private List<String> arguments = new ArrayList<String>();
//
//	public void doMain(String[] args) throws Exception
//	{
//
//		CmdLineParser parser = new CmdLineParser(this);
//		parser.setUsageWidth(80);
//
//		try {
//
//			parser.parseArgument(args);
//
//		} catch( CmdLineException e ) {
//
//			System.err.println(e.getMessage());
//			System.err.println("java SimulationRun [options...] arguments...");
//			parser.printUsage(System.err);
//			System.err.println();
//
//			// print option sample. This is useful some time
//			System.err.println("\t Example: java SimulationRun"+parser.printExample(ExampleMode.ALL));
//
//			return;
//		}
//
//		EnvironmentSession session;
//
//
//		session = ScudManager.connect(user, "127.0.0.1", password, hadoop,port,
//				ScudManager.class.getResourceAsStream("SCUD.jar"),
//				ScudManager.class.getResourceAsStream("SCUD-RUNNER.jar"));
//
//		if(action==""){
//			System.err.println("java SimulationRun [options...] arguments...");
//			parser.printUsage(System.err);
//			System.err.println();
//
//			// print option sample. This is useful some time
//			System.err.println("\t Example: java SimulationRun"+parser.printExample(ExampleMode.ALL));
//			System.exit(-1);
//		}
//
//		if(type!= ""){
//			if(type.equalsIgnoreCase("n"))
//				type = "netlogo";
//			else if(type.equalsIgnoreCase("m"))
//				type = "mason";
//			else type="generic";
//		}
//
//
//		if(action.equalsIgnoreCase("m") || action.equalsIgnoreCase("n"))
//			if(loop){
//				checkParam(loop);
//				//SdManager.makeSimulationFolderForLoop(session, type, simulationName, domainFilename, bashCommandForRunnableFunction, outputFilename, selectionFunctionFilenameExeExe, selectionFunctionFilenameExe, simulationDescription, /*simulationNote,*/ simulationProgram);
//				ScudManager.makeSimulationFolderForLoop(session, type, simulationName, domainFilename, bashCommandForRunnableFunction, outputFilename, ratingFunctionExecFileName, ratingFunctionExecFileName,simulationDescription,simulationExecProgramFileName);
//			}
//		else
//		//ConnectionSSH.makeSimulationFolder(session, type, simulationName, inputFilename, outputFilename, simulationDescription, simulationNote, simulationProgram,loop);
//	
//
//		if(action.equalsIgnoreCase("r"))
//			ScudManager.runSimulation(session, user, id, loop);
//
//
//		session.getSession().disconnect();	
//
//
//	}
//	private boolean checkParam(boolean loop) {
//
//		if(loop){
//
//			if(simulationName == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -simName for simulation name");
//				return false;
//			}
//			if(domainFilename == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -domain for domain file");
//				return false;
//			}
//			if(bashCommandForRunnableFunction == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -exec for bash command used to run selection/evaluation function");
//				return false;
//			}
//			if(selectionFunctionExecFileName == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -sf");
//				return false;
//			}
//			if(ratingFunctionExecFileName == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -ef");
//				return false;
//			}
//			if(simulationDescription == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -description");
//				return false;
//			}
//			/*	if(simulationNote == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -note");
//				return false;
//			}*/
//			if(simulationExecProgramFileName == ""){
//				System.err.println("Parameter not found! \n For loop mode you must enter -simulation");
//				return false;
//			}
//			return true;
//		}else{
//
//			if(simulationName == ""){
//				System.err.println("Parameter not found! \n For single mode you must enter -simName for simulation name");
//				return false;
//			}
//			if(domainFilename == ""){
//				System.err.println("Parameter not found! \n For single mode you must enter -domain for domain file");
//				return false;
//			}
//
//			if(inputFilename == ""){
//				System.err.println("Parameter not found! \n For single mode you must enter -input");
//				return false;
//			}
//
//			if(simulationDescription == ""){
//				System.err.println("Parameter not found! \n For single mode you must enter -description");
//				return false;
//			}
//			if(simulationExecProgramFileName == ""){
//				System.err.println("Parameter not found! \n For single mode you must enter -simulation");
//				return false;
//			}
//			return true;
//		}
//	}
//	public static void main(String[] args) {
//
//		try {
//
//			new SCUD().doMain(args);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}



	public static void main(String[] args)
	{

		/**
		 * AIDS
		 * network file:///home/lizard87/Desktop/aids netlogo file:///home/lizard87/Desktop/aids/AIDS.nlogo 
		 * file:///home/lizard87/Desktop/aids/input.tmp file:///home/lizard87/Desktop/aids/output 
		 * file:///home/lizard87/Desktop/aids/output.xml file:///home/lizard87/Desktop/input.xml loop uno due "java -jar "
		 * file:///home/lizard87/Desktop/aids/evaluate.jar
		 */


		/**
		 * NETLOGO
		 * 
		 * network file:///home/lizard87/Desktop netlogo file:///home/lizard87/Desktop/network.nlogo 
		 * file:///home/lizard87/Desktop/input.tmp file:///home/lizard87/Desktop/output file:///home/lizard87/Desktop/output.xml loop uno due 
		 * file:///home/lizard87/Desktop/evaluate.jar
		 */

		/**
		 * MASON
		 * 
		 * mason file:///home/lizard87/Desktop/mason_test mason file:///home/lizard87/Desktop/mason_test/flockers.jar  
		 * file:///home/lizard87/Desktop/mason_test/input.tmp  file:///home/lizard87/Desktop/mason_test/output 
		 * file:///home/lizard87/Desktop/mason_test/output.xml file:///home/lizard87/Desktop/mason_test/input.xml loop uno due  "python " 
		 *  file:///home/lizard87/Desktop/mason_test/evaluate.py
		 *  
		 */
		
		/*		try {//Runtime.getRuntime().exec("rm -r /home/lizard87/Desktop/mason_test/output");
				    Runtime.getRuntime().exec("rm -r /home/lizard87/Desktop/aids/output");
		         } catch (IOException e) {e.printStackTrace();}*/

		if(args.length <9)
		{

			System.out.println("Usage:");
			System.out.println("java -jar SCUD.jar <simulation_name> "
					+ "<simulation_path_home> "
					+ "<simulation_type[mason |netlogo |generic]>"
					+ "<simultion_program_path> <simulation_mapper_input_path> "
					+ "<simulation_mapper_output_path> "
					+ "<simulation_output_domain_xmlfile> "
					+ "<simulation_input_path> <oneshot[one|loop]> "
					+ "<author_name> <simulation_note> "
					+ "<path_interpreter_evaluate_file> <evaluate_file_path>" );
			System.exit(-1);
		}

		Configuration conf=null;
		JobConf job =null;
		String AUTHOR=null;/*author name*/
		String SIMULATION_NAME=null;/*simulation name*/ 
		String SIMULATION_HOME=null;/*path simulation*/ 
		String SIM_TYPE=null;/*mason, netlogo, generic*/
		String SIM_EXECUTABLE_SIMULATION_PROGRAM=null; /*executable program *.jar | *.nlogo*/
		String SIM_EXECUTION_INPUT_DATA_MAPPER=null;/*input.data path */
		String SIM_EXECUTION_OUTPUT_MAPPER=null;/*output path*/
		String SIM_OUTPUT_DOMAIN= null;/*path of domain file */
		String SIM_EXECUTION_INPUT_XML=null;/*execution input path*/
		String ONELOOP=null;/*one | loop*/
		String DESCRIPTION=null;/*simulations' description*/
		String INTERPRETER_REMOTE_PATH_EVALUATION=null;
		String EXECUTABLE_RATING_FILE=null;/*path of rating file*/

		
		
		
		if(args.length==13){
			SIMULATION_NAME=args[0]; 
			SIMULATION_HOME= args[1];
			SIM_TYPE=args[2];
			SIM_EXECUTABLE_SIMULATION_PROGRAM=args[3];
			SIM_EXECUTION_INPUT_DATA_MAPPER=args[4];
			SIM_EXECUTION_OUTPUT_MAPPER=args[5];
			SIM_OUTPUT_DOMAIN= args[6];
			SIM_EXECUTION_INPUT_XML=args[7];
			ONELOOP=args[8];
			AUTHOR=args[9];
			DESCRIPTION=args[10];
			INTERPRETER_REMOTE_PATH_EVALUATION=args[11];
			EXECUTABLE_RATING_FILE=args[12]; 
		}


		else{
			SIMULATION_NAME=args[0];
			SIMULATION_HOME=args[1];
			SIM_TYPE=args[2];
			SIM_EXECUTABLE_SIMULATION_PROGRAM=args[3];
			SIM_EXECUTION_INPUT_DATA_MAPPER=args[4];
			SIM_EXECUTION_OUTPUT_MAPPER=args[5];
			SIM_OUTPUT_DOMAIN= args[6];
			ONELOOP=args[7];
			AUTHOR=args[8];
			DESCRIPTION=args[9];
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
		job = new JobConf(conf,SCUDoldVersion.class);
		job.setJobName(SIMULATION_NAME/*SIMULATION NAME*/);
		job.set("simulation.home", SIMULATION_HOME);
		job.set("simulation.name", SIMULATION_NAME);
		job.set("simulation.type", SIM_TYPE);
		job.set("simulation.program.simulation", SIM_EXECUTABLE_SIMULATION_PROGRAM);
		job.set("simulation.executable.input", SIM_EXECUTION_INPUT_DATA_MAPPER);
		job.set("simulation.executable.output", SIM_EXECUTION_OUTPUT_MAPPER);
		job.set("simulation.executable.mode", ONELOOP);
		job.set("simulation.executable.author", AUTHOR);
		job.set("simulation.executable.note", DESCRIPTION);
		job.set("simulation.description.output",SIM_OUTPUT_DOMAIN);


		/**
		 * GENERA IL .TMP
		 * COMMENTA LA LINEA 
		 * TEST IN LOCALE 
		 * SOLO PER IL LOCALE
		 */
		//XmlToText.convertXmlFileToFileText(conf,"/home/lizard87/Desktop/mason_test/input.xml");
		//XmlToText.convertXmlFileToFileText(conf,"/home/lizard87/Desktop/input.xml");
		//XmlToText.convertXmlFileToFileText(conf,"/home/lizard87/Desktop/aids/input.xml");
		
		if(ONELOOP.equalsIgnoreCase("loop")){
			job.set("simulation.description.input", SIM_EXECUTION_INPUT_XML);
			job.set("simulation.program.rating", EXECUTABLE_RATING_FILE);
			job.set("simulation.interpreter.evaluation", INTERPRETER_REMOTE_PATH_EVALUATION);
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
					System.out.println("Sviluppi futuri");
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
