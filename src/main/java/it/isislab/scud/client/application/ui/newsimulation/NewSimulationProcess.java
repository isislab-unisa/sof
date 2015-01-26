package it.isislab.scud.client.application.ui.newsimulation;

import javax.swing.JPanel;

public class NewSimulationProcess extends JPanel{
	
	private NewSimulationPanel newsimpan;
	private NewDomain newdomain;
	private NewInputOutput newio;
	
	public NewSimulationProcess(String sim_name)
	{
		newsimpan=new NewSimulationPanel(sim_name,this);
		newdomain=new NewDomain(this);
		newio=new NewInputOutput(this);
	
		
	}
	public void setDomainNewSim() {
		this.removeAll();
		this.add(newsimpan);
		
	}
	
	public void setDomainView() {
		this.removeAll();
		this.add(newdomain);
		
	}
	public void setDomainIO() {
		this.removeAll();
		this.add(newio);
		
	}
}
