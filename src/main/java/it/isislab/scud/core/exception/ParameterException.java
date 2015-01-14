package it.isislab.scud.core.exception;

public class ParameterException extends Exception{

	
	private static final long serialVersionUID = 1L;

	public ParameterException(String message) {
	
		super("SCUD ERROR MESSAGE:\n"+message);
	}
}
