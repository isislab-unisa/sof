package it.isislab.sof.client.application.ui;

import it.isislab.sof.client.application.SOFShellClient;
import it.isislab.sof.core.engine.hadoop.sshclient.connection.HadoopFileSystemManager;
import it.isislab.sof.core.engine.hadoop.sshclient.connection.SofManager;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Loop;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.sof.core.engine.hadoop.sshclient.utils.simulation.Simulations;
import it.isislab.sof.core.exception.ParameterException;
import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.sof.core.model.parameters.xsd.input.Input;
import it.isislab.sof.core.model.parameters.xsd.message.Message;
import it.isislab.sof.core.model.parameters.xsd.output.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.jcraft.jsch.JSchException;

public class Controller {

	private static Controller instance = null;

	/*public  String host_address= "172.";
	public  String pstring="cloud";
	public  String port="22";
	public  String bindir="/home/hadoop/hadoop-2.4.0";  
	public  String homedir="/home/hadoop/"; 
	public  String javabindir ="/usr/bin/";
	public  String user_name="hadoop";
	public  String sofhomedir="/";*/
	/*public  String host_address= "";
	public  String pstring="";
	public  String port="";
	public  String bindir="";  
	public  String homedir=""; 
	public  String javabindir ="";
	public  String user_name="";
	public  String sofhomedir="";
	public  String sofjarpath="";
	public  String sofjarrunnerpath="";

	 */
	public  String host_address= "127.0.0.1";
	public  String pstring="pippo";
	public  String port="22";
	public  String bindir="/isis/hadoop-2.4.0";  
	public  String homedir="/isis/"; 
	public  String javabindir ="/usr/local/java/bin";
	public  String user_name="isis";
	public  String sofhomedir="/";
	public  String sofjarpath="sof-resources/SOF.jar";
	public  String sofjarrunnerpath="sof-resources/SOF-RUNNER.jar";


	public static Controller getInstance(
			String host_address,
			String user_name,
			String pstring,
			String port,
			String bindir,
			String homedir,
			String javabindir,
			String sofhomedir,
			String sofjarpath,
			String sofrunnerjarpath) {
		if(instance == null) {
			instance = new Controller();

			instance.host_address=host_address;
			instance.pstring=pstring;
			instance.port=port;
			instance.bindir=bindir;
			instance.homedir=homedir;
			instance.javabindir=javabindir;
			instance.user_name=user_name;
			instance.sofjarpath=sofjarpath;
			instance.sofjarrunnerpath=sofrunnerjarpath;

			return instance.login()?instance:null;
		}
		return instance;
	}

	private  EnvironmentSession session;


	public  void exit()
	{

		//			c.printf("Disconnect from "+SOFShellClient.host+".\n");
		SofManager.disconnect(session);
		System.exit(0);

	}

	public  void license()
	{

		//			c.printf("SOF is a tool utility powerd by ISISLab, 2014.\n");

	}
	public  void help()
	{
		//	   c.printf("**************************************ISISLab******************************************************\n*");
		//		c.printf("***************************************************************************************************\n*");
		//		c.printf("*    help                 |print commands list.                                                   *\n*");
		//		//c.printf("*    usage                |print commands usage, usage <command, >.                               *\n*");
		//		c.printf("*    submit               |exec the simulation corresponding to the given id.                     *\n*");
		//		c.printf("*    createsimulation     |create new simulation execution.                                       *\n*");
		//		c.printf("*    getsimulations       |print all simulations created by the user.                             *\n*");
		//		c.printf("*    getsimulation        |print status of the simulation corresponding to the given id.          *\n*");
		//		//c.printf("    printresult          |print metrics on the results of the simulation corresponding to the given id.\n");
		//		c.printf("*    getresult            |download the results of the simulation corresponding to the given id.  *\n*");
		//		c.printf("*    list                 |show the simulation list.                                              *\n*");
		//		c.printf("*    kill                 |kill the simulation corresponding to the given id.                     *\n*");
		//		c.printf("*    makexml              |generate the xml file corresponding to the given input.                *\n*");
		//		c.printf("*    exit                 |disconnect user .                                                      *\n*");
		//		c.printf("***************************************************************************************************\n");
	}

