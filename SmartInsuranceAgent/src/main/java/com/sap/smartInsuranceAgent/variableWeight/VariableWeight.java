package com.sap.smartInsuranceAgent.variableWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class VariableWeight {
	
     public void calculate(String inputFilePath, int inputcount, ArrayList<Integer> variableScopeList) throws IOException{
    	  VariableWeight  variableWeight = new VariableWeight();
    	  ArrayList<ArrayList<Integer>> variablesCountListList = new ArrayList<ArrayList<Integer>>();
    	  ArrayList<Integer>  intervalLengthList = new  ArrayList<Integer>(); 
    	  ArrayList<ArrayList<Double>> scopeVariablesCountList = new  ArrayList<ArrayList<Double>> ();
    	  ArrayList<Double>  variancesList = new  ArrayList<Double>(); 
    	  
    	  int minMultiple = 0;
    	  variablesCountListList =  variableWeight.count(inputFilePath, inputcount, variableScopeList);
    	  intervalLengthList  = variableWeight.intervalLength(variableScopeList, inputcount);
    	  minMultiple = minListMultiple(intervalLengthList);
    	  scopeVariablesCountList = scopeVariablesCountList(variablesCountListList, intervalLengthList, minMultiple);
    	  variancesList = variances(scopeVariablesCountList);
    	  System.out.print("0");   	  
     }	 
     
     //count the values amounts of each variable
     public ArrayList<ArrayList<Integer>> count(String inputFilePath, int inputcount, ArrayList<Integer> variableScopeList) throws IOException{
    	 File inputFile = new File(inputFilePath);
    	 FileReader inputFileReader = new FileReader(inputFile);
    	 BufferedReader inputReader = new BufferedReader(inputFileReader);
		 ArrayList< HashMap<String, Integer> > variablesCountMapList = new ArrayList<HashMap<String, Integer> >();
		
		 for(int i=0; i<inputcount; i++){
			 HashMap<String, Integer> variablesCountMap = new  HashMap<String, Integer>();
			 int startIndex = 2*i;
			 int endIndex = 2*i + 1;
			 int start = variableScopeList.get(startIndex);
			 int end = variableScopeList.get(endIndex);
			 
			 for(int j=start; j<=end; j++){
			 variablesCountMap.put(j+"", 0);			 
			 }
			 variablesCountMapList.add(variablesCountMap);
		 } 
	 
    	 while(true){
    		 String inputRow = inputReader.readLine();
    		 if(inputRow != null){
    		   String[] variables = inputRow.split(",");
    		   if(variables[inputcount].equals("1")){ 
        		 for(int i = 0; i < inputcount; i++){
            		 int j = variablesCountMapList.get(i).get(variables[i]);	     		 
            		 variablesCountMapList.get(i).put(variables[i], ++j);
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
     
     public ArrayList<Integer> intervalLength( ArrayList<Integer> variableScopeList, int inputcount ){
    	 ArrayList<Integer> inputAmountList = new ArrayList<Integer>();   	 
		 for(int i=0; i<inputcount; i++){
			 int startIndex = 2*i;
			 int endIndex = 2*i + 1;
			 int start = variableScopeList.get(startIndex);
			 int end = variableScopeList.get(endIndex);
			 int intervalLength = end - start + 1;	
			 inputAmountList.add(intervalLength);
			 }	
		 return inputAmountList;
		 }     
     
     //count
     public ArrayList<Double> variances (ArrayList< ArrayList<Double> > variablesCountListList){
    	 ArrayList<Double> scopedvariablescountList = new  ArrayList<Double>();
    	 ArrayList<Double> varianceList = new  ArrayList<Double>();
    	 
    	 for(int i = 0; i < variablesCountListList.size(); i++){
    		 double sum = 0;
    		 double average = 0;
    		 scopedvariablescountList = variablesCountListList.get(i);
 		 		 
    		 average = average(scopedvariablescountList);
    		 
    		 for(int j =0 ; j < scopedvariablescountList.size(); j++){    			 
    			 sum += Math.pow((scopedvariablescountList.get(j)-average),2);    			 		     			 
    		 }   
    		 varianceList.add(sum/scopedvariablescountList.size());   	
    	 }      	 
    	 return varianceList;
     } 
    
     
     //calculate average value of a list
     public static Double average (  ArrayList<Double> variablesCountList){
    	 double sum = 0;
    	 for(int i = 0; i < variablesCountList.size(); i++){
    		 sum += variablesCountList.get(i);    		 
    	 }
    	 return (double) sum/variablesCountList.size();
     }
     
     //calculate minMultiple of two number
     public static int minMultiple(int a, int b) {
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
     
     public int minListMultiple( ArrayList<Integer>  intervalLengthList){
    	int M =intervalLengthList.get(0);
    	for(int i=1; i < intervalLengthList.size(); i++) {
    		M = minMultiple(M,intervalLengthList.get(i)); 
    	}  	 
    	return M;
     }
     
    public  ArrayList<ArrayList<Double>> scopeVariablesCountList(ArrayList<ArrayList<Integer>> variablesCountListList, ArrayList<Integer>  intervalLengthList, int minMultiple){
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
     
         public static void main(String[] args) throws IOException{
        	 VariableWeight variableWeight = new VariableWeight();
        	  ArrayList<Integer> inputScope= new ArrayList<Integer>();
        	  ArrayList<ArrayList<Integer>> countListList = new  ArrayList<ArrayList<Integer>>();
        	  ArrayList<Integer> inputcount= new ArrayList<Integer>();       	  
        	  inputScope.add(1);
        	  inputScope.add(5);
        	  inputScope.add(1);
        	  inputScope.add(5);
        	  inputScope.add(1);
        	  inputScope.add(7);
        	  inputScope.add(1);
        	  inputScope.add(10);
        	  inputScope.add(1);
        	  inputScope.add(6);
        	  inputScope.add(1);
        	  inputScope.add(10);        	  
        	  
        	   variableWeight.calculate("dataFile/neuralNetwork/neuralNet-train.csv", 6, inputScope); 
        	  //inputcount  = variableWeight.intervalLength(inputScope, 6);         
        	 
         }      
}
