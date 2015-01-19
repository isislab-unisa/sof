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
package it.isislab.scud.core.engine.hadoop.sshclient.connection;

import it.isislab.scud.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.RunnableFile;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Runs;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.RunsParser;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.SimulationParser;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulations;
import it.isislab.scud.core.exception.ParameterException;
import it.isislab.scud.core.model.parameters.xsd.domain.Domain;
import it.isislab.scud.core.model.parameters.xsd.input.Inputs;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import org.xml.sax.SAXException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


public class ScudManager {
	//private static final String HDFS_HOME=File.separator+"SCUD"+File.separator;
	//private static final String SIMULATION_LIST_FILENAME="simulations.xml";
	//private static final String DESCRIPTION_FOLDER="description";
	//private static final String EXECUTION_FOLDER="execution";
	//private static final String LOOP_LIST_FILENAME="runs.xml";
	//public static final String INPUT_XML_FILENAME="input.xml";
	//public static final String INPUT_TMP_FILENAME="input.tmp";
	//public static final String OUTPUT_XML_FILENAME="output.xml";
	//public static final String DOMAIN_XML_FILENAME="domain.xml";
	//private static final String LOOP_LIST_PATHNAME=EXECUTION_FOLDER+"/"+LOOP_LIST_FILENAME;
	//private static final String MODEL_FOLDER_HOME=DESCRIPTION_FOLDER+"/model";
	//private static final String DOMAIN_FOLDER_HOME=DESCRIPTION_FOLDER+File.separator+"domain";
	/*private static final String INPUT_FOLDER_HOME=DESCRIPTION_FOLDER+File.separator+"input";
	private static final String OUTPUT_FOLDER_HOME=DESCRIPTION_FOLDER+File.separator+"output";
	private static final String RATING_FOLDER_HOME=DESCRIPTION_FOLDER+File.separator+"rating";
	private static final String SELECTION_FOLDER_HOME=DESCRIPTION_FOLDER+File.separator+"selection";*/
	//private static final String LOOP_FOLDER_NAME=EXECUTION_FOLDER+File.separator+"loop";
	//	public static final String NETLOGO_MODEL="netlogo";
	//	public static final String MASON_MODEL="mason";
	//	public static final String GENERIC_MODEL="generic";

	public static final boolean ENABLE_SIMULATION_OPTIMIZATION=true;
	public static final boolean DISABLE_SIMULATION_OPTIMIZATION=false;

	/*public static final String SIMULATION_OPTIMIZATION="loop";
	public static final String ONE_SHOT="one";*/

	/*public static String SIMULATION_TYPE_MASON="M";
	public static String SIMULATION_TYPE_NETLOGO="N";
	public static String SIMULATION_TYPE_GENERIC="G";*/
	public static FileSystemSupport fs = null;

	//private static final String TEMPORARY_FOLDER="SCUD-";

	//public static final String TEMPORARY_FOLDER=getLocalTemporaryFolder();

	public static Logger log = Logger.getLogger(ScudManager.class.getName());


	public ScudManager() {	}

