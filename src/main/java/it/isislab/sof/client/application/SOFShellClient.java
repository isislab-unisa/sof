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


import it.isislab.sof.client.application.console.Command;
import it.isislab.sof.client.application.console.Console;
import it.isislab.sof.client.application.console.PromptListener;
import it.isislab.sof.core.engine.hadoop.sshclient.connection.SofManager;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;

import java.io.*;
import java.util.*;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.kohsuke.args4j.Option;

public class SOFShellClient
{
	public static final String NO_CONSOLE = "Error: Console unavailable";
	public static final String GREETINGS = "Welcome in Simulation On the CLoud on Hadoop. Please login via SSH to your garanted hadoop server.%n";
	public static final String DENIED_ATTEMPT = "Wrong user name or password [%1$d]%n";
	public static final String ACCESS_DENIED = "Access denied\n";
	public static final String ACCESS_GRANTED = "Access granted\n";
	public static final String UNKNOWN_COMMAND = "Unknown command [%1$s]\n";
	public static final String COMMAND_ERROR = "Command error [%1$s]: [%2$s]\n";

	public static final String TIME_FORMAT = "%1$tH:%1$tM:%1$tS";
	public static final String PROMPT = "sof$ "+TIME_FORMAT + " >>>";
	public static final String USER_PROMPT = TIME_FORMAT + " User: ";
	public static final String PASS_PROMPT = TIME_FORMAT + " Password [%2$s]: ";

//	private static final String USER = "john";
//	private static final String PASS = "secret";

	public static EnvironmentSession session;
	@Option(name="-h",usage="hadoop host address")
	public static String host="127.0.0.1";
	
	@Option(name="-bindir",usage="hadoop bin dir")
	public static String bindir="/bin";
	
	@Option(name="-sofhomedir", usage="SOF root directory")
	public static String sofhomedir="/";

	@Option(name="-homedir",usage="hadoop bin dir")
	public static String homedir="/temp";
	
	@Option(name="-javabindir",usage="java remote bin dir")
	public static String javabindir="/usr/bin";
	
	@Option(name="-port",usage="ssh port to connect to hadoop host")
	public static int PORT=22;

	@Argument
	private List<String> arguments = new ArrayList<String>();

	public static void main(String[] args)
	{
		try {
			
			(new SOFShellClient()).doMain(args);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public void doMain(String[] args) throws Exception
	{
		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(80);

		try {

			parser.parseArgument(args);

		} catch( CmdLineException e ) {

			System.err.println(e.getMessage());
			System.err.println("SOFShellClient [options...] arguments...");
			parser.printUsage(System.err);
			System.err.println();

			// print option sample. This is useful some time
			System.err.println("\t Example: SOFShellClient"+parser.printExample(ExampleMode.ALL));

			return;
		}

		Console console = new Console();
		if (console != null)
		{
			if (login(console))
			{
				//session=null;
				execCommandLoop(console);
			}
		}
		else
		{
			throw new RuntimeException(NO_CONSOLE);
		}
	}

	private static boolean login(Console console) throws IOException 
	{
		console.printf(GREETINGS);
		
		boolean accessGranted = false;
		int attempts = 0;
		String name = null;
		while (!accessGranted && attempts < 5)
		{
			name = console.readLine(USER_PROMPT, new Date());
			char[] passdata = console.readPassword(PASS_PROMPT, new Date(), name);
			System.out.println("Try to connect to  Hadoop host "+host+" on port "+PORT+":");
			String pstring=new String(passdata);
			try {
				//-h 127.0.0.1 -bindir /isis/hadoop-2.4.0 -homedir /isis/ -javabindir /usr/local/java/bin/
				if(bindir.endsWith("/"))
					bindir = bindir.substring(0, bindir.lastIndexOf("/"));
			//System.out.println(bindir);
				if(!homedir.endsWith("/")){
					homedir+="/";
				}
				if(!javabindir.endsWith("/"))
					javabindir+="/";
				if(!sofhomedir.endsWith("/"))
					sofhomedir+="/";
				
				SofManager.setFileSystem(bindir,System.getProperty("user.dir"), sofhomedir, homedir, javabindir ,name);
				if ((session=SofManager.connect(name, host, pstring, bindir,PORT,
//						SOFShellClient.class.getResourceAsStream("SOF.jar"),
//						SOFShellClient.class.getResourceAsStream("SOF-RUNNER.jar")
				new FileInputStream(System.getProperty("user.dir")+File.separator+"sof-resources"+File.separator+"SOF.jar"),
				new FileInputStream(System.getProperty("user.dir")+File.separator+"sof-resources"+File.separator+"SOF-RUNNER.jar")
						))!=null)
				{
					System.out.println("Connected. Type \"help\", \"usage <command>\" or \"license\" for more information.");
					attempts = 0;
					accessGranted = true;
					break;
				}else{
					System.err.println("Login Correct but there are several problems in the hadoop environment, please contact your hadoop admin.");
					System.exit(-1);
				}
			} catch (Exception e) {
				System.err.println("Login Error. Check your credentials and ip:port of your server and try again .. ");
			
				console.printf(e.getMessage());
				console.printf(DENIED_ATTEMPT, ++attempts);
			}
			
		}

		
		
		if (! accessGranted)
		{
			console.printf(ACCESS_DENIED);
			return false;
		}
		
		console.printf(ACCESS_GRANTED);
		return true;
	}

	protected static void execCommandLoop(final Console console) throws IOException
	{
		while (true)
		{
			String commandLine = console.readLine(PROMPT, new Date());
			Scanner scanner = new Scanner(commandLine);

			if (scanner.hasNext())
			{
				final String commandName = scanner.next().toUpperCase();

				try
				{
					final Command cmd = Enum.valueOf(Command.class, commandName);
					String param= scanner.hasNext() ? scanner.nextLine() : null;
					if(param !=null && param.charAt(0)== ' ')
						param=param.substring(1,param.length());
					String[] params = param!=null?param.split(" "):null;
					String rootPrompt = "sof$";
					cmd.exec(console,params,rootPrompt, new PromptListener()
					{
						@Override
						public void exception(Exception e)
						{
							console.printf(COMMAND_ERROR, cmd, e.getMessage());
							e.printStackTrace(System.out);
						}
					});
				}
				catch (IllegalArgumentException e)
				{
					console.printf(UNKNOWN_COMMAND, commandName);
				}
			}

			scanner.close();
		}
	}
}