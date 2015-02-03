package it.isislab.scud.client.application.ui.newsimulation;



import javax.swing.*;



/**
 * @author Red red
 */
public class NewDomainParameterNumeric extends JPanel {
	public NewDomainParameterNumeric() {
		initComponents();
	}

	private void initComponents() {
		labelMin = new JLabel();
		labelMax = new JLabel();
		labelIncrement = new JLabel();
		textFieldMin = new JTextField();
		textFieldMax = new JTextField();
		textFieldIncrement = new JTextField();


		//---- label1 ----
		labelMin.setText("Min value:");

		//---- label2 ----
		labelMax.setText("Max value:");

		//---- label3 ----
		labelIncrement.setText("Increment:");






		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGap(12, 12, 12)
						.addGroup(layout.createParallelGroup()
								.addComponent(labelMin)
								.addComponent(labelMax)
								.addComponent(labelIncrement))
								.addGap(40, 40, 40)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(textFieldMin, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
										.addComponent(textFieldMax, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
										.addComponent(textFieldIncrement, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
										.addContainerGap(21, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(labelMin)
								.addComponent(textFieldMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(labelMax)
										.addComponent(textFieldMax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(labelIncrement)
												.addComponent(textFieldIncrement, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addContainerGap(30, Short.MAX_VALUE))
				);
	}




	public String getMin() {

		return textFieldMin.getText();
	}




	public String getMax() {

		return textFieldMax.getText();
	}




	public String getIncrement() {

		return textFieldIncrement.getText();
	}

	

	private JLabel labelMin;
	private JLabel labelMax;
	private JLabel labelIncrement;
	private JTextField textFieldMin;
	private JTextField textFieldMax;
	private JTextField textFieldIncrement;
}
