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
package it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.executor;

import it.isislab.scud.core.engine.hadoop.sshclient.connection.FileSystemSupport;
import it.isislab.scud.core.engine.hadoop.sshclient.connection.ScudManager;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.selection.SelectionFunction;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Loop;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Runs;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.RunsParser;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.SimulationParser;
import it.isislab.scud.core.model.parameters.xsd.input.Inputs;
import it.isislab.scud.core.model.parameters.xsd.output.Output;
import it.isislab.scud.core.model.parameters.xsd.output.Outputs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


/**
 * Class 
 * 
 * @author Michele Carillo, Serrapica Flavio, Raia Francesco
 *
 */
public class SCUDRUNNER{

	@Option(name="-u", usage="username")
	public static String USERNAME;
	
	/*	@Option(name="-host",usage="hostname")
	public String host;

	@Option(name="-p", usage="password")
	public String passowrd;*/
	
	@Option(name="-simid", usage="simulation id")
	public static String simID;
	//@Option(name="-loopid", usage="loop id")
	//public static int loopID;
	
	@Option(name="-hadoopbin", usage="hadoop bin directory installing")
	public static String hadoopbindir="";
	
	@Option(name="-hdfsrootdir",usage="Hdfs home directory")
	public static String hdfsRootDir; 

	@Option(name="-remoterootdir", usage="remote home directory")
	public static String remoteRootDir;
	
/*	private String simName;
	
	
	public String simulationHOME;
	
	
	private String simulationToolkit;
	
	private String executable_sim_file;
	
	
	private String mapperExecutionLoopInputDataPath;
	
	
	private String mapperExecutionLoopOutputPath;
	
	
	private String simulation_description_output_xml;
	
	
	private boolean LOOP;
	
	private String description;
	
	
	private String bashCommandForRunnableFile;
	private String RUNNABLE_RATING_PROGRAMM;
	private String mapperExecutionLoopInputXMLPath;*/
	private static FileSystemSupport fs =null;
	
	public static Logger log = Logger.getLogger(SCUDRUNNER.class.getName());


	/**
	 * 
	 * USE THIS CONSTRUCTOR IF YOU WANT TO RUN A SIMULATION OPTIMIZATION
	 * 
	 * @param session
	 * @param author
	 * @param simulationName
	 * @param simulationHOME
	 * @param simulationTYPE
	 * @param simulationPROGRAM
	 * @param mapperInputPath
	 * @param mapperOutputPath
	 * @param description_OUTPUT_DOMAIN_FILE
	 * @param oneloop
	 * @param note
	 * @param bashCommandForRunnableFile
	 * @param runnable_evaluation_programm
	 *//*
	public SCUDRUNNER(String author,
			String simulationName, String simulationHOME,
			String simulationTYPE, String simulationPROGRAM,
			String mapperExecutionLoopInputDataPath , String mapperExecutionLoopOutputPath,
			String description_OUTPUT_DOMAIN_FILE,
			String mapperExecutionLoopInputXMLPath,
			boolean oneloop, 
			String description, String bashCommandForRunnableFile,
			String runnable_rating_programm) {

		this.AUTHOR = author;	
		this.simName = simulationName;
		this.simulationHOME = simulationHOME;
		this.simulationToolkit = simulationTYPE;
		this.executable_sim_file = simulationPROGRAM;
		this.mapperExecutionLoopInputDataPath = mapperExecutionLoopInputDataPath ;
		this.mapperExecutionLoopOutputPath = mapperExecutionLoopOutputPath;
		this.simulation_description_output_xml = description_OUTPUT_DOMAIN_FILE;
		this.mapperExecutionLoopInputXMLPath = mapperExecutionLoopInputXMLPath;
		this.ONELOOP = oneloop;
		this.description = description;
		this.bashCommandForRunnableFile = bashCommandForRunnableFile;
		this.RUNNABLE_RATING_PROGRAMM = runnable_rating_programm;
		this.HADOOP_HOME = ;
	}

	


	*//**
	 * USE THIS CONSTRUCTOR IF YOU WANT TO RUN A ONE SHOT SIMULATION
	 * @param session
	 * @param author
	 * @param simulationName
	 * @param simulationHOME
	 * @param simulationTYPE
	 * @param simulationPROGRAM
	 * @param mapperInputPath
	 * @param mapperOutputPath
	 * @param description_OUTPUT_DOMAIN_FILE
	 * @param oneloop
	 * @param description
	 *//*
	public SCUDRUNNER(String author,
			String simulationName, String simulationHOME,
			String simulationTYPE, String simulationPROGRAM,
			String mapperExecutionLoopInputDataPath, String mapperOutputPathExecution,
			String simulation_description_output_xml,
			boolean oneloop, String description) {
		this.AUTHOR = author;
		this.simName = simulationName;
		this.simulationHOME = simulationHOME;
		this.simulationToolkit = simulationTYPE;
		this.executable_sim_file = simulationPROGRAM;
		this.mapperExecutionLoopInputDataPath = mapperExecutionLoopInputDataPath;
		this.mapperExecutionLoopOutputPath = mapperOutputPathExecution;
		this.simulation_description_output_xml = simulation_description_output_xml;
		this.ONELOOP = oneloop;
		this.description = description;
		this.HADOOP_HOME = session.getHadoop_home_path();
	}*/

