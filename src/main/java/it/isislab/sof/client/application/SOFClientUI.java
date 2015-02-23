package it.isislab.sof.client.application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alee.laf.WebLookAndFeel;

import it.isislab.sof.client.application.ui.ConnectionFrame;

public class SOFClientUI {

	public SOFClientUI(String scud_path,String scud_runner_path)
	{
		ConnectionFrame c=new ConnectionFrame(scud_path,scud_runner_path);
		c.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel( WebLookAndFeel.class.getCanonicalName () );
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SOFClientUI ui = new SOFClientUI("sof-resources/SOF.jar","sof-resources/SOF-RUNNER.jar");

	}
}

