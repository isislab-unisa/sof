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
package it.isislab.sof.client.application;

import it.isislab.sof.core.engine.hadoop.sshclient.connection.SofManager;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulations;

import java.io.File;
import java.io.FileInputStream;

import com.jcraft.jsch.SftpException;

public class SOFCoreSimpleApplication {

	public static int PORT=22;
	public static String host= "127.0.0.1";
	public static String pstring="password";
	public static String bindir="/isis/hadoop-2.4.0";  
	public static String homedir="/isis/"; 
	public static String javabindir ="/usr/local/java/bin/";
	public static String name="isis";
	public static String scudhomedir="/";

	public static  String toolkit="netlogo";
	public static String simulation_name="aids";
	public static String domain_pathname="examples-sim-aids/domain.xml";
	public static String bashCommandForRunnableFunctionSelection="/usr/local/java/bin/java";
	public static String bashCommandForRunnableFunctionEvaluate="/usr/local/java/bin/java";
	public static String output_description_filename="examples-sim-aids//output.xml";
	public static String executable_selection_function_filename="examples-sim-aids/selection.jar";
	public static String executable_rating_function_filename="examples-sim-aids/evaluate.jar";
	public static String description_simulation="this a simple simulation optimization process for AIDS NetLogo simulation example";
	public static String executable_simulation_filename="examples-sim-aids/aids.nlogo";

	/**
	 * @param args
	 * @throws SftpException 
	 */

	public static EnvironmentSession session;

	public static void main(String[] args) throws SftpException{

		Simulations sims=null;
		try {

			SofManager.setFileSystem(bindir,System.getProperty("user.dir"), scudhomedir, homedir, javabindir ,name);
			if ((session=SofManager.connect(name, host, pstring, bindir,PORT,
					new FileInputStream(System.getProperty("user.dir")+File.separator+"scud-resources"+File.separator+"SCUD.jar"),
					new FileInputStream(System.getProperty("user.dir")+File.separator+"scud-resources"+File.separator+"SCUD-RUNNER.jar")
					))!=null)
			{
				System.out.println("Connected. Type \"help\", \"usage <command>\" or \"license\" for more information.");

			}else{
				System.err.println("Login Correct but there are several problems in the hadoop environment, please contact your hadoop admin.");
				System.exit(-1);
			}
		} catch (Exception e) {
			System.err.println("Login Error. Check your credentials and ip:port of your server and try again .. ");

		}
		//CREATE SIMULATION FROM EXAMPLE IN SO MODE
		try {
			SofManager.makeSimulationFolderForLoop(session, toolkit, simulation_name, domain_pathname, bashCommandForRunnableFunctionSelection,bashCommandForRunnableFunctionEvaluate, 
					output_description_filename, executable_selection_function_filename, executable_rating_function_filename, description_simulation, executable_simulation_filename,""/*param exacutable param for generic mode, not required for netlogo and mason*/);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("SIMULATION AVAILABLE LIST: ");
		sims = SofManager.getSimulationsData(session);
		if(sims == null){
			System.err.println("No such simulations.");
		}
		System.out.println("******************************************************");

		for(int i=1; i<=sims.getSimulations().size(); i++){
			int simID= i-1;
			Simulation s = sims.getSimulations().get(simID);
			System.err.println("sim-id:"+i+") name: "+s.getName()+" state: "+s.getState()+" time: "+s.getCreationTime()+" id: "+s.getId()+"\n");
		}

		System.out.println("******************************************************");

		System.out.println("Start the simulation with sim-id "+(sims.getSimulations().size()));
		sims = SofManager.getSimulationsData(session);


		Simulation s = sims.getSimulations().get(sims.getSimulations().size()-1);
		if(s == null){
			System.err.println("No such simulation with ID "+sims.getSimulations().size());
			System.exit(-1);
		}

		SofManager.runAsynchronousSimulation(session,s);

		System.out.println("Waiting for simulation ends.");
		Simulation sim=null;


		do{
			sims = SofManager.getSimulationsData(session);
			sim = sims.getSimulations().get(sims.getSimulations().size()-1);


		}while(!(sim.getState().equals(Simulation.FINISHED)));
		System.exit(0);

	}
}
