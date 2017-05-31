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


import java.io.File;
import java.io.IOException;

import com.jcraft.jsch.JSchException;

import it.isislab.sof.core.engine.hadoop.sshclient.connection.FileSystemSupport;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.executor.SOFRUNNER;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.executor.SofRunnerUtils;

/**
 * Class for selection function    
 * 
 * 
 *
 */
public class SelectionFunction {
	private String hdfs_domain_xml_file;
	private String hdfs_selection_function_fileName;
	private String hdfs_input_fileName;
	private String hdfs_simulation_rating_folder;
	private String currentExecutionInputLoopPath;
	private String execBinPath;


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


	public SelectionFunction(String hdfs_domain_xml_file,
			String hdfs_simulation_loop_input_xml, String hdfs_selection_function_fileName,
			String hdfs_simulation_rating_folder, String currentExecutionInputLoopPath,
			String bashCommandForRunnableFunctionSelect) {
				
		this.hdfs_domain_xml_file = hdfs_domain_xml_file;
		this.hdfs_input_fileName = hdfs_simulation_loop_input_xml;
		this.hdfs_selection_function_fileName = hdfs_selection_function_fileName;
		this.hdfs_simulation_rating_folder = hdfs_simulation_rating_folder;
		this.currentExecutionInputLoopPath = currentExecutionInputLoopPath;
		this.execBinPath = bashCommandForRunnableFunctionSelect.endsWith("java")?bashCommandForRunnableFunctionSelect+" -jar":bashCommandForRunnableFunctionSelect;
		
	}


	public boolean generateNewInput(FileSystemSupport fs) throws JSchException, IOException {

		//String tmpFold = HadoopFileSystemManager.makeRemoteTempFolder(session);
		String tmpFold = fs.getRemotePathForTmpFolderForUser();
		
		if(SofRunnerUtils.mkdir(tmpFold))
			SOFRUNNER.log.info("Created "+tmpFold+" folder");

		String tmpFolderName = tmpFold.substring(tmpFold.lastIndexOf("/")+1, tmpFold.length());
		
		String xml_domain_fileName = fs.getRemotePathForTmpFileForUser(tmpFolderName);

		if(SofRunnerUtils.copyFileFromHdfs(fs,hdfs_domain_xml_file, xml_domain_fileName))
			SOFRUNNER.log.info("Copied "+hdfs_domain_xml_file+" to "+xml_domain_fileName);

		String xml_input_fileName = fs.getRemotePathForTmpFileForUser(tmpFolderName);

		if(SofRunnerUtils.copyFileFromHdfs(fs,  hdfs_input_fileName, xml_input_fileName))
			SOFRUNNER.log.info("Copied "+hdfs_input_fileName+" to "+xml_input_fileName);

		String rating_folder_name = fs.getRemotePathForTmpFolderForUser();

		
		if(SofRunnerUtils.copyFilesFromHdfs(fs, hdfs_simulation_rating_folder, rating_folder_name))
			SOFRUNNER.log.info("Copied "+hdfs_simulation_rating_folder+" to "+rating_folder_name);

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


		boolean result= false;
		/*String prefix = "if ";
		String postFix = "; then echo 0; else echo -1; fi";*/
		String tmpRedirectInputXmlFile=fs.getRemotePathForTmpFileForUser(tmpFolderName);
		File f = new File(tmpRedirectInputXmlFile);
		f.createNewFile();
		String cmd =execBinPath+" "+selection_function_fileName+
				" "+xml_domain_fileName+
				" "+xml_input_fileName+
				" "+rating_folder_name;
		
		SOFRUNNER.log.info("Launch selection function. \n"+cmd);
		
		if(SofRunnerUtils.execGenericCommand(cmd.split(" "),tmpRedirectInputXmlFile)){
			if(f.length()>0){
				if(SofRunnerUtils.copyFileInHdfs(fs, tmpRedirectInputXmlFile, currentExecutionInputLoopPath)){
					if(SofRunnerUtils.copyFileInHdfs(fs, "launcher_input/s100.xml", currentExecutionInputLoopPath)){
						SOFRUNNER.log.info("Generated successfully a new input");
						result=true;
					}
				}
					
			}else
				SOFRUNNER.log.info("Selection function terminated.");
		}else{
			SOFRUNNER.log.severe("Unexpected selection function terminated.");
		}
		SofRunnerUtils.rmr(rating_folder_name);
		SofRunnerUtils.rmr(tmpSelection_Input_folder);
		SofRunnerUtils.rmr(tmpFold);
		return result;
	}
	
}
