package com.sap.smartInsuranceAgent.SVM;

import java.io.IOException;

public class CustomerClassificationSVM {

   // for developer to refine parameters 
   public  void run() throws IOException{
	   String[] trainArgs = {"-g","0.1", "-c", "0.05", "-h", "0","dataFile/SVM/SVM-train"};
	   String modelFile = svm_train.main(trainArgs);
	   String[] testArgs = {"dataFile/SVM/SVM-train", modelFile, "dataFile/SVM/crossValidation-result.csv"};
	   Double accuracy = svm_predict.main(testArgs);
	   System.out.println("SVM Classification is done! The accuracy is " + accuracy);
	   String[] crossValidationTrainArgs = {"-v", "5", "-g","0.1", "-c", "0.05", "-h", "0", "dataFile/SVM/SVM-train"};
	   modelFile = svm_train.main(crossValidationTrainArgs);
	   System.out.print("Cross validation is done! The modelFile is " + modelFile);
	   
   }
   
   // train a neuralNetwork using data in "dataFile/neuralNetwork/neuralNet-train.csv".  
   public void model() throws IOException{
	   String trainingSetFileName = "dataFile/SVM/SVM-train";
	   String[] trainArgs = {"-h", "0", trainingSetFileName};
	   
	   //model will be saved in dataFile/SVM/SVM-train.model. 
	   svm_train.main(trainArgs);	   
   }
 
   //predicate 
   public static void toPredicate() throws IOException{
	   String inputFile = "dataFile/SVM/SVM-input.csv";
	   String modelFile = "dataFile/SVM/SVM-train.model";
	   String resultFile = "dataFile/SVM/SVM-result.csv";
	   
	   String[] testArgs = {inputFile, modelFile, resultFile};
	   svm_predict.main(testArgs);
	   
	   
   }		
   
   
     
   public static void main(String[] args) throws IOException{	   
	   CustomerClassificationSVM c = new CustomerClassificationSVM();
	   c.run();
//	   toPredicate();
   }
}
