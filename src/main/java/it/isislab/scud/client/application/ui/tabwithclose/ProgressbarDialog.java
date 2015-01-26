package it.isislab.scud.client.application.ui.tabwithclose;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;


/**
 * @author Carmine Spagnuolo
 */
public class ProgressbarDialog extends JDialog {
	public ProgressbarDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public ProgressbarDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		
		progressBar1 = new JProgressBar();
		titleMessage = new JLabel();
		noteMessage = new JLabel();

	
		Container contentPane = getContentPane();

		//---- label1 ----
		titleMessage.setText("Message1");

		//---- label2 ----
		noteMessage.setText("Message2");

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(titleMessage)
								.addComponent(noteMessage))
							.addGap(0, 298, Short.MAX_VALUE))
						.addComponent(progressBar1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addGap(12, 12, 12)
					.addComponent(titleMessage)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
					.addComponent(noteMessage)
					.addGap(28, 28, 28)
					.addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(25, 25, 25))
		);
		pack();
		setLocationRelativeTo(getOwner());
		
	}



	public String getTitleMessage() {
		return titleMessage.getText();
	}

	public void setTitleMessage(String titleMessage) {
		this.titleMessage.setText(titleMessage);
	}

	public String getNoteMessage() {
		return noteMessage.getText();
	}

	public void setNoteMessage(String noteMessage) {
		this.noteMessage.setText(noteMessage);
	}



	public JProgressBar getProgressBar1() {
		return progressBar1;
	}

	public void setProgressBar1(JProgressBar progressBar1) {
		this.progressBar1 = progressBar1;
	}



	private JProgressBar progressBar1;
	private JLabel titleMessage;
	private JLabel noteMessage;

}
