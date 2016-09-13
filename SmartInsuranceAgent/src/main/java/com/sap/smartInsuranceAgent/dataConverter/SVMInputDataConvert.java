package com.sap.smartInsuranceAgent.dataConverter;

public class SVMInputDataConvert extends BaseConvert{

	//
	public SVMInputDataConvert(){
		super("dataFile/SVM/SVM-input.csv");
	}
	
	
	@Override
	public String transform(String input) throws Exception {
       String[] inputs  = input.split(",");
       int length = inputs.length;
       StringBuffer output = new StringBuffer();
             
       output.append("0 ");
       
	   for(int i = 1; i <= length; i++){
		output.append(i+":"+inputs[i-1]+" ");		
	   }			
	   
	   return output.toString();
	}
}
