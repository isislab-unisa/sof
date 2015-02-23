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

import java.util.ArrayList;
import java.util.List;

import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterString;


public enum PromptInputParamCommand implements Prompt{
	ADD(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 3){
				c.printf("Few parameters! \nUsage: add [-string | -double | -long] varName value");
				return list;
			}else{
				if(params[0].equalsIgnoreCase("-string")){
					Parameter p = new Parameter();
					ParameterString s = new ParameterString();
					s.setvalue(params[2]);
					p.setparam(s);
					p.setvariable_name(params[1]);
					list.add(p);
					return list;
				}else{
					if(params[0].equalsIgnoreCase("-double")){
						Parameter p = new Parameter();
						ParameterDouble s = new ParameterDouble();
						s.setvalue(Double.parseDouble(params[2]));
						p.setparam(s);
						p.setvariable_name(params[1]);
						list.add(p);
						return list;
					}else{
						if(params[0].equalsIgnoreCase("-long")){
							Parameter p = new Parameter();
							ParameterLong s = new ParameterLong();
							s.setvalue(Long.parseLong(params[2]));
							p.setparam(s);
							p.setvariable_name(params[1]);
							list.add(p);
							return list;
						}else{
							c.printf("Unknows parameter! \n Usage: add [-string | -double | -long] varName value");
							return list;
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
				c.printf("Few parameters!\nUsage: remove varName");
				return list;
			}else{
				Parameter toRemove = null;
				for(Parameter p : list)
					if(p.getvariable_name().equals(params[0])){
						toRemove = p;
						break;
					}
				list.remove(toRemove);
				return list;
			}

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
			c.printf("    exit                 |go back at previusly SCUD session.");
			return list;
		}

	}),
	LIST(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(list == null || list.isEmpty()){
				c.printf("Empty!");
				return list;
			}
			c.printf(list.toString());
			return list;
		}

	});

	private Action action;
	public static List<Parameter> list = new ArrayList<>();

	private PromptInputParamCommand(Action a)
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

}
