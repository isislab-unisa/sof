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
package it.isislab.sof.core.engine.hadoop.sshclient.connection;

import java.io.File;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * File system description for SOF framework.
 * 
 * SOF-HDFS (SOF)
    ├── userLOOP (home hdfs)
    │   ├── SIM1
    │   │   ├── description
    │   │   │   ├── input
    │   │   │   │   └── input.xml
    |   |   |   |   |___input.data
    │   │   │   ├── model.executable
    │   │   │   ├── output
    │   │   │   │   └── output.xml
    │   │   │   ├── rating.executable
    │   │   │   └── selection.executable
    │   │   └── execution
    │   │   │    ├── loop1
    │   │   │    │   ├── input
    |	|	│	|	|	|__ input.xml	
    │   │   │    │   │   └── input.data
    │   │   │    │   ├── output
    │   │   │    │   │   ├── OUTPUTXXXXXXXXXX1.xml
    │   │   │    │   │   ├── OUTPUTXXXXXXXXXX2.xml
    │   │   │    │   │   ├── OUTPUTXXXXXXXXXXX.xml
    │   │   │    │   │   ├── _SUCCESS
    │   │   │    │   │   └── part-00000
    │   │   │    │   ├── ratings
    │   │   │    │   │   └── some.files
    │   │   │    │   └── selections
    │   │   │    │       └── some.files
    │   │   │    └── runs.xml
    |   |   │
    │   │   │__messages
    |   |          |_____inbox
    |   |          |       |____messagexxxxx2.xml
    |   |          |       |____messagexxxxx3.xml 
    |   |          |
    |   |          |_____delivered  
    |   |                  |____messagexxxxx1.xml 
    |   |
    |   |
    │   └── simulations
    |			|___simulation-TIMESTAMP
    |			|___simulation-TIMESTAMPX
    └── userONE
        ├── SIM1
        │   ├── description
        │   │   ├── input
        │   │   │   └── input.xml
        │   │   ├── model.executable
        │   │   └── output
        │   │       └── output.xml
        │   └── execution
        │       ├── loop1
        │       │   ├── input
        │       │   │   └── input.data
        │       │   └── output
        │       │       ├── OUTPUTXXXXXXXXXX1.xml
        │       │       ├── OUTPUTXXXXXXXXXX2.xml
        │       │       ├── OUTPUTXXXXXXXXXXX.xml
        │       │       ├── _SUCCESS
        │       │       └── part-00000
        │       └── runs.xml
        └── simulations
    			|___simulation-TIMESTAMP
    			|___simulation-TIMESTAMPX
 *
 *
 *
 *
 *SOF-CLIENT/
	└── tmp
		├── somefile
		└── somefile
 *
 *
 *
 *SOF-REMOTE/
		├── SOF-RUNNER.jar
		├── SOF.jar
		└── userLOOP
		    ├── tmp
		    	├── somefile
		    	└── somefile
		    userONE
		    ├── tmp
		    	├── somefile
		    	└── somefile


 *
 */
public class FileSystemSupport {

	private String username;

	private final String SIMULATION_LIST_FOLDER="simulations";
	private final String DESCRIPTION_FOLDER="description";
	private final String EXECUTION_FOLDER="execution";

	private final String SIMULATION_MESSAGES_FOLDER="messages";
	private final String MESSAGES_INBOX_FOLDER="inbox";
	private final String MESSAGES_INBOX_FOLDER_MESSAGE_PREFIX="MEX-";
	private final String MESSAGES_INBOX_FOLDER_MESSAGE_EXTENSION=".xml";

	private final String RATINGS_FOLDER="ratings";
	private final String SELECTIONS_FOLDER="selections";
	private final String OUTPUT_FOLDER="outputs";
	private final String INPUT_FOLDER="input";
	private final String LOOP_LIST_FILENAME="runs.xml";
	private final String INPUT_XML_FILENAME="input.xml";
	private final String INPUT_DATA_FILENAME="input.data";
	private final String OUTPUT_XML_FILENAME="output.xml";
	private final String DOMAIN_XML_FILENAME="domain.xml";
	private final String TEMP_PREFIX="SOF-TMP-DATA";
	private final String TEMP_LOG_PREFIX="SOF-TMP-LOG";
	private final String SIMULATION_FOLDER_PREFIX="SIM-";
	private final String LOOP_FOLDER_PREFIX="LOOP";


