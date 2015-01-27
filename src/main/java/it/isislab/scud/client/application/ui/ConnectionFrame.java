package it.isislab.scud.client.application.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;



/**
 * @author Carmine Spagnuolo
 */
public class ConnectionFrame extends JFrame {
	private ConnectionFrame frame;
	public ConnectionFrame() {

		initComponents();
		this.setVisible(true);
		frame=this;
	}

	private void buttonConnectActionPerformed(ActionEvent e) {

		progressBar1.setIndeterminate(true);
		
		buttonConnect.setEnabled(false);
		buttonDemo.setEnabled(false);
		textFielduserName.setEnabled(false);
		passwordFieldPassword.setEnabled(false);
		textFieldIP.setEnabled(false);
		textFieldPort.setEnabled(false);
		textFieldHadoopHome.setEnabled(false);
		textFieldJavaHome.setEnabled(false);
		textFieldSCUDHome.setEnabled(false);
		textFieldSCUDHDFSHome.setEnabled(false);
		
		class MyTaskConnect extends Thread {

	          public void run(){
	        		Controller c=Controller.getInstance();
					if(c!=null)
					{
						MainFrame main =new MainFrame(c);

						progressBar1.setIndeterminate(false);
						progressBar1.setVisible(false);
						main.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.8),
								(int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.7));
				     
						main.setVisible(true);
						frame.dispose();

					}
					
	          }
		 }

		(new MyTaskConnect()).start();
		
		return;

}

private void buttonDemoActionPerformed(ActionEvent e) {
	// TODO add your code here
}

