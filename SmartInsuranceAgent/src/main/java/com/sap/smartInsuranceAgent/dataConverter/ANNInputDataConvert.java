package com.sap.smartInsuranceAgent.dataConverter;

public class ANNInputDataConvert extends BaseConvert {
	
	//transform input dataRow to the format that ANN required.
	//because in nueroph-core. the data set read from file needs output,
	//our input file has no output. So, we need to add output in dataRow.
	//then we only get input when calculate. 
	public ANNInputDataConvert(){
		super("dataFile/neuralNetwork/neuralNet-input.csv");		
	}
		
	@Override
	public String transform(String input) throws Exception {
      String[] strs  = input.split(",");
      StringBuffer sb = new StringBuffer();
      for(int i = 1; i<strs.length; i++){
    	  sb.append(strs[i]+",");
      }
      
      String output = sb.toString() + "1";      
      return output;
	}	
	
	
}
