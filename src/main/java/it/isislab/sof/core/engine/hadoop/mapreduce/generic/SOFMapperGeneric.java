/**
 * 
 * Copyright ISISLab, 2015 Università degli Studi di Salerno.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License. You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 * @author Michele Carillo michelecarillo@gmail.com
 * @author Flavio Serrapica flavioserrapica@gmail.com
 * @author Carmine Spagnuolo spagnuolocarmine@gmail.com
 *
 */
package it.isislab.sof.core.engine.hadoop.mapreduce.generic;




import it.isislab.sof.core.engine.hadoop.mapreduce.generic.util.SimulationGeneric;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class SOFMapperGeneric extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>/*input key type, input value type, output key type, output value type*/{


	private Configuration conf=null;
	private String SIMULATION_HOME="";
	private String SIM_PROGRAM="";
	private String SIM_INPUT_MAPPER="";
	private String SIM_OUTPUT_MAPPER="";
	private String SIM_INPUT_MAPPER_FOLDER="";
	private String CONF="";
	/*private String tmpName ="";
	
	private String TMP_INPUT_MAPPER="";
	private String TMP_OUTPUT_MAPPER="";*/



	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		try{
			
			String tmpName=Thread.currentThread().getName().hashCode()<0? ""+(Thread.currentThread().getName().hashCode()*-1):""+Thread.currentThread().getName().hashCode();
			(new File(tmpName)).mkdir();
			String inpouttemp= System.currentTimeMillis()+tmpName.hashCode()<0 ?""+(System.currentTimeMillis()+tmpName.hashCode()*-1):""+System.currentTimeMillis()+tmpName.hashCode();
					
			String TMP_INPUT_MAPPER=tmpName+File.separator+"input"+inpouttemp;
					
			String TMP_OUTPUT_MAPPER=tmpName+File.separator+"output"+inpouttemp;
			
			final File tmpInputs = new File(TMP_INPUT_MAPPER);
			File tmpOutputs = new File(TMP_OUTPUT_MAPPER);

			tmpInputs.mkdir();
			tmpOutputs.mkdir();


			final FileSystem fs = FileSystem.get(conf);
			fs.copyToLocalFile(new Path(SIM_PROGRAM), new Path(tmpName));
			fs.copyToLocalFile(new Path(CONF), new Path(tmpName));



			//final String s100=this.SIMULATION_HOME+File.separator+"description"+File.separator+"s100.xml";
			if(!SIM_INPUT_MAPPER_FOLDER.isEmpty()){

				final ArrayList<String> theInputFile = new ArrayList<>();
				String[] params = value.toString().split(";");
				for(String arg: params){
                    System.out.println(arg);
					String[] couple = arg.split(":");
					if(couple[0].equalsIgnoreCase("file")){
						theInputFile.add(couple[1]);
					}

				}
				
				
				for(String file : theInputFile)
					fs.copyToLocalFile(new Path(SIM_INPUT_MAPPER_FOLDER+"/"+file), new Path(tmpInputs.getAbsolutePath()));
				//System.out.println(s100);


			/*	Thread cccc=new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							for(String file : theInputFile)
								fs.copyToLocalFile(new Path(SIM_INPUT_MAPPER_FOLDER+"/"+file), new Path(tmpInputs.getAbsolutePath()));
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
				cccc.start();
				cccc.join();*/
			}

			//while(!(new File(tmpName+File.separator+"launcher_input"+File.separator+"s100.xml").exists())){}

			//			BufferedReader br = new BufferedReader(new FileReader(tmpName+File.separator+"launcher_input"+File.separator+"s100.xml"));
			//			String s="";
			//				while((s = br.readLine())!=null){
			//					System.out.println(s);
			//				}
			//			br.close();
			//			for(File f: (new File(tmpName).listFiles()))
			//				System.out.println(f.getAbsolutePath());

			SimulationGeneric genericsim=new SimulationGeneric();

			String SIM_PROGRAM_NAME = SIM_PROGRAM.substring(SIM_PROGRAM.lastIndexOf("/")+1, SIM_PROGRAM.length());
			String CONFNAME=CONF.substring(CONF.lastIndexOf("/")+1, CONF.length());
			conf.set("simulation.mapper.conf.path", tmpName+File.separator+CONFNAME);
			genericsim.run(tmpName+File.separator+SIM_PROGRAM_NAME,tmpInputs.getAbsolutePath(),tmpOutputs.getAbsolutePath(),value.toString(),SIM_INPUT_MAPPER,SIM_OUTPUT_MAPPER,SIMULATION_HOME, output,conf);

			//da rimettere
			//System.out.println("exec "+tmpName);
			//FileUtils.deleteDirectory(new File(tmpName));
			//(new File(tmpName)).delete();

		} catch (Throwable e) {
			e.printStackTrace();
		}



	}

	@Override
	public void configure(JobConf job) {
		// TODO Auto-generated method stub
		super.configure(job);
		conf=job;
		this.SIMULATION_HOME=conf.get("simulation.home");
		this.SIM_PROGRAM=conf.get("simulation.program.simulation");
		this.SIM_INPUT_MAPPER=conf.get("simulation.executable.input");
		this.SIM_OUTPUT_MAPPER=conf.get("simulation.executable.output");
		this.CONF=conf.get("simulation.conf");
		this.SIM_INPUT_MAPPER_FOLDER = conf.get("simulation.input");
		
		/*this.tmpName = ""+Thread.currentThread().getId();
		this.TMP_INPUT_MAPPER=tmpName+File.separator+"input"+("input");
		this.TMP_OUTPUT_MAPPER=tmpName+File.separator+"output"+("output");*/
	}

}
