package it.isislab.scud.client.application.console;

import java.util.ArrayList;
import java.util.List;

import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterString;

public enum PromptOuptutParamCommand implements Prompt{
	ADD(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 2){
				c.printf("Few parameters! \nUsage: add [-string | -double | -long] varName");
				return list;
			}else{
				if(params[0].equalsIgnoreCase("-string")){
					Parameter p = new Parameter();
					ParameterString s = new ParameterString();
					p.setparam(s);
					p.setvariable_name(params[1]);
					list.add(p);
					return list;
				}else{
					if(params[0].equalsIgnoreCase("-double")){
						Parameter p = new Parameter();
						ParameterDouble s = new ParameterDouble();
						p.setparam(s);
						p.setvariable_name(params[1]);
						list.add(p);
						return list;
					}else{
						if(params[0].equalsIgnoreCase("-long")){
							Parameter p = new Parameter();
							ParameterLong s = new ParameterLong();
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

	private PromptOuptutParamCommand(Action a)
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
