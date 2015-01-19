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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;



/**
 *Management Tool for execute ssh command and hadoop comand with Jsch
 * 
 * 
 */
public class HadoopFileSystemManager {

	//private static String HDFS="/home/lizard87/Programs/hadoop-2.4.0/bin/hdfs";
	public static String HDFS="/bin/hdfs";//"bin/hdfs";
	//public static String TempHostFolderName = "temp";

	//public static final String TEMPORARY_FOLDER="SCUD-";
	//public static final String STATIC_FOLDER="SCUD";
	//public static final String TEMPORARY_STATIC_FOLDER=STATIC_FOLDER+"/"+"temp";
	/**
	 * Check if a directory or a file exist
	 * @param session
	 * @param dirPath path of directory or file
	 * @return true id directory exist false otherwise
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean ifExists(EnvironmentSession session,String dirPath) throws NumberFormatException, JSchException, IOException{
		String cmd =session.getHadoop_home_path()+HDFS+" dfs -test -e "+dirPath;
		String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";
	
		
		return Integer.parseInt(exec(session,bash))<0?false:true;
	}
	
	/**
	 * Verify if a hdfs folder is empty
	 * @param session
	 * @param hdfsDirPath
	 * @return
	 * @throws NumberFormatException
	 * @throws JSchException
	 * @throws IOException
	 */
	public static boolean isEmpty(EnvironmentSession session,String hdfsDirPath) throws NumberFormatException, JSchException, IOException{
		String tmpRemoteDir= makeRemoteTempFolder(session);
		String cmd =session.getHadoop_home_path()+HDFS+" dfs -get "+hdfsDirPath+" "+tmpRemoteDir;
		String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";
		boolean result=(Integer.parseInt(exec(session, bash))<0?false:true);
		result&= _isEmpty(session, tmpRemoteDir);
		removeRemoteTempFolder(session, tmpRemoteDir);
		return result;
	}


