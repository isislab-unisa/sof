package it.isislab.scud.core.engine.hadoop.sshclient.connection;

import it.isislab.scud.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;



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
			s = ScudManager.connect(username,password,hadoopdir,IP,PORT,
					ScudManager.class.getResourceAsStream("//SCUD.jar"),
					ScudManager.class.getResourceAsStream("//SCUD-RUNNER.jar"));

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

