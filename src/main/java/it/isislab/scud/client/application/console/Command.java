package it.isislab.scud.client.application.console;


import it.isislab.scud.client.application.SCUDShellClient;
import it.isislab.scud.core.engine.hadoop.sshclient.connection.HadoopFileSystemManager;
import it.isislab.scud.core.engine.hadoop.sshclient.connection.ScudManager;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulations;
import it.isislab.scud.core.exception.ParameterException;

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
			c.printf("Disconnect from "+SCUDShellClient.host+".\n");
			ScudManager.disconnect(SCUDShellClient.session);
			System.exit(0);
			return null;
		}
	}),
	LICENSE(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			c.printf("SCUD is a tool utility powerd by ISISLab, 2014.\n");
			return null;

		}
	}),
	HELP(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{   c.printf("**************************************ISISLab******************************************************\n*");
		c.printf("***************************************************************************************************\n*");
		c.printf("*    help                 |print commands list.                                                   *\n*");
		//c.printf("*    usage                |print commands usage, usage <command, >.                               *\n*");
		c.printf("*    submit               |exec the simulation corresponding to the given id.                     *\n*");
		c.printf("*    createsimulation     |create new simulation execution.                                       *\n*");
		c.printf("*    getsimulations       |print all simulations created by the user.                             *\n*");
		c.printf("*    getsimulation        |print status of the simulation corresponding to the given id.          *\n*");
		//c.printf("    printresult          |print metrics on the results of the simulation corresponding to the given id.\n");
		c.printf("*    getresult            |download the results of the simulation corresponding to the given id.  *\n*");
		c.printf("*    list                 |show the simulation list.                                              *\n*");
		c.printf("*    kill                 |kill the simulation corresponding to the given id.                     *\n*");
		c.printf("*    makexml              |generate the xml file corresponding to the given input.                *\n*");
		c.printf("*    exit                 |disconnect user .                                                      *\n*");
		c.printf("***************************************************************************************************\n");
		return null;
		}
	}),

	SUBMIT(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			/**
			 * Simulation s = getSimulationDatabyId(session,  username, simID);
			 */
			if(params == null || params.length < 1 )
			{
				c.printf("usage of submit command : submit ID [ID:intger ID of simulation execution environment]\n");
				return null;
			}else{
				int simID = Integer.parseInt(params[0])-1;
				Simulations sims = ScudManager.getSimulationsData(SCUDShellClient.session);
				if(sims == null){
					c.printf("No such simulation");
					return null;
				}
				Simulation sim = sims.getSimulations().get(simID);
				//sim = ScudManager.getSimulationDatabyId(SCUDShellClient.session,  SCUDShellClient.session.getUsername(), simID);
				ScudManager.runAsynchronousSimulation(SCUDShellClient.session,sim);
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

			String parsedParams[] = ScudManager.parseParamenters(params,4);

			if(parsedParams.length == 6)
			{
				try {
					ScudManager.checkParamMakeSimulationFolder(parsedParams);
				} catch (ParameterException e1) {
					// TODO Auto-generated catch block
					c.printf(e1.getMessage());
					return null;
				}
				try {


					ScudManager.makeSimulationFolder(
							SCUDShellClient.session,
							parsedParams[0],//MODEL TYPE MASON - NETLOGO -GENERIC
							parsedParams[1],//SIM NAME
							parsedParams[2],//INPUT.XML PATH 
							parsedParams[3],//OUTPUT.XML PATH 
							parsedParams[4],//DESCRIPTION SIM
							parsedParams[5]);//SIMULATION EXEC PATH 
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					c.printf("Error in making execution environment!\n");
					return null;
				}
			}else{
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

			String parsedParams[] = ScudManager.parseParamenters(params,7);

			if(parsedParams.length == 9)
			{
				try {
					ScudManager.checkParamMakeSimulationFolder(parsedParams);
				} catch (ParameterException e1) {
					// TODO Auto-generated catch block
					c.printf(e1.getMessage());
					return null;
				}
				try {


					ScudManager.makeSimulationFolderForLoop(
							SCUDShellClient.session,
							parsedParams[0]/*MODEL TYPE MASON - NETLOGO -GENERIC*/,
							parsedParams[1],/*SIM NAME*/
							parsedParams[2],/*domain_pathname*/ 
							parsedParams[3],/*bashCommandForRunnableFunction */
							parsedParams[4],/*output_description_filename*/
							parsedParams[5],/*executable_selection_function_filename */
							parsedParams[6],/*executable_rating_function_filename*/
							parsedParams[7],/*description_simulation*/
							parsedParams[8]);/*executable_simulation_filename*/
					return null;
				} catch (Exception e) {

					e.printStackTrace();
					c.printf("Error in making execution environment!\n");
					return null;
				}
			}else{
				c.printf("Error "+(parsedParams.length<9?"few":"much more")+" parameters.:\n");
				c.printf("usage: MODEL[MASON-NETLOGO-GENERIC] SIM-NAME[String] "+
						"DOMAIN.xml[String absolutely]"+"bash command[for function selection example /usr/bin/sh]"
						+ " Output.xml[String absolutely path] "+"function selection absolutly path"+
						"rating selection absolutly path"+"DESCRIPTION-SIM[String] SIMULATION-EXECUTABLE-MODEL[String absolutely path]\n");
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

				Simulations listSim  = ScudManager.getSimulationsData(SCUDShellClient.session);
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
				Simulations listSim  = ScudManager.getSimulationsData(SCUDShellClient.session);
				if(listSim == null){
					c.printf("No such simulation");
					return null;
				}
				Simulation sim = listSim.getSimulations().get(simID);
				try {
					if(!HadoopFileSystemManager.ifExists(SCUDShellClient.session,ScudManager.fs.getHdfsUserPathSimulationByID(sim.getId())))
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
			if(params == null || params.length < 2 )
			{
				c.printf("Error few parameters!\n Usage: getresult simID destinationDirPath");
				return null;
			}else{
				Simulations sims = ScudManager.getSimulationsData(SCUDShellClient.session);
				if(sims == null){
					c.printf("No such simulation");
					return null;
				}
				int simID = Integer.parseInt(params[0])-1;
				Simulation sim = sims.getSimulations().get(simID);
				ScudManager.downloadSimulation(SCUDShellClient.session,sim.getId(),params[1]);
				return null;
			}
		}
	}),
	LIST(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			Simulations sims = ScudManager.getSimulationsData(SCUDShellClient.session);
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

				Simulations listSim  = ScudManager.getSimulationsData(SCUDShellClient.session);
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

				System.err.println(bash);
				try {
					HadoopFileSystemManager.exec(SCUDShellClient.session,bash);
				} catch (Exception e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
				//
				// pkill non ha value return, trattato a parte anche nel metodo exec generale
				//
				/*boolean result = Integer.parseInt(HadoopFileSystemManager.exec(SCUDShellClient.session,bash))<0?false:true;
					if(result){
						c.printf("Process killed");
						ScudManager.setSimulationStatus(SCUDShellClient.session,sim,Simulation.KILLED);
						return null;

					else{
						c.printf("Error: unable kill process");
						return null;
					}*/ 

			}
		}
	}),
	MAKEXML(new Action()
	{
		@Override
		public Object exec(Console c, String[] params,String stringPrompt)
		{
			/*if(params == null || params.length < 1 )
			{
				c.printf("Error few parameters!\n Usage: makexml [-input | -domain | -output]");
				return null;
			}else{
				if("-input".equalsIgnoreCase(params[0])){
					try {
						String inputPrompt = stringPrompt+"/inputs";
						execCommand(c,PromptInputsCommand.class,inputPrompt);
						PromptInputsCommand.list = new ArrayList();
						return null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}else{
					if("-domain".equalsIgnoreCase(params[0])){
						String domainPrompt = stringPrompt+"/domain";
						System.err.println("function not implemented yet!");
						return null;
					}else{
						if("-output".equalsIgnoreCase(params[0])){
							try {
								String outputPrompt = stringPrompt+"/outputs";
								execCommand(c,PromptOutputsCommand.class,outputPrompt);
								PromptOutputsCommand.list = new ArrayList();
								return null;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								return null;
							}
						}else{
							c.printf("Error unexpected parameter!\n Usage: makexml [-input | -domain | -output]");
							return null;
						}
					}
				}*/
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
				console.printf(SCUDShellClient.COMMAND_ERROR, helpmsg, e.getMessage());
			}
		});


		while (true)
		{
			commandLine = console.readLine(stringPrompt+" "+SCUDShellClient.TIME_FORMAT+" >>>", new Date());
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
							console.printf(SCUDShellClient.COMMAND_ERROR, cmd, e.getMessage());
						}
					});
				}
				catch (IllegalArgumentException e)
				{
					console.printf(SCUDShellClient.UNKNOWN_COMMAND, commandName);
				}
			}

			scanner.close();
		}
	}
}