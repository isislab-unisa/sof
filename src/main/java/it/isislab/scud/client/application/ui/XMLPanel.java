package it.isislab.scud.client.application.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


/**
 * @author Carmine Spagnuolo
 */
public class XMLPanel extends JPanel {
	public XMLPanel() {
		initComponents();
		
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
