package com.sap.smartInsuranceAgent.neuralNet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.data.norm.MaxNormalizer;
import org.neuroph.util.data.norm.Normalizer;

public class CustomerClassificationANN implements LearningEventListener {
       public int[] count = new int[3];
       public int[] correct = new int[3];
       int unpredicted = 0;
	   
	    public static void main(String[] args) throws IOException {
		 
            new CustomerClassificationANN().model(6,2);
            //toPredicate(6);
        }

	    
        // train a neuralNetwork using data in "dataFile/neuralNetwork/neuralNet-train.csv". 
	    // the neuralNetwork will be saved in "dataFile/neuralNetwork/neuralNet.nnet."
	    
        // @param  inputsCount The input count of data.
	    // @param  outputsCount The output count of data.
	    	    
		/**
		 * @param inputsCount input layer - number of features
		 * @param outputsCount output layer
		 * @throws IOException
		 */
		public  void model(int inputsCount, int outputsCount) throws IOException{
	    String trainingSetFileName = "dataFile/neuralNetwork/neuralNet-train.csv";
	    String neuralNetworkModel = "dataFile/neuralNetwork/neuralNet.nnet";
			
		 System.out.println("Creating training and test set from file...");
		
		 DataSet dataSet = DataSet.createFromFile(trainingSetFileName, inputsCount, outputsCount, ",");
		 dataSet.shuffle();
		 
		 //Normalize the data
         Normalizer normalizer = new MaxNormalizer();
         normalizer.normalize(dataSet);
         
         //Prepare for cross-validation
		 DataSet[] trainingAndTestSet = dataSet.createTrainingAndTestSubsets(80, 20);
		 DataSet trainingSet = trainingAndTestSet[0];
         DataSet testSet = trainingAndTestSet[1];

         //Create MultiLayerPerceptron neural network
		 System.out.println("Creating neural network...");				
         MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(inputsCount,16,outputsCount);
		 MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
		 learningRule.addListener(this);
		 learningRule.setMomentum(0.24);
		 learningRule.setLearningRate(0.31);		 
         learningRule.setMaxError(0.049);
         learningRule.setMaxIterations(5000);
         
         //Train the network with training set	              
         neuralNet.learn(trainingSet);
         
         //Save the neuralNet
         neuralNet.save(neuralNetworkModel);
         
         //Do cross-validate 
         System.out.println("Testing network...\n\n");
         testNeuralNetwork(neuralNet, testSet);
         System.out.println("Done.");
         System.out.println("**************************************************");		
		 }