	public SCUDRUNNER(){}

	public void setFileSystem() {
		//il secondo vuoto è javabindir e nun me serve ca .:;
		fs = new FileSystemSupport(hadoopbindir,"", hdfsRootDir, remoteRootDir, "", USERNAME);
	}
	
	public void setLogFile() throws SecurityException, IOException{
		FileHandler fileHandler = new FileHandler(fs.getRemotePathForTmpLogFileForUser());
		fileHandler.setFormatter(new SimpleFormatter());
		log.addHandler(fileHandler);
	}

	private static void invokeMapperProcess(Simulation s, int loopID) {
		//String output =""; test output comand
		String bash=hadoopbindir+"/bin/hadoop jar "+fs.getRemoteSCUDHome()+"/SCUD.jar "+
				s.getName()+" "+
				fs.getHdfsUserPathSimulationByID(s.getId())+" "+
				s.getToolkit()+" "+
				s.getRunnableFile().getSimulation()+" "+
				fs.getHdfsUserPathSimulationLoopByIDsInputDATA(s.getId(), loopID)+" "+
				fs.getHdfsUserPathOutputLoopDIR(s.getId(), loopID)+" "+
				fs.getHdfsUserPathDescriptionOutputXML(s.getId())+" ";
		
		if(s.getLoop()){
			bash +=fs.getHdfsUserPathSimulationLoopByIDsInputXML(s.getId(), loopID )+
					" "+fs.getHdfsUserPathRatingFolderForSimLoop(s.getId(), loopID)+" ";
		}

		bash+=s.getLoop()+" "+ 
				s.getAuthor()+" "+ 
				s.getDescription();

		if(s.getLoop())
			bash += " "+s.getRunnableFile().getBashCommandForRunnableFunction()+" "
					+s.getRunnableFile().getRating();
		
		//System.out.println(bash);
		try {
			Process process = Runtime.getRuntime().exec(bash);
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodo supporto
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 * @throws SftpException 
	 */
	public static void launchSimulation(
			FileSystemSupport fs,
			Simulation sim
			) throws NumberFormatException, JSchException, IOException, SftpException{
		
		String simID = sim.getId();
		setSimulationStatus(fs,sim, Simulation.RUNNING);
		String tmpFolderPath = fs.getRemotePathForTmpFolderForUser();
		ScudRunnerUtils.mkdir(tmpFolderPath);
		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());
		//String hdfs_USER_HOME=fs.getHdfsUserPathHomeDir();
		
		
		//String hdfs_SimPath = fs.getHdfsUserPathSimulationByID(simID);

		String hdfs_domain_xml_file = fs.getHdfsUserPathDomainXML(simID);
		String currentLoopDirPathName;
		//String initial_inputPathname;
		String currentExecutionInputLoopPath;
		//String currentOutputPath;
		//String mapperInputLoopPath="";
		boolean doLoop=sim.getLoop();

		do{
			int idLoop = makeLoopDir(fs,simID);
			currentLoopDirPathName = fs.getHdfsUserPathSimulationLoopByIDs(simID, idLoop);
			//initial_inputPathname = fs.getHdfsUserPathDescriptionInputDirInputData(simID);
			currentExecutionInputLoopPath = fs.getHdfsUserPathSimulationLoopByIDsInputXML(simID, idLoop);
			//currentOutputPath = fs.getHdfsUserPathOutputLoopDIR(simID, idLoop);
			//mapperInputLoopPath =fs.getHdfsUserPathSimulationLoopByIDsInputDATA(simID, idLoop);

			if(doLoop /*& idLoop > 1*/){
				String hdfs_simulation_rating_folder = fs.getHdfsUserPathRatingFolderForSimLoop(simID, idLoop-1);
				String hdfs_simulation_loop_input_xml = fs.getHdfsUserPathSimulationLoopByIDsInputXML(simID, idLoop-1);
				
				SelectionFunction f= new SelectionFunction(
						hdfs_domain_xml_file,
						hdfs_simulation_loop_input_xml,
						sim.getRunnableFile().getSelection(), 
						hdfs_simulation_rating_folder, 
						currentExecutionInputLoopPath,
						sim.getRunnableFile().getBashCommandForRunnableFunction());
				
				doLoop=f.generateNewInput(fs);
				
			//	if(idLoop > 4) doLoop=false;
				
				if(!doLoop){
					
					if(removeLoopDir(fs,simID, idLoop))
						System.out.println("Deleted "+currentLoopDirPathName);
					setSimulationStatus(fs, sim, Simulation.FINISHED);
					log.info("Simulation "+simID+" terminated");
					return;
				}
			}

			log.info("Starting loop "+idLoop+" for Simulation "+simID);

			if (doLoop){
				String hdfs_input_xml = fs.getHdfsUserPathSimulationLoopByIDsInputXML(simID, idLoop);
				String hdfs_input_data = fs.getHdfsUserPathSimulationLoopByIDsInputDATA(simID, idLoop);
				ScudRunnerUtils.convertXmlToData(fs,hdfs_input_xml,hdfs_input_data);

			}
			else{
				String initial_xml_input_FileName = fs.getHdfsUserPathInputXML(simID);
				String hdfs_input_data = fs.getHdfsUserPathSimulationLoopByIDsInputDATA(simID, idLoop);
				String hdfs_input_xml = fs.getHdfsUserPathSimulationLoopByIDsInputXML(simID, idLoop);
				String tmpInputXmlFile = fs.getRemotePathForTmpFileForUser(tmpFolderName);
				ScudRunnerUtils.copyFileFromHdfs(fs,initial_xml_input_FileName, tmpInputXmlFile);
				ScudRunnerUtils.copyFileInHdfs(fs,tmpInputXmlFile, hdfs_input_xml);
				ScudRunnerUtils.convertXmlToData(fs,hdfs_input_xml,hdfs_input_data);
			}

			Runs r = setLoopProperty(fs,simID,idLoop,Loop.RUNNING);
			addLoopToSimulation(fs,sim,r);
			
			
			invokeMapperProcess(sim, idLoop);
			

			r = setLoopProperty(fs,simID,idLoop, Loop.FINISHED);
			addLoopToSimulation(fs,sim,r);
			
			log.info("Loop "+idLoop+" for Simulation "+simID+" is terminated");

		}while(doLoop);
		
		setSimulationStatus(fs,sim, Simulation.FINISHED);

		ScudRunnerUtils.rmr(tmpFolderPath);

		log.info("Simulation "+simID+" terminated");
	}
	
	
	/**
	 * Create loop directory
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 * @throws SftpException 
	 */
	private static int makeLoopDir(FileSystemSupport fs, String simID2) throws NumberFormatException, JSchException, IOException, SftpException{

	
		String tmpFolderPath = fs.getRemotePathForTmpFolderForUser();
		ScudRunnerUtils.mkdir(tmpFolderPath);
		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());
		
		
		String hdfsXMLPath = fs.getHdfsUserPathRunsXml(simID2);

		//String runsXmlFile = fs.getRemotePathForTmpFolderForUser();
		String runsXmlFile = fs.getRemotePathForTmpFileForUser(tmpFolderName);

		Runs runs = new Runs();
		int loopNumber=0;

		ScudRunnerUtils.copyFileFromHdfs(fs,hdfsXMLPath, runsXmlFile);

		runs = RunsParser.convertXMLToRuns(runsXmlFile);
		loopNumber = runs.getLoops().size()+1;
		Loop l = new Loop(loopNumber);
		l.setStatus(Loop.SUBMITTED);

		runs.addLoop(l);
		RunsParser.convertRunsToXML(runs, runsXmlFile);
		ScudRunnerUtils.rmrFromHdfs(fs,hdfsXMLPath);
		ScudRunnerUtils.copyFileInHdfs(fs,runsXmlFile, hdfsXMLPath);

		String loopPath=fs.getHdfsUserPathSimulationLoopByIDs(simID2, loopNumber);

		
		if(ScudRunnerUtils.hdfs_mkdirp(fs,loopPath))
			log.info("Created "+loopPath);



		String hdfs_loop_input_dir = fs.getHdfsUserPathInputLoopDIR(simID2, loopNumber);
		if(ScudRunnerUtils.hdfs_mkdirp(fs,hdfs_loop_input_dir))
			log.info("Created successfully"+ hdfs_loop_input_dir);

		ScudRunnerUtils.rmr(tmpFolderPath);

		return loopNumber;
	}
	
	
	private static boolean removeLoopDir(FileSystemSupport fs,String simID, int loopId) throws NumberFormatException, JSchException, IOException, SftpException{

		String tmpFolderPath = fs.getRemotePathForTmpFolderForUser();
		
		ScudRunnerUtils.mkdir(tmpFolderPath);
		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());
		
		
		String hdfsXMLPath = fs.getHdfsUserPathRunsXml(simID);
		
