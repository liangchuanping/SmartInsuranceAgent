package com.sap.smartInsuranceAgent.aggregate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AggregatedRecommendation {
	
       public static void recommendate() throws IOException{
    	   
    	 String ANNResultFile = "dataFile/neuralNetwork/neuralNet-result.csv";
    	 String SVMResultFile = "dataFile/SVM/SVM-result.csv";
    	 String aggregateResult = "dataFile/aggregate/aggregate-result.csv";
    	   
    	 File ANNFile = new File(ANNResultFile);
     	 FileReader ANNFileReader = new FileReader(ANNFile);
     	 BufferedReader ANNReader = new BufferedReader(ANNFileReader);
    	  
     	 File SVMFile = new File(SVMResultFile);
     	 FileReader SVMFileReader = new FileReader(SVMFile);
     	 BufferedReader SVMReader = new BufferedReader(SVMFileReader);
     	 
     	 File aggregateFile =new File(aggregateResult);
     	 FileWriter aggregateFileWriter = new FileWriter(aggregateFile);
     	 BufferedWriter aggregateWriter = new BufferedWriter(aggregateFileWriter);
     	 
     	 while(true){
     		 String ANNResultRow = ANNReader.readLine();             
             String SVMResult = SVMReader.readLine();
     		      		 
     		 if(ANNResultRow != null && SVMResult != null ){
                 String[]  ANN = ANNResultRow.split(" ");
                 String ANNResult = ANN[0];
                 String individualScore = ANN[1];
                 
     			 if(ANNResult.equals("1") && SVMResult.equals("1.0")){
     				aggregateWriter.write("1 " + individualScore);
     			 }else{
     				aggregateWriter.write("0 " + individualScore); 
     			 } 
     			aggregateWriter.newLine();
     		 }else{
     			 break;
     		 }  
     		
     	 }    
     	ANNReader.close();
     	SVMReader.close();
     	aggregateWriter.close();
     	
       }
       public static void main(String[] args) throws IOException{
    	   recommendate();
       }
       
}
