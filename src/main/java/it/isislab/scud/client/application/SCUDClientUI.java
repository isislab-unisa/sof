package it.isislab.scud.client.application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import it.isislab.scud.client.application.ui.ConnectionFrame;

public class SCUDClientUI {

	public SCUDClientUI(String ... args)
	{
		ConnectionFrame c=new ConnectionFrame();
		c.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SCUDClientUI ui = new SCUDClientUI();

	}
}

