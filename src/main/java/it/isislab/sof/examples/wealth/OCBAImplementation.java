package it.isislab.sof.examples.wealth;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class OCBAImplementation {


    /**
     * Determines the best design based on current simulation
	 * results
     * @param mean temporary array for sample mean of design i, i=0,1,...,ND-1
     */
	private static int best(double[] mean) {
		int min_index = 0;
		for (int i = 1; i < mean.length; i++) {
			if (mean[i] < mean[min_index]) {
				min_index = i;
			}
		}
		return min_index;
	}

	/**
	 * Determines the second best design based on current
	 * simulation results
	 * 
	 * @param t_s_mean[i]: temporary array for sample mean of design i, i=0,1,...,ND-1
	 * @param b current best design determined by function
	 * @return
	 */
	private static int second_best(double[] t_s_mean, int b)	{
		int i, second_index;
		if (b == 0)
			second_index = 1;
		else
			second_index = 0;
		for (i = 0; i < t_s_mean.length; i++) {
			if (t_s_mean[i] < t_s_mean[second_index] && i != b) {
				second_index = i;
			}
		}

		return second_index;
	}	
	

	/**
	 * OCBA implementation for the maximization problem 
	 * @param mean sample mean of design i, i=0,1,...,ND-1
	 * @param var sample variance of design i, i=0,1,...,ND-1
	 * @param m number of simulation replications of design i, i=0,1,...,ND-1
	 * @param M the additional simulation budget 
	 * @return additional number of simulation replications assigned to design i
	 */
	public static int[] ocba(double[] mean_, double[] var, int[] m, final int M) {

		int nd = mean_.length;
		
		int i, s, budget, remainingBudget;
		int b;

		double[] ratio = new double[nd]; /* Ni/Ns */
		double totalRatio;
		double temp = 0;
		boolean[] moreRun = new boolean[nd];
		

		double mean[] = new double[nd];
		/* MAX problem */
		for (i = 0; i < nd; i++)
			mean[i] = -mean_[i];

		b = best(mean);
		s = second_best(mean, b);
		ratio[s] = 1;

		for (i = 0; i < nd; i++) {
			if (i != s && i != b) {
				if (mean[b] == mean[i]) {
					ratio[i] = 1; //avoid division by 0
				} else {
					ratio[i] = Math.pow((mean[b] - mean[s]) / (mean[b] - mean[i]), 2) * var[i] / var[s];
				}
			} /* calculate ratio of Ni/Ns */
			if (i != b)
				temp += ratio[i] * ratio[i] / var[i];
		}
		ratio[b] = Math.sqrt(var[b] * temp);

		//all ratios
		/* calculate NB */
		budget = M;
		for (i = 0; i < nd; i++) {
			budget += m[i];
			moreRun[i] = true;
		}
		remainingBudget = budget;

		boolean moreAllocation = true;
		int[] z = new int[nd];
		double zdouble[]= new double[nd];
		while (moreAllocation) {
			moreAllocation = false;
			totalRatio = 0;
			for (i = 0; i < nd; i++) {
				if (moreRun[i])
					totalRatio += ratio[i];
			}
			
			for (i = 0; i < nd; i++) {
				
				if (moreRun[i]) {
					zdouble[i] = (remainingBudget * ratio[i] / totalRatio);
					z[i] = (int) zdouble[i];
				}
				// disable those design which have been run too much 
				if (z[i] < m[i]) {
					z[i] = m[i];
					moreRun[i] = false;
					moreAllocation = true;
				}
			}
			if (moreAllocation) {
				remainingBudget = budget;
				for (i = 0; i < nd; i++) {
					if (moreRun[i] == false)
						remainingBudget -= z[i];
				}
			}
		} 
		
		moreAllocation = true;
		int sumD = 0;
		while (sumD != M) {
			sumD = 0;
			for (i = 0; i < nd; i++) {				
				sumD += z[i]-m[i];				
			}
			if (sumD < M) {
				double bestD = -1E99;
				int bestIx = -1;
				for (i = 0; i < nd; i++) {
					if (zdouble[i]-z[i] > bestD) {
						bestD = zdouble[i]-z[i];
						bestIx = i;
					}		
				}				
				z[bestIx]++;
			} else if (sumD > M) {
				double bestD = 1E99;
				int bestIx = -1;
				for (i = 0; i < nd; i++) {
					if (zdouble[i]-z[i] < bestD) {
						bestD = zdouble[i]-z[i];
						bestIx = i;
					}					
				}
				z[bestIx]--;
			}
			
		}
		
		int d[] = new int[nd];
		for (i = 0; i < nd; i++)
			d[i] = z[i] - m[i];
		return d;
	}
	   


	
	 	 

}
