package it.isislab.scud.client.application.console;

public interface Prompt {

	public Object exec(final Console c, final String[] params, String stringPrompt, final PromptListener l);
}
