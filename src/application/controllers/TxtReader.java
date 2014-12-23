package application.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;


import java.util.ArrayList;

import application.Main;

public class TxtReader {
	
	//Main Main;
	int goodsAmount, dataAmount;
	String[] splittedFieldsLine;
	public TxtReader(){
		
		//this.Main=Main;
		
	}

	public void readTXT(String path){
		String line= "";
		String cvsSplitBy = ",";
		
		try {
			//LineNumberReader is aviable from Java 7
			LineNumberReader LnR= new LineNumberReader(new FileReader(path));
		
			readFile:
			while ((line = LnR.readLine()) != null){
			//Checking first 2 lines for the Data description
				
				 switch (LnR.getLineNumber()){
					
				case 1: 
					try {
						String trimmedLine=line.replace("Waren: ", "");
						goodsAmount=Integer.parseInt(trimmedLine);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						log("ERROR: Can't convert to int "+ e.getMessage()+". In Line: "+LnR.getLineNumber());
						break readFile;
					}
						//log("goodsAmount="+goodsAmount);
						break;
					
				case 2: 
					try {
						String trimmedLine2=line.replace("Daten: ", "");
						dataAmount=Integer.parseInt(trimmedLine2);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						log("ERROR: Can't convert to int "+ e.getMessage()+". In Line: "+LnR.getLineNumber());
						break readFile;
					}
						break;
				case 3:
						splittedFieldsLine=line.split(cvsSplitBy);
						break;
				default: 
						if(!line.isEmpty()){
						String[] data = line.split(cvsSplitBy);
						Main.trList.add(new Transaction(splittedFieldsLine, data,(goodsAmount+dataAmount)));
						//Main.trList.get(Main.trList.size()-1).init(data);
						}
						break;
			}
				
			}
		
			LnR.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		log("After adding Data the main arraylist has "+Main.trList.size()+" Elements");
		
	}
	
	
	//Simple method for printing to stdOUT
		public static void log(String s){
			System.out.println(s);
			
		}
	
		
		
	
	
}