	public  void start(String ...params)
	{
		/**
		 * Simulation s = getSimulationDatabyId(session,  username, simID);
		 */
		if(params == null || params.length < 1 )
		{
			//				c.printf("usage of submit command : submit ID [ID:intger ID of simulation execution environment]\n");

		}else{
			//int simID = Integer.parseInt(params[0])-1;

			Simulations sims = SofManager.getSimulationsData(session);

			if(sims == null){
				//					c.printf("No such simulation");

			}

			Simulation sim = null;
			for(Simulation s : sims.getSimulations())
				if(s.getId().equals(params[0])){
					sim =s;
					break;
				}
			//sim = sofManager.getSimulationDatabyId(session,  session.getUsername(), simID);

			SofManager.runAsynchronousSimulation(session,sim);
			//sofManager.runSimulation(session, session.getUsername(), simID, s.getLoop());	

		}
	}
	public void stop(String ...params){

		Simulations listSim  = SofManager.getSimulationsData(session);
		if(listSim == null){

		}

		Simulation sim = null;
		for(Simulation s : listSim.getSimulations())
			if(s.getId().equals(params[0])){
				sim =s;
				break;
			}

		if(sim.getState().equals(Simulation.RUNNING)){
			Message stop = new Message();
			stop.setId(SofManager.getMexID());
			stop.setMessage(Message.STOP_MESSAGE);
			SofManager.sendMessage(session, sim, stop);
			return;
		}
	}

	public  void createsimulation(String ... params)
	{

		String parsedParams[] = SofManager.parseParamenters(params,4);

		if(parsedParams.length == 6)
		{
			try {
				SofManager.checkParamMakeSimulationFolder(parsedParams);
			} catch (ParameterException e1) {

				System.out.print(e1.getMessage());

			}
			try {


				SofManager.makeSimulationFolder(
						session,
						parsedParams[0],//MODEL TYPE MASON - NETLOGO -GENERIC
						parsedParams[1],//SIM NAME
						parsedParams[2],//INPUT.XML PATH 
						parsedParams[3],//OUTPUT.XML PATH 
						parsedParams[4],//DESCRIPTION SIM
						parsedParams[5], //SIMULATION EXEC PATH
						"");//only generic


			} catch (Exception e) {

				System.out.print("Error in making execution environment!\n");

			}
		}else if(parsedParams.length == 7){
			try {
                
				SofManager.checkParamMakeSimulationFolder(parsedParams);
			} catch (ParameterException e1) {

				System.out.print(e1.getMessage());

			}
			try {


				SofManager.makeSimulationFolder(
						SOFShellClient.session,
						parsedParams[0],//MODEL TYPE MASON - NETLOGO -GENERIC
						parsedParams[1],//SIM NAME
						parsedParams[2],//INPUT.XML PATH 
						parsedParams[3],//OUTPUT.XML PATH 
						parsedParams[4],//DESCRIPTION SIM
						parsedParams[5],//SIMULATION EXEC PATH
						parsedParams[6]); //intepretergenericpath

			} catch (Exception e) {

				System.out.print("Error in making execution environment!\n");

			}
		}
		else{
			System.out.print("Error "+(parsedParams.length<6?"few":"much more")+" parameters.:\n");
			System.out.print("usage: MODEL[MASON-NETLOGO-GENERIC] SIM-NAME[String]INPUT.xml[String absolutely]"
									+ " Output.xml[String absolutely path] DESCRIPTION-SIM[String] SIMULATION-EXECUTABLE-MODEL[String absolutely path]\n");
		}
	}

