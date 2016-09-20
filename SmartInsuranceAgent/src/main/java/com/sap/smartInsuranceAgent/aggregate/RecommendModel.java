package com.sap.smartInsuranceAgent.aggregate;

public class RecommendModel {

	private String userId;
	private double probability;
	private int predict;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public int getPredict() {
		return predict;
	}

	public void setPredict(int predict) {
		this.predict = predict;
	}
	
	@Override
	public String toString() {
		return userId + "," + predict + "," + probability;
	}

}
