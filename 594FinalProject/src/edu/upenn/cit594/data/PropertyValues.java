package edu.upenn.cit594.data;

public class PropertyValues {
	private double maketValue;
	private double totalLivableArea;

	public PropertyValues(double marketValue, double totalLivableArea) {
		this.maketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
	}

	@Override
	public String toString() {
		return "PropertyValues [maketValue= " + maketValue + ", totalLivableArea= "  + totalLivableArea + "]";
	}

	/**
	 * @return the maketValue
	 */
	public double getMaketValue() {
		return maketValue;
	}

	/**
	 * @return the totalLivableArea
	 */
	public double getTotalLivableArea() {
		return totalLivableArea;
	}

}
