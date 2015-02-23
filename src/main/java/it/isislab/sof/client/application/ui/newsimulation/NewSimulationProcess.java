package it.isislab.sof.client.application.ui.newsimulation;

import it.isislab.sof.client.application.ui.MainFrame;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.sof.core.model.parameters.xsd.domain.Domain;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewSimulationProcess extends JPanel{
	
	protected NewSimulationPanel newsimpan;
	private NewDomain newdomain;
	private NewInputOutput newio;
	protected MainFrame mainFrame;
	
	public NewSimulationProcess(String sim_name, MainFrame main_frame)
	{
		newsimpan=new NewSimulationPanel(sim_name,this);
		newdomain=new NewDomain(this);
		newio=new NewInputOutput(this);
		mainFrame = main_frame;
		this.setLayout(new BorderLayout());
		
	}
	
	public Simulation getSim(){
		return newsimpan.getSim();
	}
	
	public Domain getDomain(){
		return newdomain.getDomain();
	}
	

	public void setNewSim() {
		this.removeAll();
		this.add(newsimpan);
		
	}
	
	public void setDomainView() {
		this.removeAll();
		this.add(newdomain);
		newdomain.setTreeNodes();
		
	}
	public void setDomainIO() {
		this.removeAll();
		this.add(newio);
		newio.setTreeNodes();
		
	}
}
