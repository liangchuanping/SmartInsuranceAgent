package com.sap.smartInsuranceAgent.aggregate;

import java.util.Comparator;

public class ModelComparator implements Comparator<RecommendModel> {

	public int compare(RecommendModel firstModel, RecommendModel secondModel) {
		return (firstModel.getProbability() - secondModel.getProbability()) >= 0 ? -1 : 1;
	}

}
