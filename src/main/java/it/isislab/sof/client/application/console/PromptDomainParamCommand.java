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
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomain;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainContinuous;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainDiscrete;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainListString;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainListValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public enum PromptDomainParamCommand implements Prompt{
	ADD(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error few parameters!\n");
				c.printf("Usage: add [ -discrete varName min max increment| -continuous varName min max increment | -list string varName | -list double varName]\n");
				return null;
			}else{
				if(params[0].equalsIgnoreCase("-discrete")){
					if(params.length < 5){
						c.printf("Error few parameters!\n");
						c.printf("Usage: add -discrete varName min max increment \n");
						return null;
					}else{
						//TO DO: CHECK IF MAX < MIN AND INCREMENT IS > 0
						ParameterDomain pd = new ParameterDomain();
						pd.setvariable_name(params[1]);
						
						ParameterDomainDiscrete pdd = new ParameterDomainDiscrete();
						pdd.setmin(Long.parseLong(params[2]));
						pdd.setmax(Long.parseLong(params[3]));
						pdd.setincrement(Long.parseLong(params[4]));
						
						pd.setparameter(pdd);
						list.add(pd);
						return list;
						
					}
				}else{
					if(params[0].equalsIgnoreCase("-continuous")){
						if(params.length < 5){
							c.printf("Error few parameters!\n");
							c.printf("Usage: add -continuous varName min max increment \n");
							return null;
						}else{
							//TO DO: CHECK IF MAX < MIN AND INCREMENT IS > 0
							ParameterDomain pd = new ParameterDomain();
							pd.setvariable_name(params[1]);
							
							ParameterDomainContinuous pdd = new ParameterDomainContinuous();
							pdd.setmin(Double.parseDouble(params[2]));
							pdd.setmax(Double.parseDouble(params[3]));
							pdd.setincrement(Double.parseDouble(params[4]));
							
							pd.setparameter(pdd);
							list.add(pd);
							return list;
							
						}
						
					}else{
						if(params[0].equalsIgnoreCase("-list")){
							if(params.length < 3){
								c.printf("Error few parameters!\n");
								c.printf("Usage: add [ -discrete varName min max increment| -continuous varName min max increment | -list string varName | -list double varName]\n");
								return null;
							}else{
								String s = stringPrompt +"/list";
								if(params[1].equalsIgnoreCase("string")){
									ParameterDomain pd = new ParameterDomain();
									pd.setvariable_name(params[2]);
									ParameterDomainListString pdls = new ParameterDomainListString();
									pdls.setlist((List<String>)execCommand(c,PromptDomainListStringParamCommand.class,s+"/string"));
									pd.setparameter(pdls);
									list.add(pd);
									PromptDomainListStringParamCommand.clearParamList();
								}else{
									if(params[1].equalsIgnoreCase("double")){
										ParameterDomain pd = new ParameterDomain();
										pd.setvariable_name(params[2]);
										ParameterDomainListValues pdlv = new ParameterDomainListValues();
										pdlv.setlist((List<Double>)execCommand(c,PromptDomainListDoubleParamCommand.class,s+"/double"));
										pd.setparameter(pdlv);
										list.add(pd);
										PromptDomainListDoubleParamCommand.clearParamList();
									}else{
										c.printf("Error unexpected parameter!\n");
										c.printf("Usage: add [ -discrete varName min max increment| -continuous varName min max increment | -list string varName | -list double varName]\n");
										return null;
									}
								}
							}
						}else{
							c.printf("Error unexpected parameter!\n");
							c.printf("Usage: add [ -discrete varName min max increment| -continuous varName min max increment | -list string varName | -list double varName]\n");
							return null;
						}
					}
				}
			}
			return null;
		}

	}),
	REMOVE(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			
			return null;
		}

	}),
	HELP(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			c.printf("******** PARAMETER MAKE MODE ********\n");
			c.printf("    help                 |print commands list.\n");
			c.printf("    add                  |add a new parameter.\n");
			c.printf("    remove               |remove the parameter corresponding to the given name.\n");	
			c.printf("    list                 |show the parameter list.\n");	
			c.printf("    exit                 |go back at previusly SOF session.");
			return list;
		}

	}),
	LIST(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(list == null || list.isEmpty() ){
				c.printf("Empty list!");
				return list;
			}
			c.printf(list.toString());
			return list;
		}

	});

	private Action action;
	public static List<ParameterDomain> list = new ArrayList<>();

	private PromptDomainParamCommand(Action a)
	{
		this.action = a;

	}


	public static void clearParamList(){
		list = new ArrayList<>();
	}
	
	public Object exec(final Console c, final String[] params, String stringPrompt,final PromptListener l)
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
