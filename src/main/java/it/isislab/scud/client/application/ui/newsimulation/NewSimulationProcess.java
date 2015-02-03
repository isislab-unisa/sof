package it.isislab.scud.client.application.ui.newsimulation;

import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;

import java.awt.BorderLayout;

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
	
	public Simulation getSim(){
		return newsimpan.getSim();
	}
	

	public void setNewSim() {
		this.removeAll();
		this.add(newsimpan);
		
	}
	
	public void setDomainView() {
		this.removeAll();
		this.add(newdomain);
		newdomain.setTreeNode();
		
	}
	public void setDomainIO() {
		this.removeAll();
		this.add(newio);
		
	}
}
