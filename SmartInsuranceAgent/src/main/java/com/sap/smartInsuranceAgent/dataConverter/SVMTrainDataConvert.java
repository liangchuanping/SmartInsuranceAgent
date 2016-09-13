package com.sap.smartInsuranceAgent.dataConverter;

public class SVMTrainDataConvert extends BaseConvert{

	
	public SVMTrainDataConvert(){
		super("dataFile/SVM/SVM-train");
	}
	
	@Override
	public String transform(String input) throws Exception {
       String[] inputs  = input.split(",");
       int length = inputs.length;
       StringBuffer output = new StringBuffer();
             
       output.append(inputs[0]+" ");
       
	   for(int i = 1; i < length; i++){
		output.append(i+":"+inputs[i]+" ");		
	   }			
	   
	   return output.toString();
	}

}
