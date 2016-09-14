package com.sap.smartInsuranceAgent.demo;

import com.sap.smartInsuranceAgent.SVM.CustomerClassificationSVM;
import com.sap.smartInsuranceAgent.aggregate.AggregatedRecommendation;
import com.sap.smartInsuranceAgent.dataConverter.ANNInputDataConvert;
import com.sap.smartInsuranceAgent.dataConverter.ANNTrainDataConvert;
import com.sap.smartInsuranceAgent.dataConverter.SVMInputDataConvert;
import com.sap.smartInsuranceAgent.dataConverter.SVMTrainDataConvert;
import com.sap.smartInsuranceAgent.neuralNet.CustomerClassificationANN;

public class Demo {
     public static void main(String[] args) throws Exception{
    	  	 
    	 //Convert train data set to ANN format.
    	 ANNTrainDataConvert annTrainDataConvert = new ANNTrainDataConvert();
    	 annTrainDataConvert.Convert("dataFile/original/datasetOr.csv");
    	 
    	 //Convert train data set to SVM format.
    	 SVMTrainDataConvert svmTrainDataConvert = new SVMTrainDataConvert();
    	 svmTrainDataConvert.Convert("dataFile/original/datasetOr.csv");
    	 
    	 //Convert input data set to ANN format.
    	 ANNInputDataConvert annInputDataConvert = new ANNInputDataConvert();
    	 annInputDataConvert.Convert("dataFile/original/inputdata.csv");
    	 
    	 //Convert input data set to SVM format.
    	 SVMInputDataConvert svmInputDataConvert = new SVMInputDataConvert();
    	 svmInputDataConvert.Convert("dataFile/original/inputdata.csv");
    	 
    	 
    	 
    	 
    	 //Create ANN model which stored in "dataFile/neuralNet/neuralNet.nnet".
    	 CustomerClassificationANN customerClassificationANN  = new CustomerClassificationANN();
    	 customerClassificationANN.model(6, 2);
    	 
    	 //Create SVM model which stored in "dataFile/SVM/SVM-train.model".
    	 CustomerClassificationSVM customerClassificationSVM = new CustomerClassificationSVM();
    	 customerClassificationSVM.model();
    	    	 
    	 
    	 //Use stored ANN model to predicate purchase possibility of input individuals, the input individuals data are stored in neuralNet-input.csv.
    	 CustomerClassificationANN.toPredicate(6);
    	 
    	 //Use stored SVM model to predicate purchase possibility of input individuals, the input individuals data are stored in SVM-input.csv. 
    	 CustomerClassificationSVM.toPredicate();
    	 
    	 
    	 //Aggregate the predicate result of ANN and SVM.
    	 AggregatedRecommendation.recommendate();
    	 
    	 
    	 
     }		
}
