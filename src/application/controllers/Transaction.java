package application.controllers;

import java.util.HashMap;

import application.Main;

public class Transaction {

	String[] splittedFieldsLine, splittedDataLine;
	HashMap<String, String> hm;
	int fullCount=0;
	
	public Transaction(String[] splittedFieldsLine,String[] splittedDataLine, int fullCount)
	{
		this.splittedFieldsLine=splittedFieldsLine;
		this.splittedDataLine=splittedDataLine;
		this.fullCount=fullCount;
		hm= new HashMap<String, String>();
		init();
	}
	
	
	/**
	 * Inserting data in the Structure
	 * @param String[] Array of Data Strings
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private void init(){
		for(int i=0; i<fullCount;i++){
			
			
			try {
				hm.put(splittedFieldsLine[i], splittedDataLine[i]);
				
			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO File format is incorrect
				//e.printStackTrace();
				log("File is corrupted please select another File");
				Main.trList = null;
			}
		}
		System.out.println(hm.toString());
	}
	
	
	/**
	 * Get value from key
	 * @param String 
	 * @return String value
	 */
	public String getValue(String key){
		
		return hm.get(key);
	}
	
	/**
	 * Simple method for printing to stdOUT
	 * @param s String
	 */
	public static void log(String s){
		System.out.println(s);
		
	}
	
}
