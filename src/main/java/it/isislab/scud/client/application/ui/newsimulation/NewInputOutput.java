package it.isislab.scud.client.application.ui.newsimulation;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
/*
 */



/**
 * @author Red red
 */
public class NewInputOutput extends JPanel {
	public NewInputOutput() {
		initComponents();
	}

	private void initComponents() {
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		treeInput = new JTree();
		panel3 = new JPanel();
		panel5 = new JPanel();
		buttonAddInput = new JButton();
		buttonEditInput = new JButton();
		buttonRemoveInput = new JButton();
		panel2 = new JPanel();
		scrollPane2 = new JScrollPane();
		treeOutput = new JTree();
		panel4 = new JPanel();
		panel6 = new JPanel();
		buttonAddOutput = new JButton();
		buttonEditOutput = new JButton();
		buttonRemoveOutput = new JButton();
		buttonSave = new JButton();

		//======== this ========


		//======== panel1 ========
		{
			panel1.setBorder(new TitledBorder("Input"));

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(treeInput);
			}

			//======== panel3 ========
			{
				panel3.setBorder(new TitledBorder("New Input"));

				GroupLayout panel3Layout = new GroupLayout(panel3);
				panel3.setLayout(panel3Layout);
				panel3Layout.setHorizontalGroup(
					panel3Layout.createParallelGroup()
						.addGap(0, 378, Short.MAX_VALUE)
				);
				panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
						.addGap(0, 242, Short.MAX_VALUE)
				);
			}

			//======== panel5 ========
			{

				//---- buttonAddInput ----
				buttonAddInput.setText("Add");

				//---- buttonEditInput ----
				buttonEditInput.setText("Edit");

				//---- buttonRemoveInput ----
				buttonRemoveInput.setText("Remove");

				GroupLayout panel5Layout = new GroupLayout(panel5);
				panel5.setLayout(panel5Layout);
				panel5Layout.setHorizontalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createSequentialGroup()
							.addComponent(buttonAddInput)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(buttonEditInput)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
							.addComponent(buttonRemoveInput)
							.addContainerGap())
				);
				panel5Layout.setVerticalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(buttonAddInput)
							.addComponent(buttonEditInput)
							.addComponent(buttonRemoveInput))
				);
			}

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
							.addComponent(panel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(35, Short.MAX_VALUE))
			);
		}

		//======== panel2 ========
		{
			panel2.setBorder(new TitledBorder("Output"));

			//======== scrollPane2 ========
			{
				scrollPane2.setViewportView(treeOutput);
			}

			//======== panel4 ========
			{
				panel4.setBorder(new TitledBorder("New Output"));

				GroupLayout panel4Layout = new GroupLayout(panel4);
				panel4.setLayout(panel4Layout);
				panel4Layout.setHorizontalGroup(
					panel4Layout.createParallelGroup()
						.addGap(0, 366, Short.MAX_VALUE)
				);
				panel4Layout.setVerticalGroup(
					panel4Layout.createParallelGroup()
						.addGap(0, 240, Short.MAX_VALUE)
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
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
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

			GroupLayout panel2Layout = new GroupLayout(panel2);
			panel2.setLayout(panel2Layout);
			panel2Layout.setHorizontalGroup(
				panel2Layout.createParallelGroup()
					.addGroup(panel2Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel2Layout.createParallelGroup()
							.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
							.addComponent(panel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
			);
			panel2Layout.setVerticalGroup(
				panel2Layout.createParallelGroup()
					.addGroup(panel2Layout.createSequentialGroup()
						.addGap(19, 19, 19)
						.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(42, Short.MAX_VALUE))
			);
		}

		//---- buttonSave ----
		buttonSave.setText("Save");

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addContainerGap(838, Short.MAX_VALUE)
					.addComponent(buttonSave)
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(33, 33, 33)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18, 18, 18)
					.addComponent(buttonSave)
					.addContainerGap(62, Short.MAX_VALUE))
		);
	}

	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTree treeInput;
	private JPanel panel3;
	private JPanel panel5;
	private JButton buttonAddInput;
	private JButton buttonEditInput;
	private JButton buttonRemoveInput;
	private JPanel panel2;
	private JScrollPane scrollPane2;
	private JTree treeOutput;
	private JPanel panel4;
	private JPanel panel6;
	private JButton buttonAddOutput;
	private JButton buttonEditOutput;
	private JButton buttonRemoveOutput;
	private JButton buttonSave;
}
