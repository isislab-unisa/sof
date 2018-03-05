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
package it.isislab.sof.core.engine.hadoop.sshclient.utils.selection;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.FilenameFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.input.ReaderInputStream;

import com.jcraft.jsch.JSchException;

import it.isislab.sof.core.engine.hadoop.sshclient.connection.FileSystemSupport;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.executor.SOFRUNNER;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.executor.SofRunnerUtils;
import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.sof.core.model.parameters.xsd.input.Input;
import it.isislab.sof.core.model.parameters.xsd.input.Inputs;

/**
 * Class for selection function    
 * 
 * 
 *
 */
public class SelectionFunction {
	//private String hdfs_domain_xml_file;
	private String hdfs_selection_function_fileName;
//	private String isFirstLoop;
	private String previous_hdfs_loop_path;
	//private String hdfs_input_fileName;
	//private String hdfs_simulation_rating_folder;
	
	private String currentExecutionInputLoopPath;
	private String execBinPath;
	private String previousloop;
	
	///input a selection
	private String hdfs_conf_ini_fileName;
	private String hdfs_s100__fileName;
    

	/*public SelectionFunction(EnvironmentSession session,
			String hdfs_domain_xml_file,
			String hdfs_input_fileName,
			String hdfs_selection_function_fileName,
			String hdfs_simulation_rating_folder,
			String hdsf_simulation_new_input_folder,
			String execBinPath) {

		this.session = session;
		this.execBinPath = execBinPath;
		this.hdfs_domain_xml_file = hdfs_domain_xml_file;
		this.hdfs_selection_function_fileName = hdfs_selection_function_fileName;
		this.hdfs_simulation_rating_folder = hdfs_simulation_rating_folder;
		this.hdsf_simulation_new_input_folder = hdsf_simulation_new_input_folder;
		this.hdfs_input_fileName = hdfs_input_fileName;
	}*/


	public SelectionFunction(String previos_loop,String hdfs_conf_path,String hdfs_s100_path, /*boolean isFirstloop,*/String previous_hdfsloop_path,/*domain file*/
			/*String hdfs_simulation_loop_input_xml,*/ String hdfs_selection_function_fileName,
			/*String hdfs_simulation_rating_folder,*/ String currentExecutionInputLoopPath,
			String bashCommandForRunnableFunctionSelect) {
		
		//this.hdfs_domain_xml_file = hdfs_domain_xml_file; diventa path ai conf.ini e s100.xml 
		
		//this.hdfs_input_fileName = hdfs_simulation_loop_input_xml;
		this.hdfs_conf_ini_fileName=hdfs_conf_path;
		this.hdfs_s100__fileName=hdfs_s100_path;
		
		
		
		this.hdfs_selection_function_fileName = hdfs_selection_function_fileName;
	//	this.hdfs_simulation_rating_folder = hdfs_simulation_rating_folder;
		this.currentExecutionInputLoopPath = currentExecutionInputLoopPath;
 		this.execBinPath = bashCommandForRunnableFunctionSelect.endsWith("java")?bashCommandForRunnableFunctionSelect+" -jar":bashCommandForRunnableFunctionSelect;
 		
 		//magellano selection valutare anche in generale 
 		//this.isFirstLoop=String.valueOf(isFirstloop);	
 		this.previous_hdfs_loop_path=previous_hdfsloop_path;
 		this.previousloop=previos_loop;
	}


