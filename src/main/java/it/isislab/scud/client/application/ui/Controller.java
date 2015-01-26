package it.isislab.scud.client.application.ui;

import it.isislab.scud.core.engine.hadoop.sshclient.connection.HadoopFileSystemManager;
import it.isislab.scud.core.engine.hadoop.sshclient.connection.ScudManager;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulations;
import it.isislab.scud.core.exception.ParameterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.jcraft.jsch.JSchException;

public class Controller {
	
	 private static Controller instance = null;
	   protected Controller() {
	      // Exists only to defeat instantiation.
	   }
	   public static Controller getInstance(String ... args) {
	      if(instance == null) {
	         instance = new Controller();
	         parseArguments(args);
	         return instance.login()?instance:null;
	      }
	      return instance;
	   }
	   
	private  EnvironmentSession session;
	

	public  void exit()
	{

		//			c.printf("Disconnect from "+SCUDShellClient.host+".\n");
		ScudManager.disconnect(session);
		System.exit(0);

	}

	public  void license()
	{

		//			c.printf("SCUD is a tool utility powerd by ISISLab, 2014.\n");

	}
	public  void help()
	{
		//	   c.printf("**************************************ISISLab******************************************************\n*");
		//		c.printf("***************************************************************************************************\n*");
		//		c.printf("*    help                 |print commands list.                                                   *\n*");
		//		//c.printf("*    usage                |print commands usage, usage <command, >.                               *\n*");
		//		c.printf("*    submit               |exec the simulation corresponding to the given id.                     *\n*");
		//		c.printf("*    createsimulation     |create new simulation execution.                                       *\n*");
		//		c.printf("*    getsimulations       |print all simulations created by the user.                             *\n*");
		//		c.printf("*    getsimulation        |print status of the simulation corresponding to the given id.          *\n*");
		//		//c.printf("    printresult          |print metrics on the results of the simulation corresponding to the given id.\n");
		//		c.printf("*    getresult            |download the results of the simulation corresponding to the given id.  *\n*");
		//		c.printf("*    list                 |show the simulation list.                                              *\n*");
		//		c.printf("*    kill                 |kill the simulation corresponding to the given id.                     *\n*");
		//		c.printf("*    makexml              |generate the xml file corresponding to the given input.                *\n*");
		//		c.printf("*    exit                 |disconnect user .                                                      *\n*");
		//		c.printf("***************************************************************************************************\n");
	}

	public  void submit(String ...params)
	{
		/**
		 * Simulation s = getSimulationDatabyId(session,  username, simID);
		 */
		if(params == null || params.length < 1 )
		{
			//				c.printf("usage of submit command : submit ID [ID:intger ID of simulation execution environment]\n");
			//				return null;
		}else{
			int simID = Integer.parseInt(params[0])-1;
			Simulations sims = ScudManager.getSimulationsData(session);
			if(sims == null){
				//					c.printf("No such simulation");
				//					return null;
			}
			Simulation sim = sims.getSimulations().get(simID);
			//sim = ScudManager.getSimulationDatabyId(session,  session.getUsername(), simID);
			ScudManager.runAsynchronousSimulation(session,sim);
			//ScudManager.runSimulation(session, session.getUsername(), simID, s.getLoop());	
			//				return null;
		}
	}
	public  void createsimulation(String ... params)
	{

		String parsedParams[] = ScudManager.parseParamenters(params,4);

		if(parsedParams.length == 6)
		{
			try {
				ScudManager.checkParamMakeSimulationFolder(parsedParams);
			} catch (ParameterException e1) {
				// TODO Auto-generated catch block
				//					c.printf(e1.getMessage());
				//					return null;
			}
			try {


				ScudManager.makeSimulationFolder(
						session,
						parsedParams[0],//MODEL TYPE MASON - NETLOGO -GENERIC
						parsedParams[1],//SIM NAME
						parsedParams[2],//INPUT.XML PATH 
						parsedParams[3],//OUTPUT.XML PATH 
						parsedParams[4],//DESCRIPTION SIM
						parsedParams[5]);//SIMULATION EXEC PATH 
				//					return null;
			} catch (Exception e) {
				//					e.printStackTrace();
				//					c.printf("Error in making execution environment!\n");
				//					return null;
			}
		}else{
			//				c.printf("Error "+(parsedParams.length<6?"few":"much more")+" parameters.:\n");
			//				c.printf("usage: MODEL[MASON-NETLOGO-GENERIC] SIM-NAME[String]INPUT.xml[String absolutely]"
			//						+ " Output.xml[String absolutely path] DESCRIPTION-SIM[String] SIMULATION-EXECUTABLE-MODEL[String absolutely path]\n");
			//				return null;
		}
	}

