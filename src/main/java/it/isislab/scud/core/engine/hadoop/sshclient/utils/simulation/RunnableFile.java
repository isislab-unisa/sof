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


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="runnable")
@XmlType(propOrder={"simulation","selection","rating","bashCommandForRunnableFunctionSelect","bashCommandForRunnableFunctionEvaluate","bashCommandForGenericSimulation"})
public class RunnableFile {
	
	private String simulation;
	private String selection;
	private String rating;
	private String bashCommandForRunnableFunctionSelect;
	private String bashCommandForRunnableFunctionEvaluate;
	private String bashCommandForGenericSimulation="";
	
	
	public RunnableFile() {}

	public RunnableFile(String simulation, String selection, String rating) {
		super();
		this.simulation = simulation;
		this.selection = selection;
		this.rating = rating;
	}

	public String getSimulation() {
		return simulation;
	}

	public void setSimulation(String simulation) {
		this.simulation = simulation;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String ratign) {
		this.rating = ratign;
	}
	
	
	
	public String getBashCommandForGenericSimulation()
	{return bashCommandForGenericSimulation;}
	
	public void setBashCommandForGenericSimulation(String bashcommandGeneric)
	{bashCommandForGenericSimulation=bashcommandGeneric;}
	
	
	
	public String getBashCommandForRunnableFunctionEvaluate() {
		return bashCommandForRunnableFunctionEvaluate;
	}

	public void setBashCommandForRunnableFunctionEvaluate(
			String bashCommandForRunnableFunctionEvaluate) {
		this.bashCommandForRunnableFunctionEvaluate = bashCommandForRunnableFunctionEvaluate;
	}

	public String getBashCommandForRunnableFunctionSelect() {
		return bashCommandForRunnableFunctionSelect;
	}

	public void setBashCommandForRunnableFunctionSelect(
			String bashCommandForRunnableFunctionSelect) {
		this.bashCommandForRunnableFunctionSelect = bashCommandForRunnableFunctionSelect;
	}
	
	@Override
	public String toString() {
		return "\t\tsimulation: "+simulation+"\n"
				+ "\t\tselection: "+selection+"\n"
				+ "\t\trating: "+rating+"\n"
				+"\t\texecution select command: "+getBashCommandForRunnableFunctionSelect()+"\n"
				+ "\t\texecution evaluate command: "+bashCommandForRunnableFunctionEvaluate;
	}
	
	

}
