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
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.sof.core.model.parameters.xsd.domain.Domain;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomain;
import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.input.Input;
import it.isislab.sof.core.model.parameters.xsd.input.Inputs;
import it.isislab.sof.core.model.parameters.xsd.output.Output;
import it.isislab.sof.core.model.parameters.xsd.output.Outputs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


public enum PromptCommands implements Prompt{

	NEW(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error, few parameters!\n");
				c.printf("Usage: new [-input | -output | -domain | -simulation ]\n");
				return null;
			}else{
				String s = stringPrompt;
				if(params[0].equalsIgnoreCase("-input")){
					Input i = new Input();
					s+="/input";
					List<Parameter> l = (List<Parameter>)execCommand(c,PromptInputParamCommand.class,s);
					if(l == null || l.isEmpty())
						return inputList;
					i.param_element = l;
					i.id = inputList.size()+1;
					inputList.add(i);
					PromptInputParamCommand.clearParamList();
					return inputList;
				}else{
					if(params[0].equalsIgnoreCase("-output")){
						
						s+="/output";
						//int inputID = Integer.parseInt(params[1]);
						Output o = new Output();
						List<Parameter> l = (List<Parameter>)execCommand(c,PromptOuptutParamCommand.class,s);
						if(l == null || l.isEmpty())
							return output;
						o.output_params=l;
						//o.setIdInput(inputID);
						output =o;
						PromptOuptutParamCommand.clearParamList();
						return null;
					}else{
						if(params[0].equalsIgnoreCase("-domain")){
							s+="/domain/parameter";
							
							domainList = (List<ParameterDomain>)execCommand(c,PromptDomainParamCommand.class,s);
							PromptDomainParamCommand.clearParamList();
							return domainList;
						}else{
							if(params[0].equalsIgnoreCase("-simulation")){
								s+="/simulation/parameter";
								if(sim != null){
									c.printf("Simulation already exists!\n");
									c.printf("You can remove it!\n");
									return sim;
								}
								sim = (Simulation)execCommand(c,PromptSimulationParamCommand.class,s);
								PromptSimulationParamCommand.clearSimulationParams();
								return sim;
							}else{
								c.printf("Error, bad parameter "+params[0]+"!\n");
								c.printf("Usage: new [-input | -output | -domain | -simulation]\n");
								return null;
							}
						}
					}
				}
			}
			
		}
		
	}),
	REMOVE(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error, few parameters!\n");
				c.printf("Usage: remove [-input inputID | -output outputID | -domain ]\n");
				return null;
			}else{
				String s = stringPrompt;
				if(params[0].equalsIgnoreCase("-input")){
					if(params.length < 2){
						c.printf("Few parameters!\nUsage remove -input inputID\n");
						return inputList;
					}
						int id = Integer.parseInt(params[1]);
						Input toRemove=null;
						for(Input i : inputList){
							if(i.id == id){
								toRemove = i;
								break;
							}
						}
						inputList.remove(toRemove);
					return inputList;
				}else{
					if(params[0].equalsIgnoreCase("-output")){
						/*if(params.length > 0){
							c.printf("Few parameters!\nUsage remove -output\n");
							return output;
						}
						int id = Integer.parseInt(params[1]);
						Output toRemove=null;
						for(Output i : output){
							if(i.getIdInput() == id){
								toRemove = i;
								break;
							}
						}
						output.remove(toRemove);*/
						output = new Output();
						return output;
					}else{
						if(params[0].equalsIgnoreCase("-domain")){
							if(params.length < 2){
								c.printf("Few parameters!\nUsage remove -domain varName\n");
								return output;
							}
							ParameterDomain toRemove = null;
							for(ParameterDomain pd : domainList){
								if(pd.getvariable_name().equals(params[1])){
									toRemove = pd;
									break;
								}
							}
							domainList.remove(toRemove);
						
							return domainList;
						}else{
							c.printf("Error, bad parameter "+params[0]+"!\n");
							c.printf("Usage: new [-input | -output | -domain ]\n");
							return null;
						}
					}
				}
			}	
		}
		
	}),
	LIST(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error, few parameters!\n");
				c.printf("Usage: list [-input | -output associatedInputID | -domain ]\n");
				return null;
			}else{
				String s = stringPrompt;
				if(params[0].equalsIgnoreCase("-input")){
					if(inputList.isEmpty()){
						c.printf("Empty!");
						return null;
					}
					c.printf(inputList.toString());
					return null;
				}else{
					if(params[0].equalsIgnoreCase("-output")){
						if(output == null){
							c.printf("Empty!");
							return null;
						}
						c.printf(output.toString());
						return null;
					}else{
						if(params[0].equalsIgnoreCase("-domain")){
							s+="/domain";
							if(domainList == null||domainList.isEmpty())
								c.printf("Empty List!");
							c.printf(domainList.toString());
							return domainList;
						}else{
							c.printf("Error, bad parameter "+params[0]+"!\n");
							c.printf("Usage: new [-input | -output | -domain ]\n");
							return null;
						}
					}
				}
			}
		}
		
	}),
	GENERATEXML(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error, few parameters!\n");
				c.printf("Usage: generatexml [-input | -output | -domain ]\n");
				return null;
			}else{
				
				String s = stringPrompt;
				if(params[0].equalsIgnoreCase("-input")){
					if(sim == null){
						c.printf("You must make simulation parameters before create xml files!");
						return null;
					}
					if(inputList.isEmpty()){
						c.printf("Unable create a empty xml file!\n You must create new input before.\n");
						return null;
					}
					if(params.length < 2){
						c.printf("Few parameters!\nUsage: generatexml -input destinationDir\n");
						return null;
					}
					Inputs is = new Inputs();
					is.setsimulation(sim);
					is.setinput_list(inputList);
					File dir = new File(params[1]);
					if(!dir.exists()) dir.mkdir();
					File inputXml = new File(dir.getAbsolutePath()+File.separator+"input.xml");
					inputXml.createNewFile();
					JAXBContext context= JAXBContext.newInstance(it.isislab.sof.core.model.parameters.xsd.input.Inputs.class);
					Marshaller jaxbMarshaller = context.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					jaxbMarshaller.marshal(is, inputXml);
					return null;
				}else{
					if(params[0].equalsIgnoreCase("-output")){
						if(output == null){
							c.printf("Unable create a empty xml file!\nYou must create new output before.\n");
							return null;
						}
						if(params.length < 2){
							c.printf("Few parameters!\nUsage: generatexml -output destinationDir\n");
							return null;
						}
						
						File dir = new File(params[1]);
						if(!dir.exists()) dir.mkdir();
						File outputXml = new File(dir.getAbsolutePath()+File.separator+"output.xml");
						outputXml.createNewFile();
						JAXBContext context= JAXBContext.newInstance(Outputs.class);
						Marshaller jaxbMarshaller = context.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						jaxbMarshaller.marshal(output, outputXml);
						//jaxbMarshaller.marshal(is, System.out);
						return null;
					}else{
						if(params[0].equalsIgnoreCase("-domain")){
							if(sim == null){
								c.printf("You must make simulation parameters before create xml files!");
								return null;
							}
							
							if(domainList.isEmpty()){
								c.printf("Unable create a empty xml file!\nYou must create new output before.\n");
								return domainList;
							}
							if(params.length < 2){
								c.printf("Few parameters!\nUsage: generatexml -domain destinationDir\n");
								return domainList;
							}
							Domain dom = new Domain();
							dom.param = domainList;
							dom.simulation = sim;
							File dir = new File(params[1]);
							if(!dir.exists()) dir.mkdir();
							File domainXml = new File(dir.getAbsolutePath()+File.separator+"domain.xml");
							domainXml.createNewFile();
							JAXBContext context= JAXBContext.newInstance(Domain.class);
							Marshaller jaxbMarshaller = context.createMarshaller();
							jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
							jaxbMarshaller.marshal(dom, domainXml);
							
							return domainList;
						}else{
							c.printf("Error, bad parameter "+params[0]+"!\n");
							c.printf("Usage: new [-input | -output | -domain ]\n");
							return null;
						}
					}
				}
			}
		}
		
	}),
	HELP(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			c.printf("******** XML MAKE MODE ********\n");
			c.printf("    help                 |print commands list.\n");
			c.printf("    list                 |show the list corresponding to the given xml kind [input, output, domain].\n");
			c.printf("    new                  |generate a new [input,output,domain].\n");
			c.printf("    remove               |remove the element corresponding to the given element.\n");
			c.printf("    generatexml          |generate the xml file of the corresponding xml kind in the given directory.\n");		
			c.printf("    exit                 |go back at previusly SCUD session.\n");
			return null;
		}
		
	});

	private Action action;
	public static List<Input> inputList = new ArrayList<Input>();
	public static Output output = null;
	public static List<ParameterDomain> domainList = new ArrayList<ParameterDomain>();
	public static Simulation sim = null;
	private PromptCommands(Action a)
	{
		this.action = a;
	}
	public static void clearInputList(){
		inputList.clear();
	}
	public static void clearOutputList(){
		output = null;
	}
	public static void clearDomainList(){
		domainList.clear();
	}
	public static void clearSimulationParam(){
		sim = null;
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
