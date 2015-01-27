package it.isislab.scud.client.application.ui.newsimulation;

import javax.swing.*;
import javax.swing.border.*;

public class NewDomain extends JPanel {
	public NewDomain(NewSimulationProcess newSimulationProcess) {
		sproc=newSimulationProcess;
		initComponents();
	}
	private NewSimulationProcess sproc=null;

	private void initComponents() {
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		tree1 = new JTree();
		panel2 = new JPanel();
		buttonAdd = new JButton();
		buttonEdit = new JButton();
		buttonRemove = new JButton();
		panel3 = new JPanel();
		label1 = new JLabel();
		comboBoxType = new JComboBox();
		label2 = new JLabel();
		textField1 = new JTextField();
		panel4 = new JPanel();
		panel5 = new JPanel();
		button3 = new JButton();
		buttonPrev = new JButton();

		//======== panel1 ========
		{
			panel1.setBorder(new TitledBorder("Domain"));

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(tree1);
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

		//======== panel3 ========
		{
			panel3.setBorder(new TitledBorder("New parameter domain"));

			//---- label1 ----
			label1.setText("Type:");

			//---- label2 ----
			label2.setText("Variable name:");

			//======== panel4 ========
			{
				panel4.setBorder(new TitledBorder("Details"));

				GroupLayout panel4Layout = new GroupLayout(panel4);
				panel4.setLayout(panel4Layout);
				panel4Layout.setHorizontalGroup(
					panel4Layout.createParallelGroup()
						.addGap(0, 342, Short.MAX_VALUE)
				);
				panel4Layout.setVerticalGroup(
					panel4Layout.createParallelGroup()
						.addGap(0, 491, Short.MAX_VALUE)
				);
			}

			GroupLayout panel3Layout = new GroupLayout(panel3);
			panel3.setLayout(panel3Layout);
			panel3Layout.setHorizontalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel3Layout.createParallelGroup()
							.addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(panel3Layout.createSequentialGroup()
								.addGroup(panel3Layout.createParallelGroup()
									.addComponent(label1)
									.addComponent(label2))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
								.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(textField1)
									.addComponent(comboBoxType, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))))
						.addGap(25, 25, 25))
			);
			panel3Layout.setVerticalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label1)
							.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label2)
							.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
			);
		}

		//======== panel5 ========
		{

			//---- button3 ----
			button3.setText("Next");

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
						.addComponent(button3))
			);
			panel5Layout.setVerticalGroup(
				panel5Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(button3)
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
	}


	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTree tree1;
	private JPanel panel2;
	private JButton buttonAdd;
	private JButton buttonEdit;
	private JButton buttonRemove;
	private JPanel panel3;
	private JLabel label1;
	private JComboBox comboBoxType;
	private JLabel label2;
	private JTextField textField1;
	private JPanel panel4;
	private JPanel panel5;
	private JButton button3;
	private JButton buttonPrev;
}