	public  void createsimulationloop(String ... params)
	{

		String parsedParams[] = ScudManager.parseParamenters(params,7);

		if(parsedParams.length == 9)
		{
			try {
				ScudManager.checkParamMakeSimulationFolder(parsedParams);
			} catch (ParameterException e1) {
				// TODO Auto-generated catch block
				//					c.printf(e1.getMessage());
				//					return null;
			}
			try {


//				ScudManager.makeSimulationFolderForLoop(
//						session,
//						parsedParams[0]/*MODEL TYPE MASON - NETLOGO -GENERIC*/,
//						parsedParams[1],/*SIM NAME*/
//						parsedParams[2],/*domain_pathname*/ 
//						parsedParams[3],/*bashCommandForRunnableFunction */
//						parsedParams[4],/*output_description_filename*/
//						parsedParams[5],/*executable_selection_function_filename */
//						parsedParams[6],/*executable_rating_function_filename*/
//						parsedParams[7],/*description_simulation*/
//						parsedParams[8]);/*executable_simulation_filename*/
				//					return null;
			} catch (Exception e) {

				e.printStackTrace();
				//					c.printf("Error in making execution environment!\n");
				//					return null;
			}
		}else{
			//				c.printf("Error "+(parsedParams.length<9?"few":"much more")+" parameters.:\n");
			//				c.printf("usage: MODEL[MASON-NETLOGO-GENERIC] SIM-NAME[String] "+
			//						"DOMAIN.xml[String absolutely]"+"bash command[for function selection example /usr/bin/sh]"
			//						+ " Output.xml[String absolutely path] "+"function selection absolutly path"+
			//						"rating selection absolutly path"+"DESCRIPTION-SIM[String] SIMULATION-EXECUTABLE-MODEL[String absolutely path]\n");
			//				return null;
		}
	}



	public  Simulations getsimulations(String ... params)
	{
		
		return ScudManager.getSimulationsData(session);
	}

	public  void getsimulation(String ... params)
	{

		if(params == null || params.length < 1 )
		{
			//				c.printf("few parameters!\n Usage: getsimuation #IDSIM");
			//				return null;
		}else{
			int simID = Integer.parseInt(params[0])-1;
			Simulations listSim  = ScudManager.getSimulationsData(session);
			if(listSim == null){
				//					c.printf("No such simulation");
				//					return null;
			}
			Simulation sim = listSim.getSimulations().get(simID);
			try {
				if(!HadoopFileSystemManager.
						ifExists(session,ScudManager.fs.getHdfsUserPathSimulationByID(sim.getId())
								))
				{}

				//						c.printf("Simulation SIM"+simID+" not exists\n");


				//					c.printf(sim+"\n");
				//					return null;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//					return null;
			} catch (JSchException e) {
				//					c.printf("Error during resource creating\n"+e.getMessage());
				e.printStackTrace();
				//					return null;
			} catch (IOException e) {
				//					c.printf("Error during resource creating\n"+e.getMessage());
				e.printStackTrace();
				//					return null;
			}

		}
	}

