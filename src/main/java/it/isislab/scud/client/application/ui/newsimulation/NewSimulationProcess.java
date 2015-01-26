package it.isislab.scud.client.application.ui.newsimulation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
		this.setLayout(new BorderLayout());
	
		
	}
	public void setNewSim() {
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
