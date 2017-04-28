package it.isislab.sof.examples.wealth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.jfree.util.Log;

import it.isislab.sof.core.engine.hadoop.sshclient.connection.FileSystemSupport;
import it.isislab.sof.core.model.parameters.xsd.domain.Domain;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomain;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainContinuous;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainDiscrete;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainListString;
import it.isislab.sof.core.model.parameters.xsd.domain.ParameterDomainListValues;
import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.sof.core.model.parameters.xsd.input.Input;
import it.isislab.sof.core.model.parameters.xsd.input.Inputs;

public class WealthSelection implements Serializable
{


	public String domainFile;
	public String inputFile;
	public String evaluateFolderName;
	public HashMap<Integer,SimulationDesign> scenarios;
	public static int SIM_MAX=200;
	public static int SIM__REPLICA_MAX=10;
	public WealthSelection(String domainFile, String inputFile, String evaluateFolderName)
	{
		this.domainFile = domainFile;
		this.inputFile = inputFile;
		this.evaluateFolderName = evaluateFolderName;

		try {
			File fscenaario=new File(FileSystemSupport.getRemoteSOFHome()+"scenarios.obj");
			if(!fscenaario.exists())
			{
				fscenaario.createNewFile();
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fscenaario));
				out.writeObject(new  HashMap<Integer,SimulationDesign>());
				out.close();
			}
			ObjectInputStream in =new ObjectInputStream(new FileInputStream(fscenaario));
			scenarios = (HashMap<Integer,SimulationDesign>) in.readObject();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void generateOutput()
	{
		File evaluateFolder = new File(this.evaluateFolderName);
		File domain = new File(this.domainFile);
		boolean firstStep = true;
		if ((evaluateFolder.exists()) && 
				(evaluateFolder.listFiles().length > 0)) {
			firstStep = false;
		}
		ArrayList<String> result = new ArrayList();
		Inputs is = new Inputs();
		ArrayList<Input> list_in = new ArrayList();

		Domain dom = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(new Class[] { Domain.class });
			Unmarshaller unmarshal = context.createUnmarshaller();
			dom = (Domain)unmarshal.unmarshal(domain);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		Input i = null;
		int todo=0;
		if (firstStep)
			for (int j = 0; j < SIM_MAX; j++)
			{
				result = generateNewInput(dom);
				i= new Input();
				i.id = (j + 1);
				i.rounds = SIM__REPLICA_MAX;
				todo+=i.rounds;
				i.param_element = new ArrayList();
				String[] splitted = new String[0];
				for (String s : result)
				{
					splitted = s.split(":");
					String name = splitted[0];
					String value = splitted[1];
					Parameter p = null;
					for (ParameterDomain toAdd : dom.param) {
						if (toAdd.getvariable_name().equalsIgnoreCase(name))
						{
							p = getParameter(toAdd, value);
							break;
						}
					}
					i.param_element.add(p);
				}
				list_in.add(i);
				scenarios.put(i.id,new SimulationDesign(i.id,i));
				//log.info("created new scenario  "+i.id);

			}
		if(!firstStep){
			readOutput(evaluateFolder);
			double mean[] = new double[SIM_MAX];
			double var[] = new double[SIM_MAX];
			int replications[] = new int[SIM_MAX];
			for(SimulationDesign s:scenarios.values()) {
				mean[s.id-1] = s.getMeanY();
				var[s.id-1]  = s.getVarianceY();				
				replications[s.id-1]=s.getNoSimulations();
			}
			int an[] = OCBAImplementation.ocba(mean, var, replications, SIM__REPLICA_MAX);

			for (int k = 0; k < an.length; k++) {
				SimulationDesign s=scenarios.get(k+1);
				s.i.rounds=an[k];
				//	log.info(s.id+" "+s.i.rounds);
				//STOP FLAVIO CRITERIA
				todo+=an[k];
				if(an[k] > 0)
					list_in.add(s.i);
			}
		}
		//save state
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(FileSystemSupport.getRemoteSOFHome()+"scenarios.obj"));
			out.writeObject(scenarios);
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(todo < SIM__REPLICA_MAX) System.exit(-1);
		is.setinput_list(list_in);
		try 
		{
			JAXBContext context = JAXBContext.newInstance(new Class[] { Inputs.class });
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
			marshal.marshal(is, System.out);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		//System.out.println(scenarios);

	}
	private void readOutput(File evaluateFolder){
		File[] evaluateFiles = evaluateFolder.listFiles();
		BufferedReader br = null;
		String[] params = new String[0];
		File[] arrayOfFile1;
		int j = (arrayOfFile1 = evaluateFiles).length;
		for (int i = 0; i < j; i++)
		{
			File f = arrayOfFile1[i];
			String line = "";
			try
			{
				br = new BufferedReader(new FileReader(f));
				line = br.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		
			if(line.isEmpty()) continue;
			params = line.split(";");

			int id = Integer.parseInt(params[0].split(":")[1]);

			String[] values=params[1].split(":")[1].split(",");
			//	System.out.println(id+"-> "+scenarios.get(id));
			//	log.info("Leggo scenario "+id+" "+scenarios.get(id));
			for (int k = 0; k < values.length; k++) {
				scenarios.get(id).add_result(Double.parseDouble(values[k]));
			}
		}

	}

	private Parameter getParameter(ParameterDomain toAdd, String value)
	{
		if ((toAdd.getparameter() instanceof ParameterDomainContinuous))
		{
			ParameterDouble p_d = new ParameterDouble();
			p_d.setvalue(Double.parseDouble(value));
			Parameter p = new Parameter();
			p.setvariable_name(toAdd.getvariable_name());
			p.setparam(p_d);
			return p;
		}
		if ((toAdd.getparameter() instanceof ParameterDomainDiscrete))
		{
			ParameterLong pl = new ParameterLong();
			pl.setvalue(Long.parseLong(value));
			Parameter p = new Parameter();
			p.setvariable_name(toAdd.getvariable_name());
			p.setparam(pl);
			return p;
		}
		if ((toAdd.getparameter() instanceof ParameterDomainListString))
		{
			ParameterString ps = new ParameterString();
			ps.setvalue(value);
			Parameter p = new Parameter();
			p.setvariable_name(toAdd.getvariable_name());
			p.setparam(ps);
			return p;
		}
		ParameterDouble p_d_v = new ParameterDouble();
		p_d_v.setvalue(Double.parseDouble(value));
		Parameter p = new Parameter();
		p.setvariable_name(toAdd.getvariable_name());
		p.setparam(p_d_v);
		return p;
	}
	/*
	private ArrayList<String> generateNewInput(Domain dom, File evaluateFolder)
	{
		ArrayList<String> input = new ArrayList();
		File[] evaluateFiles = evaluateFolder.listFiles();
		double stopCriteria = 15.0D;
		BufferedReader br = null;
		String[] params = new String[0];
		String[] couple = new String[0];
		ArrayList<Double> eval = new ArrayList();
		File[] arrayOfFile1;
		int j = (arrayOfFile1 = evaluateFiles).length;
		for (int i = 0; i < j; i++)
		{
			File f = arrayOfFile1[i];
			String line = "";
			try
			{
				br = new BufferedReader(new FileReader(f));
				line = br.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			params = line.split(";");
			String[] arrayOfString1;
			int m = (arrayOfString1 = params).length;
			for (int k = 0; k < m; k++)
			{
				String s = arrayOfString1[k];
				couple = s.split(":");
				if (couple[0].equalsIgnoreCase("giniinxed"))
				{
					double val = Double.parseDouble(couple[1]);
					eval.add(Double.valueOf(val));
				}
			}
		}
		if (((Double)Collections.max(eval)).doubleValue() > stopCriteria) {
			System.exit(-1);
		}
		for (ParameterDomain p : dom.param) {
			if ((p.getparameter() instanceof ParameterDomainContinuous))
			{
				ParameterDomainContinuous p_d_c = (ParameterDomainContinuous)p.getparameter();
				double min = p_d_c.getmin();
				double max = p_d_c.getmax();
				double increment = p_d_c.getincrement();
				Random r = new Random();
				double rndValue = min + (max - min) * r.nextDouble();
				double toReturn = rndValue + increment <= p_d_c.getmax() ? rndValue + increment : rndValue;
				input.add(p.getvariable_name() + ":" + toReturn);
			}
			else if ((p.getparameter() instanceof ParameterDomainDiscrete))
			{
				ParameterDomainDiscrete p_d_d = (ParameterDomainDiscrete)p.getparameter();
				long min = p_d_d.getmin();
				long max = p_d_d.getmax();
				long increment = p_d_d.getincrement();
				RandomDataGenerator r = new RandomDataGenerator();
				long rndValue = r.nextLong(min, max);
				long toReturn = rndValue + increment <= p_d_d.getmax() ? rndValue + increment : rndValue;
				input.add(p.getvariable_name() + ":" + toReturn);
			}
			else if ((p.getparameter() instanceof ParameterDomainListString))
			{
				ParameterDomainListString p_d_ls = (ParameterDomainListString)p.getparameter();
				Object list = p_d_ls.getlist();
				Random r = new Random();
				int index = r.nextInt(((List)list).size());
				input.add(p.getvariable_name() + ":" + (String)((List)list).get(index));
			}
			else if ((p.getparameter() instanceof ParameterDomainListValues))
			{
				ParameterDomainListValues p_d_lv = (ParameterDomainListValues)p.getparameter();
				Object list = p_d_lv.getlist();
				Random r = new Random();
				int index = r.nextInt(((List)list).size());
				input.add(p.getvariable_name() + ":" + ((List)list).get(index));
			}
		}
		return input;
	}
	 */
	private ArrayList<String> generateNewInput(Domain dom)
	{
		ArrayList<String> input = new ArrayList();
		for (ParameterDomain p : dom.param) { 
			if ((p.getparameter() instanceof ParameterDomainContinuous))
			{
				//double
				ParameterDomainContinuous p_d_c = (ParameterDomainContinuous)p.getparameter();
				double min = p_d_c.getmin();
				double max = p_d_c.getmax();
				double increment = p_d_c.getincrement();
				Random r = new Random();
			

				double toReturn=0;
				if(min == max) toReturn=min;
				else{
					double rndValue = min + (max - min) * r.nextDouble();
					 toReturn = rndValue + increment <= p_d_c.getmax() ? rndValue + increment : rndValue;
				}


				input.add(p.getvariable_name() + ":" + toReturn);
			}
			else if ((p.getparameter() instanceof ParameterDomainDiscrete))
			{
				//long
				ParameterDomainDiscrete p_d_d = (ParameterDomainDiscrete)p.getparameter();
				long min = p_d_d.getmin();
				long max = p_d_d.getmax();
				long increment = p_d_d.getincrement();
				RandomDataGenerator r = new RandomDataGenerator();
				long toReturn=0;
				if(min == max) toReturn=min;
				else{
					long rndValue = r.nextLong(min, max);
					toReturn = rndValue + increment <= p_d_d.getmax() ? rndValue + increment : rndValue;
				}

				input.add(p.getvariable_name() + ":" + toReturn);
			}
			else if ((p.getparameter() instanceof ParameterDomainListString))
			{
				ParameterDomainListString p_d_ls = (ParameterDomainListString)p.getparameter();
				List<String> list = p_d_ls.getlist();
				Random r = new Random();
				int index = r.nextInt(list.size());
				input.add(p.getvariable_name() + ":" + (String)list.get(index));
			}
			else if ((p.getparameter() instanceof ParameterDomainListValues))
			{
				ParameterDomainListValues p_d_lv = (ParameterDomainListValues)p.getparameter();
				List<Double> list = p_d_lv.getlist();
				Random r = new Random();
				int index = r.nextInt(list.size());
				input.add(p.getvariable_name() + ":" + list.get(index));
			}
		}
		return input;
	}

	public static void main(String[] args)
	{
		if (args.length < 3)
		{
			System.out.println("Usage domain.xml input.xml ratingFolderName");
			System.exit(-1);
		}
		WealthSelection s = new WealthSelection(args[0], args[1], args[2]);
		s.generateOutput();
	}
}