private void initComponents() {

	panel1 = new JPanel();
	label12 = new JLabel();
	label13 = new JLabel();
	label14 = new JLabel();
	label15 = new URLLabel("Version Pre-alpha 1.0, Powerd by ISISLab, 2015.","http://www.isislab.it");
	label16 = new JLabel();
	label17 = new URLLabel("View source code on GitHub.","https://github.com/isislab-unisa/scud");
	panel2 = new JPanel();
	panel3 = new JPanel();
	label4 = new JLabel();
	textFielduserName = new JTextField();
	label5 = new JLabel();
	passwordFieldPassword = new JPasswordField();
	label6 = new JLabel();
	textFieldIP = new JTextField();
	label7 = new JLabel();
	textFieldPort = new JTextField();
	panel4 = new JPanel();
	label8 = new JLabel();
	textFieldHadoopHome = new JTextField();
	label9 = new JLabel();
	textFieldJavaHome = new JTextField();
	label10 = new JLabel();
	textFieldSCUDHome = new JTextField();
	label11 = new JLabel();
	textFieldSCUDHDFSHome = new JTextField();
	buttonConnect = new JButton();
	progressBar1 = new JProgressBar(JProgressBar.HORIZONTAL,
			0, Integer.MAX_VALUE );
	buttonDemo = new JButton();

	//======== this ========
	setResizable(false);
	Container contentPane = getContentPane();

	//======== panel1 ========
	{


		label12.setIcon(new ImageIcon("scud-resources/images/isislab.png"));

		//---- label13 ----
		label13.setText("SCUD");
		label13.setFont(new Font("Lucida Grande", Font.BOLD, 36));

		//---- label14 ----
		label14.setText("Simulation optimization and exploration on the CloUD.");

		//---- label15 ----
		//label15.setText();

		//---- label16 ----
		label16.setText("This client uses Apache Hadoop as cloud infrastructure.");

		GroupLayout panel1Layout = new GroupLayout(panel1);
		panel1.setLayout(panel1Layout);
		panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
								.addComponent(label13)
								.addComponent(label15)
								.addComponent(label14)
								.addComponent(label16).addComponent(label17))
								
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
								.addComponent(label12))
				);
		panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
				.addComponent(label12)
				.addGroup(panel1Layout.createSequentialGroup()
						.addGap(11, 11, 11)
						.addComponent(label13)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label14)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label16)
						.addGap(9, 9, 9)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label17)
						.addGap(9, 9, 9)
						.addComponent(label15))
				);
	}

	//======== panel2 ========
	{
		panel2.setBorder(new TitledBorder("Connection Setting"));

		//======== panel3 ========
		{
			panel3.setBorder(new TitledBorder("Account SSH"));

			//---- label4 ----
			label4.setText("User name");

			//---- label5 ----
			label5.setText("Password");

			//---- label6 ----
			label6.setText("Host Address");

			//---- label7 ----
			label7.setText("Host Port");

			GroupLayout panel3Layout = new GroupLayout(panel3);
			panel3.setLayout(panel3Layout);
			panel3Layout.setHorizontalGroup(
					panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel3Layout.createParallelGroup()
									.addComponent(passwordFieldPassword, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
									.addComponent(textFieldIP, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
									.addComponent(textFielduserName, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
									.addGroup(panel3Layout.createSequentialGroup()
											.addGroup(panel3Layout.createParallelGroup()
													.addComponent(label5)
													.addComponent(label6)
													.addComponent(label4)
													.addComponent(label7))
													.addGap(0, 174, Short.MAX_VALUE))
													.addComponent(textFieldPort, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
													.addContainerGap())
					);
			panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
							.addGap(11, 11, 11)
							.addComponent(label4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textFielduserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label5)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(passwordFieldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label6)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textFieldIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(4, 4, 4)
							.addComponent(label7)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textFieldPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
		}

		//======== panel4 ========
		{
			panel4.setBorder(new TitledBorder("Apache Hadoop Setting"));

			//---- label8 ----
			label8.setText("Hadoop bin directory");

			//---- label9 ----
			label9.setText("Java home directory");

			//---- label10 ----
			label10.setText("SCUD User home directory");

			//---- label11 ----
			label11.setText("SCUD HDFS home directory");

			GroupLayout panel4Layout = new GroupLayout(panel4);
			panel4.setLayout(panel4Layout);
			panel4Layout.setHorizontalGroup(
					panel4Layout.createParallelGroup()
					.addGroup(panel4Layout.createSequentialGroup()
							.addGap(10, 10, 10)
							.addGroup(panel4Layout.createParallelGroup()
									.addComponent(textFieldSCUDHDFSHome, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
									.addComponent(textFieldHadoopHome, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
									.addComponent(textFieldJavaHome, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
									.addComponent(textFieldSCUDHome, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
									.addGroup(panel4Layout.createSequentialGroup()
											.addGroup(panel4Layout.createParallelGroup()
													.addComponent(label11)
													.addComponent(label10)
													.addComponent(label9)
													.addComponent(label8))
													.addGap(0, 113, Short.MAX_VALUE)))
													.addContainerGap())
					);
			panel4Layout.setVerticalGroup(
					panel4Layout.createParallelGroup()
					.addGroup(panel4Layout.createSequentialGroup()
							.addGap(10, 10, 10)
							.addComponent(label8)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textFieldHadoopHome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label9)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textFieldJavaHome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label10)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textFieldSCUDHome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label11)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textFieldSCUDHDFSHome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
		}

		GroupLayout panel2Layout = new GroupLayout(panel2);
		panel2.setLayout(panel2Layout);
		panel2Layout.setHorizontalGroup(
				panel2Layout.createParallelGroup()
				.addGroup(panel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				);
		panel2Layout.setVerticalGroup(
				panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(panel2Layout.createSequentialGroup()
						.addContainerGap(28, Short.MAX_VALUE)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(panel3, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel4, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
								.addContainerGap())
				);
	}

	//---- buttonConnect ----
	buttonConnect.setText("Connect");
	buttonConnect.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			buttonConnectActionPerformed(e);
		}
	});

	//---- buttonDemo ----
	buttonDemo.setText("Demo mode");
	buttonDemo.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			buttonDemoActionPerformed(e);
		}
	});

	GroupLayout contentPaneLayout = new GroupLayout(contentPane);
	contentPane.setLayout(contentPaneLayout);
	contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
			.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
							.addComponent(panel2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(buttonConnect, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
									.addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 572, GroupLayout.PREFERRED_SIZE))
									.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
											.addGap(0, 588, Short.MAX_VALUE)
											.addComponent(buttonDemo)))
											.addContainerGap())
			);
	contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
			.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(contentPaneLayout.createParallelGroup()
							.addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(buttonConnect))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
							.addComponent(buttonDemo)
							.addContainerGap())
			);

	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
	setLocationRelativeTo(getOwner());

}

private JPanel panel1;
private JLabel label12;
private JLabel label13;
private JLabel label14;
private URLLabel label15;
private JLabel label16;
private URLLabel label17;
private JPanel panel2;
private JPanel panel3;
private JLabel label4;
private JTextField textFielduserName;
private JLabel label5;
private JPasswordField passwordFieldPassword;
private JLabel label6;
private JTextField textFieldIP;
private JLabel label7;
private JTextField textFieldPort;
private JPanel panel4;
private JLabel label8;
private JTextField textFieldHadoopHome;
private JLabel label9;
private JTextField textFieldJavaHome;
private JLabel label10;
private JTextField textFieldSCUDHome;
private JLabel label11;
private JTextField textFieldSCUDHDFSHome;
private JButton buttonConnect;
private JProgressBar progressBar1;
private JButton buttonDemo;

}
