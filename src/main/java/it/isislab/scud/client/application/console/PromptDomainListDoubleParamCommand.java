package it.isislab.scud.client.application.console;

import java.util.ArrayList;
import java.util.List;

public enum PromptDomainListDoubleParamCommand implements Prompt{
	ADD(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 1){
				c.printf("Error few parameters!\n");
				c.printf("Usage: add value\n");
				return null;
			}else{
				// TO DO: CHEK IF VALUE IS A DOUBLE VALUE
				double val = Double.parseDouble(params[0]);
				list.add(val);
				return list;
			}
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
			double toRemove = Double.parseDouble(params[0]);
			list.remove(toRemove);
			return list;
		}

	}),
	HELP(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			c.printf("******** PARAMETER MAKE MODE ********\n");
			c.printf("    help                 |print commands list.\n");
			c.printf("    add                  |add a new double value.\n");
			c.printf("    remove               |remove the value corresponding to the given double value.\n");	
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
	public static List<Double> list = new ArrayList<Double>();

	private PromptDomainListDoubleParamCommand(Action a)
	{
		this.action = a;

	}


	public static void clearParamList(){
		list = new ArrayList<Double>();
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
