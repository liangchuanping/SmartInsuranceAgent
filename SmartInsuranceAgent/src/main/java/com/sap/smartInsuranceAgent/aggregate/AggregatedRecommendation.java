package com.sap.smartInsuranceAgent.aggregate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregatedRecommendation {

	public static void recommendate() throws IOException {

		String ANNResultFile = "dataFile/neuralNetwork/neuralNet-result.csv";
		String SVMResultFile = "dataFile/SVM/SVM-result.csv";
		String inputFile = "dataFile/original/inputdata.csv";
		String aggregateResult = "dataFile/aggregate/aggregate-result.csv";

		BufferedReader ANNReader = new BufferedReader(new FileReader(ANNResultFile));
		BufferedReader SVMReader = new BufferedReader(new FileReader(SVMResultFile));
		BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));

		BufferedWriter aggregateWriter = new BufferedWriter(new FileWriter(aggregateResult));

		String ANNRowData;
		String SVMResult;
		// Discard the first line data because it is column header 
		String inputRowData = inputReader.readLine();
		List<RecommendModel> recommendModels = new ArrayList<RecommendModel>();
		while ((ANNRowData = ANNReader.readLine()) != null 
				&& (SVMResult = SVMReader.readLine()) != null
				&& (inputRowData = inputReader.readLine()) != null) {

				String[] inputData = inputRowData.split(",");
				String userId = inputData[0];
			
				String[] ANN = ANNRowData.split(",");
				String ANNResult = ANN[0];
				String probability = ANN[1];

				if (ANNResult.equals("1") && SVMResult.equals("1.0")) {
					RecommendModel recommendModel = new RecommendModel();
					recommendModel.setProbability(Double.parseDouble(probability));
					recommendModel.setPredict(1);
					recommendModel.setUserId(userId);
					
					recommendModels.add(recommendModel);
				}
		}
		Collections.sort(recommendModels, new ModelComparator());
		
		for (RecommendModel recommendModel : recommendModels) {
			aggregateWriter.write(recommendModel.toString());
			aggregateWriter.newLine();
		}
		
		ANNReader.close();
		SVMReader.close();
		inputReader.close();
		aggregateWriter.close();

	}

	public static void main(String[] args) throws IOException {
		recommendate();
	}

}
