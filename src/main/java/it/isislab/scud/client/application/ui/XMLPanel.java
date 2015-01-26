package it.isislab.scud.client.application.ui;

import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Loop;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * @author Carmine Spagnuolo
 */
public class XMLPanel extends JPanel {
	Simulation s;
	public XMLPanel(Simulation simulation) {
		this.s=simulation;
		initComponents();
		initTree();
	}

	private void initTree() {
	
		DefaultMutableTreeNode new_sim = new DefaultMutableTreeNode("Simulation name: "+s.getName());
		scrollPane1.remove(tree1);
		tree1 = new JTree(new_sim);
		scrollPane1.setViewportView(tree1);

		new_sim.add(new DefaultMutableTreeNode("Author: "+s.getAuthor()));
		new_sim.add(new DefaultMutableTreeNode("Creation time: "+s.getCreationTime()));
		DefaultMutableTreeNode descr=new DefaultMutableTreeNode("Description: "+s.getDescription().substring(0, (s.getDescription().length()>15)?15:s.getDescription().length()-1));
		descr.add(new DefaultMutableTreeNode(s.getDescription()));
		new_sim.add(descr);
		new_sim.add(new DefaultMutableTreeNode("Id: "+s.getId()));
		new_sim.add(new DefaultMutableTreeNode("Toolkit: "+s.getToolkit()));
		new_sim.add(new DefaultMutableTreeNode("Status: "+s.getState()));
		DefaultMutableTreeNode mode=new DefaultMutableTreeNode("Mode: "+(s.getLoop()?"Simulation Optimization":"Parameters Space Exploration"));
		new_sim.add(mode);
		DefaultMutableTreeNode loops=new DefaultMutableTreeNode("Loops");
		new_sim.add(loops);

		if(s.getLoop())
		{
			for(Loop l: s.getRuns().getLoops())
			{
				DefaultMutableTreeNode loop=new DefaultMutableTreeNode("Loop Id: "+l.getId());
				loop.add(new DefaultMutableTreeNode("Status: "+l.getStatus()));	
				loop.add(new DefaultMutableTreeNode("Start time: "+l.getStartTime()));
				loop.add(new DefaultMutableTreeNode("Stop time: "+l.getStopTime()));
				loop.add(new DefaultMutableTreeNode("Loop time: "+l.getLoopTime()));

				class PointTree
				{
					public Input getI() {
						return i;
					}
					public void setI(Input i) {
						this.i = i;
					}
					public Output getO() {
						return o;
					}
					public void setO(Output o) {
						this.o = o;
					}
					private Input i;
					private Output o;
				}
				HashMap<Integer, PointTree> mapio=new HashMap<Integer, PointTree>();

				if(l.getInputs()!=null)
				{
					for(Input i: l.getInputs().getinput_list())
					{
						PointTree p=new PointTree();
						p.setI(i);
						mapio.put(i.id, p);

					}

					if(l.getOutputs()!=null && l.getOutputs().getOutput_list()!=null)
						for(Output i: l.getOutputs().getOutput_list())
						{

							mapio.get(i.getIdInput()).setO(i);
						}
					else System.out.println("No output found.");

					for (Integer pt : mapio.keySet()) {

						DefaultMutableTreeNode point=new DefaultMutableTreeNode("Simulation point: "+pt);
						DefaultMutableTreeNode input=new DefaultMutableTreeNode("Input");
						DefaultMutableTreeNode output=new DefaultMutableTreeNode("Output");


						point.add(input);
						point.add(output);

						for(Parameter p : mapio.get(pt).getI().param_element)
						{
							DefaultMutableTreeNode vname=new DefaultMutableTreeNode("Variable Name: "+p.getvariable_name());
							input.add(vname);
							vname.add(new DefaultMutableTreeNode("Value: "+p.getparam()));
						}
						if(mapio.get(pt).getO()!=null)
							for(Parameter p : mapio.get(pt).getO().output_params)
							{
								DefaultMutableTreeNode vname=new DefaultMutableTreeNode("Variable Name: "+p.getvariable_name());
								output.add(vname);
								vname.add(new DefaultMutableTreeNode("Value: "+p.getparam()));
							}

						loop.add(point);
					}

				}
				loops.add(loop);
			}
		}
			
		tree1.expandRow(0);
	}

	private void initComponents() {

		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		tree1 = new JTree();
		panel2 = new JPanel();
		scrollPane2 = new JScrollPane();

		//======== this ========

	
		//======== panel1 ========
		{

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(tree1);
			}

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
			);
		}

		//======== panel2 ========
		{

			GroupLayout panel2Layout = new GroupLayout(panel2);
			panel2.setLayout(panel2Layout);
			panel2Layout.setHorizontalGroup(
				panel2Layout.createParallelGroup()
					.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
			);
			panel2Layout.setVerticalGroup(
				panel2Layout.createParallelGroup()
					.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
			);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(36, 36, 36))
		);
		
	}

	
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTree tree1;
	private JPanel panel2;
	private JScrollPane scrollPane2;
	
}
