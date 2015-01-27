package it.isislab.scud.client.application.ui;

import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Loop;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.sun.tools.javac.code.Attribute.Array;


/**
 * @author Carmine Spagnuolo
 */
public class XMLPanel extends JPanel {
	Simulation s;
	ArrayList<Loop> loops;
	public XMLPanel(Simulation simulation) {
		this.s=simulation;
		this.loops=s.getRuns().getLoops();
		Collections.sort(loops,new Comparator<Loop>() {

			@Override
			public int compare(Loop o1, Loop o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});
		initComponents();
		initTree();
		initChart(0);
	}
	private void doMouseClicked(MouseEvent me)
	{
	

			DefaultMutableTreeNode selected =(DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
			if(selected!=null && me.getClickCount() == 2)
			{
				System.out.println(selected.toString());
				if(selected.toString().contains("Loop Id"))
				{
					initChart(Integer.parseInt(selected.toString().split(": ")[1])-1);
					
				}
			}
		

	}
	private void initChart(int id) {
		DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
		Loop l=loops.get(id);
		
			if(l.getInputs()!=null)
			{

				if(l.getOutputs()!=null)
				{
					List<Output> outputs=l.getOutputs().getOutput_list();
					
					Collections.sort(outputs,new Comparator<Output>() {

						@Override
						public int compare(Output o1, Output o2) {
							return Integer.compare(o1.getIdInput(), o2.getIdInput());
						}
					});
					for(Output i: outputs)
					{
						
						for (Parameter p : i.output_params) {
							if((p.getparam() instanceof ParameterDouble))
								line_chart_dataset.addValue(((ParameterDouble)p.getparam()).getvalue(), "Output: "+i.getIdInput()+ " Loop: "+l.getId(), p.getvariable_name());
							else
								if( (p.getparam() instanceof ParameterLong))
									line_chart_dataset.addValue(((ParameterLong)p.getparam()).getvalue(), "Output: "+i.getIdInput()+ " Loop: "+l.getId(), p.getvariable_name());
						}
						
					}
				}
			
			
		}

	
		JFreeChart lineChartObject=ChartFactory.createLineChart("Simulation "+s.getName()+" Output Loop: "+(id+1),"Variables","Values",line_chart_dataset,PlotOrientation.VERTICAL,true,true,false);                

		ChartPanel chartPanel = new ChartPanel( lineChartObject );
//		chartPanel.setSize( new java.awt.Dimension( ((int)panel2.getPreferredSize().width/2), ((int)panel2.getPreferredSize().height)) );
		chartPanel.setSize( new java.awt.Dimension( 800, 600 ));
		JPanel p=new JPanel();
		p.add(chartPanel);
		p.setSize( new java.awt.Dimension( 800, 600 ));
		(scrollPane2.getViewport()).removeAll();
		(scrollPane2.getViewport()).add(p);        


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
		
		tree1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});
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
