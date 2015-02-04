package it.isislab.scud.client.application.ui;


import it.isislab.scud.client.application.ui.newsimulation.NewSimulationProcess;
import it.isislab.scud.client.application.ui.tabwithclose.JTabbedPaneWithCloseIcons;
import it.isislab.scud.client.application.ui.tabwithclose.ProgressbarDialog;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Loop;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulations;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.message.Message;
import it.isislab.scud.core.model.parameters.xsd.output.Output;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;



/**
 * @author Carmine Spagnuolo
 */
public class MainFrame extends JFrame {
	protected final Controller controller; 
	protected JFrame main_frame;
	private static final long serialVersionUID = 1L;
	public MainFrame(Controller controller) {
		this.controller=controller;
		main_frame=this;
		initComponents();

		
		
	}

	private void initComponents() {
		initFileSystem();


		menuBar1 = new JMenuBar();
		menuPrincipal = new JMenu();
		menuItemAbout = new JMenuItem();
		BackgroundPanel = new JPanel();
		ButtonPanel = new JPanel();
		buttonReload = new JButton();
		buttonAdd = new JButton();
		buttonExport = new JButton();
		buttonStop = new JButton();
		buttonShow = new JButton();
		buttonSubmit = new JButton();
		MainPanel = new JPanel();
		LeftPanel = new JPanel();
		scrollPane1 = new JScrollPane();

		CenterPanel = new JPanel();
		splitPane1 = new JSplitPane();
		Toppanel = new JPanel();
		CenterTabbedscrollPane = new JScrollPane();
		CentraltabbedPane = new JTabbedPaneWithCloseIcons();
		TestCentralTabbedpanel = new JPanel();
		Bottonpanel = new JPanel();
		CenterBottomscrollPane = new JScrollPane();
		CenterBottomlist = new JList();

		//======== this ========
		setTitle("SCUD Client");
		Container contentPane = getContentPane();

		//======== menuBar1 ========
		{

			//======== menuSCUDCLIENT ========
			{
				menuPrincipal.setText("SCUD Client");
				
				//---- menuItemAbout ----
				menuItemAbout.setText("About         ");
		
				menuPrincipal.add(menuItemAbout);
								
			
			}
			
			
			
			menuBar1.add(menuPrincipal);
		}
		setJMenuBar(menuBar1);

		//======== BackgroundPanel ========
		{


			//======== ButtonPanel ========
			{
				ButtonPanel.setBorder(null);

				try {
//				buttonReload.setIcon(new ImageIcon("scud-resources/images/ic_action_refresh.png"));
			
					buttonReload.setIcon(new ImageIcon(
					new URL("https://raw.githubusercontent.com/isislab-unisa/scud/master/scud-resources/images/ic_action_refresh.png")));
			
				buttonReload.setToolTipText("Reload. Reloads the simulations from HDFS.");

//				buttonAdd.setIcon(new ImageIcon("scud-resources/images/ic_action_new.png"));
				buttonAdd.setIcon(new ImageIcon(
				new URL("https://raw.githubusercontent.com/isislab-unisa/scud/master/scud-resources/images/ic_action_new.png")));
				buttonAdd.setToolTipText("Add. Creates a new child node.");

//				buttonSubmit.setIcon(new ImageIcon("scud-resources/images/ic_action_play.png"));
				buttonSubmit.setIcon(new ImageIcon(
				new URL("https://raw.githubusercontent.com/isislab-unisa/scud/master/scud-resources/images/ic_action_play.png")));
				buttonSubmit.setToolTipText("Submit. Submits the selected simulation to the system.");

				buttonStop.setIcon(new ImageIcon(
				new URL("https://raw.githubusercontent.com/isislab-unisa/scud/master/scud-resources/images/ic_action_stop.png")));
				buttonStop.setToolTipText("Stop. Interrupts the selected simulation.");

//				buttonExport.setIcon(new ImageIcon("scud-resources/images/ic_action_download.png"));
				buttonExport.setIcon(new ImageIcon(
				new URL("https://raw.githubusercontent.com/isislab-unisa/scud/master/scud-resources/images/ic_action_download.png")));
				buttonExport.setToolTipText("Download. Downloads the simulation package from HDFS.");

				buttonShow.setIcon(new ImageIcon(
				new URL("https://raw.githubusercontent.com/isislab-unisa/scud/master/scud-resources/images/ic_action_about.png")));
				buttonShow.setToolTipText("Show. Shows details about the selected simulations.");
				
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					System.err.println("Error in loading images.");
					e1.printStackTrace();
				}
				
				buttonShow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonShowActionPerformed(e);
					}
				});

