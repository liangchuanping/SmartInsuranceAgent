package com.sap.smartInsuranceAgent.dataConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public abstract class BaseConvert {
	String outputFilePath = "";

	public BaseConvert(String outputFile) {
		outputFilePath = outputFile;
	}

	// Convert the data to target format.
	public void convert(String inputFilePath) throws Exception {

		File inputFile = new File(inputFilePath);
		FileReader inputFileReader = new FileReader(inputFile);
		BufferedReader inputReader = new BufferedReader(inputFileReader);

		File outputFile = new File(outputFilePath);
		FileWriter outputFileWriter = new FileWriter(outputFile);
		BufferedWriter outputWriter = new BufferedWriter(outputFileWriter);

		// Discard the first row data which is column header as default
		String dataRow = inputReader.readLine();
		while ((dataRow = inputReader.readLine()) != null) {
			String transformedDataRow = transform(dataRow);
			outputWriter.write(transformedDataRow);
			outputWriter.newLine();
		}
		inputReader.close();
		outputWriter.close();
	}

	public abstract String transform(String input) throws Exception;

}