	public boolean generateNewInput(FileSystemSupport fs) throws JSchException, IOException {

		//String tmpFold = HadoopFileSystemManager.makeRemoteTempFolder(session);
		String tmpFold = fs.getRemotePathForTmpFolderForUser();
		
		if(SofRunnerUtils.mkdir(tmpFold))
			SOFRUNNER.log.info("Created "+tmpFold+" folder");

		String tmpFolderName = tmpFold.substring(tmpFold.lastIndexOf("/")+1, tmpFold.length());
		
	/*	String xml_domain_fileName = fs.getRemotePathForTmpFileForUser(tmpFolderName);
		if(SofRunnerUtils.copyFileFromHdfs(fs,hdfs_domain_xml_file, xml_domain_fileName))
			SOFRUNNER.log.info("Copied "+hdfs_domain_xml_file+" to "+xml_domain_fileName);*/

		/*String xml_input_fileName = fs.getRemotePathForTmpFileForUser(tmpFolderName);

		if(SofRunnerUtils.copyFileFromHdfs(fs,  hdfs_input_fileName, xml_input_fileName))
			SOFRUNNER.log.info("Copied "+hdfs_input_fileName+" to "+xml_input_fileName);*/
/*
		String rating_folder_name = fs.getRemotePathForTmpFolderForUser();

		
		if(SofRunnerUtils.copyFilesFromHdfs(fs, hdfs_simulation_rating_folder, rating_folder_name))
			SOFRUNNER.log.info("Copied "+hdfs_simulation_rating_folder+" to "+rating_folder_name);*/

		String tmpPath_loop=fs.getRemotePathForTmpFileForUser(tmpFolderName) /*+File.separator+"LOOP"+previousloop*/;
		if(SofRunnerUtils.mkdir(tmpPath_loop))
			SOFRUNNER.log.info("Created "+tmpPath_loop+" for loop folder");
		
				if(SofRunnerUtils.copyFileFromHdfs(fs,previous_hdfs_loop_path, tmpPath_loop))
					SOFRUNNER.log.info("Copied "+previous_hdfs_loop_path+" to "+tmpPath_loop);
		
		String tmpPathforSelectionexec=tmpPath_loop+File.separator+"LOOP"+previousloop; //copio nella cartella loopi ed aggiungo '/loopI' al path per la selection
				
				
		String selection_function_fileName = fs.getRemotePathForTmpFileForUser(tmpFolderName);
		if(SofRunnerUtils.copyFileFromHdfs(fs, hdfs_selection_function_fileName, selection_function_fileName))
			SOFRUNNER.log.info("Copied "+hdfs_selection_function_fileName+" to "+selection_function_fileName);

		String tmpSelection_Input_folder = fs.getRemotePathForTmpFolderForUser();
		if(SofRunnerUtils.mkdir(tmpSelection_Input_folder))
			SOFRUNNER.log.info("Created folder "+tmpSelection_Input_folder);


		/*String makeExecutableFilecmd="chmod +x "+selection_function_fileName;
		if(Integer.parseInt(HadoopFileSystemManager.exec(session,makeExecutableFilecmd))<0?false:true)
			SOFRUNNER.log.info("Make executable "+selection_function_fileName);*/
		if(SofRunnerUtils.chmodX(selection_function_fileName))
			SOFRUNNER.log.info("Make executable "+selection_function_fileName);

		//la cartella che colleziona gli output della selction sxx.xml conxx.ini e erratamente input.xml
		String folderTmmOutputOfSelection=fs.getRemotePathForTmpFileForUser(tmpFolderName)+File.separator+"file";
		if(SofRunnerUtils.mkdir(folderTmmOutputOfSelection))
			SOFRUNNER.log.info("Created "+folderTmmOutputOfSelection+" to store input files(s1xx.xml, confx.ini and input.xml(dovrebbe essere l output )) for magellano simulation");
		
		
		///create tmp folder for s100.xml and conf.ini  dovrebbero essere presi dal jar dall fselection in futuro
		String tmpFileInputToFselection=fs.getRemotePathForTmpFileForUser(tmpFolderName)+File.separator+"input_file";
		if(SofRunnerUtils.mkdir(tmpFileInputToFselection))
			SOFRUNNER.log.info("Created "+tmpFileInputToFselection+" for input files to selection function");
	
		
		
		
		////copy of s100 e conf nella tmp folder
		if(SofRunnerUtils.copyFileFromHdfs(fs, hdfs_conf_ini_fileName, tmpFileInputToFselection))
			SOFRUNNER.log.info("Copied "+hdfs_conf_ini_fileName+" to "+tmpFileInputToFselection);
		
		if(SofRunnerUtils.copyFileFromHdfs(fs, hdfs_s100__fileName, tmpFileInputToFselection))
			SOFRUNNER.log.info("Copied "+hdfs_s100__fileName+" to "+tmpFileInputToFselection);
		
		

		boolean result= false;
		/*String prefix = "if ";
		String postFix = "; then echo 0; else echo -1; fi";*/
		String tmpRedirectInputXmlFile=fs.getRemotePathForTmpFileForUser(tmpFolderName);
		File f = new File(tmpRedirectInputXmlFile);
		f.createNewFile();
		
		
		String cmd =execBinPath+" "+selection_function_fileName+
				" "+tmpFileInputToFselection+     //isFirstLoop+// sarà il path tmp con i file s100.xml e conf.ini
				" "+tmpPathforSelectionexec+
		/*		" "+xml_input_fileName+*/
				" "+folderTmmOutputOfSelection+
				" "+tmpFolderName; //if the FS was created some files
		
		SOFRUNNER.log.info("Launch selection function. \n"+cmd);
		//if the FS made external input file(s), they have to be created in tmpFolderName and the absolute path has to be given in output
		//as multiple line of couple "file:path/to/external/input/file"
		//
		if(SofRunnerUtils.execGenericCommand(cmd.split(" "),tmpRedirectInputXmlFile)){
			if(f.length()>0){
				
				/*ATTEZIONE QUA */
				//tmpRedirectInputXmlFile  dato che in output ci sono log fantasmi che i bloomer non riescono a togliere fselect crea input.xml e lo mette
				// tra gli s1xx e conxx.ini----> cambio tmpRedirectInputXmlFile con il file prodotto sotto /files terzo parametro funzione selection
				tmpRedirectInputXmlFile=folderTmmOutputOfSelection+File.separator+"input.xml";
				verifyExternalInputFiles(fs, tmpFolderName,tmpRedirectInputXmlFile, currentExecutionInputLoopPath);
				if(SofRunnerUtils.copyFileInHdfs(fs, tmpRedirectInputXmlFile, currentExecutionInputLoopPath)){
					
					File filesinputs=new File(folderTmmOutputOfSelection);
					/*System.out.println("print list file in "+filemag);
					for(File fx: filesinputs.listFiles()) {
						
						System.out.println(fx.getAbsolutePath());
					}
					*/
					
					
					File tocopy[]=filesinputs.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							
							return ( name.startsWith("conf") && name.endsWith(".ini") )  || (name.startsWith("s1")&& name.endsWith(".xml"));
						}
					});
					
					String pathLoopCur=currentExecutionInputLoopPath.replace("/input.xml", ""); // Loopi/input
					// tutti gli s1xx e confxx.ini sotto Loopi/input
					for (File tmpfile : tocopy) {
                        System.out.println( tmpfile.getAbsolutePath() +"  to "+pathLoopCur);
						SofRunnerUtils.copyFileInHdfs(fs, tmpfile.getAbsolutePath(), pathLoopCur);
					}
					
					
					
					SOFRUNNER.log.info("Generated successfully a new input");
					result=true;
					
				}
					
			}else
				SOFRUNNER.log.info("Selection function terminated.");
		}else{
			SOFRUNNER.log.severe("Unexpected selection function terminated.");
		}
		//SofRunnerUtils.rmr(rating_folder_name);
		SofRunnerUtils.rmr(tmpSelection_Input_folder);
	//	SofRunnerUtils.rmr(tmpFold);
		return result;
	}

