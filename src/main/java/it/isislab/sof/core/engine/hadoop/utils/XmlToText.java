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
package it.isislab.sof.core.engine.hadoop.utils;

import it.isislab.sof.core.model.parameters.xsd.elements.Parameter;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.sof.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.sof.core.model.parameters.xsd.input.Input;
import it.isislab.sof.core.model.parameters.xsd.input.Inputs;
import it.isislab.sof.core.model.parameters.xsd.output.Output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 *
 *UTILITIES CLASS for converting file   
 */
public class XmlToText {

	/**
	 * 
	 * Converte il file xml di input, crenado un nuovo file  nel formato <nomevariabile:valore;>
	 * @return il path del file di testo con estensione .tmp
	 */
	public static String convertXmlFileToFileText(Configuration config, String xmlFilePathName) {
		FileSystem fs;
		String destinationPath = xmlFilePathName.substring(0,xmlFilePathName.lastIndexOf('/'));
		String filename = xmlFilePathName.substring(xmlFilePathName.lastIndexOf('/') + 1, xmlFilePathName.lastIndexOf('.'));

		String line="";
		try {
			fs = FileSystem.get(config);
			JAXBContext context=JAXBContext.newInstance(Inputs.class);
			Unmarshaller unmarshall=context.createUnmarshaller();
			Inputs inputs=(Inputs)unmarshall.unmarshal(fs.open(new Path(xmlFilePathName)));

			for(Input in: inputs.getinput_list()){
				line+="id:"+in.id+";";
				for(Parameter param: in.param_element){
					line+=param.getvariable_name()+":";
					if(param.getparam() instanceof ParameterDouble){
						ParameterDouble pd = (ParameterDouble)param.getparam();

						line+=""+pd.getvalue()+";";
					}
					else if(param.getparam() instanceof ParameterLong){
						ParameterLong pd = (ParameterLong)param.getparam();

						line+=""+pd.getvalue()+";";
					}
					else if(param.getparam() instanceof ParameterString){
						ParameterString pd = (ParameterString)param.getparam();

						line+=""+pd.getvalue()+";";
					}

				}
				line+='\n';
			}
			BufferedWriter br=new BufferedWriter(new OutputStreamWriter(fs.create(new Path(destinationPath+"/"+filename+".tmp"),true)));
			br.write(line);
			br.close();


		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return destinationPath+"/"+filename+".tmp";
	}



	/**
	 * Converte il file predefinito output.xml in una stringa <nomevariabile:;>da processare 

	 * @return la stringa formattata
	 */
	public static String convertOutputXmlIntoText(Configuration config, String xmlFilePathName, int idInputSimulation) {	

		FileSystem fs;

		String line="";
		try {
			fs = FileSystem.get(config);
			JAXBContext context=JAXBContext.newInstance(Output.class);
			Unmarshaller unmarshall=context.createUnmarshaller();
			Output output= (Output) unmarshall.unmarshal(fs.open(new Path(xmlFilePathName)));


			for(Parameter param: output.output_params){
				line+=param.getvariable_name()+":;";
			}			

		}


		catch (IOException e) {

			e.printStackTrace();
		} catch (JAXBException e) {

			e.printStackTrace();
		}


		return line;
	}




}