	public  void createsimulationloop(String ... params)
	{

		String parsedParams[] = SofManager.parseParamenters(params,8);

		if(parsedParams.length == 10)
		{
			try {
				SofManager.checkParamMakeSimulationFolder(parsedParams);
			} catch (ParameterException e1) {

				System.out.print(e1.getMessage());

			}
			try {


				SofManager.makeSimulationFolderForLoop(
						session,
						parsedParams[0]/*MODEL TYPE MASON - NETLOGO -GENERIC*/,
						parsedParams[1],/*SIM NAME*/
						parsedParams[2],/*domain_pathname*/ 
						parsedParams[3],/*bashCommandForRunnableFunctionSelection */
						parsedParams[4],/*bashCommandForRunnableFunctionEvaluate*/
						parsedParams[5],/*output_description_filename*/
						parsedParams[6],/*executable_selection_function_filename*/ 
						parsedParams[7],/*executable_rating_function_filename*/
						parsedParams[8],/*description_simulation*/
						parsedParams[9],"");/*executable_simulation_filename*/

			} catch (Exception e) {

				System.out.print("Error in making execution environment!\n");
				System.out.print(e.getMessage());
				e.printStackTrace();

			}
		}else if(parsedParams.length == 11){

			try {
				SofManager.checkParamMakeSimulationFolder(parsedParams);
			} catch (ParameterException e1) {

				System.out.print(e1.getMessage());
				e1.printStackTrace();

			}
			try {



				SofManager.makeSimulationFolderForLoop(
						session,
						parsedParams[0]/*MODEL TYPE MASON - NETLOGO -GENERIC*/,
						parsedParams[1],/*SIM NAME*/
						parsedParams[2],/*domain_pathname*/ 
						parsedParams[3],/*bashCommandForRunnableFunctionSelection */
						parsedParams[4],/*bashCommandForRunnableFunctionEvaluate*/
						parsedParams[5],/*output_description_filename*/
						parsedParams[6],/*executable_selection_function_filename*/ 
						parsedParams[7],/*executable_rating_function_filename*/
						parsedParams[8],/*description_simulation*/
						parsedParams[9],/*executable_simulation_filename*/
						parsedParams[10]);//interpreter generic


			} catch (Exception e) {


				System.out.print("Error in making execution environment!\n");
				e.printStackTrace();

			}



		}else{
			System.out.print("Error "+(parsedParams.length<9?"few":"much more")+" parameters.:\n");
			System.out.print("usage: MODEL[MASON-NETLOGO-GENERIC] SIM-NAME[String] "+
									"DOMAIN.xml[String absolutely]"+"bash command[for function selection example /usr/bin/sh]"
									+ " Output.xml[String absolutely path] "+"function selection absolutly path"+
									"rating selection absolutly path"+"DESCRIPTION-SIM[String] SIMULATION-EXECUTABLE-MODEL[String absolutely path]\n");

		}
	}



	public  Simulations getsimulations(String ... params)
	{

		return SofManager.getSimulationsData(session);
	}

	public  void getsimulation(String ... params)
	{

		if(params == null || params.length < 1 )
		{
			//				c.printf("few parameters!\n Usage: getsimuation #IDSIM");

		}else{
			int simID = Integer.parseInt(params[0])-1;
			Simulations listSim  = SofManager.getSimulationsData(session);
			if(listSim == null){
				//					c.printf("No such simulation");

			}
			Simulation sim = listSim.getSimulations().get(simID);
			try {
				if(!HadoopFileSystemManager.
						ifExists(session,SofManager.fs.getHdfsUserPathSimulationByID(sim.getId())
								))
				{}

				//						c.printf("Simulation SIM"+simID+" not exists\n");


				//					c.printf(sim+"\n");

			} catch (NumberFormatException e) {

				e.printStackTrace();

			} catch (JSchException e) {
				//					c.printf("Error during resource creating\n"+e.getMessage());
				e.printStackTrace();

			} catch (IOException e) {
				//					c.printf("Error during resource creating\n"+e.getMessage());
				e.printStackTrace();

			}

		}
	}