		//Use stored ANN model to predicate purchase possibility of input individuals, the input individuals data are stored in SVM-input.csv. 		
        @SuppressWarnings("static-access")
        public static void toPredicate(int inputsCount) throws IOException{
	    String inputFile = "dataFile/neuralNetwork/neuralNet-input.csv";
	    String resultFile = "dataFile/neuralNetwork/neuralNet-result.csv";
	    String neuralNetworkModel = "dataFile/neuralNetwork/neuralNet.nnet";
	    String sep = ",";
	    int outputsCount = 1;
	    
	    File outFile  = new File(resultFile);	    	    
	    FileWriter writer = new FileWriter(outFile);
	    BufferedWriter bw = new BufferedWriter(writer);	
	    
	    DataSet dataSet = DataSet.createFromFile(inputFile, inputsCount, outputsCount, sep);
	    NeuralNetwork<MomentumBackpropagation> neuralNet = (NeuralNetwork<MomentumBackpropagation>)NeuralNetwork.createFromFile(neuralNetworkModel);	 
	 	
	 	//Normalize the data
        Normalizer normalizer = new MaxNormalizer();
        normalizer.normalize(dataSet);    
        
	    for(DataSetRow  dataSetRow : dataSet.getRows()){
	    	neuralNet.setInput(dataSetRow.getInput());
	    	neuralNet.calculate();
	    	double[] results = neuralNet.getOutput();	
	    	if(results[0] >  0.9){	    		
	    		bw.write("1 " + String.valueOf(results[0]));
	    	}else{
	    		bw.write("0 " + String.valueOf(results[0]));
	    	}
	    	
	    	bw.newLine();
	    }	    
	    bw.close();  
 }		
		
		
 public void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) throws IOException {
	    File outfile  = new File("dataFile/neuralNetwork/crossValidation-result.txt");
	    FileWriter writer = new FileWriter(outfile);
	    BufferedWriter bw = new BufferedWriter(writer);
        System.out.println("**************************************************");
        System.out.println("**********************RESULT**********************");
        System.out.println("**************************************************");
        for (DataSetRow testSetRow : testSet.getRows()) {
 
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();

            //Finding network output
            double[] networkOutput = neuralNet.getOutput();
            String s = Double.toString(networkOutput[0])+  Double.toString(networkOutput[1]);
            bw.write(s);
            bw.newLine();            
            int predicted = maxOutput(networkOutput);

            //Finding actual output
            double[] networkDesiredOutput = testSetRow.getDesiredOutput();
            int ideal = maxOutput(networkDesiredOutput);

            //Colecting data for network evaluation
            keepScore(predicted, ideal);
        }
		 bw.close();

//the result==========================================================================================================
        System.out.println("Total cases: " + this.count[2] + ". ");
        System.out.println("Correctly predicted cases: " + this.correct[2] + ". ");
        System.out.println("Incorrectly predicted cases: " + (this.count[2] - this.correct[2] - unpredicted) + ". ");
        System.out.println("Unrecognized cases: " + unpredicted + ". ");
        double percentTotal = (double) this.correct[2] * 100 / (double) this.count[2];
        System.out.println("Predicted correctly: " + formatDecimalNumber(percentTotal) + "%. ");

        double percentM = (double) this.correct[0] * 100.0 / (double) this.count[0];
        System.out.println("Prediction for 'Y (Buy)' => (Correct/total): "
                + this.correct[0] + "/" + count[0] + "(" + formatDecimalNumber(percentM) + "%). ");

        double percentB = (double) this.correct[1] * 100.0 / (double) this.count[1];
        System.out.println("Prediction for 'N (Don't Buy)' => (Correct/total): "
                + this.correct[1] + "/" + count[1] + "(" + formatDecimalNumber(percentB) + "%). ");
    }
//=====================================================================================================================
		   
 
    public void handleLearningEvent(LearningEvent event) {
        BackPropagation bp = (BackPropagation) event.getSource();
        if (event.getEventType().equals(LearningEvent.Type.LEARNING_STOPPED)) {
            double error = bp.getTotalNetworkError();
            System.out.println("Training completed in " + bp.getCurrentIteration() + " iterations, ");
            System.out.println("With total error: " + formatDecimalNumber(error));
        } else {
            System.out.println("Iteration: " + bp.getCurrentIteration() + " | Network error: " + bp.getTotalNetworkError());
        }
    }

    //https://pvgd50898942a:3841/sap/bc/ui5_ui5/ui2/ushell/shells/abap/FioriLaunchpad.html
    //Metod determines the maximum output. Maximum output is network prediction for one row. 
    public static int maxOutput(double[] array) {
        double max = array[0];
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                index = i;
                max = array[i];
            }
        }
        //If maximum is less than 0.5, that prediction will not count. 
        if (max < 0.5) {
            return -1;
        }
        return index;
    }

    //Colecting data to evaluate network. 
    public void keepScore(int prediction, int ideal) {
        count[ideal]++;
        count[2]++;

        if (prediction == ideal) {
            correct[ideal]++;
            correct[2]++;
        }
        if (prediction == -1) {
            unpredicted++;
        }
    }

    //Formating decimal number to have 3 decimal places
    public String formatDecimalNumber(double number) {
        return new BigDecimal(number).setScale(4, RoundingMode.HALF_UP).toString();
    }
}