package it.isislab.scud.client.application.ui.newsimulation;

import javax.swing.*;
import javax.swing.GroupLayout;
/*
 */



/**
 * @author Red red
 */
public class NewDomainListValues extends JPanel {
	public NewDomainListValues() {
		initComponents();
	}

	private void initComponents() {
		scrollPane1 = new JScrollPane();
		listValue = new JList();
		button1 = new JButton();

		//======== this ========

		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(listValue);
		}

		//---- button1 ----
		button1.setText("Add");

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
							.addGap(0, 285, Short.MAX_VALUE)
							.addComponent(button1)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addComponent(button1)
					.addContainerGap(8, Short.MAX_VALUE))
		);
	}

	private JScrollPane scrollPane1;
	private JList listValue;
	private JButton button1;
}
