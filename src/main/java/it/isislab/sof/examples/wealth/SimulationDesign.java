package it.isislab.sof.examples.wealth;

import java.io.Serializable;
import java.util.ArrayList;

import it.isislab.sof.core.model.parameters.xsd.input.Input;

/**
 * This class emulates simulation to test OCBA
 */
public  class SimulationDesign implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * vector of simulation results
	 */
	public ArrayList<Double> ys;
	public int id;
	public Input i;

	public double getMeanY() {		
		if (ys.size()==0) return 0.0;
		double ys_sum=0;
		for (double y:ys) ys_sum+=y;
		return ys_sum/ys.size();
	}

	public double getVarianceY() {
		if (ys.size()<=1) return 1.0;
		double mean = getMeanY();
		double temp = 0;
		for(double y:ys)
			temp += (y-mean)*(y-mean);
		return temp/(ys.size()-1);
	}

	public SimulationDesign (int id, Input i){
		this.id=id;
		ys = new ArrayList<Double>();
		this.i=i;

	}

	public double add_result(double y) {
		/** this emulates running some simulations **/

		ys.add(y);
		return y;
	}

	public int getNoSimulations () {
		return ys.size();
	}

	@Override
	public String toString() {
		return "SimulationDesign [ys=" + ys + ", id=" + id + ", i=" + i + "]";
	}
	
}
