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
      String output = input + ",1";      
      return output;
	}	
	
	
}