				buttonReload.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonReloadActionPerformed(e);
					}
				});

				buttonAdd.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonAddActionPerformed(e);
					}
				});

				buttonExport.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonExportActionPerformed(e);
					}
				});

				buttonStop.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonStopActionPerformed(e);
					}
				});
				buttonSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonSubmitActionPerformed(e);
					}
				});


				GroupLayout ButtonPanelLayout = new GroupLayout(ButtonPanel);
				ButtonPanel.setLayout(ButtonPanelLayout);
				ButtonPanelLayout.setHorizontalGroup(
						ButtonPanelLayout.createParallelGroup()
						.addGroup(ButtonPanelLayout.createSequentialGroup()
								.addComponent(buttonReload)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonAdd)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonSubmit)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonStop)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonExport)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(buttonShow)
								.addGap(0, 0, Short.MAX_VALUE))
						);
				ButtonPanelLayout.setVerticalGroup(
						ButtonPanelLayout.createParallelGroup()
						.addGroup(ButtonPanelLayout.createSequentialGroup()
								.addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(buttonReload)
										.addComponent(buttonAdd)
										.addComponent(buttonSubmit)
										.addComponent(buttonStop)
										.addComponent(buttonExport)
										.addComponent(buttonShow))
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						);
			}

			//======== MainPanel ========
			{

				//======== LeftPanel ========
				{
					LeftPanel.setBorder(new TitledBorder("File System"));

					//======== scrollPane1 ========
					{
						scrollPane1.setViewportView(tree1);
					}

					GroupLayout LeftPanelLayout = new GroupLayout(LeftPanel);
					LeftPanel.setLayout(LeftPanelLayout);
					LeftPanelLayout.setHorizontalGroup(
							LeftPanelLayout.createParallelGroup()
							.addGroup(LeftPanelLayout.createSequentialGroup()
									.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
							);
					LeftPanelLayout.setVerticalGroup(
							LeftPanelLayout.createParallelGroup()
							.addComponent(scrollPane1)
							);
				}

				//======== CenterPanel ========
				{

					//======== splitPane1 ========
					{
						splitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);

						//======== Toppanel ========
						{
							Toppanel.setBackground(new Color(255, 153, 153));
							//======== CenterTabbedscrollPane ========
							{

								//======== CentraltabbedPane ========
								{

									//======== TestCentralTabbedpanel ========
									{

										GroupLayout TestCentralTabbedpanelLayout = new GroupLayout(TestCentralTabbedpanel);
										TestCentralTabbedpanel.setLayout(TestCentralTabbedpanelLayout);
										TestCentralTabbedpanelLayout.setHorizontalGroup(
												TestCentralTabbedpanelLayout.createParallelGroup()
												.addGap(0, 1072, Short.MAX_VALUE)
												);
										TestCentralTabbedpanelLayout.setVerticalGroup(
												TestCentralTabbedpanelLayout.createParallelGroup()
												.addGap(0, 50, Short.MAX_VALUE)
												);
									}
									//									CentraltabbedPane.addTab("Test 01", TestCentralTabbedpanel);
									//									CentraltabbedPane.addTab("Test 01", new NewSimulationPanel(""));
									//									CentraltabbedPane.addTab("Test 02", new NewDomain());
									//									CentraltabbedPane.addTab("Test 03", new NewInputOutput());
									//									CentraltabbedPane.addTab("Test 04", new XMLPanel());
								}
								CenterTabbedscrollPane.setViewportView(CentraltabbedPane);
							}

							GroupLayout ToppanelLayout = new GroupLayout(Toppanel);
							Toppanel.setLayout(ToppanelLayout);
							ToppanelLayout.setHorizontalGroup(
									ToppanelLayout.createParallelGroup()
									.addComponent(CenterTabbedscrollPane)
									);
							ToppanelLayout.setVerticalGroup(
									ToppanelLayout.createParallelGroup()
									.addGroup(ToppanelLayout.createSequentialGroup()
											.addComponent(CenterTabbedscrollPane)
											.addGap(0, 0, 0))
									);
						}
						splitPane1.setTopComponent(Toppanel);

						//======== Bottonpanel ========
						{
							Bottonpanel.setBackground(new Color(51, 51, 255));

							//======== CenterBottomscrollPane ========
							{
								CenterBottomscrollPane.setViewportView(CenterBottomlist);
							}

							GroupLayout BottonpanelLayout = new GroupLayout(Bottonpanel);
							Bottonpanel.setLayout(BottonpanelLayout);
							BottonpanelLayout.setHorizontalGroup(
									BottonpanelLayout.createParallelGroup()
									.addComponent(CenterBottomscrollPane, GroupLayout.DEFAULT_SIZE, 1097, Short.MAX_VALUE)
									);
							BottonpanelLayout.setVerticalGroup(
									BottonpanelLayout.createParallelGroup()
									.addComponent(CenterBottomscrollPane, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
									);
						}
						splitPane1.setBottomComponent(Bottonpanel);
					}

					GroupLayout CenterPanelLayout = new GroupLayout(CenterPanel);
					CenterPanel.setLayout(CenterPanelLayout);
					CenterPanelLayout.setHorizontalGroup(
							CenterPanelLayout.createParallelGroup()
							.addGroup(CenterPanelLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(splitPane1)
									.addContainerGap())
							);
					CenterPanelLayout.setVerticalGroup(
							CenterPanelLayout.createParallelGroup()
							.addGroup(CenterPanelLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(splitPane1)
									.addContainerGap())
							);
				}

				GroupLayout MainPanelLayout = new GroupLayout(MainPanel);
				MainPanel.setLayout(MainPanelLayout);
				MainPanelLayout.setHorizontalGroup(
						MainPanelLayout.createParallelGroup()
						.addGroup(MainPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(LeftPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(CenterPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
						);
				MainPanelLayout.setVerticalGroup(
						MainPanelLayout.createParallelGroup()
						.addComponent(CenterPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(MainPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(LeftPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
						);
			}

			GroupLayout BackgroundPanelLayout = new GroupLayout(BackgroundPanel);
			BackgroundPanel.setLayout(BackgroundPanelLayout);
			BackgroundPanelLayout.setHorizontalGroup(
					BackgroundPanelLayout.createParallelGroup()
					.addComponent(ButtonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(BackgroundPanelLayout.createSequentialGroup()
							.addComponent(MainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(12, 12, 12))
					);
			BackgroundPanelLayout.setVerticalGroup(
					BackgroundPanelLayout.createParallelGroup()
					.addGroup(BackgroundPanelLayout.createSequentialGroup()
							.addComponent(ButtonPanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(MainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
					);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(BackgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addComponent(BackgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(31, 31, 31))
				);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splitPane1.setDividerLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.8));
		splitPane1.setOneTouchExpandable(true);
		splitPane1.setContinuousLayout(true);


		updateFileSystem();
		tree1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

		main_frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				controller.exit();
			}
		});
		
		pack();
		setLocationRelativeTo(null);
	}

	protected void buttonShowActionPerformed(ActionEvent e) {
		DefaultMutableTreeNode selected =(DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
		if(selected !=null && selected.toString().contains("Simulation name")){

			Enumeration<DefaultMutableTreeNode> children = selected.children();
			String status;
			String idSim=null;
			while(children.hasMoreElements()){
				DefaultMutableTreeNode node = children.nextElement();
				if(node.toString().contains("Id")){
					String[] split = node.toString().split(":");
					idSim = split[1].trim();
				}
			}

			XMLPanel panel=new XMLPanel(sims_hdfs.get(idSim));

			CentraltabbedPane.add(panel,"Sim: "+idSim);

			CentraltabbedPane.setSelectedComponent(panel);
			
		}


	}

	protected void buttonSubmitActionPerformed(ActionEvent e) {
		DefaultMutableTreeNode selected =(DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
		if(selected !=null && selected.toString().contains("Simulation name")){

			Enumeration<DefaultMutableTreeNode> children = selected.children();
			String status;
			String idSim=null;
			while(children.hasMoreElements()){
				DefaultMutableTreeNode node = children.nextElement();
				if(node.toString().contains("Status")){
					String[] split = node.toString().split(":");
					status = split[1].trim();
					if(!status.equalsIgnoreCase(Simulation.CREATED) && !status.equalsIgnoreCase(Simulation.STOPPED)){
						JOptionPane.showMessageDialog(this,"Selection error!\n You must select either created or stopped simulation only");
						return;
					}
				}
				if(node.toString().contains("Id")){
					String[] split = node.toString().split(":");
					idSim = split[1].trim();
				}
			}
			final String final_sim_id=idSim;
			final ProgressbarDialog pd=new ProgressbarDialog(this);
			pd.setNoteMessage("Please wait.");
			pd.setVisible(true);
			final JProgressBar bar=pd.getProgressBar1();
			pd.setTitleMessage("Submit simulation with id: "+final_sim_id);
			bar.setIndeterminate(true);
			class MyTaskConnect extends Thread {

				public void run(){
					if(final_sim_id!=null)
						controller.start(final_sim_id);
					else{
						JOptionPane.showMessageDialog(main_frame,"Submit Error! Please try again.");
						return;
					}

					updateFileSystem();

					bar.setIndeterminate(false);
					pd.setVisible(false);
				}
			}

			(new MyTaskConnect()).start();

		}
		else {
			JOptionPane.showMessageDialog(this,"Selection error!\n You must select selection node from SCUD filesystem");
		}

	}

	protected void buttonStopActionPerformed(ActionEvent e) {
		DefaultMutableTreeNode selected =(DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
		if(selected !=null && selected.toString().contains("Simulation name")){

			Enumeration<DefaultMutableTreeNode> children = selected.children();
			String status;
			String idSim=null;
			while(children.hasMoreElements()){
				DefaultMutableTreeNode node = children.nextElement();
				if(node.toString().contains("Status")){
					String[] split = node.toString().split(":");
					status = split[1].trim();
					if(status.equalsIgnoreCase("stopped")){
						JOptionPane.showMessageDialog(this,"Simulation already stopped");
						return;
					}else
						if(!status.equalsIgnoreCase("running")){
							JOptionPane.showMessageDialog(this,"Selection error!\n You must select a running simulation");
							return;
						}
				}
				if(node.toString().contains("Id")){
					String[] split = node.toString().split(":");
					idSim = split[1].trim();
				}
			}
			final String final_sim_id=idSim;
			final ProgressbarDialog pd=new ProgressbarDialog(this);
			pd.setNoteMessage("Please wait.");
			pd.setVisible(true);
			final JProgressBar bar=pd.getProgressBar1();
			pd.setTitleMessage("Stopping simulation with id: "+final_sim_id);
			bar.setIndeterminate(true);
			class MyTaskConnect extends Thread {

				public void run(){
					if(final_sim_id!=null)
						controller.stop(final_sim_id);
					else{
						JOptionPane.showMessageDialog(main_frame,"Stop Error! Please try again.");
						return;
					}

					updateFileSystem();

					bar.setIndeterminate(false);
					pd.setVisible(false);
				}
			}

			(new MyTaskConnect()).start();

		}
		else {
			JOptionPane.showMessageDialog(this,"Selection error!\n You must select selection node from SCUD filesystem");
		}

	}

	protected void buttonExportActionPerformed(ActionEvent e) {

	
			DefaultMutableTreeNode selected =(DefaultMutableTreeNode)tree1.getLastSelectedPathComponent();
			if(selected !=null && selected.toString().contains("Simulation name")){
				
				String simualtionID=null;
				Enumeration<DefaultMutableTreeNode> children = selected.children();
				while(children.hasMoreElements()){
					DefaultMutableTreeNode node = children.nextElement();
					if(node.toString().contains("Id")){
						String[] split = node.toString().split(":");
						simualtionID = split[1].trim();
						
					}
				}
				if(simualtionID==null) 
					{
						JOptionPane.showMessageDialog(this,"Error in reading simulation ID, try again!.");
						return;
					}
				
				Object[] possibilities = {"choose export method", "Excel", "Backup"};
				String s = (String)
				JOptionPane.showInputDialog(this,
						"Select how to export the simulation with ID:"+ simualtionID, 
						"Export",
						JOptionPane.QUESTION_MESSAGE,
						javax.swing.UIManager.getIcon("OptionPane.informationIcon"), 
						possibilities, 
						"choose export method");
				if(s == null && s.equals("choose export method"))
				{
					return;
				}
				chooser = new JFileChooser();
				chooser.setDialogTitle("Select your donwload directory");
				chooser.setCurrentDirectory(new File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
	
	
	
				if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	
					final ProgressbarDialog pd=new ProgressbarDialog(this);
					pd.setNoteMessage("Please wait.");
				
					final JProgressBar bar=pd.getProgressBar1();
					
					bar.setIndeterminate(true);
				
					
					
					final String idSim =simualtionID;
					class MyTaskConnectBackup extends Thread {

						public void run(){

							controller.getresult(idSim,chooser.getSelectedFile().getAbsolutePath());
							bar.setIndeterminate(false);

							pd.setVisible(false);
							JOptionPane.showMessageDialog(main_frame,"Zip File exported correctly!");
						}
					}
					class MyTaskConnectExcel extends Thread {

						public void run(){

							controller.getresultExcel(idSim,chooser.getSelectedFile().getAbsolutePath());
							bar.setIndeterminate(false);

							pd.setVisible(false);
							
							JOptionPane.showMessageDialog(main_frame,"Excel File exported correctly!");
						}
					}

					if ((s != null) && (s.length() > 0)) {
						if(s.equals("Excel"))
						{
							pd.setTitleMessage("Export simulation in: "+chooser.getSelectedFile().getAbsolutePath());
							pd.setVisible(true);
							(new MyTaskConnectExcel()).start();
						}
						else if(s.equals("Backup"))
						{
							pd.setTitleMessage("Download simulation in: "+chooser.getSelectedFile().getAbsolutePath());
							pd.setVisible(true);
							(new MyTaskConnectBackup()).start();
						}else
						{
						
						}
					   
					}
					
					
				}
			}
			else {
				
				JOptionPane.showMessageDialog(this,"Selection error!\n You must select selection node from SCUD filesystem.");
			}
		

	}


	protected void buttonAddActionPerformed(ActionEvent e) {
		String sim_name=JOptionPane.showInputDialog("Simulation name:");
        while(sim_name.trim().equals("")){
        	JOptionPane.showMessageDialog(buttonShow, "Simulation name cannot be null","Warning", 0);
        	sim_name=JOptionPane.showInputDialog("Simulation name:");
        }
		
		NewSimulationProcess tab=new NewSimulationProcess(sim_name,this);
		tab.setNewSim();
		CentraltabbedPane.add(tab,sim_name);
		CentraltabbedPane.setSelectedComponent(tab);


	}

	protected void buttonReloadActionPerformed(ActionEvent e) {
		final ProgressbarDialog pd=new ProgressbarDialog(this);
		pd.setTitleMessage("Loading simulations on SCUD file system.");
		pd.setNoteMessage("Please wait.");
		final JProgressBar bar=pd.getProgressBar1();

		pd.setVisible(true);
		bar.setIndeterminate(true);

		class MyTaskConnect extends Thread {

			public void run(){

				updateFileSystem();
				bar.setIndeterminate(false);

				pd.setVisible(false);
			}
		}

		(new MyTaskConnect()).start();


	}

	private void initFileSystem()
	{

		fs_root = new DefaultMutableTreeNode("SCUD FileSystem");
		tree1 = new JTree(fs_root);

	}
	private void doMouseClicked(MouseEvent me)
	{
		TreePath tp = tree1.getPathForLocation(me.getX(), me.getY());
		if(tp !=null)
		{
			DefaultMutableTreeNode node= (DefaultMutableTreeNode) tp.getLastPathComponent();
			if(node!=null && me.getClickCount() == 2)
			{
				if(node.toString().contains("Input"))
				{
					DefaultMutableTreeNode sim_point= (DefaultMutableTreeNode) node.getParent();
					DefaultMutableTreeNode sim_loop= (DefaultMutableTreeNode) node.getParent();


				}
			}

		}

	}
	private void updateFileSystem()
	{
		System.out.println("Loading simulations");
		Simulations sims=controller.getsimulations();
		fs_root = new DefaultMutableTreeNode("SCUD FileSystem");
		scrollPane1.remove(tree1);
		tree1 = new JTree(fs_root);
		scrollPane1.setViewportView(tree1);
		sims_hdfs = new HashMap<String, Simulation>();
		DefaultMutableTreeNode new_sim=null;
		if(sims == null)
		{
			JOptionPane.showMessageDialog(this,"No simulations found on HDFS.");
			return;
		}

		for(Simulation s : sims.getSimulations())
			sims_hdfs.put(s.getId(), s);

		Simulation s=null;
		for (String key : sims_hdfs.keySet()) {
			s = sims_hdfs.get(key);
			new_sim = new DefaultMutableTreeNode("Simulation name: "+s.getName());
			fs_root.add(new_sim);

			new_sim.add(new DefaultMutableTreeNode("Author: "+s.getAuthor()));
			new_sim.add(new DefaultMutableTreeNode("Creation time: "+s.getCreationTime()));
			DefaultMutableTreeNode descr=new DefaultMutableTreeNode("Description: "+s.getDescription().substring(0, (s.getDescription().length()>15)?15:s.getDescription().length()-1));
			//descr.add(new DefaultMutableTreeNode(s.getDescription()));
			new_sim.add(descr);
			new_sim.add(new DefaultMutableTreeNode("Id: "+s.getId()));
			new_sim.add(new DefaultMutableTreeNode("Toolkit: "+s.getToolkit()));
			new_sim.add(new DefaultMutableTreeNode("Status: "+s.getState()));
			DefaultMutableTreeNode mode=new DefaultMutableTreeNode("Mode: "+(s.getLoop()?"Simulation Optimization":"Parameters Space Exploration"));
			new_sim.add(mode);
			DefaultMutableTreeNode loops=new DefaultMutableTreeNode("Loops");
			new_sim.add(loops);

			if(s.getLoop())
			{
				for(Loop l: s.getRuns().getLoops())
				{
					DefaultMutableTreeNode loop=new DefaultMutableTreeNode("Loop Id: "+l.getId());
					loop.add(new DefaultMutableTreeNode("Status: "+l.getStatus()));	
					loop.add(new DefaultMutableTreeNode("Start time: "+l.getStartTime()));
					loop.add(new DefaultMutableTreeNode("Stop time: "+l.getStopTime()));
					loop.add(new DefaultMutableTreeNode("Loop time: "+l.getLoopTime()));

					class PointTree
					{
						public Input getI() {
							return i;
						}
						public void setI(Input i) {
							this.i = i;
						}
						public Output getO() {
							return o;
						}
						public void setO(Output o) {
							this.o = o;
						}
						private Input i;
						private Output o;
					}
					HashMap<Integer, PointTree> mapio=new HashMap<Integer, PointTree>();

					if(l.getInputs()!=null)
					{
						for(Input i: l.getInputs().getinput_list())
						{
							PointTree p=new PointTree();
							p.setI(i);
							mapio.put(i.id, p);

						}

						if(l.getOutputs()!=null && l.getOutputs().getOutput_list()!=null)
							for(Output i: l.getOutputs().getOutput_list())
							{

								mapio.get(i.getIdInput()).setO(i);
							}
						else System.out.println("No output found.");

						for (Integer pt : mapio.keySet()) {

							DefaultMutableTreeNode point=new DefaultMutableTreeNode("Simulation point: "+pt);
							DefaultMutableTreeNode input=new DefaultMutableTreeNode("Input");
							DefaultMutableTreeNode output=new DefaultMutableTreeNode("Output");


							point.add(input);
							point.add(output);

							for(Parameter p : mapio.get(pt).getI().param_element)
							{
								DefaultMutableTreeNode vname=new DefaultMutableTreeNode("Variable Name: "+p.getvariable_name());
								input.add(vname);
								vname.add(new DefaultMutableTreeNode("Value: "+p.getparam()));
							}
							if(mapio.get(pt).getO()!=null)
								for(Parameter p : mapio.get(pt).getO().output_params)
								{
									DefaultMutableTreeNode vname=new DefaultMutableTreeNode("Variable Name: "+p.getvariable_name());
									output.add(vname);
									vname.add(new DefaultMutableTreeNode("Value: "+p.getparam()));
								}

							loop.add(point);
						}

					}
					loops.add(loop);

				}
			}
		}
		tree1.expandRow(0);

	}
	private DefaultMutableTreeNode fs_root;
	private JMenuBar menuBar1;
	private JMenu menuPrincipal;
	private JMenuItem menuItemAbout;
	private JPanel BackgroundPanel;
	private JPanel ButtonPanel;
	private JButton buttonReload;
	private JButton buttonAdd;
	private JButton buttonSubmit;
	private JButton buttonExport;
	private JButton buttonStop;
	private JButton buttonShow;
	private JPanel MainPanel;
	private JPanel LeftPanel;
	private JScrollPane scrollPane1;
	private JTree tree1;
	private JPanel CenterPanel;
	private JSplitPane splitPane1;
	private JPanel Toppanel;
	private JScrollPane CenterTabbedscrollPane;
	private JTabbedPaneWithCloseIcons CentraltabbedPane;
	private JPanel TestCentralTabbedpanel;
	private JPanel Bottonpanel;
	private JScrollPane CenterBottomscrollPane;
	private JList CenterBottomlist;
	private JFileChooser chooser;
	private HashMap<String, Simulation> sims_hdfs = new HashMap<String, Simulation>();
}
