package it.isislab.sof.client.application.ui.newsimulation;


import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;


public class NewSimulationPanel extends JPanel {
	private String sim_name="";
	public NewSimulationPanel(String sim_name, NewSimulationProcess newSimulationProcess) {
		this.sim_name=sim_name;
		this.sproc=newSimulationProcess;
		initComponents();
		initMyComponents();
	}
	private NewSimulationProcess sproc=null;

	private void initComponents() {
		panel1 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		textFieldAuthor = new JTextField();
		comboBoxToolkit = new JComboBox<>();
		comboBoxMode = new JComboBox<>();
		label4 = new JLabel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		panel3 = new JPanel();
		labelModelFile = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		textFieldSimCommand = new JTextField();
		textFieldSelectionCommand = new JTextField();
		textFieldEvaluateCommand = new JTextField();
		textFieldSelectionFunctionFIle = new JTextField();
		textFieldModelFile = new JTextField();
		textFieldEvaluateFunctionFile = new JTextField();
		panel4 = new JPanel();
		buttonNext = new JButton();

		//======== this ========

		//======== panel1 ========
		{
			panel1.setBorder(new TitledBorder("Simulation details"));

			//---- label1 ----
			label1.setText("Author:");

			//---- label2 ----
			label2.setText("Simulation toolkit:");

			//---- label3 ----
			label3.setText("Mode:");

			//---- comboBoxToolkit ----
			comboBoxToolkit.setModel(new DefaultComboBoxModel<>(new String[] {
				"MASON",
				"NetLogo",
				"Generic"
			}));

			//---- comboBoxMode ----
			comboBoxMode.setModel(new DefaultComboBoxModel<>(new String[] {
				"Parameters Space Exploration (PSE)",
				"SImulation Optimization (SO)"
			}));

			//---- label4 ----
			label4.setText("Description:");

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(textArea1);
			}

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
							.addGroup(panel1Layout.createSequentialGroup()
								.addGroup(panel1Layout.createParallelGroup()
									.addComponent(label2)
									.addComponent(label3))
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addGroup(panel1Layout.createSequentialGroup()
										.addGap(7, 7, 7)
										.addGroup(panel1Layout.createParallelGroup()
											.addComponent(comboBoxMode, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
											.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)))
									.addGroup(panel1Layout.createSequentialGroup()
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
											.addComponent(comboBoxToolkit, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
											.addComponent(textFieldAuthor, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)))))
							.addComponent(label4)
							.addComponent(label1))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label1)
							.addComponent(textFieldAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label2)
							.addComponent(comboBoxToolkit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label3)
							.addComponent(comboBoxMode, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(label4)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
			);
		}

		//======== panel3 ========
		{
			panel3.setBorder(new TitledBorder("Simulation Optimization details"));

			//---- label5 ----
			labelModelFile.setText("Simulation model:");

			//---- label6 ----
			label6.setText("Simulation toolkit command:");

			//---- label7 ----
			label7.setText("Selection function:");

			//---- label8 ----
			label8.setText("Selection command:");

			//---- label9 ----
			label9.setText("Evaluate function:");

			//---- label10 ----
			label10.setText("Evaluate command:");

			GroupLayout panel3Layout = new GroupLayout(panel3);
			panel3.setLayout(panel3Layout);
			panel3Layout.setHorizontalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel3Layout.createParallelGroup()
							.addComponent(label6)
							.addComponent(label8)
							.addComponent(label10)
							.addComponent(label7)
							.addComponent(labelModelFile)
							.addComponent(label9))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addComponent(textFieldSelectionCommand, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
							.addComponent(textFieldEvaluateCommand)
							.addComponent(textFieldSelectionFunctionFIle)
							.addComponent(textFieldModelFile)
							.addComponent(textFieldEvaluateFunctionFile)
							.addComponent(textFieldSimCommand, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			panel3Layout.setVerticalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(labelModelFile)
							.addComponent(textFieldModelFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label6)
							.addComponent(textFieldSimCommand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label7)
							.addComponent(textFieldSelectionFunctionFIle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label8)
							.addComponent(textFieldSelectionCommand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label9)
							.addComponent(textFieldEvaluateFunctionFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label10)
							.addComponent(textFieldEvaluateCommand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
		}

		//======== panel4 ========
		{

			//---- button1 ----
			buttonNext.setText("Next");

			GroupLayout panel4Layout = new GroupLayout(panel4);
			panel4.setLayout(panel4Layout);
			panel4Layout.setHorizontalGroup(
				panel4Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(buttonNext)
						.addContainerGap())
			);
			panel4Layout.setVerticalGroup(
				panel4Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(buttonNext))
			);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(panel1, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel1, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
					.addGap(14, 14, 14)
					.addComponent(panel3, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
	}
	
	private void initMyComponents()
	{
//		DefaultComboBoxModel<String> model_mode=new DefaultComboBoxModel<String>();
//		model_mode.addElement("Parameters Space Exploration (PSE)");
//		model_mode.addElement("Simulaiton Optimization (SO)");
//		comboBoxMode.setModel(model_mode);
//		
//		
//
//		DefaultComboBoxModel<String> model_Toolkit=new DefaultComboBoxModel<String>();
//		model_Toolkit.addElement("MASON");
//		model_Toolkit.addElement("NetLogo");
//		model_Toolkit.addElement("Generic");
//		
//		comboBoxToolkit.setModel(model_Toolkit);
		
		
		comboBoxToolkit.setSelectedIndex(0);
		comboBoxToolkit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((String)comboBoxToolkit.getSelectedItem()).equalsIgnoreCase("generic")){
					textFieldSimCommand.setEnabled(true);
				}else{
					textFieldSimCommand.setEnabled(false);
				}
				
			}
		});
		comboBoxMode.setSelectedIndex(0);
		comboBoxMode.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(((String)comboBoxMode.getSelectedItem()).contains("SO"))
		        	{
		        		switchSOPanel(true);
		        	}
		        else{
	        		switchSOPanel(false);
		        }
		    }
		});
		switchSOPanel(false);
		
		buttonNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 buttonNextActionPerformed(e);
				
			}
		});
		
		textFieldModelFile.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Select your simulation model");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(textFieldModelFile.getText().isEmpty())
					fc.setCurrentDirectory(new File("."));
				else
					fc.setCurrentDirectory(new File(textFieldModelFile.getText()));
				
				fc.setMultiSelectionEnabled(false);
				if (fc.showOpenDialog(textFieldModelFile)== JFileChooser.APPROVE_OPTION)
					textFieldModelFile.setText(fc.getSelectedFile().getAbsolutePath());
				else
					JOptionPane.showMessageDialog(textFieldModelFile, "Selection failed!\nPlease select your simulation model");
			}
		});
		textFieldSelectionFunctionFIle.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Select your Selection function executable");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(textFieldSelectionFunctionFIle.getText().isEmpty())
					fc.setCurrentDirectory(new File("."));
				else
					fc.setCurrentDirectory(new File(textFieldSelectionFunctionFIle.getText()));
				
				fc.setMultiSelectionEnabled(false);
				if (fc.showOpenDialog(textFieldSelectionFunctionFIle)== JFileChooser.APPROVE_OPTION)
					textFieldSelectionFunctionFIle.setText(fc.getSelectedFile().getAbsolutePath());
				else
					JOptionPane.showMessageDialog(textFieldSelectionFunctionFIle, "Selection failed!\nPlease select your selection function executable");
				
			}
		});
		
		textFieldEvaluateFunctionFile.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Select your evaluate function executable");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(textFieldEvaluateFunctionFile.getText().isEmpty())
					fc.setCurrentDirectory(new File("."));
				else
					fc.setCurrentDirectory(new File(textFieldEvaluateFunctionFile.getText()));
				
				fc.setMultiSelectionEnabled(false);
				if (fc.showOpenDialog(textFieldEvaluateFunctionFile)== JFileChooser.APPROVE_OPTION)
					textFieldEvaluateFunctionFile.setText(fc.getSelectedFile().getAbsolutePath());
				else
					JOptionPane.showMessageDialog(textFieldEvaluateFunctionFile, "Selection failed!\nPlease select your evaluate function executable");
				
			}
		});
	}
	private void switchSOPanel(boolean status)
	{
//		boolean status=!panel3.isEnabled();
		//panel3.setEnabled(status);
		textFieldSimCommand.setEnabled(status);
		textFieldSelectionCommand.setEnabled(status);
		textFieldEvaluateCommand.setEnabled(status);
		textFieldSelectionFunctionFIle.setEnabled(status);
		//textFieldModelFile.setEnabled(status);
		textFieldEvaluateFunctionFile.setEnabled(status);
		//labelModelFile.setEnabled(status);
		label6.setEnabled(status);
		label7.setEnabled(status);
		label8.setEnabled(status);
		label9.setEnabled(status);
		label10.setEnabled(status);
	}
	protected void buttonNextActionPerformed(ActionEvent e) {

		
		sim = new Simulation();
		 
		author=textFieldAuthor.getText();
		mode=(String) comboBoxMode.getSelectedItem();
		toolkit=(String) comboBoxToolkit.getSelectedItem();
		descr=textArea1.getText();
		
		if( author.isEmpty() )
		{
			JOptionPane.showMessageDialog(this, "You must fulfill all fields.");
			return;
		}
		sim.setAuthor(author);
		sim.setToolkit(toolkit);
		sim.setDescription(descr);
		sim.setLoop(isLoop());
		sim.setName(sim_name);
		sproc.setDomainView();
		simdirpath=textFieldModelFile.getText();
		if(isLoop())
		{
			simcommand=textFieldSimCommand.getText();
			//simdirpath=textFieldModelFile.getText();
			selcommand=textFieldSelectionCommand.getText();
			selpath=textFieldSelectionFunctionFIle.getText();
			evalcomma=textFieldEvaluateCommand.getText();
			evalpath=textFieldEvaluateFunctionFile.getText();
			
			if( simcommand.isEmpty() || simdirpath.isEmpty() || selcommand.isEmpty() || selpath.isEmpty() 
					|| evalcomma.isEmpty() || evalpath.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "You must fulfill all fields.");
				return;
			}
		}
		
	}
	
	private boolean isLoop(){
		return (((String)comboBoxMode.getSelectedItem()).contains("SO"))?true:false;
	}
	
	public Simulation getSim(){
		return sim;
	}
	
	public String author;
	public String mode;
	public String toolkit;
	public String descr;
	public String simcommand;
	public String simdirpath;
	public String selcommand;
	public String selpath;
	public String evalcomma;
	public String evalpath;
	
	private JPanel panel1;
	private Simulation sim;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JTextField textFieldAuthor;
	private JComboBox comboBoxToolkit;
	private JComboBox comboBoxMode;
	private JLabel label4;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JPanel panel3;
	private JLabel labelModelFile;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JLabel label10;
	private JTextField textFieldSimCommand;
	private JTextField textFieldSelectionCommand;
	private JTextField textFieldEvaluateCommand;
	private JTextField textFieldSelectionFunctionFIle;
	private JTextField textFieldModelFile;
	private JTextField textFieldEvaluateFunctionFile;
	private JPanel panel4;
	private JButton buttonNext;
}
