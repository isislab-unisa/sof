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

import it.isislab.sof.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;



@Deprecated
public class ConnectionSession {
	private EnvironmentSession s;

	private ConnectionSession() {
		// prevent instantiation from outside
	}

//	public static ConnectionSession getInstance() {
//
//		return SingletonUtil.getSessionInstance( ConnectionSession.class );
//	}
	public boolean initConnectionToHadoop(String username,String password,String hadoopdir,String IP,Integer PORT)
	{

//		RWT.getUISession().setAttribute("username", username);
//		RWT.getUISession().setAttribute("password", password);
//		RWT.getUISession().setAttribute("hadoopdir", hadoopdir);
//		RWT.getUISession().setAttribute("IP", IP);

		//Check if the user is in the system.
		try{
			s = SofManager.connect(username,password,hadoopdir,IP,PORT,
					SofManager.class.getResourceAsStream("//SOF.jar"),
					SofManager.class.getResourceAsStream("//SOF-RUNNER.jar"));

			return s!=null;

		}catch(Exception e)
		{
			
			return false;

		}
	}
	public EnvironmentSession getEnvironmentSession()
	{
		return s;
	}
	public void resetEnvironmentSession()
	{
		s=null;
	}

	//	public EnvironmentSession reInitConnectionToHadoop()
	//	{	
	//		//Check if the user is in the system.
	//		try{
	//			EnvironmentSession s = ConnectionSSH.connect(""+RWT.getUISession().getAttribute("username"), ""+RWT.getUISession().getAttribute("password"),
	//					""+RWT.getUISession().getAttribute("hadoopdir"), ""+RWT.getUISession().getAttribute("IP"));
	//
	//			return s;
	//			
	//		}catch(Exception e)
	//		{
	//			e.printStackTrace();
	//			return null;
	//
	//		}
}

