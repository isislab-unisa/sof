package core.engine.hadoop.sshclient.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import core.engine.hadoop.sshclient.connection.ScudManager;
import core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;


public class Test {
	private static String HADOOP_HOME = "hadoop-2.4.0";
	private static String IP="172.16.15.103";
	private static String PASSWORD="";
	/**
	 * @param args
	 * @throws SftpException 
	 */
	public static void main(String[] args) throws SftpException{
		
		
		EnvironmentSession s;
		try {
			s = ScudManager.connect("hadoop", IP, PASSWORD, HADOOP_HOME,22,
					ScudManager.class.getResourceAsStream("//SCUD.jar"),
					ScudManager.class.getResourceAsStream("//SCUD-RUNNER.jar"));
		
			if(!ScudManager.existsUser(s, s.getUsername())){
				if(ScudManager.addUser(s, s.getUsername()))
					System.out.println("Created user "+s.getUsername());

			}
	
		int j=0;

		String model="netlogo";
		String dirPath = "/home/flavio/Scrivania/TestSSH/aids/";
		String javaPath = "/usr/lib/jvm/jdk1.6.0_45/bin/java -jar";
		boolean LOOP = false;

		try {

			/** 
			 * session, 
			 * username, 
			 * model_pathname, 
			 * simulation_name,
			 * domain_pathname, (for loop)
			 * input_pathname, 
			 * output_pathname,
			 * selection_pathname  (for loop)
			 * rating_pathname,   (for loop)
			 * description_simulation_filename, 
			 * note, 
			 * executableSimulationFilePath
			 * 
			 * */

			//j= SshWrapperHadoop.makeSimulationFolder(s,  s.getUserName(), model, "domain", args[0], args[1],args[2] ,args[3], args[4],"vediamo se funzion", "ki te biv", args[5]);

			if(LOOP){
//				j = ConnectionSSH.makeSimulationFolder(s, s.getUsername(), model,"Simulazione 1",
//						dirPath+"domain.xml", dirPath+"input.xml", javaPath, dirPath+"output.xml",
//						dirPath+"Selection.jar",dirPath+"evaluate.jar",
//						"vediamo se funzion", "queste sono le note",
//						dirPath+"AIDS.nlogo");

				/*model=SshWrapperHadoop.NETLOGO_MODEL;

				j = SshWrapperHadoop.makeSimulationFolder(s, s.getUserName(), model,"Netlogo",
					"domain", "/home/flavio/Scrivania/TestSSH/netlogo_input.xml", "/home/flavio/Scrivania/TestSSH/netlogo_output.xml",
					"/home/flavio/Scrivania/TestSSH/netlogo_functionSelection.py","/home/flavio/Scrivania/TestSSH/evaluate.py", "vediamo se funzion", "ki te biv","/home/flavio/Scrivania/TestSSH/network.nlogo");*/

			}else{

//				ConnectionSSH.makeSimulationFolder(s, s.getUsername(), model,"flockers", 
//						dirPath+"input.xml", dirPath+"output.xml",
//						"vediamo se funzion", "ki te biv",dirPath+"AIDS.nlogo");

				/*model=SshWrapperHadoop.NETLOGO_MODEL;
				j = SshWrapperHadoop.makeSimulationFolder(s, s.getUserName(), model,"Netlogo",
						"/home/flavio/Scrivania/TestSSH/netlogo_input.xml", "/home/flavio/Scrivania/TestSSH/netlogo_output.xml",
						"vediamo se funzion", "ki te biv","/home/flavio/Scrivania/TestSSH/network.nlogo");*/

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println(model+" aaaaaaaaaaaaaaaaaaaaaaaaa");
	//	ScudManager.runSimulation(s, s.getUsername(), j, LOOP);

		/*SshHadoopEnvTool.executeSelectionFunction(s, "/SCUD/hadoop/SIM-10/description/selection/functionSelection.py",
				"", "/SCUD/hadoop/SIM-10/execution/loop-1/output/rating", "");*/
		s.getSession().disconnect();
		
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