		String hdfs_loopFolder = fs.getHdfsUserPathSimulationLoopByIDs(simID, loopId);

		String localXMLPath = fs.getRemotePathForTmpFileForUser(tmpFolderName);

		Runs runs = new Runs();

		ScudRunnerUtils.copyFileFromHdfs(fs,hdfsXMLPath, localXMLPath);

		runs = RunsParser.convertXMLToRuns(localXMLPath);
		Loop toRemove=null;
		for(Loop l :runs.getLoops()){
			if(l.getId()==loopId){
				toRemove = l;
				break;
			}
		}
		runs.getLoops().remove(toRemove);

		RunsParser.convertRunsToXML(runs, localXMLPath);
		ScudRunnerUtils.rmrFromHdfs(fs,hdfsXMLPath);

		ScudRunnerUtils.copyFileInHdfs(fs,localXMLPath, hdfsXMLPath);

		boolean result=ScudRunnerUtils.rmrFromHdfs(fs,hdfs_loopFolder);

		ScudRunnerUtils.rmr(tmpFolderPath);

		return result;
	}
	
	private static Runs setLoopProperty(FileSystemSupport fs, String simID, int idLoop,
			String status) throws NumberFormatException, JSchException, IOException, SftpException {

		String tmpFolderPath = fs.getRemotePathForTmpFolderForUser();
		ScudRunnerUtils.mkdir(tmpFolderPath);
		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());

		String hdfs_runs_xml=fs.getHdfsUserPathRunsXml(simID);

		String tmpRunsFile= fs.getRemotePathForTmpFileForUser(tmpFolderName);

		String hdfs_input_xml_file = fs.getHdfsUserPathSimulationLoopByIDsInputXML(simID, idLoop);
		
		
		if(ScudRunnerUtils.copyFileFromHdfs(fs,hdfs_runs_xml, tmpRunsFile))
			ScudManager.log.info("Copied successfully "+hdfs_runs_xml+" to "+tmpRunsFile);

		Runs r = RunsParser.convertXMLToRuns(tmpRunsFile);
		for(Loop l : r.getLoops())
			if(l.getId()==idLoop){
				l.setStatus(status);
				if(status.equalsIgnoreCase(Loop.RUNNING)){
					l.setStartTime();

					String tmpInputXmlFile = fs.getRemotePathForTmpFileForUser(tmpFolderName);
					
					if(ScudRunnerUtils.copyFileFromHdfs(fs,hdfs_input_xml_file, tmpInputXmlFile))
						ScudManager.log.info("Copied successfully "+hdfs_runs_xml+" to "+tmpInputXmlFile);

					Inputs i = l.convertXMLInputToInput(tmpInputXmlFile);

					l.setInputs(i);
				}else{
					if(status.equalsIgnoreCase(Loop.FINISHED)){
						l.setStopTime();

						String tmpDir = fs.getRemotePathForTmpFolderForUser();
						String hdfs_loop_output = fs.getHdfsUserPathOutputLoopDIR(simID, idLoop);
						ScudRunnerUtils.mkdir(tmpDir);
						if(ScudRunnerUtils.copyFilesFromHdfs(fs,hdfs_loop_output, tmpDir))	
							log.info("Copied "+hdfs_loop_output+" to "+tmpDir);

						Outputs out = new Outputs();
						out.setOutput_list(new ArrayList<Output>());
						ArrayList<Output> out_list = new ArrayList<Output>();

						File outputFolder = new File(tmpDir);

						for(File f: outputFolder.listFiles()){
							if(f.isFile()){
								int i = f.getName().lastIndexOf('.');
								if (i > 0) {
									String extension = f.getName().substring(i+1);

									if(extension.equalsIgnoreCase("xml")){
										Output o = l.convertXMLOutputToOutput(f);
										out_list.add(o);
									}
								}
							}
						}
						out.setOutput_list(out_list);
						l.setOutputs(out);
						ScudRunnerUtils.rmr(tmpDir);
					}
				}
			}
		RunsParser.convertRunsToXML(r, tmpRunsFile);
		ScudRunnerUtils.rmrFromHdfs(fs,hdfs_runs_xml);
		
		if(ScudRunnerUtils.copyFileInHdfs(fs,tmpRunsFile, hdfs_runs_xml))
			ScudManager.log.info("Copied "+tmpRunsFile+" to "+hdfs_runs_xml);


		ScudRunnerUtils.rmr(tmpFolderPath);

		return r;
	}
	
	private static void addLoopToSimulation(FileSystemSupport fs, Simulation sim, Runs runs) throws NumberFormatException, JSchException, IOException, SftpException {

		String tmpFolderPath =fs.getRemotePathForTmpFolderForUser();
		ScudRunnerUtils.mkdir(tmpFolderPath);

		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());
		
		String hdfsXmlSim=fs.getHdfsUserPathSimulationXMLFile(sim.getId()); 

		String localFile = fs.getRemotePathForTmpFileForUser(tmpFolderName);
		
		/*if(ScudRunnerUtils.copyFileFromHdfs(fs,hdfsXmlSim, localFile))
			SCUDRUNNER.log.info("Copied "+hdfsXmlSim+" to "+localFile);*/

		//Simulation sim = SimulationParser.convertXMLToSimulation(localFile);

		sim.setRuns(runs);
		
		SimulationParser.convertSimulationToXML(sim, localFile);
		if(ScudRunnerUtils.ifExists(fs, hdfsXmlSim)){
			if(ScudRunnerUtils.rmrFromHdfs(fs,hdfsXmlSim))
				SCUDRUNNER.log.info("Removed successfully "+hdfsXmlSim);
		}
		ScudRunnerUtils.copyFileInHdfs(fs,localFile, hdfsXmlSim);

		ScudRunnerUtils.rmr(tmpFolderPath);

	}
	
	private static void setSimulationStatus(FileSystemSupport fs,
			Simulation sim, String status){

		String tmpFolderPath =fs.getRemotePathForTmpFolderForUser();
		ScudRunnerUtils.mkdir(tmpFolderPath);
		
		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());

		String localSimXml =fs.getRemotePathForTmpFileForUser(tmpFolderName);

		//String hdfs_simulation_xml = fs.getHdfsUserPathSimulationsXml();
		String hdfsXmlSim=fs.getHdfsUserPathSimulationXMLFile(sim.getId()); 
		
		/*if(ScudRunnerUtils.copyFileFromHdfs(fs,hdfsXmlSim, localSimXml))
			log.info("Copied successfully from "+hdfsXmlSim +" to "+localSimXml);*/

		//Simulation s = SimulationParser.convertXMLToSimulation(localSimXml);
		sim.setState(status);
		
		/*for(Simulation s: list.getSimulations())
			if(s.getId().equals(simID)){
				s.setState(status);
				break;
			}*/

		//SimulationParser.convertSimulationsToXML(list, localSimXml);
		SimulationParser.convertSimulationToXML(sim, localSimXml);

		/** LOCK ***/

		if(ScudRunnerUtils.ifExists(fs, hdfsXmlSim)){
			if(ScudRunnerUtils.rmrFromHdfs(fs,hdfsXmlSim))
				log.info("Removed "+hdfsXmlSim+" successfully");
		}
		
		if(ScudRunnerUtils.copyFileInHdfs(fs,localSimXml, hdfsXmlSim))
			log.info("Copied "+localSimXml+" to "+hdfsXmlSim);
		
		ScudRunnerUtils.rmr(tmpFolderPath);
		
	}
	
	
	public static void main(String[] params){


		SCUDRUNNER sr = new SCUDRUNNER();
		CmdLineParser parser = new CmdLineParser(sr);
		try {
			parser.parseArgument(params);
		} catch (CmdLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sr.setFileSystem();
		try {
			sr.setLogFile();
			Simulation s = ScudRunnerUtils.getSimulationById(sr.fs, sr.simID);
			sr.launchSimulation(fs, s);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
