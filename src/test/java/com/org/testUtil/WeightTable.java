package com.org.testUtil;

public class WeightTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double earthWeight =Double.parseDouble("175");
		double mass = earthWeight/Planet.EARTH.surfaceGravity();
		for (Planet p: Planet.values()){
			System.out.printf("weight on %s is %f%n",p,p.surfaceWeight(mass));
		}

	}

}
