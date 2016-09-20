package com.sap.smartInsuranceAgent.variableWeight;

public class VarRange {
	 public VarRange(int min, int max) throws Exception{
		 if(max>min){
			 this.max = max;
			 this.min = min;
			 this.length = max - min +1;
		 }else{
			 throw new Exception("max < min");
		 }
	 }
     private int max;
     
     private int min;
	
     private int length ;
     
     public void setMax(int max){
    	 this.max = max;
     }
     
     public void setMin(int min){
    	 this.min = min;
     }
     
     public int getMax(){
    	 return max;
     }
     
     public int getMin(){
    	 return min;
     }
	
     public int getLength(){
    	 return length;
     }
}
