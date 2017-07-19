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
package it.isislab.sof.core.engine.hadoop.sshclient.utils.environment;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

import com.jcraft.jsch.Session;

/**
 * Class that initialize the ssh enviroment.
 * 
 *
 *
 */
public class EnvironmentSession{

	/**
	 * Object from JSch library. It is used for manage the ssh connection.
	 */
	private Session session = null;
	/**
	 * JSch is a java library for ssh connection. It is used for make ssh session.
	 */
	private JSch jsch = null;
	/**
	 * It is the ssh username
	 */
	private String username;
	/**
	 * It is the ssh host
	 */
	private String host;

	private int port;

	private static int DEFAULT_SSH_PORT = 22;

	private String HADOOP_HOME_PATH="";

	private static final String SSH_KEY_PATH="/home/miccar/Desktop/sof_cluster.pem"; //aws or azure

	
	
	/**
	 * Constructor that make the session by username, host and password parameters.
	 * Using JSch library via getSession method it's possible to make a new session.
	 * 
	 * @param username It is the remote machine username
	 * @param host It is the host to which to connect
	 * @param password It is the user password 
	 * @throws JSchException 
	 */
	public EnvironmentSession(String username, String host, String password, String hadoop_home_path,Integer PORT) throws JSchException{
		 jsch = new JSch();
			this.username = username;
			this.host = host;
			this.port = DEFAULT_SSH_PORT;
			this.HADOOP_HOME_PATH = hadoop_home_path;
			//jsch.addIdentity(SSH_KEY_PATH);
			session = jsch.getSession(username, host, DEFAULT_SSH_PORT);
			session.setPassword(password);
			session.setPort(PORT);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();


	}

	/**
	 * Constructor that make the session by username, host and password parameters.
	 * Using JSch library via getSession method it's possible to make a new session.
	 * 
	 * @param username It is the remote machine username
	 * @param host It is the host to which to connect
	 * @param password It is the user password 
	 * @param port It is the ssh listen port
	 */
	public EnvironmentSession(String username, String host, String password, int port, String hadoop_home_path,Integer PORT){
		jsch = new JSch();
		try {
			this.username = username;
			this.host = host;
			this.port = port;
			this.HADOOP_HOME_PATH = hadoop_home_path;
			//jsch.addIdentity(SSH_KEY_PATH);
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			session.setPort(PORT);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();

		} catch (JSchException e) {
			e.printStackTrace();
		} 
	}



	/**
	 * Get session
	 * @return session 
	 */
	public Session getSession() {
		return session;
	}


	/**
	 * Get UserName
	 * @return usarname
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get Host
	 * @return host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * Get port 
	 * @return port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * Get home's path 
	 * @return homes' path
	 */
	public String getHadoop_home_path() {
		return HADOOP_HOME_PATH;
	}




}
