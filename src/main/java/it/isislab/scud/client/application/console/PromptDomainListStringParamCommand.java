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

package it.isislab.scud.client.application.console;

import java.util.ArrayList;
import java.util.List;

public enum PromptDomainListStringParamCommand implements Prompt{
	ADD(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error few parameters!\n");
				c.printf("Usage: add value\n");
				return null;
			}
				list.add(params[0]);
				return list;
		}

	}),
	REMOVE(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error few parameters!\n");
				c.printf("Usage: add value\n");
				return list;
			}
			list.remove(params[0]);
			return list;
		}

	}),
	HELP(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			c.printf("******** PARAMETER MAKE MODE ********\n");
			c.printf("    help                 |print commands list.\n");
			c.printf("    add                  |add a new string value.\n");
			c.printf("    remove               |remove the value corresponding to the given string value.\n");	
			c.printf("    list                 |show the values list.\n");	
			c.printf("    exit                 |go back at previusly SCUD session.");
			return list;
		}

	}),
	LIST(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(list.isEmpty()){
				c.printf("Empty list!");
				return list;
			}
			c.printf(list.toString());
			return list;
		}

	});

	private Action action;
	public static List<String> list = new ArrayList<>();

	private PromptDomainListStringParamCommand(Action a)
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
