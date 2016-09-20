package com.sap.smartInsuranceAgent.demo;

import java.io.IOException;
import java.util.ArrayList;

import com.sap.smartInsuranceAgent.variableWeight.VarRange;
import com.sap.smartInsuranceAgent.variableWeight.VariableWeight;

public class VariableWeightDemo {

	  public static void main(String[] args) throws Exception{
     	 VariableWeight variableWeight = new VariableWeight();
     	  ArrayList<String> variances= new ArrayList<String>();       
     	  
     	  VarRange var1 = new VarRange(1, 5);
     	  VarRange var2 = new VarRange(1, 5);
     	  VarRange var3 = new VarRange(1, 7);
       	  VarRange var4 = new VarRange(1, 10);
          VarRange var5 = new VarRange(1, 6); 
          VarRange var6 = new VarRange(1, 10);
     	  
     	  
     	  ArrayList<VarRange> varList = new ArrayList<VarRange>();
     	  varList.add(var1);
       	  varList.add(var2);
     	  varList.add(var3);
     	  varList.add(var4);
     	  varList.add(var5); 
     	  varList.add(var6);
     	  
     	  variances =  variableWeight.calculate("dataFile/original/datasetOr.csv",varList); 
     	  System.out.print(variances);  
      }     
}
