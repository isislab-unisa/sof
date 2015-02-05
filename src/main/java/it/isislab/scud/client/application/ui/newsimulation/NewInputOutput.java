package it.isislab.scud.client.application.ui.newsimulation;

import it.isislab.scud.client.application.ui.tabwithclose.ProgressbarDialog;
import it.isislab.scud.core.model.parameters.xsd.domain.Domain;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomain;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomainContinuous;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomainDiscrete;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomainListString;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.input.Inputs;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
/*
 */
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.sun.opengl.util.FileUtil;



/**
 * @author Red red
 */
public class NewInputOutput extends JPanel {
	public NewInputOutput(NewSimulationProcess newSimulationProcess) {
		sproc=newSimulationProcess;
		initComponents();
	}
	private NewSimulationProcess sproc=null;
	private void initComponents() {
		panelInputDetails = new JPanel();
		scrollPane1 = new JScrollPane();
		rootInputNode = new DefaultMutableTreeNode("Inputs");
		treeModelInput = new DefaultTreeModel(rootInputNode);
		treeInput = new JTree(treeModelInput);
		panelNewInput = new JPanel();
		panelInnerInput = new JPanel();
		labelRounds = new JLabel();
		labelInputVarType = new JLabel();
		labelInputVarName = new JLabel();
		textFieldRounds = new JTextField();
		comboBoxInputVarTypeModel = new DefaultComboBoxModel<String>();
		comboBoxInputVarType = new JComboBox<String>(comboBoxInputVarTypeModel);
		comboBoxInputVarTypeModel.addElement(new String ("-- Select a parameter type --"));
		comboBoxInputVarTypeModel.addElement(new String ("continuous"));
		comboBoxInputVarTypeModel.addElement(new String ("discrete"));
		comboBoxInputVarTypeModel.addElement(new String ("string"));
		textFieldInputValue = new JTextField();
		labelInputValue = new JLabel();
		comboBoxInputVarNameModel = new DefaultComboBoxModel<String>();
		comboBoxInputVarName = new JComboBox<String>(comboBoxInputVarNameModel);
		
		buttonAddParam = new JButton();
		panel5 = new JPanel();
		buttonNewInput = new JButton();
		buttonEditInput = new JButton();
		buttonRemoveInput = new JButton();
		panelOutputDetails = new JPanel();
		scrollPane2 = new JScrollPane();
		rootOutputNode = new DefaultMutableTreeNode("Output");
		treeModelOutput = new DefaultTreeModel(rootOutputNode);
		treeOutput = new JTree(treeModelOutput);
		panelNewOutput = new JPanel();
		panelInnerOutput = new JPanel();
		labelOutputVarType = new JLabel();
		labelOutputVarName = new JLabel();
		comboBoxOutputVarTypeModel = new DefaultComboBoxModel<String>();
		comboBoxOutputVarType = new JComboBox<String>(comboBoxOutputVarTypeModel);
		comboBoxOutputVarTypeModel.addElement(new String (COMBOBOX_ZERO_INDEX_ELEMENT));
		comboBoxOutputVarTypeModel.addElement(new String ("continuous"));
		comboBoxOutputVarTypeModel.addElement(new String ("discrete"));
		comboBoxOutputVarTypeModel.addElement(new String ("string"));
		comboBoxOutputVarNameModel = new DefaultComboBoxModel<String>();
		comboBoxOutputVarName = new JComboBox<String>(comboBoxOutputVarNameModel);
		panel6 = new JPanel();
		buttonAddOutput = new JButton();
		buttonEditOutput = new JButton();
		buttonRemoveOutput = new JButton();
		panel7 = new JPanel();
		buttonSave = new JButton();
		buttonPrev = new JButton();
		
		panelInnerInput.setVisible(false);
		
		//======== panelInputDetails ========
		{
			panelInputDetails.setBorder(new TitledBorder("Input"));

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(treeInput);
			}

			//======== panelNewInput ========
			{
				panelNewInput.setBorder(new TitledBorder("New Input"));
				panelNewInput.setPreferredSize(new Dimension(378, 298));

				//======== panelInnerInput ========
				{

					//---- labelRounds ----
					labelRounds.setText("rounds");

					//---- labelInputVarType ----
					labelInputVarType.setText("variable type");

					//---- labelInputVarName ----
					labelInputVarName.setText("variable name");

					//---- labelInputValue ----
					labelInputValue.setText("value");
					
					buttonAddParam.setText("Add param");


					GroupLayout panelInnerInputLayout = new GroupLayout(panelInnerInput);
					panelInnerInput.setLayout(panelInnerInputLayout);
					panelInnerInputLayout.setHorizontalGroup(
						panelInnerInputLayout.createParallelGroup()
							.addGroup(panelInnerInputLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(panelInnerInputLayout.createParallelGroup()
									.addGroup(panelInnerInputLayout.createSequentialGroup()
										.addGroup(panelInnerInputLayout.createParallelGroup()
											.addComponent(labelRounds, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
											.addComponent(labelInputVarName, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
											.addComponent(labelInputVarType, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
										.addGroup(panelInnerInputLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
											.addGroup(panelInnerInputLayout.createSequentialGroup()
												.addGap(29, 29, 29)
												.addGroup(panelInnerInputLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
													.addComponent(comboBoxInputVarType, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
													.addComponent(textFieldRounds, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
											.addGroup(GroupLayout.Alignment.TRAILING, panelInnerInputLayout.createSequentialGroup()
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
												.addComponent(comboBoxInputVarName, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))))
									.addGroup(panelInnerInputLayout.createSequentialGroup()
										.addGroup(panelInnerInputLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
											.addComponent(buttonAddParam, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
											.addComponent(labelInputValue, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
										.addGap(29, 29, 29)
										.addComponent(textFieldInputValue, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(46, Short.MAX_VALUE))
					);
					panelInnerInputLayout.setVerticalGroup(
							panelInnerInputLayout.createParallelGroup()
								.addGroup(panelInnerInputLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(panelInnerInputLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(labelRounds, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
										.addComponent(textFieldRounds, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
									.addGap(28, 28, 28)
									.addGroup(panelInnerInputLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(labelInputVarType, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
										.addComponent(comboBoxInputVarType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(27, 27, 27)
									.addGroup(panelInnerInputLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(labelInputVarName, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
										.addComponent(comboBoxInputVarName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(30, 30, 30)
									.addGroup(panelInnerInputLayout.createParallelGroup()
										.addComponent(labelInputValue, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
										.addGroup(panelInnerInputLayout.createSequentialGroup()
											.addGap(3, 3, 3)
											.addComponent(textFieldInputValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
									.addComponent(buttonAddParam)
									.addContainerGap())
						);
				}

				GroupLayout panelNewInputLayout = new GroupLayout(panelNewInput);
				panelNewInput.setLayout(panelNewInputLayout);
				panelNewInputLayout.setHorizontalGroup(
					panelNewInputLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panelNewInputLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelInnerInput, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
				);
				panelNewInputLayout.setVerticalGroup(
					panelNewInputLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panelNewInputLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelInnerInput, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
				);
			}

			//======== panel5 ========
			{

				//---- buttonAddInput ----
				buttonNewInput.setText("New");

				//---- buttonEditInput ----
				buttonEditInput.setText("Edit");

				//---- buttonRemoveInput ----
				buttonRemoveInput.setText("Remove");

				GroupLayout panel5Layout = new GroupLayout(panel5);
				panel5.setLayout(panel5Layout);
				panel5Layout.setHorizontalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createSequentialGroup()
							.addComponent(buttonNewInput)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(buttonEditInput)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
							.addComponent(buttonRemoveInput))
				);
				panel5Layout.setVerticalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(buttonNewInput)
							.addComponent(buttonEditInput)
							.addComponent(buttonRemoveInput))
				);
			}

			GroupLayout panelInputDetailsLayout = new GroupLayout(panelInputDetails);
			panelInputDetails.setLayout(panelInputDetailsLayout);
			panelInputDetailsLayout.setHorizontalGroup(
				panelInputDetailsLayout.createParallelGroup()
					.addGroup(panelInputDetailsLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panelInputDetailsLayout.createParallelGroup()
							.addComponent(panel5, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
							.addComponent(panelNewInput, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
						.addContainerGap())
			);
			panelInputDetailsLayout.setVerticalGroup(
				panelInputDetailsLayout.createParallelGroup()
					.addGroup(panelInputDetailsLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panelNewInput, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(36, Short.MAX_VALUE))
			);
		}

		//======== panelOutputDetails ========
		{
			panelOutputDetails.setBorder(new TitledBorder("Output"));
			panelOutputDetails.setPreferredSize(new Dimension(424, 774));

			//======== scrollPane2 ========
			{
				scrollPane2.setViewportView(treeOutput);
			}

			//======== panelNewOutput ========
			{
				panelNewOutput.setBorder(new TitledBorder("New Output"));
				panelNewOutput.setPreferredSize(new Dimension(378, 298));

				//======== panelInnerInput2 ========
				{
					panelInnerOutput.setPreferredSize(new Dimension(356, 288));

					//---- labelOutputVarType ----
					labelOutputVarType.setText("variable type");

					//---- labelOutputVarName ----
					labelOutputVarName.setText("variable name");

					GroupLayout panelInnerInput2Layout = new GroupLayout(panelInnerOutput);
					panelInnerOutput.setLayout(panelInnerInput2Layout);
					panelInnerInput2Layout.setHorizontalGroup(
						panelInnerInput2Layout.createParallelGroup()
							.addGroup(panelInnerInput2Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(panelInnerInput2Layout.createParallelGroup()
									.addGroup(panelInnerInput2Layout.createSequentialGroup()
										.addComponent(labelOutputVarType, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
										.addGap(29, 29, 29)
										.addComponent(comboBoxOutputVarType, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
									.addGroup(panelInnerInput2Layout.createSequentialGroup()
										.addComponent(labelOutputVarName, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
										.addComponent(comboBoxOutputVarName, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap())
					);
					panelInnerInput2Layout.setVerticalGroup(
						panelInnerInput2Layout.createParallelGroup()
							.addGroup(panelInnerInput2Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(panelInnerInput2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(labelOutputVarType, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(comboBoxOutputVarType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(27, 27, 27)
								.addGroup(panelInnerInput2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(labelOutputVarName, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(comboBoxOutputVarName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(192, Short.MAX_VALUE))
					);
				}

				GroupLayout panelNewOutputLayout = new GroupLayout(panelNewOutput);
				panelNewOutput.setLayout(panelNewOutputLayout);
				panelNewOutputLayout.setHorizontalGroup(
					panelNewOutputLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panelNewOutputLayout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panelInnerOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
				);
				panelNewOutputLayout.setVerticalGroup(
					panelNewOutputLayout.createParallelGroup()
						.addGroup(panelNewOutputLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelInnerOutput, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
							.addContainerGap())
				);
			}

			//======== panel6 ========
			{

				//---- buttonAddOutput ----
				buttonAddOutput.setText("Add");

				//---- buttonEditOutput ----
				buttonEditOutput.setText("Edit");

				//---- buttonRemoveOutput ----
				buttonRemoveOutput.setText("Remove");

				GroupLayout panel6Layout = new GroupLayout(panel6);
				panel6.setLayout(panel6Layout);
				panel6Layout.setHorizontalGroup(
					panel6Layout.createParallelGroup()
						.addGroup(panel6Layout.createSequentialGroup()
							.addComponent(buttonAddOutput)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(buttonEditOutput)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(buttonRemoveOutput))
				);
				panel6Layout.setVerticalGroup(
					panel6Layout.createParallelGroup()
						.addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(buttonAddOutput)
							.addComponent(buttonEditOutput)
							.addComponent(buttonRemoveOutput))
				);
			}

			GroupLayout panelOutputDetailsLayout = new GroupLayout(panelOutputDetails);
			panelOutputDetails.setLayout(panelOutputDetailsLayout);
			panelOutputDetailsLayout.setHorizontalGroup(
				panelOutputDetailsLayout.createParallelGroup()
					.addGroup(panelOutputDetailsLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panelOutputDetailsLayout.createParallelGroup()
							.addComponent(panel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrollPane2)
							.addComponent(panelNewOutput, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
						.addContainerGap())
			);
			panelOutputDetailsLayout.setVerticalGroup(
				panelOutputDetailsLayout.createParallelGroup()
					.addGroup(panelOutputDetailsLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panelNewOutput, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
		}

		//======== panel7 ========
		{

			//---- button9 ----
			buttonSave.setText("Save");

			//---- buttonPrev ----
			buttonPrev.setText("Prev");

			GroupLayout panel7Layout = new GroupLayout(panel7);
			panel7.setLayout(panel7Layout);
			panel7Layout.setHorizontalGroup(
				panel7Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel7Layout.createSequentialGroup()
						.addGap(0, 736, Short.MAX_VALUE)
						.addComponent(buttonPrev)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(buttonSave))
			);
			panel7Layout.setVerticalGroup(
				panel7Layout.createParallelGroup()
					.addGroup(panel7Layout.createSequentialGroup()
						.addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(buttonSave)
							.addComponent(buttonPrev))
						.addGap(0, 8, Short.MAX_VALUE))
			);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
						.addComponent(panel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
							.addComponent(panelInputDetails, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(panelOutputDetails, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(58, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(33, 33, 33)
					.addGroup(layout.createParallelGroup()
						.addComponent(panelInputDetails, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelOutputDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(panel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		buttonNewInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setInputPanel();
				selectedInputVarName.clear();
				inputNode = new DefaultMutableTreeNode("Input");
				treeModelInput.insertNodeInto(inputNode, rootInputNode, rootInputNode.getChildCount());
				
			}
		});
		
		buttonAddParam.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode roundsNode = new DefaultMutableTreeNode("rounds: "+textFieldRounds.getText());
				if(inputNode.getChildCount()==0)
					treeModelInput.insertNodeInto(roundsNode, inputNode, inputNode.getChildCount());
				
				DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode("param");
				treeModelInput.insertNodeInto(elementNode, inputNode, inputNode.getChildCount());
				
				selectedInputVarName.add(comboBoxInputVarName.getSelectedItem().toString());
				
				treeModelInput.insertNodeInto(new DefaultMutableTreeNode("variableName: "+comboBoxInputVarName.getSelectedItem()), elementNode, elementNode.getChildCount());
				DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(comboBoxInputVarType.getSelectedItem());
				treeModelInput.insertNodeInto(typeNode, elementNode, elementNode.getChildCount());
				treeModelInput.insertNodeInto(new DefaultMutableTreeNode("value: "+textFieldInputValue.getText()), typeNode, typeNode.getChildCount());
				treeInput.expandPath(new TreePath(elementNode.getPath()));
				treeInput.expandPath(new TreePath(typeNode.getPath()));
				resetNewInputPanel();
			}
		});
		
		comboBoxInputVarType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxInputVarNameModel.removeAllElements();
				fillInputVarNamePanel(comboBoxInputVarType.getSelectedItem().toString());
				
			}
		});
		
		comboBoxOutputVarType.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxOutputVarNameModel.removeAllElements();
				fillOutputVarNamePanel(comboBoxOutputVarType.getSelectedItem().toString());
				
			}
		});
		
		buttonAddOutput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode("param");
				treeModelOutput.insertNodeInto(elementNode, rootOutputNode, rootOutputNode.getChildCount());*/
				DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(comboBoxOutputVarType.getSelectedItem());
				treeModelOutput.insertNodeInto(typeNode, rootOutputNode, rootOutputNode.getChildCount());
				
				selectedOutputVarName.add(comboBoxOutputVarName.getSelectedItem().toString());
				
				treeModelOutput.insertNodeInto(new DefaultMutableTreeNode("variableName: "+comboBoxOutputVarName.getSelectedItem()), typeNode, typeNode.getChildCount());
				
				treeOutput.expandPath(new TreePath(typeNode.getPath()));
				resetNewOutputPanel();
			}
		});
		
		buttonPrev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sproc.setDomainView();
			}
		});
		
		buttonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputs = new Inputs();
				output = new Output();
				inputs.setsimulation(sproc.getSim());
				final ProgressbarDialog pd=new ProgressbarDialog(sproc.mainFrame);

				pd.setVisible(true);
				final JProgressBar bar=pd.getProgressBar1();
				pd.setTitleMessage("Saving simulation");
				pd.setNoteMessage("Please wait.");
				bar.setIndeterminate(true);
				class MyTaskConnect extends Thread {

					public void run(){
						loadInput();
						loadOutput();
						String tmpDir = generateXMLfiles();
						pd.setTitleMessage("Loading simulation on HDFS");
						pd.setNoteMessage("Please wait.");
						saveSimulationOnHdfs(tmpDir);
						removeTMPFile(tmpDir);
						bar.setIndeterminate(false);
						pd.setVisible(false);
					}
				}
				(new MyTaskConnect()).start();
			}
		});
	}
	
	private void loadInput(){
		ArrayList<Input> listInput = new ArrayList<Input>();
		DefaultMutableTreeNode inputNode;
		for(int index=0;index<rootInputNode.getChildCount(); index++){
			inputNode =(DefaultMutableTreeNode) rootInputNode.getChildAt(index);
			if(inputNode.toString().equalsIgnoreCase("input")){
				listInput.add(getInput(listInput.size()+1,inputNode));
			}
		}
		inputs.setinput_list(listInput);
        
	}
	
	private void loadOutput(){
		output = new Output();
		ArrayList<Parameter> list_out = new ArrayList<Parameter>();
		DefaultMutableTreeNode child;
		Parameter p;
		for(int index=0; index<rootOutputNode.getChildCount(); index++){
			child = (DefaultMutableTreeNode)rootOutputNode.getChildAt(index);
			p = new Parameter();
			if(child.toString().equalsIgnoreCase("continuous")){
				ParameterDouble pd = new ParameterDouble();
				p.setvariable_name(child.getFirstChild().toString().split(":")[1].trim());
				p.setparam(pd);
			}else{
				if(child.toString().equalsIgnoreCase("discrete")){
					ParameterLong pl = new ParameterLong();
					p.setvariable_name(child.getFirstChild().toString().split(":")[1].trim());
					p.setparam(pl);
				}else{
					if(child.toString().equalsIgnoreCase("string")){
						ParameterString ps = new ParameterString();
						p.setvariable_name(child.getFirstChild().toString().split(":")[1].trim());
						p.setparam(ps);
					}
				}
			}
			list_out.add(p);
		}
		output.output_params = list_out;
		
		/*JAXBContext context;
		try {
			context = JAXBContext.newInstance(Output.class);
			Marshaller jaxbMarshaller = context.createMarshaller();
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        jaxbMarshaller.marshal(output, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	private String generateXMLfiles(){
		File f = FileUtils.getTempDirectory();
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		JAXBContext context;
		Marshaller jaxbMarshaller;
		
		File tmpFile = new File(f.getAbsolutePath()+File.separator+"domain.xml");
		try {
			context = JAXBContext.newInstance(Domain.class);
			jaxbMarshaller = context.createMarshaller();
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        jaxbMarshaller.marshal(sproc.getDomain(), tmpFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tmpFile = new File(f.getAbsolutePath()+File.separator+"input.xml");
		try {
			context = JAXBContext.newInstance(Inputs.class);
			jaxbMarshaller = context.createMarshaller();
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        jaxbMarshaller.marshal(inputs, tmpFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tmpFile = new File(f.getAbsolutePath()+File.separator+"output.xml");
		try {
			context = JAXBContext.newInstance(Output.class);
			jaxbMarshaller = context.createMarshaller();
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        jaxbMarshaller.marshal(output, tmpFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return f.getAbsolutePath();
	}
	
	private String saveSimulationOnHdfs(String tmpDir){
		
		if(inputs.getsimulation().getLoop()){
			sproc.mainFrame.getController().createsimulationloop(
					inputs.getsimulation().getToolkit(),/*MODEL TYPE MASON - NETLOGO -GENERIC*/   
					inputs.getsimulation().getName(),/*SIM NAME*/                               
					tmpDir+File.separator+"domain.xml",/*domain_pathname*/                        
					sproc.newsimpan.selcommand,/*bashCommandForRunnableFunctionSelection */
					sproc.newsimpan.evalcomma,/*bashCommandForRunnableFunctionEvaluate*/ 
					tmpDir+File.separator+"output.xml",/*output_description_filename*/            
					sproc.newsimpan.selpath,/*executable_selection_function_filename*/ 
					sproc.newsimpan.evalpath,/*executable_rating_function_filename*/    
					inputs.getsimulation().getDescription(),/*description_simulation*/
					sproc.newsimpan.simdirpath,
					(inputs.getsimulation().getToolkit().equalsIgnoreCase("generic"))?sproc.newsimpan.simcommand:""	);
		}else{
			sproc.mainFrame.getController().createsimulation(
					inputs.getsimulation().getToolkit(),//MODEL TYPE MASON - NETLOGO -GENERIC 
					inputs.getsimulation().getName(),//SIM NAME                            
					tmpDir+File.separator+"input.xml",//INPUT.XML PATH                      
					tmpDir+File.separator+"output.xml",//OUTPUT.XML PATH                     
					inputs.getsimulation().getDescription(),//DESCRIPTION SIM                     
					sproc.newsimpan.simdirpath, //SIMULATION EXEC PATH 
					(inputs.getsimulation().getToolkit().equalsIgnoreCase("generic"))?sproc.newsimpan.simcommand:"");
		}
		
		return tmpDir;
	}
	
	private void removeTMPFile(String tmpDir){
		File f = new File(tmpDir);
		if(!f.exists())
			return;
		for(File c : f.listFiles()){
			c.delete();
		}
		f.delete();
	}
	
	private Input getInput(int nexId,DefaultMutableTreeNode inputNode){
		Input i = new Input();
		i.id = nexId;
		DefaultMutableTreeNode child;
		DefaultMutableTreeNode varType;
		Parameter p;
		ArrayList<Parameter> param_list = new ArrayList<Parameter>();
		for(int index=0; index<inputNode.getChildCount(); index++){
				child =(DefaultMutableTreeNode) inputNode.getChildAt(index);
				if(child.getChildCount() > 0){ // param treeNode
					p = new Parameter();
					for(int j=0; j<child.getChildCount(); j++){
						varType =(DefaultMutableTreeNode) child.getChildAt(j);
					
						if(varType.getChildCount()==0)
							p.setvariable_name(varType.toString().split(":")[1].trim());
						else{
							String varValue = varType.getFirstChild().toString();
							if(varType.toString().equalsIgnoreCase("continuous")){
								ParameterDouble pd = new ParameterDouble();
								pd.setvalue(Double.parseDouble(varType.getFirstChild().toString().split(":")[1].trim()));
								p.setparam(pd);
							}else{
								if(varType.toString().equalsIgnoreCase("discrete")){
									ParameterLong pl = new ParameterLong();
									pl.setvalue(Long.parseLong(varType.getFirstChild().toString().split(":")[1].trim()));
									p.setparam(pl);
								}else{
									if(varType.toString().equalsIgnoreCase("string")){
										ParameterString ps = new ParameterString();
										ps.setvalue(varType.getFirstChild().toString().split(":")[1].trim());
										p.setparam(ps);
									}
								}
							}
							param_list.add(p);
						}
					}
				}else{
					i.rounds = Integer.parseInt(child.toString().split(":")[1].trim());
				}
		}
		i.param_element = param_list;
		return i;
	}
	
	private void resetNewInputPanel(){
		comboBoxInputVarTypeModel.setSelectedItem(COMBOBOX_ZERO_INDEX_ELEMENT);
		comboBoxInputVarNameModel.removeAllElements();
		textFieldInputValue.setText("");
	}
	
	private void resetNewOutputPanel(){
		comboBoxOutputVarTypeModel.setSelectedItem(COMBOBOX_ZERO_INDEX_ELEMENT);
		comboBoxOutputVarNameModel.removeAllElements();
	}
	
	private void fillInputVarNamePanel(String varType){
		if(varType.equalsIgnoreCase(COMBOBOX_ZERO_INDEX_ELEMENT))
			return;
		Domain d = sproc.getDomain();
		for(ParameterDomain pd : d.param){
			if(selectedInputVarName.contains(pd.getvariable_name()))
				continue;
			Object paramObj = pd.getparameter();
			if (paramObj instanceof ParameterDomainContinuous){
				ParameterDomainContinuous pdc = (ParameterDomainContinuous)paramObj;
				if(varType.equalsIgnoreCase("continuous"))
					comboBoxInputVarNameModel.addElement(pd.getvariable_name());
			}else{
				if (paramObj instanceof ParameterDomainDiscrete){
					ParameterDomainDiscrete pdd = (ParameterDomainDiscrete)paramObj;
					if(varType.equalsIgnoreCase("discrete"))
						comboBoxInputVarNameModel.addElement(pd.getvariable_name());
				}else{
					if (paramObj instanceof ParameterDomainListString){
						ParameterDomainListString pdls = (ParameterDomainListString)paramObj;
						if(varType.equalsIgnoreCase("string"))
							comboBoxInputVarNameModel.addElement(pd.getvariable_name());
					}
				}
			}
		}
	}
	private void fillOutputVarNamePanel(String varType){
		if(varType.equalsIgnoreCase(COMBOBOX_ZERO_INDEX_ELEMENT))
			return;
		Domain d = sproc.getDomain();
		for(ParameterDomain pd : d.param){
			if(selectedOutputVarName.contains(pd.getvariable_name()))
				continue;
			Object paramObj = pd.getparameter();
			if (paramObj instanceof ParameterDomainContinuous){
				ParameterDomainContinuous pdc = (ParameterDomainContinuous)paramObj;
				if(varType.equalsIgnoreCase("continuous"))
					comboBoxOutputVarNameModel.addElement(pd.getvariable_name());
			}else{
				if (paramObj instanceof ParameterDomainDiscrete){
					ParameterDomainDiscrete pdd = (ParameterDomainDiscrete)paramObj;
					if(varType.equalsIgnoreCase("discrete"))
						comboBoxOutputVarNameModel.addElement(pd.getvariable_name());
				}else{
					if (paramObj instanceof ParameterDomainListString){
						ParameterDomainListString pdls = (ParameterDomainListString)paramObj;
						if(varType.equalsIgnoreCase("string"))
							comboBoxOutputVarNameModel.addElement(pd.getvariable_name());
					}
				}
			}
		}
	}
	
	private void setInputPanel(){
		panelInnerInput.setVisible(true);
		comboBoxInputVarType.setSelectedIndex(0);
		comboBoxInputVarNameModel.removeAllElements();
		textFieldInputValue.setText("");
		textFieldRounds.setText("");
		
	}

	
	public void setTreeNodes(){
		if(rootInputNode.getChildCount()!=0 && rootInputNode.getFirstChild().toString().equalsIgnoreCase("Simulation"))
			return;
		DefaultMutableTreeNode simNode = new DefaultMutableTreeNode("Simulation");
		treeModelInput.insertNodeInto(simNode, rootInputNode, rootInputNode.getChildCount());
		treeModelInput.insertNodeInto(new DefaultMutableTreeNode("author: "+sproc.getSim().getAuthor()), simNode, simNode.getChildCount());
		treeModelInput.insertNodeInto(new DefaultMutableTreeNode("name: "+sproc.getSim().getName()), simNode, simNode.getChildCount());
		treeModelInput.insertNodeInto(new DefaultMutableTreeNode("toolkit: "+sproc.getSim().getToolkit()), simNode, simNode.getChildCount());
		treeModelInput.insertNodeInto(new DefaultMutableTreeNode("description: "+sproc.getSim().getDescription()), simNode, simNode.getChildCount());
		treeModelInput.insertNodeInto(new DefaultMutableTreeNode("mode: "+(sproc.getSim().getLoop()?"SO":"PSE")), simNode, simNode.getChildCount());
		treeInput.expandPath(new TreePath(simNode.getPath()));
	}
	

	private static String COMBOBOX_ZERO_INDEX_ELEMENT = "-- Select a parameter type --";
	private ArrayList<String> selectedInputVarName = new ArrayList<String>();
	private ArrayList<String> selectedOutputVarName = new ArrayList<String>();
	private DefaultMutableTreeNode rootInputNode;
	private DefaultMutableTreeNode rootOutputNode;
	private DefaultMutableTreeNode inputNode;
	private DefaultTreeModel treeModelInput;
	private DefaultTreeModel treeModelOutput;
	private Inputs inputs;
	private Output output;
	private JPanel panelInputDetails;
	private JScrollPane scrollPane1;
	private JTree treeInput;
	private JPanel panelNewInput;
	private JPanel panelInnerInput;
	private JLabel labelRounds;
	private JLabel labelInputVarType;
	private JLabel labelInputVarName;
	private JTextField textFieldRounds;
	private DefaultComboBoxModel<String> comboBoxInputVarTypeModel;
	private JComboBox<String> comboBoxInputVarType;
	private JTextField textFieldInputValue;
	private JLabel labelInputValue;
	private DefaultComboBoxModel<String> comboBoxInputVarNameModel;
	private JComboBox<String> comboBoxInputVarName;
	private JButton buttonAddParam;
	private JPanel panel5;
	private JButton buttonNewInput;
	private JButton buttonEditInput;
	private JButton buttonRemoveInput;
	private JPanel panelOutputDetails;
	private JScrollPane scrollPane2;
	private JTree treeOutput;
	private JPanel panelNewOutput;
	private JPanel panelInnerOutput;
	private JLabel labelOutputVarType;
	private JLabel labelOutputVarName;
	private DefaultComboBoxModel<String> comboBoxOutputVarTypeModel;
	private JComboBox<String> comboBoxOutputVarType;
	private DefaultComboBoxModel<String> comboBoxOutputVarNameModel;
	private JComboBox<String> comboBoxOutputVarName;
	private JPanel panel6;
	private JButton buttonAddOutput;
	private JButton buttonEditOutput;
	private JButton buttonRemoveOutput;
	private JPanel panel7;
	private JButton buttonSave;
	private JButton buttonPrev;
}
