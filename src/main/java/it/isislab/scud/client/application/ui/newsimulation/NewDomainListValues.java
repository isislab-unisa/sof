package it.isislab.scud.client.application.ui.newsimulation;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;



/**
 * @author Red red
 */
public class NewDomainListValues extends JPanel {
	public NewDomainListValues() {
		initComponents();
	}

	private void initComponents() {
		scrollPane1 = new JScrollPane();
		listModel = new DefaultListModel<String>();
		listValue = new JList(listModel);
		buttonAdd = new JButton();


		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(listValue);
		}

		//---- button1 ----
		buttonAdd.setText("Add");

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
							.addComponent(buttonAdd)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addComponent(buttonAdd)
					.addContainerGap(8, Short.MAX_VALUE))
		);
		
		buttonAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String newValue = JOptionPane.showInputDialog("Insert the new value here");
				if(!listModel.contains(newValue))
					listModel.addElement(newValue);
			}
		});
	}
	
	public ArrayList<String> getListValues(){
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < listModel.getSize(); i++) {
			list.add(listModel.getElementAt(i));
		}
		return list;
	}

	private JScrollPane scrollPane1;
	private JList listValue;
	private DefaultListModel<String> listModel;
	private JButton buttonAdd;
}
