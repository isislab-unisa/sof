package it.isislab.scud.client.application.ui.newsimulation;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 */



/**
 * @author Red red
 */
public class NewSimulationPanel extends JPanel {
	public NewSimulationPanel() {
		initComponents();
	}

	private void initComponents() {
		panel1 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		textFieldAuthor = new JTextField();
		comboBoxToolkit = new JComboBox();
		comboBoxMode = new JComboBox();
		label4 = new JLabel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		panel3 = new JPanel();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		textFieldSimCommand = new JTextField();
		textFieldSelectionCommand = new JTextField();
		textFieldEvaluateCommand = new JTextField();
		textFieldSelectionFunctionFIle = new JTextField();
		textField1ModelFile = new JTextField();
		textFieldEvaluateFunctionFile = new JTextField();
		panel4 = new JPanel();
		button1 = new JButton();

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
								.addComponent(label3)
								.addGap(97, 97, 97)
								.addComponent(comboBoxMode))
							.addGroup(panel1Layout.createSequentialGroup()
								.addComponent(label4)
								.addGap(58, 58, 58)
								.addComponent(scrollPane1))
							.addGroup(panel1Layout.createSequentialGroup()
								.addGroup(panel1Layout.createParallelGroup()
									.addGroup(panel1Layout.createSequentialGroup()
										.addComponent(label2)
										.addGap(18, 18, 18)
										.addComponent(comboBoxToolkit, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
									.addGroup(panel1Layout.createSequentialGroup()
										.addComponent(label1)
										.addGap(89, 89, 89)
										.addComponent(textFieldAuthor, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)))
								.addGap(0, 0, Short.MAX_VALUE)))
						.addGap(9, 9, 9))
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
							.addComponent(comboBoxToolkit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label3)
							.addComponent(comboBoxMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(label4)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
						.addGap(20, 20, 20))
			);
		}

		//======== panel3 ========
		{
			panel3.setBorder(new TitledBorder("Simulation Optimization details"));

			//---- label5 ----
			label5.setText("Simulation model:");

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
							.addComponent(label5)
							.addComponent(label9))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addComponent(textFieldSimCommand, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
							.addComponent(textFieldSelectionCommand, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
							.addComponent(textFieldEvaluateCommand, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
							.addComponent(textFieldSelectionFunctionFIle, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
							.addComponent(textField1ModelFile, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
							.addComponent(textFieldEvaluateFunctionFile, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			panel3Layout.setVerticalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label5)
							.addComponent(textField1ModelFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
			button1.setText("Next");

			GroupLayout panel4Layout = new GroupLayout(panel4);
			panel4.setLayout(panel4Layout);
			panel4Layout.setHorizontalGroup(
				panel4Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
						.addContainerGap(291, Short.MAX_VALUE)
						.addComponent(button1)
						.addContainerGap())
			);
			panel4Layout.setVerticalGroup(
				panel4Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(button1))
			);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
						.addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel3, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

	private JPanel panel1;
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
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JLabel label10;
	private JTextField textFieldSimCommand;
	private JTextField textFieldSelectionCommand;
	private JTextField textFieldEvaluateCommand;
	private JTextField textFieldSelectionFunctionFIle;
	private JTextField textField1ModelFile;
	private JTextField textFieldEvaluateFunctionFile;
	private JPanel panel4;
	private JButton button1;
}