	public  void getresult(String ... params)
	{

		if(params == null)
		{
			//				c.printf("Error few parameters!\n Usage: getresult simID [destinationDirPath]");

		}else{
			Simulations sims = SofManager.getSimulationsData(session);
			if(sims == null){
				//					c.printf("No such simulation");

			}
			Simulation sim = null;
			try{
				for(Simulation s : sims.getSimulations())
					if(s.getId().equals(params[0])){
						sim =s;
						break;
					}
			}catch(IndexOutOfBoundsException e){
				//					c.printf("No such simulation");

			}
			//if no path is specified, saves in current directory
			String path = (params.length < 2)? System.getProperty("user.dir"):params[1];
			//				c.printf("Simulation will download in: "+path);
			SofManager.downloadSimulation(session,sim.getId(),path);

		}
	}
	public  void getresultExcel(String ... params)
	{

		if(params == null)
		{
			//				c.printf("Error few parameters!\n Usage: getresult simID [destinationDirPath]");

		}else{
			Simulations sims = SofManager.getSimulationsData(session);
			if(sims == null){
				//					c.printf("No such simulation");

			}
			Simulation sim = null;
			try{
				for(Simulation s : sims.getSimulations())
					if(s.getId().equals(params[0])){
						sim =s;
						break;
					}
			}catch(IndexOutOfBoundsException e){
				//					c.printf("No such simulation");

			}
			//if no path is specified, saves in current directory
			String path = (params.length < 2)? System.getProperty("user.dir"):params[1];
			path+=File.separator+"SIM-"+sim.getId()+".xls";

			SXSSFWorkbook workbook = new SXSSFWorkbook();
			Sheet sheet = workbook.createSheet("Simulation ID "+sim.getId());
   

			int row_num=0;
			if(sim.getLoop())
			{
				List<Loop> loops=sim.getRuns().getLoops();
				Collections.sort(loops,new Comparator<Loop>() {

					@Override
					public int compare(Loop o1, Loop o2) {
						return Integer.compare(o1.getId(), o2.getId());
					}
				});
				for(Loop l: loops)
				{
					Row row_loop=sheet.createRow(++row_num);
					Cell c_loop_id=row_loop.createCell(0);
					c_loop_id.setCellValue("Loop ID "+l.getId());
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
						else {
							System.out.println("No output found.");
						}

						for (Integer pt : mapio.keySet()) {

							Row row_input_id=sheet.createRow(++row_num);
							Cell c_input_id=row_input_id.createCell(1);
							c_input_id.setCellValue("Input ID "+pt);

							Row row_input_names=sheet.createRow(++row_num);
							Row row_input_values=sheet.createRow(++row_num);

							Row row_output_id=sheet.createRow(++row_num);
							Cell c_output_id=row_output_id.createCell(1);
							c_output_id.setCellValue("Output ID "+pt);

							Row row_output_names=sheet.createRow(++row_num);
							Row row_output_values=sheet.createRow(++row_num);

							int cell_input=1,cell_output=1;
							for(Parameter p : mapio.get(pt).getI().param_element)
							{
								Cell c_input_name=row_input_names.createCell(cell_input);
								Cell c_input_value=row_input_values.createCell(cell_input);
								cell_input++;
								c_input_name.setCellValue(p.getvariable_name());
								if(p.getparam() instanceof ParameterDouble) 
									c_input_value.setCellValue(((ParameterDouble)p.getparam()).getvalue());
								else  if(p.getparam() instanceof ParameterString) 
									c_input_value.setCellValue(((ParameterString)p.getparam()).getvalue());
								else if(p.getparam() instanceof ParameterLong) 
									c_input_value.setCellValue(((ParameterLong)p.getparam()).getvalue());

							}
							if(mapio.get(pt).getO()!=null)
								for(Parameter p : mapio.get(pt).getO().output_params)
								{
									Cell c_output_name=row_output_names.createCell(cell_output);
									Cell c_output_value=row_output_values.createCell(cell_output);
									cell_output++;
									c_output_name.setCellValue(p.getvariable_name());
									if(p.getparam() instanceof ParameterDouble) 
										c_output_value.setCellValue(((ParameterDouble)p.getparam()).getvalue());
									else  if(p.getparam() instanceof ParameterString) 
										c_output_value.setCellValue(((ParameterString)p.getparam()).getvalue());
									else if(p.getparam() instanceof ParameterLong) 
										c_output_value.setCellValue(((ParameterLong)p.getparam()).getvalue());
								}
						}

					}

				}
			}


			try {
				FileOutputStream out = 
						new FileOutputStream(new File(path));
				workbook.write(out);
				out.close();
				System.out.println("Excel written successfully..");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public  void list(String ... params)
	{

		Simulations sims = SofManager.getSimulationsData(session);
		if(sims == null){
			//				c.printf("No such simulation");

		}
		for(int i=1; i<=sims.getSimulations().size(); i++){
			int simID= i-1;
			Simulation s = sims.getSimulations().get(simID);
			//				c.printf("*****************************************************************************************************************\n");
			//				c.printf("sim-id: "+i+" name: "+s.getName()+" state: "+s.getState()+" time: "+s.getCreationTime()+" hdfsId: "+s.getId()+" *\n");
			//				c.printf("*****************************************************************************************************************\n");
		}

	}
	public  void kill(int simID,String ... params)
	{

		if(params == null || params.length < 1 )
		{
			//				c.printf("Error few parameters!\n Usage: kill simID");

		}else{
			//				int simID = Integer.parseInt(params[0])-1;

			Simulations listSim  = SofManager.getSimulationsData(session);
			if(listSim == null){
				//					c.printf("No such simulation");

			}
			if(listSim.getSimulations().size()-1<simID){
				//					c.printf("Simulation not exists");

			}

			Simulation sim = listSim.getSimulations().get(simID);
			String cmd="pkill -f \""+sim.getProcessName()+"\"";

			String bash = "if "+cmd+" ; then echo 0; else echo -1; fi";

			System.err.println(bash);
			try {
				HadoopFileSystemManager.exec(session,bash);
			} catch (Exception e ) {

				e.printStackTrace();
			}



		}
	}
	
	private  boolean login()
	{
		EnvironmentSession session=null;
		boolean accessGranted=false;
		int attempts = 0;
		while (!accessGranted && attempts < 5)
		{
			try {
				//-h 127.0.0.1 -bindir /isis/hadoop-2.4.0 -homedir /isis/ -javabindir /usr/local/java/bin/
				if(bindir.endsWith("/"))
					bindir = bindir.substring(0, bindir.indexOf("/")-1);
				if(!homedir.endsWith("/")){
					homedir+="/";
				}
				if(!javabindir.endsWith("/"))
					javabindir+="/";

				if(!sofhomedir.endsWith("/"))
					sofhomedir+="/";

				SofManager.setFileSystem(bindir,System.getProperty("user.dir"), sofhomedir, homedir, javabindir ,user_name);
				if ((session=SofManager.connect(user_name, host_address, pstring, bindir,Integer.parseInt(port),
						new FileInputStream(System.getProperty("user.dir")+File.separator+"sof-resources"+File.separator+"SOF.jar"),
						new FileInputStream(System.getProperty("user.dir")+File.separator+"sof-resources"+File.separator+"SOF-RUNNER.jar")
						))!=null)
				{
					//					System.out.println("Connected. Type \"help\", \"usage <command>\" or \"license\" for more information.");
					attempts = 0;
					accessGranted = true;
					break;
				}else{
					//					System.err.println("Login Correct but there are several problems in the hadoop environment, please contact your hadoop admin.");
					System.exit(-1);
				}
			} catch (Exception e) {
				//				System.err.println("Login Error. Check your credentials and ip:port of your server and try again .. ");
				e.printStackTrace();
			}

		}
		this.session=session;
		return session!=null;

	}




}
