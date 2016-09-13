package com.sap.smartInsuranceAgent.dataConverter;

public class ANNTrainDataConvert extends BaseConvert{
	
	public ANNTrainDataConvert(){
		super("dataFile/neuralNetwork/neuralNet-train.csv");
	}
	
	@Override
	public String transform(String input) throws Exception {
      String[] inputs = input.split(",");
      int length = inputs.length;
      StringBuffer output = new StringBuffer();
      
      // Get Attribute part
      for(int i = 1; i < length; i++){
    	  output.append(inputs[i]+",");   	  
      }
      
      //Add result
	  if(inputs[0].equals("0")){
		  output.append("0,1");	  
	  }else if(inputs[0].equals("1")){
		  output.append("1,0");
	  }else{
		  throw new Exception("Input Format Error.");
	  }	  
	  
      return output.toString();
	}	
}
