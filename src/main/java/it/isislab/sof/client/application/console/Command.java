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
package it.isislab.sof.client.application.console;


import it.isislab.sof.client.application.SOFShellClient;
import it.isislab.sof.core.engine.hadoop.sshclient.connection.HadoopFileSystemManager;
import it.isislab.sof.core.engine.hadoop.sshclient.connection.SofManager;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulations;
import it.isislab.sof.core.exception.ParameterException;
import it.isislab.sof.core.model.parameters.xsd.message.Message;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.jcraft.jsch.JSchException;

public enum Command implements Prompt
{
	EXIT(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			c.printf("Disconnect from "+SOFShellClient.host+".\n");
			SofManager.disconnect(SOFShellClient.session);
			System.exit(0);
			return null;
		}
	}),
	LICENSE(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			c.printf("SOF is a tool utility powerd by ISISLab, 2014.\n");
			return null;

		}
	}),
	HELP(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{   
		c.printf("**************************************ISISLab**UNISA***********************************************\n*");
		c.printf("***************************************************************************************************\n*");
		c.printf("*    help                  |print commands list                                                   *\n*");
		c.printf("*    start                 |exec the simulation corresponding to the given id                     *\n*");
		c.printf("*    stop                  |stop the simulation corresponding to the given id                     *\n*");
		c.printf("*    createsimulation      |create new simulation execution in space exploitation mode            *\n*");
		c.printf("*    createsimulationloop  |create new simulation execution in simulation optimization            *\n*");
		c.printf("*    getsimulations        |print all simulations created by the user                             *\n*");
		c.printf("*    getsimulation         |print status of the simulation corresponding to the given id          *\n*");
		c.printf("*    getresult             |download the results of the simulation corresponding to the given id  *\n*");
		c.printf("*    list                  |show the simulation list                                              *\n*");
		c.printf("*    kill                  |kill the simulation corresponding to the given id                     *\n*");
		c.printf("*    makexml               |generate the xml file corresponding to the given input                *\n*");
		c.printf("*    exit                  |disconnect user                                                       *\n*");
		c.printf("***************************************************************************************************\n");
		return null;
		}
	}),

	START(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			/**
			 * Simulation s = getSimulationDatabyId(session,  username, simID);
			 */
			if(params == null || params.length < 1 )
			{
				c.printf("usage of start command : start ID [ID:intger ID of simulation execution environment]\n");
				return null;
			}else{
				int simID = Integer.parseInt(params[0])-1;
				Simulations sims = SofManager.getSimulationsData(SOFShellClient.session);
				if(sims == null){
					c.printf("No such simulation");
					return null;
				}
				Simulation sim = sims.getSimulations().get(simID);
				//sim = ScudManager.getSimulationDatabyId(SCUDShellClient.session,  SCUDShellClient.session.getUsername(), simID);
				SofManager.runAsynchronousSimulation(SOFShellClient.session,sim);
				//ScudManager.runSimulation(SCUDShellClient.session, SCUDShellClient.session.getUsername(), simID, s.getLoop());	
				return null;
			}
		}
	}),

	CREATESIMULATION(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{

			String parsedParams[] = SofManager.parseParamenters(params,4);

			if(parsedParams.length == 6)
			{
				
				try {
					SofManager.checkParamMakeSimulationFolder(parsedParams);
					
				} catch (ParameterException e1) {
					// TODO Auto-generated catch block
					c.printf(e1.getMessage());
					return null;
				}
				try {


					SofManager.makeSimulationFolder(
							
							SOFShellClient.session,
							parsedParams[0],//MODEL TYPE MASON - NETLOGO -GENERIC
							parsedParams[1],//SIM NAME
							parsedParams[2],//INPUT.XML PATH 
							parsedParams[3],//OUTPUT.XML PATH 
							parsedParams[4],//DESCRIPTION SIM
							parsedParams[5],//SIMULATION EXEC PATH
							""); //interpretergenericpath
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					c.printf("Error in making execution environment!\n");
					return null;
				}
			}
			else if(parsedParams.length == 7){
				try {
					
					SofManager.checkParamMakeSimulationFolder(parsedParams);
				} catch (ParameterException e1) {
					// TODO Auto-generated catch block
					c.printf(e1.getMessage());
					return null;
				}
				try {


					SofManager.makeSimulationFolder(
							SOFShellClient.session,
							parsedParams[0],//MODEL TYPE MASON - NETLOGO -GENERIC
							parsedParams[1],//SIM NAME
							parsedParams[2],//INPUT.XML PATH 
							parsedParams[3],//OUTPUT.XML PATH 
							parsedParams[4],//DESCRIPTION SIM
							parsedParams[5],//SIMULATION EXEC PATH
							parsedParams[6]); //intepretergenericpath
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					c.printf("Error in making execution environment!\n");
					return null;
				}
			}
			
			else{
				c.printf("Error "+(parsedParams.length<6?"few":"much more")+" parameters.:\n");
				c.printf("usage: MODEL[MASON-NETLOGO-GENERIC] SIM-NAME[String]INPUT.xml[String absolutely]"
						+ " Output.xml[String absolutely path] DESCRIPTION-SIM[String] SIMULATION-EXECUTABLE-MODEL[String absolutely path]\n");
				return null;
			}
		}
	}),
	CREATESIMULATIONLOOP(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{

			String parsedParams[] = SofManager.parseParamenters(params,8);
				
			if(parsedParams.length == 10)
			{
				try {
					SofManager.checkParamMakeSimulationFolder(parsedParams);
				} catch (ParameterException e1) {
					// TODO Auto-generated catch block
					c.printf(e1.getMessage());
					return null;
				}
				try {

					
					
					SofManager.makeSimulationFolderForLoop(
							SOFShellClient.session,
							parsedParams[0]/*MODEL TYPE MASON - NETLOGO -GENERIC*/,
							parsedParams[1],/*SIM NAME*/
							parsedParams[2],/*domain_pathname*/ 
							parsedParams[3],/*bashCommandForRunnableFunctionSelection */
							parsedParams[4],/*bashCommandForRunnableFunctionEvaluate*/
							parsedParams[5],/*output_description_filename*/
							parsedParams[6],/*executable_selection_function_filename*/ 
							parsedParams[7],/*executable_rating_function_filename*/
							parsedParams[8],/*description_simulation*/
							parsedParams[9],"");/*executable_simulation_filename*/
					
					return null;
				} catch (Exception e) {

					e.printStackTrace();
					c.printf("Error in making execution environment!\n");
					return null;
				}
			}
			else if(parsedParams.length == 11){
				
				try {
					SofManager.checkParamMakeSimulationFolder(parsedParams);
				} catch (ParameterException e1) {
					// TODO Auto-generated catch block
					c.printf(e1.getMessage());
					return null;
				}
				try {

					
					
					SofManager.makeSimulationFolderForLoop(
							SOFShellClient.session,
							parsedParams[0]/*MODEL TYPE MASON - NETLOGO -GENERIC*/,
							parsedParams[1],/*SIM NAME*/
							parsedParams[2],/*domain_pathname*/ 
							parsedParams[3],/*bashCommandForRunnableFunctionSelection */
							parsedParams[4],/*bashCommandForRunnableFunctionEvaluate*/
							parsedParams[5],/*output_description_filename*/
							parsedParams[6],/*executable_selection_function_filename*/ 
							parsedParams[7],/*executable_rating_function_filename*/
							parsedParams[8],/*description_simulation*/
							parsedParams[9],/*executable_simulation_filename*/
							parsedParams[10]);//interpreter generic
					
					return null;
				} catch (Exception e) {

					e.printStackTrace();
					c.printf("Error in making execution environment!\n");
					return null;
				}
				
				
				
			}
			
			
			else{
				c.printf("Error "+(parsedParams.length<10?"few":"much more")+" parameters.:\n");
				c.printf("usage: MODEL[MASON-NETLOGO-GENERIC] SIM-NAME[String] "+
						"DOMAIN.xml[String absolutely] "+
						"bash command[for selection function example /usr/bin/sh] "+
						"bash command[for evaluate function example /usr/bin/sh] "+
						"Output.xml[String absolutely path] "+"function selection absolutly path "+
						"rating selection absolutly path "+
						"DESCRIPTION-SIM[String] "+
						"SIMULATION-EXECUTABLE-MODEL[String absolutely path]\n");
				return null;
			}
		}
	}),
	GETSIMULATIONS(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{	
			try {

				Simulations listSim  = SofManager.getSimulationsData(SOFShellClient.session);
				if(listSim == null){
					c.printf("No such simulation");
					return null;
				}

				for(Simulation s: listSim.getSimulations())
					c.printf(s+"\n");
				return null;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}),
	GETSIMULATION(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			if(params == null || params.length < 1 )
			{
				c.printf("few parameters!\n Usage: getsimuation #IDSIM");
				return null;
			}else{
				int simID = Integer.parseInt(params[0])-1;
				Simulations listSim  = SofManager.getSimulationsData(SOFShellClient.session);
				if(listSim == null){
					c.printf("No such simulation");
					return null;
				}
				Simulation sim = listSim.getSimulations().get(simID);
				try {
					if(!HadoopFileSystemManager.ifExists(SOFShellClient.session,SofManager.fs.getHdfsUserPathSimulationByID(sim.getId())))
						c.printf("Simulation SIM"+simID+" not exists\n");

					//sim = ScudManager.getSimulationDatabyId(SCUDShellClient.session,SCUDShellClient.session.getUsername(),simID);
					c.printf(sim+"\n");
					return null;
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (JSchException e) {
					c.printf("Error during resource creating\n"+e.getMessage());
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					c.printf("Error during resource creating\n"+e.getMessage());
					e.printStackTrace();
					return null;
				}

			}
		}
	}),

	GETRESULT(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			if(params == null)
			{
				c.printf("Error few parameters!\n Usage: getresult simID [destinationDirPath]");
				return null;
			}else{
				Simulations sims = SofManager.getSimulationsData(SOFShellClient.session);
				if(sims == null){
					c.printf("No such simulation");
					return null;
				}
				int simID = Integer.parseInt(params[0])-1;
				Simulation sim = null;
				try{
					sim = sims.getSimulations().get(simID);
				}catch(IndexOutOfBoundsException e){
					c.printf("No such simulation");
					return null;
				}
				//if no path is specified, saves in current directory
				String path = (params.length < 2)? System.getProperty("user.dir"):params[1];
				c.printf("Simulation will download in: "+path);
				SofManager.downloadSimulation(SOFShellClient.session,sim.getId(),path);
				return null;
			}
		}
	}),
	LIST(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			Simulations sims = SofManager.getSimulationsData(SOFShellClient.session);
			if(sims == null){
				c.printf("No such simulation");
				return null;
			}
			for(int i=1; i<=sims.getSimulations().size(); i++){
				int simID= i-1;
				Simulation s = sims.getSimulations().get(simID);
				c.printf("*****************************************************************************************************************\n");
				c.printf("sim-id: "+i+" name: "+s.getName()+" state: "+s.getState()+" time: "+s.getCreationTime()+" hdfsId: "+s.getId()+" *\n");
				c.printf("*****************************************************************************************************************\n");
			}
			return null;
		}
	}),
	KILL(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			if(params == null || params.length < 1 )
			{
				c.printf("Error few parameters!\n Usage: kill simID");
				return null;
			}else{
				int simID = Integer.parseInt(params[0])-1;

				Simulations listSim  = SofManager.getSimulationsData(SOFShellClient.session);
				if(listSim == null){
					c.printf("No such simulation");
					return null;
				}
				if(listSim.getSimulations().size()-1<simID){
					c.printf("Simulation not exists");
					return null;
				}

				Simulation sim = listSim.getSimulations().get(simID);
				String cmd="pkill -f \""+sim.getProcessName()+"\"";

				String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";

				try {
					HadoopFileSystemManager.exec(SOFShellClient.session,bash);
				} catch (Exception e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
				c.printf("Process killed");
				SofManager.setSimulationStatus(SOFShellClient.session,sim,Simulation.KILLED);
				return null;
			}
		}
	}),
	STOP(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			if(params == null || params.length < 1 )
			{
				c.printf("Error few parameters!\n Usage: stop simID");
				return null;
			}else{
				int simID = Integer.parseInt(params[0])-1;

				Simulations listSim  = SofManager.getSimulationsData(SOFShellClient.session);
				if(listSim == null){
					c.printf("No such simulation");
					return null;
				}
				if(listSim.getSimulations().size()-1<simID){
					c.printf("Simulation not exists");
					return null;
				}

				Simulation sim = listSim.getSimulations().get(simID);
				if(sim.getState().equals(Simulation.STOPPED)){
					c.printf("Already stopped!\n");
					return null;
				}
				sim = listSim.getSimulations().get(simID);
				if(sim.getState().equals(Simulation.RUNNING)){
				   Message stop = new Message();
				   stop.setId(SofManager.getMexID());
				   stop.setMessage(Message.STOP_MESSAGE);
				   SofManager.sendMessage(SOFShellClient.session, sim, stop);
				   return null;
				}
				return null;
			}
		}
	}),
	MAKEXML(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			
			try {
				if(!PromptCommands.inputList.isEmpty())
					PromptCommands.clearInputList();
				if(PromptCommands.output!=null)
					PromptCommands.clearOutputList();
				if(!PromptCommands.domainList.isEmpty())
					PromptCommands.clearDomainList();
				if(PromptCommands.sim != null)
					PromptCommands.clearSimulationParam();

				execCommand(c,PromptCommands.class,stringPrompt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	});

	private Action action;

	private Command(Action a)
	{
		this.action = a;
	}

	public Object exec(final Console c, final String[] params,String stringPrompt, final PromptListener l)
	{
		try
		{
			return action.exec(c, params,stringPrompt);
		}
		catch (Exception e)
		{
			l.exception(e);
			return null;
		}
	}

	private static Object execCommand(final Console console, Class enumClass, String stringPrompt) throws IOException{
		String commandLine = null;
		Scanner scanner = null;
		Object toReturn = null;
		final Enum helpmsg = Enum.valueOf(enumClass, "help".toUpperCase());
		((Prompt)helpmsg).exec(console,null,stringPrompt, new PromptListener()
		{
			@Override
			public void exception(Exception e)
			{
				console.printf(SOFShellClient.COMMAND_ERROR, helpmsg, e.getMessage());
			}
		});


		while (true)
		{
			commandLine = console.readLine(stringPrompt+" "+SOFShellClient.TIME_FORMAT+" >>>", new Date());
			scanner = new Scanner(commandLine);

			if (scanner.hasNext())
			{
				final String commandName = scanner.next().toUpperCase();
				if(commandName.equalsIgnoreCase("exit")){
					return toReturn;
				}
				try
				{
					final Enum cmd = Enum.valueOf(enumClass, commandName);
					String param= scanner.hasNext() ? scanner.nextLine() : null;
					if(param !=null && param.charAt(0)== ' ')
						param=param.substring(1,param.length());
					String[] params = param!=null?param.split(" "):null;

					toReturn = ((Prompt)cmd).exec(console,params, stringPrompt, new PromptListener()
					{
						@Override
						public void exception(Exception e)
						{
							console.printf(SOFShellClient.COMMAND_ERROR, cmd, e.getMessage());
						}
					});
				}
				catch (IllegalArgumentException e)
				{
					console.printf(SOFShellClient.UNKNOWN_COMMAND, commandName);
				}
			}

			scanner.close();
		}
	}
}