package it.isislab.scud.client.application.ui.newsimulation;

import javax.swing.JPanel;

public class NewSimulationProcess extends JPanel{
	
	private NewSimulationPanel newsimpan;
	private NewDomain newdomain;
	private NewInputOutput newio;
	
	public NewSimulationProcess(String sim_name)
	{
		newsimpan=new NewSimulationPanel(sim_name);
		newdomain=new NewDomain();
		newio=new NewInputOutput();
		
	}
}