	public  void getresult(String ... params)
	{

		if(params == null)
		{
			//				c.printf("Error few parameters!\n Usage: getresult simID [destinationDirPath]");
			//				return null;
		}else{
			Simulations sims = ScudManager.getSimulationsData(session);
			if(sims == null){
				//					c.printf("No such simulation");
				//					return null;
			}
			int simID = Integer.parseInt(params[0])-1;
			Simulation sim = null;
			try{
				sim = sims.getSimulations().get(simID);
			}catch(IndexOutOfBoundsException e){
				//					c.printf("No such simulation");
				//					return null;
			}
			//if no path is specified, saves in current directory
			String path = (params.length < 2)? System.getProperty("user.dir"):params[1];
			//				c.printf("Simulation will download in: "+path);
			ScudManager.downloadSimulation(session,sim.getId(),path);
			//				return null;
		}
	}
	public  void list(String ... params)
	{

		Simulations sims = ScudManager.getSimulationsData(session);
		if(sims == null){
			//				c.printf("No such simulation");
			//				return null;
		}
		for(int i=1; i<=sims.getSimulations().size(); i++){
			int simID= i-1;
			Simulation s = sims.getSimulations().get(simID);
			//				c.printf("*****************************************************************************************************************\n");
			//				c.printf("sim-id: "+i+" name: "+s.getName()+" state: "+s.getState()+" time: "+s.getCreationTime()+" hdfsId: "+s.getId()+" *\n");
			//				c.printf("*****************************************************************************************************************\n");
		}
		//			return null;
	}
	public  void kill(int simID,String ... params)
	{

		if(params == null || params.length < 1 )
		{
			//				c.printf("Error few parameters!\n Usage: kill simID");
			//				return null;
		}else{
			//				int simID = Integer.parseInt(params[0])-1;

			Simulations listSim  = ScudManager.getSimulationsData(session);
			if(listSim == null){
				//					c.printf("No such simulation");
				//					return null;
			}
			if(listSim.getSimulations().size()-1<simID){
				//					c.printf("Simulation not exists");
				//					return null;
			}

			Simulation sim = listSim.getSimulations().get(simID);
			String cmd="pkill -f \""+sim.getProcessName()+"\"";

			String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";

			System.err.println(bash);
			try {
				HadoopFileSystemManager.exec(session,bash);
			} catch (Exception e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//				return null;


		}
	}
	private  boolean login()
	{
		EnvironmentSession session=null;
		boolean accessGranted=false;
		int attempts = 0;
		while (!accessGranted && attempts < 5)
		{
			try {
				//-h 172.16.142.103 -bindir /isis/hadoop-2.4.0 -homedir /isis/ -javabindir /usr/local/java/bin/
				if(bindir.endsWith("/"))
					bindir = bindir.substring(0, bindir.indexOf("/")-1);
				if(!homedir.endsWith("/")){
					homedir+="/";
				}
				if(!javabindir.endsWith("/"))
					javabindir+="/";

				ScudManager.setFileSystem(bindir,System.getProperty("user.dir"), scudhomedir, homedir, javabindir ,name);
				if ((session=ScudManager.connect(name, host, pstring, bindir,Integer.parseInt(PORT),
						new FileInputStream(System.getProperty("user.dir")+File.separator+"scud-resources"+File.separator+"SCUD.jar"),
						new FileInputStream(System.getProperty("user.dir")+File.separator+"scud-resources"+File.separator+"SCUD-RUNNER.jar")
						))!=null)
				{
//					System.out.println("Connected. Type \"help\", \"usage <command>\" or \"license\" for more information.");
					attempts = 0;
					accessGranted = true;
					break;
				}else{
//					System.err.println("Login Correct but there are several problems in the hadoop environment, please contact your hadoop admin.");
					System.exit(-1);
				}
			} catch (Exception e) {
//				System.err.println("Login Error. Check your credentials and ip:port of your server and try again .. ");
				e.printStackTrace();
			}

		}
		this.session=session;
		return session!=null;

	}
	private  static void parseArguments(String[] args) {

		for (int i = 0; i < args.length; i++) {
			if(i%2==0)
			{
				switch (args[i-1]) {
				case PARAM_USER_NAME:
					name=args[i];
					break;
				case PARAM_PASSWORD:
					pstring=args[i];
					break;
				case PARAM_IP:
					host=args[i];
					break;
				case PARAM_PORT:
					PORT=args[i];
					break;
				case PARAM_BINDIR:
					bindir=args[i];
					break;
				case PARAM_JAVA_HOME:
					javabindir=args[i];
					break;
				case PARAM_SCUD_HOME:
					scudhomedir=args[i];
					break;
				case PARAM_USER_HOME_DIR:
					homedir=args[i];
					break;
				default:
					break;
				}
			}
		}

	}
	
	public static String host= "172.16.15.103";
	public static String pstring="cloudsim1205";
	public static String PORT="22";
	public static String bindir="/home/hadoop/hadoop-2.4.0";  
	public static String homedir="/home/hadoop/"; 
	public static String javabindir ="/usr/bin/";
	public static String name="hadoop";
	public static String scudhomedir="/";

	public static final String PARAM_USER_NAME="username";
	public static final String PARAM_PASSWORD="password";
	public static final String PARAM_IP="ip";
	public static final String PARAM_PORT="port";
	public static final String PARAM_BINDIR="bindir";
	public static final String PARAM_JAVA_HOME="javahome";
	public static final String PARAM_SCUD_HOME="scudhome";
	public static final String PARAM_USER_HOME_DIR="homedir";


}
