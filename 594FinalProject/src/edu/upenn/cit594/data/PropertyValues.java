package edu.upenn.cit594.data;

public class PropertyValues {
	private double maketValue;
	private double totalLivableArea;
	private int counter;

	public PropertyValues(double marketValue, double totalLivableArea) {
		this.maketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		counter = 1;
	}

	/**
	 * @param maketValue the maketValue to set, totalLivableArea and counter
	 */
	public void update(double marketValue, double totalLivableArea) {
		this.maketValue += marketValue;
		this.totalLivableArea += totalLivableArea;
		counter++;
	}

	@Override
	public String toString() {
		return "PropertyValues [maketValue=" + maketValue + ", totalLivableArea=" + totalLivableArea + ", counter="
				+ counter + "]";
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

	/**
	 * @return the counter
	 */
	public double getCounter() {
		return counter;
	}

}