	//private final String SELECTION_EXE_PREFIX="SELCTION_EXE";
	//private final String RATING_EXE_PREFIX="RATING_EXE";
	private String JAVA_REMOTE_BIN_FOLDER;
	private String SOF_HDFS_HOME;
	private String SOF_REMOTE_HOME;
	private String REMOTE_ROOT_PATH;
	private String SOF_LOCAL_CLIENT_INSTALL_HOME;
	private String SOF_REMOTE_TMP="temporaryfiles";
	private String SOF_LOCAL_CLIENT_TMP;
	private String HDFS_ROOT_PATH;
	private String HADOOP_ROOT_INSTALL_PATH;
	private String SEPARATOR=File.separator;

	class FileSystemSupportException extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FileSystemSupportException(String message)
		{
			super(message);
		}
	}

	public FileSystemSupport(String hadoopInstallerDir,String client_home_path, String hdfs_home_path,String remote_home_path,String javabinremotepath, String username) {
		super();

		this.username=username;
		this.REMOTE_ROOT_PATH=remote_home_path;
		if(!(new File(client_home_path).isAbsolute())) new FileSystemSupportException("Client Home path must be an absolute path!");
		if(!(new File(hdfs_home_path).isAbsolute()))   new FileSystemSupportException("HDFS Home path must be an absolute path!");
		if(!(new File(remote_home_path).isAbsolute())) new FileSystemSupportException("Remote Home path must be an absolute path!");
		this.HDFS_ROOT_PATH=hdfs_home_path;
		HADOOP_ROOT_INSTALL_PATH=hadoopInstallerDir;
		if(client_home_path.endsWith("/"))
			client_home_path=client_home_path.substring(0, client_home_path.lastIndexOf("/"));

		if(remote_home_path.endsWith("/"))
			remote_home_path=remote_home_path.substring(0, remote_home_path.lastIndexOf("/"));

		if(hdfs_home_path.endsWith("/"))
			hdfs_home_path=hdfs_home_path.substring(0, hdfs_home_path.lastIndexOf("/"));

		SOF_HDFS_HOME=hdfs_home_path+"/SOF";
		SOF_REMOTE_HOME=remote_home_path+"/SOF";
		SOF_LOCAL_CLIENT_INSTALL_HOME=client_home_path+SEPARATOR+"SOF";
		SOF_LOCAL_CLIENT_TMP=SOF_LOCAL_CLIENT_INSTALL_HOME+SEPARATOR+SOF_REMOTE_TMP;
		JAVA_REMOTE_BIN_FOLDER=javabinremotepath;

	}
	/**
	 * SOF-REMOTE FS
	 */

	public String getRemoteJavaBinPath(){
		return JAVA_REMOTE_BIN_FOLDER;
	}

	public String getRemoteHadoopInstallPath(){
		return HADOOP_ROOT_INSTALL_PATH;
	}

	public String getRemoteHadoopInstallBinPath(){
		return HADOOP_ROOT_INSTALL_PATH+"/bin";
	}

	public String getRemoteRootPath(){return REMOTE_ROOT_PATH;}

	public String getRemoteSOFHome()
	{
		return SOF_REMOTE_HOME;
	}
	public String getRemoteSOFHomeForUser()
	{
		return SOF_REMOTE_HOME+"/"+username;
	}
	public String getRemoteSOFtmpForUser()
	{
		return this.getRemoteSOFHomeForUser()+"/"+SOF_REMOTE_TMP;
	}

	public String getRemotePathForFile(String file_name)
	{
		return this.getRemoteSOFHome()+"/"+file_name;
	}
	public String getRemotePathForFolder(String folder_name)
	{
		return this.getRemoteSOFHome()+"/"+folder_name;
	}
	/**
	 * before use this method you must create the getRemotePathForTmpFolderForUser
	 * @param folderName
	 * @return
	 */
	public String getRemotePathForTmpFileForUser(String folderName)
	{
		return this.getRemoteSOFtmpForUser()+"/"+folderName+"/"+DigestUtils.md5Hex(System.currentTimeMillis()+"");
	}

	public String getRemotePathForTmpLogFileForUser()
	{
		return this.getRemoteSOFtmpForUser()+"/"+TEMP_LOG_PREFIX+"-"+DigestUtils.md5Hex(System.currentTimeMillis()+"");
	}

	public String getRemotePathForTmpFolderForUser()
	{
		return this.getRemoteSOFtmpForUser()+"/"+TEMP_PREFIX+"-"+DigestUtils.md5Hex(System.currentTimeMillis()+"");
	}
	/**
	 * END SOF-REMOTE FS
	 */


	/**
	 * SOF-CLIENT FS
	 */
	public String getClientSOFHome()
	{
		return SOF_LOCAL_CLIENT_INSTALL_HOME;
	}

	//user.dir/temporaryfile
	public String getClientSOFtmp()
	{
		return SOF_LOCAL_CLIENT_TMP;
	}
	public String getClientPathForFile(String file_name)
	{
		return this.getClientSOFHome()+SEPARATOR+file_name;
	}
	public String getClientPathForFolder(String folder_name)
	{
		return this.getClientSOFHome()+SEPARATOR+folder_name;
	}
	public String getClientPathForTmpFile()
	{
		return this.getClientSOFtmp()+SEPARATOR+DigestUtils.md5Hex(System.currentTimeMillis()+"");
	}
	public String getClientPathForTmpFolder()
	{
		return this.getClientSOFtmp()+SEPARATOR+TEMP_PREFIX+"-"+DigestUtils.md5Hex(System.currentTimeMillis()+"");
	}
	/**
	 * END SOF-CLIENT FS
	 */

	/**
	 * SOF-HDFS FS
	 */

	public String getHdfsRootPath(){
		return HDFS_ROOT_PATH;}

	public String getHdfsPathHomeDir()
	{
		return SOF_HDFS_HOME;
	}


	//MESSAGES 
	public String getHdfsUserPathSimulationMessagesByID(String simid){

		return this.getHdfsUserPathSimulationByID(simid)+"/"+SIMULATION_MESSAGES_FOLDER;
	}
	//inbox folder
	public String getHdfsUserPathSimulationInboxMessages(String simid){
		return this.getHdfsUserPathSimulationMessagesByID(simid)+"/"+MESSAGES_INBOX_FOLDER;
	}

	//message.xml 
	public String getHdfsUserPathSimulationInboxMessagesFileByID(String simid, String mexID){
		return this.getHdfsUserPathSimulationInboxMessages(simid)+"/"+MESSAGES_INBOX_FOLDER_MESSAGE_PREFIX+mexID+MESSAGES_INBOX_FOLDER_MESSAGE_EXTENSION;
	}

	public String getHdfsUserPathHomeDir()
	{
		return SOF_HDFS_HOME+"/"+username;
	}
	public String getHdfsUserPathSimulationByID(String simid)
	{
		return this.getHdfsUserPathHomeDir()+"/"+SIMULATION_FOLDER_PREFIX+simid;
	}
	public String getHdfsUserPathSimulationLoopByIDs(String simid,int loopid)
	{
		return this.getHdfsUserPathSimulationByID(simid)+"/"+EXECUTION_FOLDER+"/"+LOOP_FOLDER_PREFIX+loopid;
	}
	public String getHdfsUserPathDescriptionDirForSimId(String simid)
	{
		return this.getHdfsUserPathSimulationByID(simid)+"/"+DESCRIPTION_FOLDER;
	}

	public String getHdfsUserPathDescriptionOutputDir(String simid)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+OUTPUT_FOLDER;
	}
	public String getHdfsUserPathDescriptionInputDir(String simid)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+INPUT_FOLDER;
	}

	public String getHdfsUserPathExecutionDirForSimId(String simid)
	{
		return this.getHdfsUserPathSimulationByID(simid)+"/"+EXECUTION_FOLDER;
	}	

	public String getHdfsUserPathSimulationsListDir(){
		return this.getHdfsUserPathHomeDir()+"/"+SIMULATION_LIST_FOLDER;
	}

	public String getHdfsUserPathSimulationXMLFile(String id){
		return this.getHdfsUserPathSimulationsListDir()+"/SIM-"+id+".xml";
	}


	public String getHdfsUserPathRunsXml(String simid)
	{
		return this.getHdfsUserPathExecutionDirForSimId(simid)+"/"+LOOP_LIST_FILENAME;
	}
	public String getHdfsUserPathOutputLoopDIR(String simid,int loopid)
	{
		return this.getHdfsUserPathSimulationLoopByIDs(simid,loopid)+"/"+OUTPUT_FOLDER;
	}
	public String getHdfsUserPathInputLoopDIR(String simid,int loopid)
	{
		return this.getHdfsUserPathSimulationLoopByIDs(simid,loopid)+"/"+INPUT_FOLDER;
	}
	public String getHdfsUserPathSimulationLoopByIDsInputXML(String simid, int loopid){
		return this.getHdfsUserPathInputLoopDIR(simid,loopid)+"/"+INPUT_XML_FILENAME;
	}


	public String getHdfsUserPathDomainXML(String simid)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+DOMAIN_XML_FILENAME;
	}
	public String getHdfsUserPathInputXML(String simid)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+INPUT_FOLDER+"/"+INPUT_XML_FILENAME;
	}
	public String getHdfsUserPathDescriptionOutputXML(String simid)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+OUTPUT_FOLDER+"/"+OUTPUT_XML_FILENAME;
	}

	public String getHdfsUserPathDescriptionInputDirInputData(String simid){
		return this.getHdfsUserPathDescriptionInputDir(simid)+"/"+INPUT_DATA_FILENAME;
	}
	public String getHdfsUserPathSimulationLoopByIDsInputDATA(String simid,int loopid)
	{
		return this.getHdfsUserPathSimulationLoopByIDs(simid, loopid)+"/"+INPUT_FOLDER+"/"+INPUT_DATA_FILENAME;
	}
	public String getHdfsUserPathSimulationExeForId(String simid,String exe_file_name)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+exe_file_name;
	}
	public String getHdfsUserPathSelectionExeForId(String simid,String exe_file_name)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+exe_file_name;
	}
	public String getHdfsUserPathRatingExeForId(String simid,String exe_file_name)
	{
		return this.getHdfsUserPathDescriptionDirForSimId(simid)+"/"+exe_file_name;
	}
	public String getHdfsUserPathRatingFolderForSimLoop(String simid,int loopid)
	{
		return this.getHdfsUserPathSimulationLoopByIDs(simid,loopid)+"/"+RATINGS_FOLDER;
	}
	public String getHdfsUserPathSelectionFolder(String simid,int loopid)
	{
		return this.getHdfsUserPathSimulationLoopByIDs(simid,loopid)+"/"+SELECTIONS_FOLDER;
	}


	/**
	 * END SOF-HDFS FS
	 */
	public static void main(String[] args)
	{


		//TEST
		/*FileSystemSupport fs=new FileSystemSupport("",System.getProperty("user.dir"), "/myapplication/","/home/michele/il/bello/", "/usr/giuann/java/bin","umberto");

		System.out.println("******************CLIENT APPLICATION**********************************");
		System.out.println("Client sof home:[getClientSOFHome()]"+fs.getClientSOFHome());
		System.out.println("Client sof tmp:[getClientSOFtmp()]"+fs.getClientSOFtmp());
		System.out.println("Client sof tmp folder:[getClientSOFtmp()]"+fs.getClientPathForTmpFolder());
		System.out.println("Client sof tmp file:"+fs.getClientPathForTmpFile());
		System.out.println("Client sof home folder:"+fs.getClientPathForFolder("testfolder"));
		System.out.println("Client sof home file:"+fs.getClientPathForFile("tmpfile"));
		System.out.println("**********************************************************************");
		System.out.println("**********************************************************************");
		System.out.println("******************REMOTE HADOOP MACHINE*******************************");
		System.out.println("Remote sof home:"+fs.getRemoteSOFHome());
		System.out.println("Remote sof home user:"+fs.getRemoteSOFHomeForUser());
		System.out.println("Remote sof tmp user :"+fs.getRemoteSOFtmpForUser());
		System.out.println("Remote sof tmp user folder:"+fs.getRemotePathForTmpFolderForUser());
		//System.out.println("Remote sof tmp user file:"+fs.getRemotePathForTmpFileForUser());
		System.out.println("Remote sof home user folder:"+fs.getRemotePathForFolder("testfolder"));
		System.out.println("Remote sof home user file:"+fs.getRemotePathForFile("tmpfile"));
		System.out.println("**********************************************************************");
		System.out.println("**********************************************************************");
		System.out.println("******************HDFS HADOOP MACHINE*******************************");
		System.out.println("HDFS sof user home:"+fs.getHdfsUserPathHomeDir());
		System.out.println("HDFS sof simulation by id:"+fs.getHdfsUserPathSimulationByID(1+""));
		System.out.println("HDFS sof simulation loop by id:"+fs.getHdfsUserPathSimulationLoopByIDs(1+"", 1));
		System.out.println("HDFS sof simulation description:"+fs.getHdfsUserPathDescriptionDirForSimId(1+""));
		System.out.println("HDFS sof simulation execution:"+fs.getHdfsUserPathExecutionDirForSimId(1+""));
		System.out.println("HDFS sof simulation messages:"+fs.getHdfsUserPathSimulationMessagesByID("1"));
		System.out.println("HDFS sof simulation inbox messages:"+fs.getHdfsUserPathSimulationInboxMessages("1"));
		//System.out.println("HDFS sof simulation.xml:"+fs.getHdfsUserPathSimulationsXml());
		System.out.println("HDFS sof domain.xml for SIM1:"+fs.getHdfsUserPathDomainXML(1+""));
		System.out.println("HDFS sof runx.xml for SIM1:"+fs.getHdfsUserPathRunsXml(1+""));
		System.out.println("HDFS sof input.xml for SIM1:"+fs.getHdfsUserPathInputXML(1+""));
		System.out.println("HDFS sof output.xml for SIM1:"+fs.getHdfsUserPathDescriptionOutputXML(1+""));
		System.out.println("HDFS sof output folder loop 1 for SIM1:"+fs.getHdfsUserPathOutputLoopDIR(1+"",1));
		System.out.println("HDFS sof input folder loop 1 for SIM1:"+fs.getHdfsUserPathInputLoopDIR(1+"",1));
		System.out.println("HDFS sof input.data for loop 1 for SIM1:"+fs.getHdfsUserPathSimulationLoopByIDsInputDATA(1+"",1));
		System.out.println("HDFS sof model executable for loop 1 for SIM1:"+fs.getHdfsUserPathSimulationExeForId(1+"","testmodel"));
		System.out.println("HDFS sof selection executable for loop 1 for SIM1:"+fs.getHdfsUserPathSelectionExeForId(1+"","testselection"));
		System.out.println("HDFS sof rating executable for loop 1 for SIM1:"+fs.getHdfsUserPathRatingExeForId(1+"","testrating"));
		System.out.println("HDFS sof rating folder for loop 1 for SIM1:"+fs.getHdfsUserPathRatingFolderForSimLoop(1+"",1));
		System.out.println("HDFS sof selection folder for loop 1 for SIM1:"+fs.getHdfsUserPathSelectionFolder(1+"",1));
		System.out.println("HDFS File description inputtempdata"+fs.getHdfsUserPathDescriptionInputDirInputData(1+""));

		System.out.println("**********************************************************************");
		System.out.println("**********************************************************************");*/
	}


}