/**
 * If the tmpRedirectInputXmlFile is a correct Inputs xml object then nothing to do,
 * otherwise we need to convert the external input file in a correct Input object
 * @param tmpRedirectInputXmlFile
 */
	private void verifyExternalInputFiles(FileSystemSupport fs, String tmpFolderName, String tmpRedirectInputXmlFile, String hdfs_to) {
		File tmpInput = new File(tmpRedirectInputXmlFile);
		
		//pulire tmp
		JAXBContext context;
		Inputs i = new Inputs();
		ArrayList<String> externalFiles = null;
		try {
			 context = JAXBContext.newInstance(Inputs.class);

			Unmarshaller unmarshal = context.createUnmarshaller();
			i = (Inputs) unmarshal.unmarshal(tmpInput);
		} catch (JAXBException e) {
			// So tmpInput is not a valid Input object. We need to parse it!
			try {
				BufferedReader br = new BufferedReader(new FileReader(tmpInput));
				String line="";
				String[] args=null;
				Inputs is = new Inputs();
				Input inp =null;
				ArrayList<Input> input_list = new ArrayList<>();
				ParameterString ext_file_name=null;
				Parameter ext_file =null;
				externalFiles = new ArrayList<>();
				while((line=br.readLine())!=null){
					args = line.split(";");
					inp = new Input();
					inp.id = Integer.parseInt(args[0].split(":")[1]);
					inp.rounds = Integer.parseInt(args[1].split(":")[1]);
					ext_file_name = new ParameterString();
					ext_file_name.setvalue(args[2].split(":")[1]);
					ext_file = new Parameter();
					ext_file.setparam(ext_file_name);
					externalFiles.add(ext_file_name.getvalue());
					inp.param_element = new ArrayList<Parameter>();
					inp.param_element.add(ext_file);
					input_list.add(inp);
				}
				Simulation s=new Simulation();
		        s.setId("fakeID-ForGeneralExecution");
		        s.setName("thisIsTheSimulationName");
		        s.setAuthor("thisIsTheAuthorOfSimulation");
		        s.setDescription("thisIsTheDescription");
		        s.setToolkit("generic");
		        s.setState(s.CREATED);
		        s.setCreationTime();
		        is.setinput_list(input_list);
		        is.setsimulation(s);
		        context = JAXBContext.newInstance(it.isislab.sof.core.model.parameters.xsd.input.Inputs.class);
		        Marshaller jaxbMarshaller = context.createMarshaller();
		        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		        jaxbMarshaller.marshal(is, tmpInput);
			} catch (FileNotFoundException e1) {
				SOFRUNNER.log.severe("Somethig was wrong with the Function Selection.");
				SOFRUNNER.log.severe("The file "+tmpRedirectInputXmlFile+" does not exist.");
				e1.printStackTrace();
			} catch (IOException e1) {
				SOFRUNNER.log.severe("Somethig was wrong with the Function Selection.");
				SOFRUNNER.log.severe("Unable to read "+tmpRedirectInputXmlFile);
				e1.printStackTrace();
			} catch (JAXBException e1) {
				SOFRUNNER.log.severe("Somethig was wrong with the Function Selection.");
				SOFRUNNER.log.severe("Unable to create "+tmpRedirectInputXmlFile+" xml");
				e1.printStackTrace();
			}
			
			if(externalFiles != null || !externalFiles.isEmpty()){
				for(String extFile : externalFiles){
					SofRunnerUtils.copyFileInHdfs(fs, tmpFolderName+File.separator+extFile, hdfs_to);
				}
			}
		}
	}
	
}
