package it.isislab.scud.client.application.console;

public interface Action {

	public Object exec(Console c, String[] params,String stringPrompt) throws Exception;
}
