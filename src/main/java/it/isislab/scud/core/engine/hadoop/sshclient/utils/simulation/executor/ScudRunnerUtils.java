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

import it.isislab.scud.core.engine.hadoop.sshclient.connection.FileSystemSupport;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.SimulationParser;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.input.Inputs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class ScudRunnerUtils {

	public static boolean mkdir(String dirname){
		File f = new File(dirname);
		if(f.exists())
			return true;
		else
			return f.mkdirs();
	}
	
	public static boolean rmr(String pathname){
		File f = new File(pathname);
		if(!f.exists())
			return true;
		else{
			if(f.isDirectory()){
				for(File f2: f.listFiles()){
					if(f2.isDirectory())
						rmr(f2.getAbsolutePath());
					else
						f2.delete();
				}
				
				return f.delete();
			}else{
				return f.delete();
			}
		}
	}
	
	public static Simulation getSimulationById(FileSystemSupport fs,String simID) {

		String tmpFolderPath = fs.getRemotePathForTmpFolderForUser();
		mkdir(tmpFolderPath);
		
		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());
		
		//String hdfsFile=fs.getHdfsUserPathSimulationsXml();
		String hdfsFile=fs.getHdfsUserPathSimulationXMLFile(simID);

		//String localFile = tmpFolderName+File.separator+SIMULATION_LIST_FILENAME;
		String localFile = fs.getRemotePathForTmpFileForUser(tmpFolderName);


		if(copyFileFromHdfs(fs,hdfsFile, localFile))
			SCUDRUNNER.log.info("Copied "+hdfsFile+" to "+localFile);
		else{
			SCUDRUNNER.log.info("Unable to copy "+hdfsFile+" to "+localFile);
			return null;
		}

		Simulation s = SimulationParser.convertXMLToSimulation(localFile);
		
		/*Simulations list = SimulationParser.convertXMLToSimulations(localFile);
		Simulation sim=null;
		for(Simulation s: list.getSimulations())
			if(s.getId().equals(simID)){
				sim = s;
				break;
			}*/
		rmr(tmpFolderPath);
		return s;
	}
	
	public static boolean ifExists(FileSystemSupport fs,String hdfsPath){
		String cmd =fs.getRemoteHadoopInstallBinPath()+"/hdfs dfs -test -e "+hdfsPath;
		return exec(cmd);
	}
	
	public static boolean copyFileFromHdfs(FileSystemSupport fs,String hdfsPath, String destinationPath){
		String command = fs.getRemoteHadoopInstallBinPath()+"/hdfs dfs -get "+hdfsPath+" "+destinationPath;
		return exec(command);
	}
	
	public static boolean copyFileInHdfs(FileSystemSupport fs,String local_from, String hdfs_to){
		String command = fs.getRemoteHadoopInstallBinPath()+"/hdfs dfs -put "+local_from+" "+hdfs_to;
		return exec(command);
	}
	
	public static boolean hdfs_mkdirp(FileSystemSupport fs,String newDirectory){
		String command = fs.getRemoteHadoopInstallBinPath()+"/hdfs dfs -mkdir -p "+newDirectory;
		return exec(command);
	}
	
	public static boolean rmrFromHdfs(FileSystemSupport fs,String hdfs_path){
		String command=fs.getRemoteHadoopInstallBinPath()+"/hdfs dfs -rm -r "+hdfs_path;
		return exec(command);
	}
	
	public static boolean chmodX(String filename){
		File f = new File(filename);
		if(f.exists())
			return f.setExecutable(true);
		return false;
	}
	/**
	 * It executes a command and redirect output in the specific filename
	 * @param command command to execute
	 * @param redirectOutput output filename
	 * @return
	 */
	public static boolean execGenericCommand(String[] command, String redirectOutput){
		ProcessBuilder procBild = new ProcessBuilder(command);
		procBild.redirectOutput(new File(redirectOutput));
		try {
			Process p = procBild.start();
			p.waitFor();
			return (p.exitValue() == 0);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	private static boolean exec(String command){
		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
			return (process.exitValue()==0);
		} catch (IOException e) {
			SCUDRUNNER.log.severe("SCUD-RUNNER: errore while execute the follow command:\n"+command+"\n");
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			SCUDRUNNER.log.severe("SCUD-RUNNER: errore while execute the follow command:\n"+command+"\n");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean copyFilesFromHdfs(FileSystemSupport fs,String hdfs_from, String localDir_to){
		String command =fs.getRemoteHadoopInstallBinPath()+"/hdfs dfs -get "+hdfs_from+" "+localDir_to;
		return exec(command);
	}
	
	public static boolean moveFromHdfsToHdfs(FileSystemSupport fs,String hdfs_from, String hdfs_to){
		boolean result = true;
		String folder = fs.getRemotePathForTmpFolderForUser();
		String folderName = folder.substring(folder.lastIndexOf("/")+1, folder.length());
		result &=mkdir(folder);
		String tmpFile = fs.getRemotePathForTmpFileForUser(folderName);
		result &=copyFileFromHdfs(fs, hdfs_from, tmpFile);
		result &=copyFileInHdfs(fs, tmpFile, hdfs_to);
		result &=rmr(folder);
		return result;
	}
	
	
	
	public static String convertXmlToData(FileSystemSupport fs, String hdfs_from, String hdfs_to) throws NumberFormatException, JSchException, IOException, SftpException {

		//String destinationPath = hdfs_xmlFilePathName.substring(0,hdfs_xmlFilePathName.lastIndexOf('/'));
		String tmpFolderPath = fs.getRemotePathForTmpFolderForUser();
		mkdir(tmpFolderPath);
		
		String tmpFolderName = tmpFolderPath.substring(tmpFolderPath.lastIndexOf("/")+1, tmpFolderPath.length());
		
		String tmpXmlFile = fs.getRemotePathForTmpFileForUser(tmpFolderName);

		File tmp_file = null;
		Inputs inputs;
		copyFileFromHdfs(fs,hdfs_from, tmpXmlFile);
		
		tmp_file = new File(tmpXmlFile);
		try {
			String line = "";
			JAXBContext contex = JAXBContext.newInstance(Inputs.class);
			Unmarshaller unmarshall = contex.createUnmarshaller();
			inputs = (Inputs) unmarshall.unmarshal(tmp_file);
			for(Input input : inputs.getinput_list()){
				line+="id:"+input.id+";";
				line+="rounds:"+input.rounds+";";
				for(Parameter p : input.param_element){
					if(p.getparam() instanceof ParameterDouble){
						ParameterDouble pd = (ParameterDouble) p.getparam();
						line+=p.getvariable_name()+":"+pd.getvalue()+";";
					}else
						if(p.getparam() instanceof ParameterLong) {
							ParameterLong pd = (ParameterLong) p.getparam();
							line+=p.getvariable_name()+":"+pd.getvalue()+";";
						}else
							if(p.getparam() instanceof ParameterString){
								ParameterString pd = (ParameterString) p.getparam();
								line+=p.getvariable_name()+":"+pd.getvalue()+";";
							}
				}
				line+='\n';
			}
			String newTmpFilename = fs.getRemotePathForTmpFileForUser(tmpFolderName);
			File newTmpFile = new File(newTmpFilename);
			FileWriter fw = new FileWriter(newTmpFile);
			BufferedWriter br=new BufferedWriter(fw);
			br.write(line);
			br.close();
			
			
			if(copyFileInHdfs(fs,newTmpFilename, hdfs_to))
				SCUDRUNNER.log.info("Converted "+hdfs_from+" to "+hdfs_to);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rmr(tmpFolderPath);
		return hdfs_to;
	}
	
}