	/**
	 * Copy from hdfs to local machine
	 * @param session
	 * @param from path of file 
	 * @param to local path 
	 * @return true if copy occur 
	 * @return false otherwise
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean copyFromHdfsToClient(EnvironmentSession session, String from, String to) throws NumberFormatException, JSchException, IOException{
		boolean result =false;
		String tmpFolderName = makeRemoteTempFolder(session);
		result = copyFromHdfsToRemote(session, from, tmpFolderName);
		String nomeFile=from.substring(from.lastIndexOf("/")+1, from.length());
		/*String cmdScp="scp ./tmp/"+nomeFile+" "+to;
		String bashScp="if "+cmdScp+"; then echo 0; else echo -1; fi ";*/
		result=result && sftpFromRemoteToClient(session,tmpFolderName+"/"+nomeFile,to);
		//boolean result = Integer.parseInt(exec(session, bashScp))<0?false:true;
		return result && removeRemoteTempFolder(session,tmpFolderName);

	}
	
	/**
	 * download a specific folder from Hdfs filesystem to specific folder on client filesystem
	 * @param session
	 * @param from
	 * @param to
	 * @return
	 * @throws NumberFormatException
	 * @throws JSchException
	 * @throws IOException
	 */
	public static boolean downloadFolderFromHdfsToClient(EnvironmentSession session, String from, String to) throws NumberFormatException, JSchException, IOException{
		boolean result =false;
		String tmpFolderName = makeRemoteTempFolder(session);
		result = copyFromHdfsToRemote(session, from, tmpFolderName);
		String nomeFile=from.substring(from.lastIndexOf("/")+1, from.length());
		result=result && sftpCopyFolderFromRemoteToClient(session,tmpFolderName+"/"+nomeFile,to);
		return result && removeRemoteTempFolder(session,tmpFolderName);

	}


	/**
	 * Copy from hdfs to local machine
	 * @param session
	 * @param from path of file 
	 * @param to local path 
	 * @return true if copy occur 
	 * @return false otherwise
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean copyOutputFolderFromHdfsToClient(EnvironmentSession session, String from, String to) throws NumberFormatException, JSchException, IOException{
		boolean result =false;
		String tmpFolderName = makeRemoteTempFolder(session);
		result = copyFromHdfsToRemote(session, from, tmpFolderName);
		String folderName=from.substring(from.lastIndexOf("/")+1, from.length());
		sftpOutputXMLFileFromRemoteToClient(session, tmpFolderName+"/"+folderName, to);
		result &=removeRemoteTempFolder(session,tmpFolderName);
		return result;

	}
	
	public static boolean copyFolderFromHdfsToClient(EnvironmentSession session, String fromDirName, String toDirName) throws JSchException, IOException{
		boolean result =false;
		String tmpFolderName = makeRemoteTempFolder(session);
		result = copyFromHdfsToRemote(session, fromDirName, tmpFolderName);
		String folderName=fromDirName.substring(fromDirName.lastIndexOf("/")+1, fromDirName.length());
		result &= sftpCopyFolderFromRemoteToClient(session,tmpFolderName+"/"+folderName,toDirName);
		result &=removeRemoteTempFolder(session,tmpFolderName);
		return result;
		
	}




	/**
	 * Exec hdfs -put of a file on dir .
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean copyFromRemoteToHdfs(EnvironmentSession session, String from, String to) throws NumberFormatException, JSchException, IOException{
		//String cmd=session.getHadoop_home_path()+File.separator+HDFS+" dfs -put "+from+" "+to;
		String cmd=session.getHadoop_home_path()+HDFS+" dfs -put "+from+" "+to;
		String bash="if "+cmd+"; then echo 0; else echo -1; fi"; 
		return Integer.parseInt(exec(session,bash))<0?false:true;

	}


	/**
	 * Exec hdfs -get of a file on dir .
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean copyFromHdfsToRemote(EnvironmentSession session,  String hdfsDir, String to) throws NumberFormatException, JSchException, IOException{
		//makeHostTempFolder(session);
		String filename=hdfsDir.substring(hdfsDir.lastIndexOf("/")+1, hdfsDir.length());
		String cmd=session.getHadoop_home_path()+HDFS+" dfs -get "+hdfsDir+" "+to;
		String bash="if "+cmd+" ; then echo 0; else echo -1; fi";
		return Integer.parseInt(exec(session,bash))<0?false:true;
	}


	/**
	 * Copy from local machine to hdfs 
	 * @param session
	 * @param from local file
	 * @param to    location to copy
	 * @return true if copy occur, false otherwise
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 * @throws SftpException 
	 */
	public static boolean copyFromClientToHdfs(EnvironmentSession session, String from, String to) throws NumberFormatException, JSchException, IOException, SftpException{
		//String sess=session.getUserName()+"@"+session.getHost();
		/*String cmdScp="scp "+from+" .";
		String bashScp="if "+cmdScp+"; then echo 0; else echo -1; fi ";
		//System.out.println(Integer.parseInt(exec(session, bashScp))<0?false:true);
		 */		
		String tmpFolderName = makeRemoteTempFolder(session);

		String nomeFile=from.substring(from.lastIndexOf(File.separator)+1, from.length());
		boolean result=sftpFromClientToRemote(session, from, tmpFolderName);
		int tentative=0;
		int endTentative =3;
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bash ="if ls "+ tmpFolderName+"/"+nomeFile+" ; then echo 0; else echo -1; fi";
		int res = Integer.parseInt(exec(session, bash));
		
		while(res <0 && tentative < endTentative){
			Logger.getLogger(HadoopFileSystemManager.class.getName()).info("Retrying to copy "+from);

			result=sftpFromClientToRemote(session, from, tmpFolderName);
			try {

				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res = Integer.parseInt(exec(session, bash));
			tentative++;
		}
		//System.out.println(nomeFile);
		result = result && copyFromRemoteToHdfs(session,tmpFolderName+"/"+nomeFile, to);
		if(!result)
			Logger.getLogger(HadoopFileSystemManager.class.getName()).severe("Error in copy from local FS to HDFS:\n"
					+ "REMOTE FS: "+tmpFolderName+"/"+nomeFile+"\n"
					+" HDFS: "+to+"\n"
					+" CLIENT FS: "+from);
		result = result && removeRemoteTempFolder(session,tmpFolderName);
		
		
		
		return result;

	}

	/**
	 * Delete a file on hdfs
	 * @param session
	 * @param filePath path of file
	 * @return true if delete of file have success 
	 * @return false if path not exist   
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean removeFile(EnvironmentSession session, String filePath) throws NumberFormatException, JSchException, IOException{
		String cmd = session.getHadoop_home_path()+HDFS+" dfs -rm -r "+filePath;
		String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";
		return Integer.parseInt(exec(session,bash))<0?false:true;
	}
	/**
	 * Exec a mkdir on hdfs
	 * @param session
	 * @param path of new dir 
	 * @return true if directory is created false otherwise 
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean mkdir(EnvironmentSession session, String path) throws NumberFormatException, JSchException, IOException{
		
		String cmd = session.getHadoop_home_path()+HDFS+" dfs -mkdir -p "+path;
		String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";
		return Integer.parseInt(exec(session,bash))<0?false:true;
	}

	/**
	 * Exec copy on hdfs 
	 * @param session  
	 * @param inputFileName   path of file 
	 * @param outputFileName  output path    
	 * @return true if copy occur, false otherwise  
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	public static boolean copyFromHdfsToHdfs(EnvironmentSession session, String inputFileName, String outputFileName) throws NumberFormatException, JSchException, IOException{
		String cmd = session.getHadoop_home_path()+HDFS+" dfs -cp "+inputFileName+" "+outputFileName;
		String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";
		return Integer.parseInt(exec(session, bash))<0?false:true;
	}


	/**
	 * Execute a command with Jsch 
	 * @param session session
	 * @param cmd script for command execution
	 * @return result of status of exec 0 or 1 
	 * @throws JSchException 
	 * @throws IOException 
	 */
	public static String exec(EnvironmentSession session,String cmd) throws JSchException, IOException{
		Channel channel;
		String output ="";
		//	System.out.println(cmd);
		channel = session.getSession().openChannel("exec");
		((ChannelExec) channel).setCommand(cmd);
		channel.setInputStream(null);
		InputStream in = channel.getInputStream();
		//((ChannelExec) channel).setErrStream(System.err);
		channel.connect();
		
		 BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	       String line;
	        int index = 0;

	        while ((line= reader.readLine()) != null)
	        {
	        	output+=line.trim();
	            //System.out.println(++index + " : " + output);
	        }
		
		
		/*byte[] b = new byte[1024];
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
		}*/
	        
		//if(cmd.contains("pkill"))
			//System.out.println("aaaaaaaaaaaa |"+output+"|");
		//if(output.isEmpty() && channel.getExitStatus()<0)
			//output+=-1;
		//else
			//output+=0;
	     //   if(cmd.contains("test")){System.out.println(output);}
		channel.disconnect();
		
		/*if(output.length()<=2)
			return output;*/
		/*if(cmd.contains("if ls"))
			System.out.println("|"+cmd+"\n"+output +" endsWith 1 "+output.endsWith("1")+"|");*/
		
		//if(output.substring(output.length()-1).equalsIgnoreCase("1"))
		if(cmd.contains("kill")) return "";
		if(output.endsWith("1"))
			return output.substring(output.length()-2);
		else
			return output.substring(output.length()-1);
	}

	/**
	 * Make temporary folder
	 * @param session
	 * @return the name of folder
	 * @throws IOException 
	 * @throws JSchException 
	 */
	private static String makeRemoteTempFolder(EnvironmentSession session) throws JSchException, IOException{
		//String tempFolderName = TempHostFolderName+System.currentTimeMillis();
		//String tempFolderName = TEMPORARY_STATIC_FOLDER+File.separator+TEMPORARY_FOLDER+System.currentTimeMillis();
		String tempFolderName = ScudManager.fs.getRemotePathForTmpFolderForUser();
		exec(session, "if mkdir "+tempFolderName+" ; then echo 0; else echo -1; fi");
		return tempFolderName;
	}

	/**
	 * Make temporary folder
	 * @param session
	 * @return folder's name
	 * @throws IOException 
	 * @throws JSchException 
	 */
	public static String makeRemoteTempFolder(EnvironmentSession session,String folderName) throws JSchException, IOException{
		exec(session, "if mkdir -p "+folderName+" ; then echo 0; else echo -1; fi");
		return folderName;
	}



	/**
	 * Remove temporary folder
	 * @param session 
	 * @param folderName the name of folder
	 * @return true if remove occur
	 * @return false otherwise
	 * @throws IOException 
	 * @throws JSchException 
	 * @throws NumberFormatException 
	 */
	private static boolean removeRemoteTempFolder(EnvironmentSession session, String folderName) throws NumberFormatException, JSchException, IOException{
		return Integer.parseInt(exec(session, "if rm -r "+folderName+" ; then echo 0; else echo -1; fi"))<0?false:true;
	}


	
	private static boolean _isEmpty(EnvironmentSession session, String remoteDir){
		Channel channel;
			try {
				channel = session.getSession().openChannel("sftp");
				channel.connect();
				ChannelSftp sftpChannel = (ChannelSftp) channel;
				Vector<ChannelSftp.LsEntry> list = sftpChannel.ls(remoteDir);
				boolean result = list.size()<=2;
				if(result){
					for(ChannelSftp.LsEntry entry : list){
						result &=(".".equalsIgnoreCase(entry.getFilename()) || "..".equalsIgnoreCase(entry.getFilename()));
					}
				}
				sftpChannel.disconnect();
				channel.disconnect();
				
				return result;// ls={. ..} (. current dir) (.. previusly dir) if the folder is empty
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		
	}
	

	/**
	 * Exec a sftp comand with Jsch
	 */
	private static boolean sftpFromRemoteToClient(EnvironmentSession session, String remote, String local)
	{
		Channel channel;
		try {
			channel = session.getSession().openChannel("sftp");

			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;

			sftpChannel.get(remote, local);

			sftpChannel.disconnect();
			channel.disconnect();
			return true;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SftpException e) {
			ScudManager.log.severe(e.getMessage());
			//e.printStackTrace();
			return false;
		}
	}


	/**
	 * Exec a sftp comand over output folder with Jsch
	 */
	private static void sftpOutputXMLFileFromRemoteToClient(EnvironmentSession session, String hostFolderName, String local)
	{
		Channel channel;
		try {
			channel = session.getSession().openChannel("sftp");

			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			/*System.out.println("i file "+remoteFolderName+"/*.xml");
			System.out.println("destintion "+local);*/
			Vector<ChannelSftp.LsEntry> list = sftpChannel.ls(hostFolderName+"/*.xml");
			for(ChannelSftp.LsEntry entry : list) {
				
				sftpChannel.get(hostFolderName+"/"+entry.getFilename(), local+File.separator+ entry.getFilename());
			}

			sftpChannel.disconnect();
			channel.disconnect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			ScudManager.log.severe(e.getMessage()+"\n");
			e.printStackTrace();
		}
	}
	
	private static boolean sftpCopyFolderFromRemoteToClient(EnvironmentSession session, String remoteFolder, String clientFolder)
	{
		Channel channel;
		try {
			channel = session.getSession().openChannel("sftp");

			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			Vector<ChannelSftp.LsEntry> list = sftpChannel.ls(remoteFolder);
			for(ChannelSftp.LsEntry entry : list) {
				//sftpChannel.get(remoteFolder+"/"+entry.getFilename(), clientFolder+File.separator+ entry.getFilename());
				if(!entry.getAttrs().isDir())
					sftpChannel.get(remoteFolder+"/"+entry.getFilename(), clientFolder+File.separator+entry.getFilename());
				else{
					if(!entry.getFilename().equals(".") && !"..".equalsIgnoreCase(entry.getFilename())){
						(new File(clientFolder+File.separator+entry.getFilename())).mkdirs();
						sftpCopyFolderFromRemoteToClient(session, remoteFolder+"/"+entry.getFilename(), clientFolder+File.separator+entry.getFilename());
					}
				}
			}
			sftpChannel.disconnect();
			channel.disconnect();
			return true;
		} catch (JSchException e) {
			ScudManager.log.severe(e.getMessage()+"\n");
			e.printStackTrace();
			return false;
		} catch (SftpException e) {
			ScudManager.log.severe(e.getMessage()+"\n");
			e.printStackTrace();
			return false;
		}
	}



	/**
	 * Exec sftp with Jsch
	 * @throws SftpException 
	 * @throws JSchException 
	 * 	 
	 * */
	public static boolean sftpFromClientToRemote(EnvironmentSession session, String local, String remote) throws SftpException, JSchException
	{
		Channel channel;

		channel = session.getSession().openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;

		sftpChannel.put(local,remote);
		sftpChannel.disconnect();
		channel.disconnect();
		
		return true;
	}

	public static void sftpFromClientToRemote(EnvironmentSession session, InputStream local, String remote)
	{
		Channel channel;
		try {
			channel = session.getSession().openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;

			sftpChannel.put(local,remote);

			sftpChannel.disconnect();
			channel.disconnect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			ScudManager.log.severe(e.getMessage());
			//e.printStackTrace();
		}

	}



}
