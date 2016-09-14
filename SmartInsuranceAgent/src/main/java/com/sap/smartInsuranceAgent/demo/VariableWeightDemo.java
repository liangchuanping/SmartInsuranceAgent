package com.sap.smartInsuranceAgent.demo;

import java.io.IOException;
import java.util.ArrayList;
import com.sap.smartInsuranceAgent.variableWeight.VariableWeight;

public class VariableWeightDemo {

	  public static void main(String[] args) throws IOException{
     	 VariableWeight variableWeight = new VariableWeight();
     	  ArrayList<String> variances= new ArrayList<String>();   
     	  int [] inputScope = {1,5,1,5,1,7,1,10,1,6,1,10};        	  
     	  variances =  variableWeight.calculate("dataFile/original/datasetOr.csv", 6, inputScope); 
     	  System.out.print(variances);    	 
      }     
}