	/**
	 * Create a connection  
	 * 
	 * @param username 
	 * @param host
	 * @param password
	 * @param HADOOP_HOME 
	 * @return an environment session
	 * @throws JSchException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static EnvironmentSession connect(String username, String host, String password, String HADOOP_HOME,Integer PORT,InputStream SCUD_STREAM,InputStream SCUD_RUNNER_STREAM) throws JSchException, NumberFormatException, IOException {

		EnvironmentSession s=new EnvironmentSession(username, host, password,HADOOP_HOME,PORT);
		//if(s==null) return null;
		log.info("Verify the SCUD Environment...");
		InputStream srcSCUD=null;
		InputStream srcSCUDR=null;
		try{
			/************************QUESTO FUNZIONA PER RAP!!!!!*************************/
			//			srcSCUD=ConnectionSSH.class.getResourceAsStream("//SCUD.jar");
			//			srcSCUDR=ConnectionSSH.class.getResourceAsStream("//SCUD-RUNNER.jar");
			/*****************************************************************************/
			srcSCUD=SCUD_STREAM;
			srcSCUDR=SCUD_RUNNER_STREAM;
			//		srcSCUD =RWT.getResourceManager().getLocation( "executable"+File.separator+"SCUD.jar" ); 
			//		srcSCUDR = RWT.getResourceManager().getLocation( "executable"+File.separator+"SCUD-RUNNER.jar" ); 
		}catch(Exception e)
		{
			System.err.println("PATH"+srcSCUD);
			e.printStackTrace();
		}


		if(srcSCUD==null || srcSCUDR==null) 
		{
			log.info("Problems in loading SCUD.jar and SCUD-RUNNER.jar");
			return null;
		}


		if(s!=null)
		{	
			if(!HadoopFileSystemManager.ifExists(s, fs.getHdfsPathHomeDir()))
			{
				if(HadoopFileSystemManager.mkdir(s, fs.getHdfsPathHomeDir()))
					log.info("Created root directory "+fs.getHdfsPathHomeDir());
				else{
					log.info("Unable to create root directory "+fs.getHdfsPathHomeDir());
					return null;
				}	
			}


			if(!ScudManager.existsUser(s,username))
				if(ScudManager.addUser(s,username))
				{
					log.info("Created user on hadoop and on Remote for name"+username);
					/*log.info("Copying SCUD.jar");
					log.info("Copying SCUD-RUNNER.jar");*/

					HadoopFileSystemManager.makeRemoteTempFolder(s, fs.getRemoteSCUDtmpForUser());
					/*HadoopFileSystemManager.sftpFromLocalToHost(s,srcSCUD, fs.getRemotePathForFile("SCUD.jar"));
					HadoopFileSystemManager.sftpFromLocalToHost(s,srcSCUDR, fs.getRemotePathForFile("SCUD-RUNNER.jar"));
					 */
				}
				else{
					log.severe("Unable to create new user and "+fs.getRemoteSCUDtmpForUser());
					return null;
				}

			if(!ScudManager.existOnHost(s, fs.getRemoteSCUDHome()))
			{
				HadoopFileSystemManager.makeRemoteTempFolder(s, fs.getRemoteSCUDtmpForUser());
				log.info("Created Remote SCUD and user "+username);
			}

			if(!ScudManager.existOnHost(s, fs.getRemotePathForFile("SCUD.jar"))
					||
					!ScudManager.existOnHost(s, fs.getRemotePathForFile("SCUD-RUNNER.jar")))
			{
				log.info("Copying SCUD-RUNNER.jar");
				log.info("Copying SCUD.jar");


				/*String remoteSCUDHome = fs.getRemoteSCUDHome();
				HadoopFileSystemManager.makeHostTempFolder(s, remoteSCUDHome);*/
				HadoopFileSystemManager.sftpFromClientToRemote(s,srcSCUD, fs.getRemotePathForFile("SCUD.jar"));
				HadoopFileSystemManager.sftpFromClientToRemote(s,srcSCUDR, fs.getRemotePathForFile("SCUD-RUNNER.jar"));

				//return s;
			} 

			//if(!ScudManager.existOnHost(s, HadoopFileSystemManager.TEMPORARY_STATIC_FOLDER))
			if(!ScudManager.existOnHost(s, fs.getRemoteSCUDtmpForUser()))
			{	
				HadoopFileSystemManager.makeRemoteTempFolder(s, fs.getRemoteSCUDtmpForUser());
				log.info("Created root temp directory "+fs.getRemoteSCUDtmpForUser());

			}
			
			if(!(new File(fs.getClientSCUDtmp()).exists()))
				makeLocalTemporaryFolder(fs.getClientSCUDtmp());
			return s;
		}
		return null;
	}
	
	public static void disconnect(EnvironmentSession session){
		removeLocalTemporaryFolder(fs.getClientSCUDHome());
		session.getSession().disconnect();
	}

	/**
	 * Create main folder of simulation
	 * kind simulation : one 
	 * 
	 * @param session
	 * @param username
	 * @param toolkit
	 * @param simulation_name
	 * @param input_filename
	 * @param output_description_filename
	 * @param description_simulation
	 * @param executable_simulation_filename
	 * @param clientHomeDir
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 * @throws SftpException 
	 */
	public static Simulation makeSimulationFolder(EnvironmentSession session,
			String toolkit,
			String simulation_name,
			String input_filename,
			String output_description_filename,
			String description_simulation,
			String executable_simulation_filename) 
					throws ParserConfigurationException, SAXException, IOException, TransformerException, NumberFormatException, JSchException, SftpException{

		/**crea cartella temporanea lato client*/
		/****************AIUTO****************/
		//makeLocalTemporaryFolder(fs.getClientPathForTmpFolder());


		
		
		/**path file simulations.xml utente hdfs*/
		//String hdfs_path_list_sim = fs.getHdfsUserPathSimulationsXml();

		/**path file simulations.xml utente lato client*/
		String tmp_sim_xml_file = fs.getClientPathForTmpFile();

		//LOCK simulations.xml
		//Simulations simListFile = new Simulations();
		
		
		/*if(HadoopFileSystemManager.ifExists(session, hdfs_path_list_sim)){
			if(HadoopFileSystemManager.copyFromHdfsToClient(session, hdfs_path_list_sim, tmp_path_list_sim))
				log.info("Copied successfully from "+hdfs_path_list_sim+" to "+tmp_path_list_sim);

			simListFile = SimulationParser.convertXMLToSimulations(tmp_path_list_sim);
		}else{
			SimulationParser.convertSimulationsToXML(simListFile, tmp_path_list_sim);
		}*/
		
		//id simulation xml file
		String idSimXmlFile = getSimID();
				//path for simulation xml file
		String hdfsSimXmlFile = fs.getHdfsUserPathSimulationXMLFile(idSimXmlFile);
		
		//int simulationID=simListFile.getSimulations().size()+1;

		/**path simulazione utente su hdfs (comprende l'sim_ID)*/
		String hdfs_sim_dir= fs.getHdfsUserPathSimulationByID(idSimXmlFile);

		/**path file runs.xml per la data simulazione HDFS*/
		//String hdfs_runXmlFile_path = hdfs_sim_dir+File.separator+LOOP_LIST_PATHNAME;
		String hdfs_runXmlFile_path = fs.getHdfsUserPathRunsXml(idSimXmlFile);

		/**Path output directory su HDFS*/
		//String hdfs_output_folder = hdfs_sim_dir+File.separator+OUTPUT_FOLDER_HOME;
		String hdfs_output_folder = fs.getHdfsUserPathDescriptionOutputDir(idSimXmlFile);

		/**Path input directory su HDFS*/
		//String hdfs_input_folder = hdfs_sim_dir+File.separator+INPUT_FOLDER_HOME;
		String hdfs_input_folder = fs.getHdfsUserPathDescriptionInputDir(idSimXmlFile);

		//Simulation s = new Simulation(simulationID);
		Simulation sim = new Simulation(idSimXmlFile);
		sim.setName(simulation_name);
		sim.setAuthor(session.getUsername());
		sim.setDescription(description_simulation);
		sim.setCreationTime();
		sim.setState(Simulation.CREATED);
		sim.setToolkit(toolkit);
		sim.setLoop(DISABLE_SIMULATION_OPTIMIZATION);
		RunnableFile f = new RunnableFile();
		String execFileName = executable_simulation_filename.substring(executable_simulation_filename.lastIndexOf(File.separator)+1, executable_simulation_filename.length());
		//f.setSimulation(hdfs_sim_dir+File.separator+execFileName);
		f.setSimulation(fs.getHdfsUserPathSimulationExeForId(idSimXmlFile, execFileName));
		sim.setRunnableFile(f);
		//simListFile.addSimulation(s);

		/**
		 * create simulation folder
		 */
		if(HadoopFileSystemManager.mkdir(session, hdfs_sim_dir))
			log.info("Created "+hdfs_sim_dir);

		//if(HadoopFileSystemManager.mkdir(session, hdfs_sim_dir+File.separator+DESCRIPTION_FOLDER))
		String hdfs_description_folder = fs.getHdfsUserPathDescriptionDirForSimId(idSimXmlFile);
		if(HadoopFileSystemManager.mkdir(session, hdfs_description_folder))
			log.info("Created "+hdfs_description_folder);


		//if(HadoopFileSystemManager.mkdir(session,  hdfs_sim_dir+File.separator+EXECUTION_FOLDER))
		String hdfs_execution_folder = fs.getHdfsUserPathExecutionDirForSimId(idSimXmlFile);
		if(HadoopFileSystemManager.mkdir(session,  hdfs_execution_folder))
			log.info("Created "+hdfs_execution_folder);



		/**
		 * copy executable file in sim_dir
		 */
		//if(HadoopFileSystemManager.copyFromLocalToHdfs(session,  executable_simulation_filename, hdfs_sim_dir))
		String hdfs_path_for_model_exec = fs.getHdfsUserPathDescriptionDirForSimId(idSimXmlFile);
		if(HadoopFileSystemManager.copyFromClientToHdfs(session,  executable_simulation_filename,hdfs_path_for_model_exec))
			log.info("Copied "+executable_simulation_filename +" in "+hdfs_path_for_model_exec);
		else 
			log.severe("Failed to copy " +executable_simulation_filename +" in "+hdfs_path_for_model_exec);



		/**
		 * create the input folder in simulation folder and copy from local the file <input_pathname>
		 */
		if(HadoopFileSystemManager.mkdir(session, hdfs_input_folder))
			log.info("Created "+hdfs_input_folder);


		//if(HadoopFileSystemManager.copyFromLocalToHdfs(session, input_filename, hdfs_input_folder+File.separator+INPUT_XML_FILENAME)){
		if(HadoopFileSystemManager.copyFromClientToHdfs(session, input_filename, fs.getHdfsUserPathInputXML(idSimXmlFile))){
			log.info("Copied: "+input_filename+" to "+hdfs_input_folder);

		}else{
			log.severe("Unable to copy : "+input_filename+" to "+hdfs_input_folder);
			return null;
		}

		/**
		 * create the output folder and copy it on hdfs
		 */
		if(HadoopFileSystemManager.mkdir(session, hdfs_output_folder))
			log.info("Created: "+hdfs_output_folder);

		//if(HadoopFileSystemManager.copyFromLocalToHdfs(session,  output_description_filename, hdfs_output_folder+File.separator+OUTPUT_XML_FILENAME))
		if(HadoopFileSystemManager.copyFromClientToHdfs(session,  output_description_filename, fs.getHdfsUserPathDescriptionOutputXML(idSimXmlFile)))
			log.info("Copied "+output_description_filename+" to "+hdfs_output_folder);


		//String tmpRunXmlFilePath = tmpFolderName+File.separator+LOOP_LIST_FILENAME;
		String tmpRunXmlFilePath = fs.getClientPathForTmpFile();

		Runs r = new Runs();
		RunsParser.convertRunsToXML(r, tmpRunXmlFilePath);

		if(HadoopFileSystemManager.copyFromClientToHdfs(session, tmpRunXmlFilePath, hdfs_runXmlFile_path))
			log.info("Copied "+tmpRunXmlFilePath+" to "+hdfs_runXmlFile_path);



		//UPDATE SIMULATIONS.XML FILE
		/**
		 * send the modified xml file to hdfs
		 */

		//SimulationParser.convertSimulationsToXML(simListFile, tmp_path_list_sim);
		SimulationParser.convertSimulationToXML(sim, tmp_sim_xml_file);

		
		//UNLOCK simulation.xml
		//if(HadoopFileSystemManager.deleteFile(session, hdfs_path_list_sim));

		if(!HadoopFileSystemManager.ifExists(session, fs.getHdfsUserPathSimulationsListDir())){
			if(HadoopFileSystemManager.mkdir(session, fs.getHdfsUserPathSimulationsListDir()))
				log.info("Created "+fs.getHdfsUserPathSimulationsListDir());
			else{
				log.severe("Unable to create "+fs.getHdfsUserPathSimulationsListDir());
			}
		}
		
		
		if(HadoopFileSystemManager.copyFromClientToHdfs(session, tmp_sim_xml_file, hdfsSimXmlFile))
			log.info("Copied "+hdfsSimXmlFile);
		else 
			log.info("Failed Copied "+hdfsSimXmlFile);


		//removeLocalTemporaryFolder(tmpFolderName);
		//removeLocalTemporaryFolder(fs.getClientSCUDHome());

		log.info("Simulation has been created.");
		return sim;
	}
	/**
	 * Create main folder of simulation
	 * kind simulation : loop 
	 * 
	 * @param session
	 * @param username
	 * @param toolkit
	 * @param simulation_name
	 * @param domain_pathname
	 * @param bashCommandForRunnableFunction
	 * @param output_description_filename
	 * @param executable_selection_function_filename
	 * @param executable_rating_function_filename
	 * @param description_simulation
	 * @param executable_simulation_filename
	 * @return 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static Simulation makeSimulationFolderForLoop(
			EnvironmentSession session,
			String toolkit,
			String simulation_name,
			String domain_pathname,
			String bashCommandForRunnableFunction,
			String output_description_filename,
			String executable_selection_function_filename,
			String executable_rating_function_filename,
			String description_simulation,
			String executable_simulation_filename) throws Exception,ParserConfigurationException, SAXException, IOException, TransformerException, NumberFormatException, JSchException{


		//String tmpFolderName = makeLocalTemporaryFolder(System.getProperty("java.io.tmpdir")+System.currentTimeMillis()+File.separator+HadoopManagerOnSSH.TEMPORARY_FOLDER+session.getUsername());
		//String tmpFolderName = makeLocalTemporaryFolder(homeDir+System.currentTimeMillis()+File.separator+HadoopFileSystemManager.TEMPORARY_FOLDER+session.getUsername());

		String tmpFolderName = fs.getClientPathForTmpFolder();
		//makeLocalTemporaryFolder(tmpFolderName);

		//String hdfs_user_dir = HDFS_HOME+session.getUsername();
		//String hdfs_user_dir = fs.getHdfsUserPathHomeDir();

		//String hdfs_sim_dir=hdfs_user_dir+File.separator+"SIM-";
		//String hdfs_path_list_sim = hdfs_user_dir+File.separator+SIMULATION_LIST_FILENAME;
		//String hdfs_path_list_sim = fs.getHdfsUserPathSimulationsXml();
		//String tmp_path_list_sim = tmpFolderName+File.separator+SIMULATION_LIST_FILENAME;
		
		String tmp_sim_xml_file= fs.getClientPathForTmpFile();

		//LOCK simulations.xml
		//Simulations simListFile = new Simulations();

		/*if(HadoopFileSystemManager.ifExists(session, hdfs_path_list_sim)){
			if(HadoopFileSystemManager.copyFromHdfsToClient(session, hdfs_path_list_sim, tmp_path_list_sim))
				log.info("Copied successfully from "+hdfs_path_list_sim+" to "+tmp_path_list_sim);

			simListFile = SimulationParser.convertXMLToSimulations(tmp_path_list_sim);
		}else{
			SimulationParser.convertSimulationsToXML(simListFile, tmp_path_list_sim);
		}*/


		//int simulationID=simListFile.getSimulations().size()+1;
		String simulationID=getSimID();

		String hdfsSimXmlFile = fs.getHdfsUserPathSimulationXMLFile(simulationID);

		//tmp_path_list_sim = tmpFolderName+File.separator+SIMULATION_LIST_FILENAME;
		String hdfs_sim_dir=fs.getHdfsUserPathSimulationByID(simulationID);
		//String hdfs_runXmlFile_path = hdfs_sim_dir+File.separator+LOOP_LIST_PATHNAME;
		String hdfs_runXmlFile_path = fs.getHdfsUserPathRunsXml(simulationID);
		//String hdfs_output_folder = hdfs_sim_dir+File.separator+OUTPUT_FOLDER_HOME;
		String hdfs_output_folder = fs.getHdfsUserPathDescriptionOutputDir(simulationID);
		//String hdfs_input_folder = hdfs_sim_dir+File.separator+INPUT_FOLDER_HOME;
		String hdfs_input_folder = fs.getHdfsUserPathDescriptionInputDir(simulationID);

		String hdfs_domain_xml_file = fs.getHdfsUserPathDomainXML(simulationID);


		Simulation s = new Simulation(simulationID);
		s.setName(simulation_name);
		s.setAuthor(session.getUsername());
		s.setDescription(description_simulation);
		s.setCreationTime();
		s.setState(Simulation.CREATED);
		s.setToolkit(toolkit);
		s.setLoop(ENABLE_SIMULATION_OPTIMIZATION);
		RunnableFile f = new RunnableFile();

		String execFileName = executable_simulation_filename.substring(executable_simulation_filename.lastIndexOf(File.separator)+1, executable_simulation_filename.length());
		f.setSimulation(fs.getHdfsUserPathSimulationExeForId(simulationID, execFileName));

		String selectionFileName = executable_selection_function_filename.substring(executable_selection_function_filename.lastIndexOf(File.separator)+1, executable_selection_function_filename.length());
		f.setSelection(fs.getHdfsUserPathSelectionExeForId(simulationID, selectionFileName));

		String ratingFileName = executable_rating_function_filename.substring(executable_rating_function_filename.lastIndexOf(File.separator)+1, executable_rating_function_filename.length());
		f.setRating(fs.getHdfsUserPathRatingExeForId(simulationID, ratingFileName));

		f.setBashCommandForRunnableFunction(bashCommandForRunnableFunction);

		s.setRunnableFile(f);
		//simListFile.addSimulation(s);

		/**
		 * create simulation folder
		 */
		if(HadoopFileSystemManager.mkdir(session, hdfs_sim_dir))
			log.info("Created "+hdfs_sim_dir);

		//if(HadoopFileSystemManager.mkdir(session, hdfs_sim_dir+File.separator+DESCRIPTION_FOLDER))
		if(HadoopFileSystemManager.mkdir(session, fs.getHdfsUserPathDescriptionDirForSimId(simulationID)))
			log.info("Created successfully "+fs.getHdfsUserPathDescriptionDirForSimId(simulationID));

		//if(HadoopFileSystemManager.mkdir(session,  hdfs_sim_dir+File.separator+EXECUTION_FOLDER))
		if(HadoopFileSystemManager.mkdir(session,  fs.getHdfsUserPathExecutionDirForSimId(simulationID)))
			log.info("Created successfully "+fs.getHdfsUserPathExecutionDirForSimId(simulationID));


		/**
		 * copy executable file in sim_dir
		 */
		if(HadoopFileSystemManager.copyFromClientToHdfs(session,  executable_simulation_filename, fs.getHdfsUserPathDescriptionDirForSimId(simulationID)))
			log.info("Copied successfully "+executable_simulation_filename +" in "+fs.getHdfsUserPathDescriptionDirForSimId(simulationID));
		else 
			log.severe("Failed to copy " +executable_simulation_filename +" in "+fs.getHdfsUserPathDescriptionDirForSimId(simulationID));

		/**
		 * copy SELECTION file in sim_dir
		 */
		if(HadoopFileSystemManager.copyFromClientToHdfs(session,  executable_selection_function_filename, fs.getHdfsUserPathDescriptionDirForSimId(simulationID)))
			log.info("Copied "+executable_selection_function_filename +" in "+fs.getHdfsUserPathDescriptionDirForSimId(simulationID));
		else 
			log.severe("Failed to copy " +executable_selection_function_filename +" in "+fs.getHdfsUserPathDescriptionDirForSimId(simulationID));

		/**
		 * copy RATING file in sim_dir
		 */
		if(HadoopFileSystemManager.copyFromClientToHdfs(session,  executable_rating_function_filename, fs.getHdfsUserPathDescriptionDirForSimId(simulationID)))
			log.info("Copied "+executable_rating_function_filename +" in "+fs.getHdfsUserPathDescriptionDirForSimId(simulationID));
		else 
			log.severe("Failed to copy " +executable_rating_function_filename +" in "+fs.getHdfsUserPathDescriptionDirForSimId(simulationID));

		/**
		 * copy DOMAIN file in sim_dir
		 */
		if(HadoopFileSystemManager.copyFromClientToHdfs(session, domain_pathname, hdfs_domain_xml_file))
			log.info("Copied "+domain_pathname +" to "+hdfs_domain_xml_file);

		/**
		 * create the input folder in simulation folder 
		 */
		if(HadoopFileSystemManager.mkdir(session, hdfs_input_folder))
			log.info("Created "+hdfs_input_folder);

		/**
		 * create the output folder and copy it on hdfs
		 */
		if(HadoopFileSystemManager.mkdir(session, hdfs_output_folder))
			log.info("Created "+hdfs_output_folder);

		if(HadoopFileSystemManager.copyFromClientToHdfs(session,  output_description_filename, fs.getHdfsUserPathDescriptionOutputXML(simulationID)))
			log.info("Copied successfully "+output_description_filename+" to "+hdfs_output_folder);


		//String tmpRunXmlFilePath = tmpFolderName+File.separator+LOOP_LIST_FILENAME;
		String tmpRunXmlFilePath = fs.getClientPathForTmpFile();

		Runs r = new Runs();
		RunsParser.convertRunsToXML(r, tmpRunXmlFilePath);

		if(HadoopFileSystemManager.copyFromClientToHdfs(session, tmpRunXmlFilePath, hdfs_runXmlFile_path))
			log.info("Copied successfully "+tmpRunXmlFilePath+" to "+hdfs_runXmlFile_path);



		//UPDATE SIMULATIONS.XML FILE
		/**
		 * send the modified xml file to hdfs
		 */

		//SimulationParser.convertSimulationsToXML(simListFile, tmp_path_list_sim);
		SimulationParser.convertSimulationToXML(s, tmp_sim_xml_file);




		//UNLOCK simulation.xml
		//if(HadoopFileSystemManager.deleteFile(session, hdfs_path_list_sim));
		
		if(!HadoopFileSystemManager.ifExists(session, fs.getHdfsUserPathSimulationsListDir())){
			
			if(HadoopFileSystemManager.mkdir(session, fs.getHdfsUserPathSimulationsListDir()))
				log.info("Created "+fs.getHdfsUserPathSimulationsListDir());
			else{
				log.severe("Unable to create "+fs.getHdfsUserPathSimulationsListDir());
			}
		}
		
		if(HadoopFileSystemManager.copyFromClientToHdfs(session, tmp_sim_xml_file, hdfsSimXmlFile))
			log.info("Copied "+hdfsSimXmlFile);
		
		/*(new File(tmp_sim_xml_file)).delete();
		(new File(tmpRunXmlFilePath)).delete();*/
		//removeLocalTemporaryFolder(tmpFolderName);

		log.info("Simulation has been created.");
		return s;
	}


	/**
	 *  Check if user exist 
	 * @param s
	 * @param username
	 * @return
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean existsUser(EnvironmentSession s, String username) throws NumberFormatException, JSchException, IOException{
		return HadoopFileSystemManager.ifExists(s, fs.getHdfsUserPathHomeDir());
	}

	/**
	 * Add a user 
	 * @param session
	 * @param username
	 * @return
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean addUser(EnvironmentSession session, String username) throws NumberFormatException, JSchException, IOException{
		if(HadoopFileSystemManager.ifExists(session, fs.getHdfsUserPathHomeDir()))
			return false;
		else {
			return HadoopFileSystemManager.mkdir(session, fs.getHdfsUserPathHomeDir());
		}
	}


	/*public static Simulation getSimulationDatabyId(EnvironmentSession session,String username, String simID) throws NumberFormatException, JSchException, IOException{

		//String tmpFolderName = makeLocalTemporaryFolder(HadoopFileSystemManager.TEMPORARY_FOLDER+username+"-"+simID+"/simulation");

		//String tmpFolderName = HadoopFileSystemManager.makeHostTempFolder(session, HadoopFileSystemManager.TEMPORARY_FOLDER+username+"-"+simID+"/simulation");
		String tmpFolderName = makeLocalTemporaryFolder(fs.getClientSCUDtmp());

		//String hdfsFile=HDFS_HOME+username+"/"+SIMULATION_LIST_FILENAME;
		//String hdfsFile=fs.getHdfsUserPathSimulationsXml();
		String hdfsFile=fs.getHdfsUserPathSimulationXMLFile(simID);

		//String localFile = tmpFolderName+File.separator+SIMULATION_LIST_FILENAME;
		String localFile = fs.getClientPathForTmpFile();


		if(HadoopFileSystemManager.copyFromHdfsToClient(session, hdfsFile, localFile))
			ScudManager.log.info("Copied "+hdfsFile+" to "+localFile);

		Simulation sim = SimulationParser.convertXMLToSimulation(localFile);
		Simulations list = SimulationParser.convertXMLToSimulations(localFile);
		Simulation sim=null;
		for(Simulation s: list.getSimulations())
			if(s.getId().equals(simID)){
				sim = s;
				break;
			}
		removeLocalTemporaryFolder(tmpFolderName);


		return sim;
	}
*/


	/*public static Simulations getSimulationsData(EnvironmentSession session, String username) throws NumberFormatException, JSchException, IOException{

		//String tmpFolderName = makeLocalTemporaryFolder(HadoopFileSystemManager.TEMPORARY_FOLDER+username+"-"+System.currentTimeMillis());

		//String tmpFolderName = HadoopFileSystemManager.makeHostTempFolder(session, HadoopFileSystemManager.TEMPORARY_FOLDER+username+"-"+System.currentTimeMillis());
		String tmpFolderName = makeLocalTemporaryFolder(fs.getClientPathForTmpFolder());

		//String hdfsFile=HDFS_HOME+username+File.separator+SIMULATION_LIST_FILENAME;
		String hdfsFile=fs.getHdfsUserPathSimulationsXml();

		//String localFile = tmpFolderName+File.separator+SIMULATION_LIST_FILENAME;
		String localFile = fs.getClientPathForTmpFile();

		if(!HadoopFileSystemManager.copyFromHdfsToClient(session, hdfsFile, localFile)) return null;
		Simulations list = SimulationParser.convertXMLToSimulations(localFile);

		removeLocalTemporaryFolder(tmpFolderName);

		return list;
	}*/


	/**
	 * Get path of temp folder 
	 */  
	private static final String makeLocalTemporaryFolder(String folderName){
		//File tmp = new File(System.getProperty("user.dir")+"/temp");
		File tmp=null;
		try {
			//			tmp = new File(System.getProperty("user.dir")+File.separator+SshHadoopEnvTool.TEMPORARY_FOLDER);
			tmp = new File(folderName);
			if(tmp.exists())
				return tmp.getAbsolutePath();
			if(!(tmp.mkdirs()))
				throw new IOException("Could not create temp directory: " + tmp.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		return tmp.getAbsolutePath();
	}

	private static final boolean removeLocalTemporaryFolder(String folderName){
		//File tmp = new File(System.getProperty("user.dir")+"/temp");
		File tmp = new File(folderName);
		if(!tmp.exists())
			return true;

		if(!tmp.isDirectory()){
			if(tmp.delete()){
				return true;
			}
		}else{
			for(File f: tmp.listFiles())
				removeLocalTemporaryFolder(f.getAbsolutePath());
			if(tmp.delete()){
				return true;
			}

		}
		return false;
	}

	public static boolean runAsynchronousSimulation(EnvironmentSession session,Simulation simulation) 
	{
		if(simulation.getState().equals(Simulation.RUNNING)){
			System.out.println("Simulation already running");
			return true;
		}

		
		//only one
		String simID=simulation.getId();
		String author=simulation.getAuthor();
		String hdfsRootPath=fs.getHdfsRootPath();
		String rootRemotePath=fs.getRemoteRootPath();
		String hadoop_home_path=session.getHadoop_home_path();

		//one and loop
		/*int idLoop=1;
		 * String nameSim=simulation.getName();
		String homeSim=fs.getHdfsUserPathSimulationByID(simID);
		String toolkit=simulation.getToolkit();
		String simRunFile=simulation.getRunnableFile().getSimulation();
		String mapperInputData=fs.getHdfsUserPathSimulationLoopByIDsInputDATA(simID, idLoop);
		String mapperOutputPathExecution=fs.getHdfsUserPathOutputLoopDIR(simID, idLoop)	;			
		String descrOutputXml=fs.getHdfsUserPathDescriptionOutputXML(simID);
		boolean isLoop=simulation.getLoop();
		String simDescr=simulation.getDescription();
		String host=session.getHost();
		int port=session.getPort();
		String clientHomePath=fs.getClientSCUDHome();*/

		String cmd=null;

		
		String remoteJavaBin=fs.getRemoteJavaBinPath();
		remoteJavaBin=remoteJavaBin.trim();
		
		try{
			cmd=remoteJavaBin+"java -jar "+fs.getRemoteSCUDHome()+"/SCUD-RUNNER.jar -u "+author+" "
					+"-simid "+simID+" "
					//+"-loopid "+idLoop+" "
					+"-hadoopbin "+hadoop_home_path+" "
					+"-hdfsrootdir "+hdfsRootPath+" "
					+"-remoterootdir "+rootRemotePath+" &"; 
			
			
			setSimulationProcess(session,simulation,cmd);
			ChannelExec channel;
     
			channel = (ChannelExec)session.getSession().openChannel("exec");
			
			System.out.println(cmd);
			((ChannelExec) channel).setCommand(cmd);
			channel.setInputStream(null);

			channel.setOutputStream(null);
			channel.setErrStream(null);
			channel.setPty(false);
			channel.connect();
			
//			System.out.println(channel.getExitStatus());
			
			String output ="";
			InputStream in = channel.getInputStream();
			//((ChannelExec) channel).setErrStream(System.err);
			channel.connect();
			
			channel.disconnect();
			
			return true;
		}catch(Exception e)
		{
			return false;

		}
	}


	private static void setSimulationProcess(EnvironmentSession session,Simulation simulation, String cmd) {
		String hdfsSimXmlFile = fs.getHdfsUserPathSimulationXMLFile(simulation.getId());
		String tmpsimXmlFile = fs.getClientPathForTmpFile();
		try {
			if(HadoopFileSystemManager.copyFromHdfsToClient(session, hdfsSimXmlFile, tmpsimXmlFile))
				log.info("Copied "+hdfsSimXmlFile+" successfully");
			Simulation s = SimulationParser.convertXMLToSimulation(tmpsimXmlFile);
			cmd = cmd.substring(0, cmd.lastIndexOf("&")-1); //remove &
			s.setProcessName(cmd);
			SimulationParser.convertSimulationToXML(s, tmpsimXmlFile);
			
			if(HadoopFileSystemManager.removeFile(session, hdfsSimXmlFile))
				log.info("Deleted "+hdfsSimXmlFile+" successfully");
			
			if(HadoopFileSystemManager.copyFromClientToHdfs(session, tmpsimXmlFile, hdfsSimXmlFile))
				log.info("Copied "+hdfsSimXmlFile+" successfully");
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
	
	public static void setSimulationStatus(EnvironmentSession session, Simulation simulation, String status) {
		String hdfsSimXmlFile = fs.getHdfsUserPathSimulationXMLFile(simulation.getId());
		String tmpsimXmlFile = fs.getClientPathForTmpFile();
		try {
			simulation.setState(status);
			SimulationParser.convertSimulationToXML(simulation, tmpsimXmlFile);
			
			if(HadoopFileSystemManager.ifExists(session, hdfsSimXmlFile)){
				if(HadoopFileSystemManager.removeFile(session, hdfsSimXmlFile))
					log.info("Deleted "+hdfsSimXmlFile+" successfully");
			}
			
			if(HadoopFileSystemManager.copyFromClientToHdfs(session, tmpsimXmlFile, hdfsSimXmlFile))
				log.info("Copied "+hdfsSimXmlFile+" successfully");
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

	public static boolean existOnHost(EnvironmentSession session,String path)
	{
		String output ="";
		String cmd="if ls "+path+"; then echo 0; else echo -1; fi";
		//System.out.println("Start check "+path+ " "+cmd);
		try{
			Channel channel;

			channel = session.getSession().openChannel("exec");
			((ChannelExec) channel).setCommand(cmd);

			InputStream in = channel.getInputStream();
			//((ChannelExec) channel).setErrStream(System.err);
			channel.connect();
			byte[] b = new byte[1024];
			while(true){
				while(in.available() > 0){
					int i= in.read(b, 0, 1024);
					if (i < 0 )	break;
					output+=new String(b, 0, i).trim();
				}

				if(channel.isClosed()){
					if(in.available() > 0) continue;
					break;
				}
			}
			channel.disconnect();

			return !output.contains("-1");
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;

		}
	}


	public static boolean checkParamMakeSimulationFolder(String[] params) throws ParameterException{

		//params[0]/*TOOLKIT TYPE MASON - NETLOGO -GENERIC*/,
		//params[1],/*SIM NAME*/
		//params[2],/*INPUT.XML PATH*/ 
		//params[3],/*OUTPUT.XML PATH */
		//params[4],/*DESCRIPTION SIM*/
		//params[5],/*SIMULATION EXEC PATH */


		/*params[0]MODEL TYPE MASON - NETLOGO -GENERIC,
		params[1],SIM NAME
		params[2],domain_pathname 
		params[3],bashCommandForRunnableFunction 
		params[4],output_description_filename
		params[5],executable_selection_function_filename 
		params[6],executable_rating_function_filename
		params[7],description_simulation
		params[8],executable_simulation_filename*/


		if(   ! (   params[0].equalsIgnoreCase("netlogo") || 
				params[0].equalsIgnoreCase("mason") || 
				params[0].equalsIgnoreCase("generic")      )
				){

			throw new ParameterException("TOOLKIT NAME ERROR\n Use: [netlogo|mason|generic]\n");

		}
		JAXBContext context;
		if(params.length >6){
			Domain dom = new Domain();
			try {
				context = JAXBContext.newInstance(Domain.class);

				Unmarshaller unmarshal = context.createUnmarshaller();
				dom = (Domain) unmarshal.unmarshal(new File(params[2]));
			} catch (JAXBException e) {
				throw new ParameterException("Invalid file DOMAIN.xml");
			}
		}else{
			Inputs i = new Inputs();

			try {
				context = JAXBContext.newInstance(Inputs.class);

				Unmarshaller unmarshal = context.createUnmarshaller();
				i = (Inputs) unmarshal.unmarshal(new File(params[2]));
			} catch (JAXBException e) {
				throw new ParameterException("Invalid file INPUT.xml");
			}
		}

		Output out = new Output();
		try {
			context = JAXBContext.newInstance(Output.class);

			Unmarshaller unmarshal = context.createUnmarshaller();
			if(params.length > 6)
				out = (Output) unmarshal.unmarshal(new File(params[4]));
			else
				out = (Output) unmarshal.unmarshal(new File(params[3]));
		} catch (JAXBException e) {
			throw new ParameterException("Invalid file OUTPUT.xml");
		}

		if(params[0].equalsIgnoreCase("netlogo"))
			if(params.length > 6){
				if(!params[8].endsWith(".nlogo"))
					throw new ParameterException("Invalid file extension netlogo");
			}
			else{
				if(!params[5].endsWith(".nlogo"))
					throw new ParameterException("Invalid file extension netlogo");
			}
		if(params[0].equalsIgnoreCase("mason"))
			if(params.length > 6){
				if(!params[8].endsWith(".jar"))
					throw new ParameterException("Invalid file extension mason");
			}
			else{
				if(!params[5].endsWith(".jar"))
					throw new ParameterException("Invalid file extension mason");
			}

		return true;
	}


	public static FileSystemSupport setFileSystem(String hadoopInstallPath,String client_home_path, String hdfs_root_path, String remote_home_path,
			String remote_java_bin_path, String sshUsername){
		fs = new FileSystemSupport(hadoopInstallPath,client_home_path, hdfs_root_path, remote_home_path, remote_java_bin_path, sshUsername);
		return fs;
	}
	
	private static String getSimID(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
		    while(networks.hasMoreElements()) {
		      NetworkInterface network = networks.nextElement();
		      byte[] mac = network.getHardwareAddress();

		      if(mac != null) {
		        StringBuilder sb = new StringBuilder();
		        for (int i = 0; i < mac.length; i++) {
		          sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
		        }
		        return DigestUtils.md5Hex(sb.toString()+(System.currentTimeMillis()+""));
		      }
		    }
		    return null;
		  } catch (UnknownHostException e) {
		    e.printStackTrace();
		    return null;
		  } catch (SocketException e){
		    e.printStackTrace();
		    return null;
		  }
			
		//return System.currentTimeMillis()+"";
	}
	
	public static Simulations getSimulationsData(EnvironmentSession session){
		String pathListSims=fs.getHdfsUserPathSimulationsListDir();
		String tmpFolder = fs.getClientPathForTmpFolder();
		makeLocalTemporaryFolder(tmpFolder);
		try {
			if(!HadoopFileSystemManager.ifExists(session, pathListSims) || HadoopFileSystemManager.isEmpty(session, pathListSims)){
				/*System.out.println("I'm here!\n"
						+ pathListSims+" Exists: "+HadoopFileSystemManager.ifExists(session, pathListSims)+"\n"
								+ "isEmpty: "+HadoopFileSystemManager.isEmpty(session, pathListSims));*/
				return null;
			}
			
			HadoopFileSystemManager.copyFolderFromHdfsToClient(session, pathListSims, tmpFolder);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Simulations sims = SimulationParser.convertXmlListToSimulations(tmpFolder);
		removeLocalTemporaryFolder(tmpFolder);
		return sims;
	}

	/**
	 * parse the input command parameters
	 * @param params command parameters array
	 * @param indexBeginDescription index of description parameter
	 * @return
	 */
	public static String[] parseParamenters(String[] params, int indexBeginDescription) {

		//params[0]/*TOOLKIT TYPE MASON - NETLOGO -GENERIC*/,
		//params[1],/*SIM NAME*/
		//params[2],/*INPUT.XML PATH*/ 
		//params[3],/*OUTPUT.XML PATH */
		//params[4],/*DESCRIPTION SIM*/
		//params[5],/*SIMULATION EXEC PATH */
		ArrayList<String> newParams =new ArrayList<>();
		String description = new String();

		for (int i = 0; i < indexBeginDescription; i++) 
				newParams.add(params[i]);

		int indexEndDescription = 0;

		if(!params[indexBeginDescription].startsWith("\"") && (params.length==6 || params.length==9))
			return params;

		if(params[indexBeginDescription].startsWith("\"")){
			if(params[indexBeginDescription].endsWith("\"")){
				description = params[indexBeginDescription].substring(1, params[indexBeginDescription].lastIndexOf("\""));
				indexEndDescription=indexBeginDescription+1;
				newParams.add(description);
			}else{
				description = params[indexBeginDescription].substring(1, params[indexBeginDescription].length())+" ";
				//esclude the last one parameter (-1)
				for(int i=indexBeginDescription+1; i<params.length-1; i++){
					if(params[i].endsWith("\"")){
						description += params[i].substring(0, params[i].lastIndexOf("\""));
						indexEndDescription = i+1;
						newParams.add(description);
					}
					else{
						description += params[i]+" ";
					}

				}
			}
		}
		newParams.add(params[indexEndDescription]);
		

		return newParams.toArray(new String[newParams.size()]);
	}

	private static void zipDir(String zipFileName, String dir) throws Exception {
		File dirObj = new File(dir);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		log.info("Creating : " + zipFileName);
		addDir(dirObj, out);
		out.close();
	}

	private static void addDir(File dirObj, ZipOutputStream out) throws IOException {
		File[] files = dirObj.listFiles();
		byte[] tmpBuf = new byte[1024];

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				addDir(files[i], out);
				continue;
			}
			
			FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
			System.out.println("Zipping: " + files[i].getName());
			out.putNextEntry(new ZipEntry(files[i].getAbsolutePath()));
			int len;
			while ((len = in.read(tmpBuf)) > 0) {
				out.write(tmpBuf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
	}

	/**
	 * download the simulation's folder from hdfs filesystem in zip format
	 * @param session
	 * @param simID simulation id
	 * @param dowload_client_path destination download directory (client side)
	 */
	public static void downloadSimulation(EnvironmentSession session, String simID,String dowload_client_path){
		String hdfs_sim_path = fs.getHdfsUserPathSimulationByID(simID);
		String client_tmp_file_path = fs.getClientPathForTmpFile();

		try {
			if(!HadoopFileSystemManager.ifExists(session, hdfs_sim_path)){
				System.err.println("Simulation SIM-"+simID+" not exists");
				return;
			}
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String client_tmp_download = fs.getClientPathForTmpFolder();
		makeLocalTemporaryFolder(client_tmp_download);

		try {
			HadoopFileSystemManager.downloadFolderFromHdfsToClient(session, hdfs_sim_path, client_tmp_download);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		String fileZipName = session.getUsername()+"-SIM"+simID+".zip";
		File zip = new File(dowload_client_path+File.separator+fileZipName);
		if(zip.exists())
			zip.delete();
		try {
			zipDir(client_tmp_file_path, client_tmp_download);
			//makeLocalTemporaryFolder(dowload_client_path);
			FileUtils.moveFile(new File(client_tmp_file_path), new File(dowload_client_path+File.separator+fileZipName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		removeLocalTemporaryFolder(fs.getClientSCUDHome());
	}

}
