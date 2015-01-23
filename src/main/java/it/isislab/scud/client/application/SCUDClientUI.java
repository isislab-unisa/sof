package it.isislab.scud.client.application;

import it.isislab.scud.client.application.ui.ConnectionFrame;

public class SCUDClientUI {

	public SCUDClientUI(String ... args)
	{
		ConnectionFrame c=new ConnectionFrame();
		c.setVisible(true);
	}

	public static void main(String[] args) {

		SCUDClientUI ui = new SCUDClientUI();

	}
}

