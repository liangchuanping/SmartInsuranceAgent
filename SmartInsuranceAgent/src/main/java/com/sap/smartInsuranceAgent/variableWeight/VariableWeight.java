package com.sap.smartInsuranceAgent.variableWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class VariableWeight {
	
     public ArrayList<String> calculate(String inputFilePath,  ArrayList<VarRange> variableScopeList) throws IOException{
    	  int inputcount = variableScopeList.size();
       	  VariableWeight  variableWeight = new VariableWeight();
    	  ArrayList<ArrayList<Integer>> variablesCountListList = new ArrayList<ArrayList<Integer>>();
    	  ArrayList<Integer>  intervalLengthList = new  ArrayList<Integer>(); 
    	  ArrayList<ArrayList<Double>> scopeVariablesCountList = new  ArrayList<ArrayList<Double>> ();
    	  ArrayList<Double>  variancesList = new  ArrayList<Double>(); 
    	  ArrayList<String>  formatedVariancesList = new ArrayList<String>(); 
    	  
    	  int minMultiple = 0;
    	  variablesCountListList =  variableWeight.count(inputFilePath, inputcount, variableScopeList);
    	  intervalLengthList  = variableWeight.intervalLength(variableScopeList, inputcount);
    	  minMultiple = minListMultiple(intervalLengthList);
    	  scopeVariablesCountList = scopeVariablesCountList(variablesCountListList, intervalLengthList, minMultiple);
    	  variancesList = variances(scopeVariablesCountList);
    	  formatedVariancesList = format(variancesList);
    	  return formatedVariancesList;
     }	 
     
     /* count the values amounts of each variable
     *  
     */
     
     private ArrayList<ArrayList<Integer>> count(String inputFilePath, int inputcount, ArrayList<VarRange> varRangeList) throws IOException{
    	 File inputFile = new File(inputFilePath);
    	 FileReader inputFileReader = new FileReader(inputFile);
    	 BufferedReader inputReader = new BufferedReader(inputFileReader);
		 ArrayList< HashMap<String, Integer> > variablesCountMapList = new ArrayList<HashMap<String, Integer> >();
		
		 for(int i=0; i<inputcount; i++){
			 HashMap<String, Integer> variablesCountMap = new  HashMap<String, Integer>();
			 VarRange varRange = varRangeList.get(i);
			 int start = varRange.getMin();
			 int end = varRange.getMax();
			 
			 for(int j=start; j<=end; j++){
			 variablesCountMap.put(j+"", 0);			 
			 }
			 variablesCountMapList.add(variablesCountMap);
		 } 
	 
    	 while(true){
    		 String inputRow = inputReader.readLine();
    		 if(inputRow != null){
    		   String[] variables = inputRow.split(",");
    		   if(variables[0].equals("1")){ 
        		 for(int i = 0; i < inputcount; i++){
            		 int j = variablesCountMapList.get(i).get(variables[i+1]);	     		 
            		 variablesCountMapList.get(i).put(variables[i+1], ++j);
            		 }      			 
    		   }  			     			 
    		   }else{
    			 break;
    		   }	 
         }     	    	 
    	 inputReader.close();
    	 HashMap<String, Integer> countMap = new HashMap<String, Integer>();
    	 ArrayList<ArrayList<Integer>> countListList = new  ArrayList<ArrayList<Integer>>();
    	 
    	 for(int i = 0; i < variablesCountMapList.size(); i++ ){
    		 countMap = variablesCountMapList.get(i);
    		 Collection <Integer>  countCollection = countMap.values();
    		 ArrayList<Integer> countList=new ArrayList<Integer>() ; 
    		 Integer[] countArray1 =new Integer[countCollection.size()]; ;
    		 countCollection.toArray(countArray1);    		
    		 for(int p = 0; p<countCollection.size(); p++){
    			 countList.add(countArray1[p]);
    		 }
    		 countListList.add(countList);
    	 } 
    	 return countListList;
     }
     
     /*calculate the length of each variable's figure region.      
     */
     
     private ArrayList<Integer> intervalLength(ArrayList<VarRange> varRangeList, int inputcount ){
    	 ArrayList<Integer> inputAmountList = new ArrayList<Integer>();   	 
		 for(int i=0; i<inputcount; i++){			 
			 int intervalLength = varRangeList.get(i).getLength();	
			 inputAmountList.add(intervalLength);
			 }	
		 return inputAmountList;
		 }     
     
     //calculate the variance of each List.
     private ArrayList<Double> variances (ArrayList< ArrayList<Double> > variablesCountListList){
    	 ArrayList<Double> scopedvariablescountList = new  ArrayList<Double>();
    	 ArrayList<Double> varianceList = new  ArrayList<Double>();
    	 
    	 for(int i = 0; i < variablesCountListList.size(); i++){
    		 double variance = 0;
    		 double sum = 0;
    		 double average = 0;
    		 scopedvariablescountList = variablesCountListList.get(i);
 		 		 
    		 average = average(scopedvariablescountList);
    		 
    		 for(int j =0 ; j < scopedvariablescountList.size(); j++){    			 
    			 sum += Math.pow((scopedvariablescountList.get(j)-average),2);    			 		     			 
    		 }   
    		 variance = sum/scopedvariablescountList.size();
    		 varianceList.add(Math.pow(variance, 0.5));   	
    	 }      	 
    	 return varianceList;
     } 
    
     
     //calculate average value of a list
     private  Double average (  ArrayList<Double> variablesCountList){
    	 double sum = 0;
    	 for(int i = 0; i < variablesCountList.size(); i++){
    		 sum += variablesCountList.get(i);    		 
    	 }
    	 return (double) sum/variablesCountList.size();
     }
     
     //calculate minMultiple of two number
     private int minMultiple(int a, int b) {
    	 int r = a, s = a, t = b;
    	 if (a < b) {
    	 r = a;
    	 a = b;
    	 b = r;
    	 }
    	 while (r != 0) {
    	 r = a % b;
    	 a = b;
    	 b = r;
    	 }
    	 return s * t / a;
    	 }
     
     private int minListMultiple( ArrayList<Integer>  intervalLengthList){
    	int M =intervalLengthList.get(0);
    	for(int i=1; i < intervalLengthList.size(); i++) {
    		M = minMultiple(M,intervalLengthList.get(i)); 
    	}  	 
    	return M;
     }
     
     private  ArrayList<ArrayList<Double>> scopeVariablesCountList(ArrayList<ArrayList<Integer>> variablesCountListList, ArrayList<Integer>  intervalLengthList, int minMultiple){
    	ArrayList<ArrayList<Double>> scopedVariableCountListList = new ArrayList<ArrayList<Double>>();
    	ArrayList<Double>   scopedVariableCountList;
    	ArrayList<Integer>   variableCountList;
    	for(int i=0; i< variablesCountListList.size(); i++){
    		scopedVariableCountList = new ArrayList<Double>();
    		int times = minMultiple/intervalLengthList.get(i);
    		variableCountList = variablesCountListList.get(i);    		
    			for(int k = 0; k < variableCountList.size(); k++){
    				scopedVariableCountList.add(((double)variableCountList.get(k))/times);    			   			
    		} 
    		scopedVariableCountListList.add(scopedVariableCountList);
    	}   	
    	return scopedVariableCountListList;
    }
     
    private ArrayList<String> format(ArrayList<Double> variancesList){
    	DecimalFormat df = new DecimalFormat("0.0000"); 
    	ArrayList<String> formatedVariance = new ArrayList<String>();
    	double sum = 0;
    	double sum1 = 0;
    	for(int i = 0; i<variancesList.size(); i++){
    		sum += variancesList.get(i);
    	}
    	for(int i=0; i<(variancesList.size()-1); i++){
    		 Double c = variancesList.get(i)/sum;
    		 String formated = df.format(c);
    		 sum1 += Double.valueOf(formated);
    		 formatedVariance.add(formated); 		
    	}
    	 Double last = 1-sum1;
    	 String l = df.format(last);
    	 formatedVariance.add(l);
    	 return formatedVariance;
    }   
     
}
