package client.application.console;

import core.engine.hadoop.sshclient.utils.simulation.Simulation;

public enum PromptSimulationParamCommand implements Prompt{
	ADD(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length < 2){
				c.printf("Error few parameters!\n");
				c.printf("Usage: add [-author | -name | -description | -toolkit] value \n");
				return s;
			}
			if(!params[0].startsWith("-")){
				c.printf("Error uknown parameter!\n");
				c.printf("Usage: add [-author | -name | -description | -toolkit] value\n");
				return s;
			}
				
			final Params p = Enum.valueOf(Params.class,params[0].substring(1).toUpperCase());
			switch(p){
			case AUTHOR: s.setAuthor(params[1]); break;
			case NAME: String simName = "";
						for(int i=1; i<params.length; i++){
								simName+=params[i]+" ";
						} 
						s.setName(simName); 
						break;
			case DESCRIPTION: String description = "";
							 for(int i=1; i<params.length; i++){
								 description+=params[i]+" ";
							 }
							 s.setDescription(description);
							 break;
			case TOOLKIT: String toolkit=(params[1].equalsIgnoreCase("mason") || params[1].equalsIgnoreCase("netlogo") || params[1].equalsIgnoreCase("generic"))?params[1]:null;
						if(toolkit==null){
							c.printf("Error uknown parameter!\n");
							c.printf("Usage: add -toolkit [mason | netlogo | generic]\n");
						}
						s.setToolkit(toolkit);
						break;
			default:c.printf("Error uknown parameter!\n");
			c.printf("Usage: \n");
			break;
			}
			return s;
		}

	}),
	REMOVE(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			if(params == null || params.length <1){
				c.printf("Error few parameters!\n");
				c.printf("Usage: remove [-author | -name | -description | -toolkit]\n");
				return s;
			}
			if(!params[0].startsWith("-")){
				c.printf("Error uknown parameter!\n");
				c.printf("Usage: remove [-author | -name | -description | -toolkit]\n");
				return s;
			}
			final Params p = Enum.valueOf(Params.class,params[0].substring(1));
			switch(p){
			case AUTHOR: s.setAuthor("");break;
			case NAME: s.setName(""); break;
			case DESCRIPTION: s.setDescription(""); break;
			case TOOLKIT:s.setToolkit("");break;
			default:c.printf("Error uknown parameter!\n"); c.printf("Usage: \n");break;
			}
			return s;
		}

	}),
	HELP(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			c.printf("******** PARAMETER MAKE MODE ********\n");
			c.printf("    help                 |print commands list.\n");
			c.printf("    add                  |add a new parameter.\n");
			c.printf("    remove               |remove the parameter corresponding to the given name.\n");	
			c.printf("    list                 |show the simulation parameters.\n");	
			c.printf("    exit                 |go back at previusly SCUD session.");
			return s;
		}

	}),
	LIST(new Action(){

		@Override
		public Object exec(Console c, String[] params, String stringPrompt) throws Exception {
			c.printf(s.toString());
			return s;
		}

	});

	private static Simulation s = new Simulation();
	private Action action;
	
	private PromptSimulationParamCommand(Action action) {
		this.action = action;
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

	private enum Params{
		AUTHOR, NAME,DESCRIPTION,TOOLKIT;
	}

	public static void clearSimulationParams() {
		s = new Simulation();
		
	}
	
}

