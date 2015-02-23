package it.isislab.sof.client.application.ui.newsimulation;

import it.isislab.sof.core.model.parameters.xsd.domain.Domain;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomain;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainContinuous;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainDiscrete;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainListString;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class NewDomain extends JPanel {
	public NewDomain(NewSimulationProcess newSimulationProcess) {
		sproc=newSimulationProcess;
		initComponents();
	}
	private NewSimulationProcess sproc=null;

	private void initComponents() {
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		rootTreeNode = new DefaultMutableTreeNode("Domain");
		treeModel = new DefaultTreeModel(rootTreeNode);
		tree = new JTree(treeModel);
		tree.setExpandsSelectedPaths(true);
		panel2 = new JPanel();
		buttonAdd = new JButton();
		buttonEdit = new JButton();
		buttonRemove = new JButton();
		panel3 = new JPanel();
		labelType = new JLabel();
		comboBoxType = new JComboBox<String>();
		comboBoxType.addItem(new String ("-- Select a parameter type --"));
		comboBoxType.addItem(new String ("continuous"));
		comboBoxType.addItem(new String ("discrete"));
		comboBoxType.addItem(new String ("string"));
		labelVarName = new JLabel();
		textFieldVarName = new JTextField();
		panelDetails = new JPanel();
		panelInnerDetailsNumeric = new NewDomainParameterNumeric();
		panelInnerDetailsListValues = new NewDomainListValues();
		panel5 = new JPanel();
		buttonNext = new JButton();
		buttonPrev = new JButton();

		//======== panel1 ========
		{
			panel1.setBorder(new TitledBorder("Domain"));

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(tree);
			}

			//======== panel2 ========
			{

				//---- buttonAdd ----
				buttonAdd.setText("Add");

				//---- buttonEdit ----
				buttonEdit.setText("Edit");
							
				
				

				//---- buttonRemove ----
				buttonRemove.setText("Remove");

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addComponent(buttonAdd)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(buttonEdit)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(buttonRemove))
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(buttonAdd)
							.addComponent(buttonEdit)
							.addComponent(buttonRemove))
				);
			}

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(panel1Layout.createSequentialGroup()
								.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap())
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
		}

		{
			panel3.setBorder(new TitledBorder("New parameter domain"));

			//---- label1 ----
			labelType.setText("Type:");

			//---- label2 ----
			labelVarName.setText("Variable name:");

			//======== panelDetails ========
			{
				panelDetails.setBorder(new TitledBorder("Details"));

				
				GroupLayout panelDetailsLayout = new GroupLayout(panelDetails);
				panelDetails.setLayout(panelDetailsLayout);
				panelDetailsLayout.setHorizontalGroup(
						panelDetailsLayout.createParallelGroup()
						.addGap(0, 342, Short.MAX_VALUE)
				);
				panelDetailsLayout.setVerticalGroup(
					panelDetailsLayout.createParallelGroup()
						.addGap(0, 491, Short.MAX_VALUE)
				);
				/*GroupLayout panelDetailsLayout = new GroupLayout(panelDetails);
				panelDetails.setLayout(panelDetailsLayout);
				panelDetailsLayout.setHorizontalGroup(
					panelDetailsLayout.createParallelGroup()
						.addGroup(panelDetailsLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelInnerDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
				);
				panelDetailsLayout.setVerticalGroup(
					panelDetailsLayout.createParallelGroup()
						.addGroup(panelDetailsLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelInnerDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
				);*/
			}

			GroupLayout panel3Layout = new GroupLayout(panel3);
			panel3.setLayout(panel3Layout);
			panel3Layout.setHorizontalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel3Layout.createParallelGroup()
							.addComponent(panelDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(panel3Layout.createSequentialGroup()
								.addGroup(panel3Layout.createParallelGroup()
									.addComponent(labelType)
									.addComponent(labelVarName))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
								.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(textFieldVarName)
									.addComponent(comboBoxType, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))))
						.addGap(25, 25, 25))
			);
			panel3Layout.setVerticalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(labelType)
							.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(labelVarName)
							.addComponent(textFieldVarName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(panelDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
			);
		}

		//======== panel5 ========
		{

			//---- button3 ----
			buttonNext.setText("Next");

			//---- buttonPrev ----
			buttonPrev.setText("Prev");

			GroupLayout panel5Layout = new GroupLayout(panel5);
			panel5.setLayout(panel5Layout);
			panel5Layout.setHorizontalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
						.addGap(0, 679, Short.MAX_VALUE)
						.addComponent(buttonPrev)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(buttonNext))
			);
			panel5Layout.setVerticalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(buttonNext)
							.addComponent(buttonPrev)))
			);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
						.addComponent(panel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
							.addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18, 18, 18)
							.addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
						.addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(panel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		
		comboBoxType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(((String)comboBoxType.getSelectedItem()).equalsIgnoreCase("discrete") ||
						((String)comboBoxType.getSelectedItem()).equalsIgnoreCase("continuous")){

					panelDetails.removeAll();
					
					GroupLayout panelDetailsLayout = new GroupLayout(panelDetails);
					panelDetails.setLayout(panelDetailsLayout);
					panelDetailsLayout.setHorizontalGroup(
						panelDetailsLayout.createParallelGroup()
							.addGroup(panelDetailsLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelInnerDetailsNumeric, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
					);
					panelDetailsLayout.setVerticalGroup(
						panelDetailsLayout.createParallelGroup()
							.addGroup(panelDetailsLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelInnerDetailsNumeric, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
					);
					
				}else{
					if(((String)comboBoxType.getSelectedItem()).equalsIgnoreCase("string")){
						panelDetails.removeAll();
						//aggiungere pannello per stringhe
						GroupLayout panelDetailsLayout = new GroupLayout(panelDetails);
						panelDetails.setLayout(panelDetailsLayout);
						panelDetailsLayout.setHorizontalGroup(
								panelDetailsLayout.createParallelGroup()
								.addGroup(panelDetailsLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(panelInnerDetailsListValues, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addContainerGap())
								);
						panelDetailsLayout.setVerticalGroup(
								panelDetailsLayout.createParallelGroup()
								.addGroup(panelDetailsLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(panelInnerDetailsListValues, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addContainerGap())
								);
					}else{
						panelDetails.removeAll();
						GroupLayout panelDetailsLayout = new GroupLayout(panelDetails);
						panelDetails.setLayout(panelDetailsLayout);
						panelDetailsLayout.setHorizontalGroup(
								panelDetailsLayout.createParallelGroup()
								.addGap(0, 342, Short.MAX_VALUE)
						);
						panelDetailsLayout.setVerticalGroup(
							panelDetailsLayout.createParallelGroup()
								.addGap(0, 491, Short.MAX_VALUE)
						);
					}
				}
			}
		});
		

		
		//ACTION LISTENER
		
		buttonEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(buttonEdit, "Not implemented yet");
				
			}
		});
		
		
		//ACTIONLISTENER
		
		
		buttonPrev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(buttonPrev, "Not implemented yet");
				sproc.setNewSim();
				
			}
		});
		
		
		
		buttonAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				paramTreeNode = new DefaultMutableTreeNode("param");
				treeModel.insertNodeInto(paramTreeNode, rootTreeNode, rootTreeNode.getChildCount());
				treeModel.insertNodeInto(new DefaultMutableTreeNode("variableName: "+textFieldVarName.getText()), paramTreeNode, paramTreeNode.getChildCount());
				DefaultMutableTreeNode varType;
				if(((String)comboBoxType.getSelectedItem()).equalsIgnoreCase("string")){
					varType = new DefaultMutableTreeNode("list");
					treeModel.insertNodeInto(varType, paramTreeNode, paramTreeNode.getChildCount());
					for(String v : panelInnerDetailsListValues.getListValues())
						treeModel.insertNodeInto(new DefaultMutableTreeNode("value: "+v), varType, varType.getChildCount());
					
				}else{
					varType = new DefaultMutableTreeNode(comboBoxType.getSelectedItem());
					treeModel.insertNodeInto(varType, paramTreeNode, paramTreeNode.getChildCount());
					treeModel.insertNodeInto(new DefaultMutableTreeNode("min: "+panelInnerDetailsNumeric.getMin()), varType, varType.getChildCount());
					treeModel.insertNodeInto(new DefaultMutableTreeNode("max: "+panelInnerDetailsNumeric.getMax()), varType, varType.getChildCount());
					treeModel.insertNodeInto(new DefaultMutableTreeNode("increment: "+panelInnerDetailsNumeric.getIncrement()), varType, varType.getChildCount());
				}
				tree.expandPath(new TreePath(paramTreeNode.getPath()));
				
				panelInnerDetailsNumeric.cleanField();
				panelInnerDetailsListValues.cleanField();
				comboBoxType.setSelectedIndex(0);
				textFieldVarName.setText("");
				
			}
		});
		
		buttonRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selected =(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(selected !=null && selected.toString().equalsIgnoreCase("param")){
					treeModel.removeNodeFromParent(selected);
					
				}else{
					JOptionPane.showMessageDialog(tree, "Selection error.\nSelect a param object to delete it.");
				}
				
			}
		});
		
		buttonNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				buttonNextActionPerformed(e);
			}
		});
		
	}
	
	public void setTreeNodes(){
		if(rootTreeNode.getChildCount()!=0 && rootTreeNode.getFirstChild().toString().equalsIgnoreCase("Simulation"))
			return;
		DefaultMutableTreeNode simNode = new DefaultMutableTreeNode("Simulation");
		treeModel.insertNodeInto(simNode, rootTreeNode, rootTreeNode.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("author: "+sproc.getSim().getAuthor()), simNode, simNode.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("name: "+sproc.getSim().getName()), simNode, simNode.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("toolkit: "+sproc.getSim().getToolkit()), simNode, simNode.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("description: "+sproc.getSim().getDescription()), simNode, simNode.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("mode: "+(sproc.getSim().getLoop()?"SO":"PSE")), simNode, simNode.getChildCount());
		tree.expandPath(new TreePath(simNode.getPath()));
	}
	
	protected void buttonNextActionPerformed(ActionEvent e) {

		
		dom = new Domain();
		dom.simulation = sproc.getSim();
		List<ParameterDomain> list = new ArrayList<>();
		Enumeration<DefaultMutableTreeNode> en = rootTreeNode.children();
		while(en.hasMoreElements()){
			DefaultMutableTreeNode node = en.nextElement();
			if(!node.toString().equalsIgnoreCase("simulation") ||
				!node.toString().equalsIgnoreCase("domain") ){
				if(node.toString().equalsIgnoreCase("param")){
					Enumeration<DefaultMutableTreeNode> paramNodeTree = node.children();
					ParameterDomain pd = new ParameterDomain();
					while(paramNodeTree.hasMoreElements()){
						DefaultMutableTreeNode paramChild = paramNodeTree.nextElement();
						if(paramChild.toString().contains("variableName")){
							pd.setvariable_name(paramChild.toString().split(":")[1].trim());
						}else{
							if(paramChild.toString().equalsIgnoreCase("continuous")){
								ParameterDomainContinuous pdc = new ParameterDomainContinuous();
								Enumeration<DefaultMutableTreeNode> continuousNodeTree = paramChild.children();
								while(continuousNodeTree.hasMoreElements()){
									DefaultMutableTreeNode child = continuousNodeTree.nextElement();
									if(child.toString().contains("min"))
										pdc.setmin(Double.parseDouble(child.toString().split(":")[1].trim()));
									else
										if(child.toString().contains("max"))
											pdc.setmax(Double.parseDouble(child.toString().split(":")[1].trim()));
										else
											pdc.setincrement(Double.parseDouble(child.toString().split(":")[1].trim()));
								}										
								
								pd.setparameter(pdc);
								
							}else{
								if(paramChild.toString().equalsIgnoreCase("list")){
									ParameterDomainListString pdls = new ParameterDomainListString();
									Enumeration<DefaultMutableTreeNode> stringNodeTree = paramChild.children();
									ArrayList<String> list_string = new ArrayList<String>();
									while(stringNodeTree.hasMoreElements()){
										DefaultMutableTreeNode child = stringNodeTree.nextElement();
										list_string.add(child.toString().split(":")[1].trim());
										
									}										
									pdls.setlist(list_string);
									pd.setparameter(pdls);
									
								}else{
									if(paramChild.toString().equalsIgnoreCase("discrete")){
										ParameterDomainDiscrete pdd = new ParameterDomainDiscrete();
										Enumeration<DefaultMutableTreeNode> discreteNodeTree = paramChild.children();
										while(discreteNodeTree.hasMoreElements()){
											DefaultMutableTreeNode child = discreteNodeTree.nextElement();
											if(child.toString().contains("min"))
												pdd.setmin(Long.parseLong(child.toString().split(":")[1].trim()));
											else
												if(child.toString().contains("max"))
													pdd.setmax(Long.parseLong(child.toString().split(":")[1].trim()));
												else
													pdd.setincrement(Long.parseLong(child.toString().split(":")[1].trim()));
										}										
										
										pd.setparameter(pdd);
									}
								}
							}
						}
					}
					list.add(pd);
				}
			}
			
		}
		dom.param = list;
		sproc.setDomainIO();
		
	}
	
	public Domain getDomain(){
		return dom;
	}


	private Domain dom;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTree tree;
	private DefaultMutableTreeNode rootTreeNode;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode paramTreeNode;
	private JPanel panel2;
	private JButton buttonAdd;
	private JButton buttonEdit;
	private JButton buttonRemove;
	private JPanel panel3;
	private JLabel labelType;
	private JComboBox<String> comboBoxType;
	private JLabel labelVarName;
	private JTextField textFieldVarName;
	private JPanel panelDetails;
	private NewDomainParameterNumeric panelInnerDetailsNumeric;
	private NewDomainListValues panelInnerDetailsListValues; 
	private JPanel panel5;
	private JButton buttonNext;
	private JButton buttonPrev;
}
