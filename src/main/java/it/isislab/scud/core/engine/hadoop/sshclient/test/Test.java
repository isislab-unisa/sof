package it.isislab.scud.core.engine.hadoop.sshclient.test;



import it.isislab.scud.client.application.SCUDShellClient;
import it.isislab.scud.core.engine.hadoop.sshclient.connection.HadoopFileSystemManager;
import it.isislab.scud.core.engine.hadoop.sshclient.connection.ScudManager;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulations;

import java.io.IOException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


public class Test {
	public static int PORT=22;
	public static String host= "172.16.142.103";
	public static String pstring="clgvittorio";
	public static String bindir="/isis/hadoop-2.4.0";  
	public static String homedir="/isis/"; 
	public static String javabindir ="/usr/local/java/bin/";
	public static String name="isis";
	public static String scudhomedir="/";
	/**
	 * @param args
	 * @throws SftpException 
	 */

	public static EnvironmentSession session;
	int attempts = 0;
	boolean accessGranted = false;
	public static void main(String[] args) throws SftpException{
		//Session s =SshWrapperHadoop.connect("hadoop", "172.16.15.103", "cloudsim1205");
		//String HADOOP_HOME = "hadoop-2.4.0";
		//String HADOOP_HOME = "/usr/local/hadoop-2.4.0";
		//EnviromentSession s =SshWrapperHadoop.connect("isis", "172.16.15.1", "clgvittorio", 3322, HADOOP_HOME);

		 
		




		String toolkit="netlogo";
		String simulation_name="aids";
		String domain_pathname="/home/michele/Scrivania/aids/domain.xml";
		String bashCommandForRunnableFunction="/usr/local/java/bin/java";
		String output_description_filename="/home/michele/Scrivania/aids/output.xml";
		String executable_selection_function_filename="/home/michele/Scrivania/aids/Netlogo_Selection.jar";
		String executable_rating_function_filename="/home/michele/Scrivania/aids/Netlogo_Evaluate.jar";
		String description_simulation="a description";
		String executable_simulation_filename="/home/michele/Scrivania/aids/aids.nlogo";

		Simulations sims=null;

		try {

			//-h 172.16.142.103 -bindir /isis/hadoop-2.4.0 -homedir /isis/ -javabindir /usr/local/java/bin/


			ScudManager.setFileSystem(bindir,System.getProperty("user.dir"), scudhomedir, homedir, javabindir ,name);

			if ((session=ScudManager.connect(name, host, pstring, bindir,PORT,
					SCUDShellClient.class.getResourceAsStream("SCUD.jar"),
					SCUDShellClient.class.getResourceAsStream("SCUD-RUNNER.jar")))!=null)
			{
				System.out.println("Connected. Type \"help\", \"usage <command>\" or \"license\" for more information.");

			}else{
				System.err.println("Login Correct but there are several problems in the hadoop environment, please contact your hadoop admin.");
				System.exit(-1);
			}
		} catch (Exception e) {
			System.err.println("Login Error. Check your credentials and ip:port of your server and try again .. ");

		}


		System.out.println("creo 3 sim, ");

		String cmd="createsimulationloop netlogo aids /home/michele/Scrivania/aids/domain.xml /usr/local/java/bin/java /home/michele/Scrivania/aids/output.xml /home/michele/Scrivania/aids/Netlogo_Selection.jar /home/michele/Scrivania/aids/Netlogo_Evaluate.jar a description /home/michele/Scrivania/aids/aids.nlogo"; 

		System.out.println("Invoking cmd "+cmd);


		for(int i=0;i<3;i++){
			try {
				ScudManager.makeSimulationFolderForLoop(session, toolkit, simulation_name, domain_pathname, bashCommandForRunnableFunction, output_description_filename, 
						executable_selection_function_filename, executable_rating_function_filename, description_simulation, executable_simulation_filename);
				int j=i;
				j++;
				System.err.println("creating sim "+j);
				//Thread.sleep(10000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("3 Simulations created");

		System.out.println("\n\n\n********************************************************************************************");







		System.out.println("show the list of simulations, invoking command: list ");
		sims = ScudManager.getSimulationsData(session);
		if(sims == null){
			System.err.println("No such simulation");

		}

		System.out.println("********************************************************************************************");

		for(int i=1; i<=sims.getSimulations().size(); i++){
			int simID= i-1;
			Simulation s = sims.getSimulations().get(simID);
			System.err.println("sim-id:"+i+") name: "+s.getName()+" state: "+s.getState()+" time: "+s.getCreationTime()+" id: "+s.getId()+"\n");
		}

		System.out.println("********************************************************************************************");


		System.out.println("\n\n\n********************************************************************************************");


		System.out.println("Running the simulation with sim-id 2, invoking submit 2 command");
		sims = ScudManager.getSimulationsData(session);

		int simID = 2;
		if(sims == null){
			System.err.println("No such simulation");
		}

		Simulation s = sims.getSimulations().get(simID-1);
		//sim = ScudManager.getSimulationDatabyId(SCUDShellClient.session,  SCUDShellClient.session.getUsername(), simID);
		ScudManager.runAsynchronousSimulation(session,s);
		System.out.println("********************************************************************************************\n\n\n");




		System.out.println("Waiting for first Loop ");
		sims = ScudManager.getSimulationsData(session);
		Simulation sim = sims.getSimulations().get(simID);
		int loop=sim.getRuns().getLoops().size();
		while(loop!=1){
			sims = ScudManager.getSimulationsData(session);
			sim = sims.getSimulations().get(simID);
			loop=sim.getRuns().getLoops().size();
		}
		System.out.println("********************************************************************************************\n\n\n");





		System.err.println("Show simulation info of the simulation 2, getsimulation 2");


		Simulations listSim  = ScudManager.getSimulationsData(session);
		if(listSim == null){
			System.err.println("No such simulation");

		}
		Simulation ss = listSim.getSimulations().get(simID-1);

		try {
			if(!HadoopFileSystemManager.ifExists(session,ScudManager.fs.getHdfsUserPathSimulationByID(ss.getId())))
				System.err.println("Simulation SIM"+simID+" not exists\n");
		} catch (Exception e) {

			e.printStackTrace();
		} 

		System.err.println(ss+"\n");
		System.out.println("\n\n\n********************************************************************************************\n\n\n");









		System.out.println("I want to download results of first loop, invoking getresult 2 /user/localpath ");
		sims = ScudManager.getSimulationsData(session);
		if(sims == null){
			System.err.println("No such simulation");

		}

		Simulation si = sims.getSimulations().get(simID);
		String localPath=System.getProperty("user.dir");
		ScudManager.downloadSimulation(session,si.getId(),localPath);
		System.err.println("Zipped file created in "+localPath);
		System.out.println("********************************************************************************************\n\n\n");












		System.out.println("Killing simulation with id 2");

		sims  = ScudManager.getSimulationsData(session);
		if(sims == null){
			System.err.println("No such simulation");

		}
		if(sims.getSimulations().size()-1<simID){
			System.err.println("Simulation not exists");

		}

		s = sims.getSimulations().get(simID);

		String comm="pkill -f \""+s.getProcessName()+"\"";

		String bash = "if "+comm+" ; then echo 0; else echo -1; fi";

		System.err.println(bash);
		System.err.println("Invoking command **********************************");


		try {
			HadoopFileSystemManager.exec(session,bash);
		} catch (JSchException  e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (IOException e2 ) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}



		System.exit(0);

	}
}
