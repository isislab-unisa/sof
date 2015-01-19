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
package it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation;

import it.isislab.scud.core.engine.hadoop.sshclient.connection.ScudManager;
import it.isislab.scud.core.engine.hadoop.sshclient.connection.HadoopFileSystemManager;
import it.isislab.scud.core.engine.hadoop.sshclient.utils.environment.EnvironmentSession;
import it.isislab.scud.core.model.parameters.xsd.elements.Parameter;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterDouble;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterLong;
import it.isislab.scud.core.model.parameters.xsd.elements.ParameterString;
import it.isislab.scud.core.model.parameters.xsd.input.Input;
import it.isislab.scud.core.model.parameters.xsd.input.Inputs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
/**
 * Convert xml file into data file
 * 
 * 
 */
@Deprecated
public class InputXmlConverter {

	public static String convertXmlToData(EnvironmentSession session, String hdfs_xmlFilePathName, String tempFolderName) throws NumberFormatException, JSchException, IOException, SftpException {

		//String destinationPath = hdfs_xmlFilePathName.substring(0,hdfs_xmlFilePathName.lastIndexOf('/'));
		
		String filename = hdfs_xmlFilePathName.substring(hdfs_xmlFilePathName.lastIndexOf('/') + 1, hdfs_xmlFilePathName.lastIndexOf('.'));

		File tmp_file = null;
		Inputs inputs;
		HadoopFileSystemManager.copyFromHdfsToClient(session, hdfs_xmlFilePathName, tempFolderName);
		
		//tmp_file = new File(tempFolderName+File.separator+filename+".xml");
		tmp_file = new File(tempFolderName+"/"+filename+".xml");
		try {
			String line = "";
			JAXBContext contex = JAXBContext.newInstance(Inputs.class);
			Unmarshaller unmarshall = contex.createUnmarshaller();
			inputs = (Inputs) unmarshall.unmarshal(tmp_file);
			for(Input input : inputs.getinput_list()){
				line+="id:"+input.id+";";
				for(Parameter p : input.param_element){
					if(p.getparam() instanceof ParameterDouble){
						ParameterDouble pd = (ParameterDouble) p.getparam();
						line+=p.getvariable_name()+":"+pd.getvalue()+";";
					}else
						if(p.getparam() instanceof ParameterLong) {
							ParameterLong pd = (ParameterLong) p.getparam();
							line+=p.getvariable_name()+":"+pd.getvalue()+";";
						}else
							if(p.getparam() instanceof ParameterString){
								ParameterString pd = (ParameterString) p.getparam();
								line+=p.getvariable_name()+":"+pd.getvalue()+";";
							}
				}
				line+='\n';
			}
			//File newTmpFile = new File(tempFolderName+File.separator+filename+".tmp");
			File newTmpFile = new File(tempFolderName+"/"+filename+".data");
			FileWriter fw = new FileWriter(newTmpFile);
			BufferedWriter br=new BufferedWriter(fw);
			//System.out.println(line);
			br.write(line);
			br.close();
			
			if(HadoopFileSystemManager.copyFromClientToHdfs(session, tempFolderName+"/"+newTmpFile.getName(), tempFolderName))
				ScudManager.log.info("Copied "+tempFolderName+"/"+newTmpFile.getName()+" to "+tempFolderName);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tempFolderName+"/"+filename+".tmp";
	}

	/*public static String convertTextToXml(EnvironmentSession session, String hdfs_textFilePathName, String tempFolderName){


		String destinationPath = hdfs_textFilePathName.substring(0,hdfs_textFilePathName.lastIndexOf('/'));
		//System.out.println(destinationPath);
		String filename = hdfs_textFilePathName.substring(hdfs_textFilePathName.lastIndexOf('/') + 1, hdfs_textFilePathName.length());

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			FileReader tmp_fileReader = null;
			BufferedReader b;
			if(SshHadoopEnvTool.copyFromHdfsToLocal(session, hdfs_textFilePathName, tempFolderName))
				ConnectionSSH.log.info("Copied "+hdfs_textFilePathName+" to "+tempFolderName);
			tmp_fileReader=new FileReader(tempFolderName+File.separator+filename);
			b=new BufferedReader(tmp_fileReader);
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("inputs");
			String line;
			while((line =b.readLine())!=null){
				String[] tag = line.split("\t");
				if(!tag[0].equalsIgnoreCase("end")){
					Element input = doc.createElement("input");
					Element e = doc.createElement(tag[0]);
					e.setTextContent(tag[1]);
					input.appendChild(e);
					rootElement.appendChild(input);
				}
			}

			b.close();
			doc.appendChild(rootElement);

			File newTmpFile = new File(tempFolderName+File.separator+ConnectionSSH.OUTPUT_XML_FILENAME);
			newTmpFile.createNewFile();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(newTmpFile);
			transformer.transform(source, result);

			if(SshHadoopEnvTool.copyFromLocalToHdfs(session, tempFolderName+File.separator+newTmpFile.getName(), destinationPath))
				ConnectionSSH.log.info("Copied "+tempFolderName+File.separator+newTmpFile.getName()+" to "+destinationPath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destinationPath+"/"+filename+".xml";
	}*/
}
