package application.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;


import application.Main;

public class TxtReader {
	
	Main Main;
	
	public TxtReader(Main Main){
		
		this.Main=Main;
		
	}

	public void readTXT(String path){
		String line= "";
		String cvsSplitBy = ";";
		
		try {
			//LineNumberReader is aviable from Java 7
			LineNumberReader LnR= new LineNumberReader(new FileReader(path));
		
		
			while ((line = LnR.readLine()) != null){
				
			
				String[] data = line.split(cvsSplitBy);
				if(!line.isEmpty()){
				System.out.println(line);
				}	
				
			}
		
			LnR.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	//Simple method for printing to stdOUT
		public static void log(String s){
			System.out.println(s);
			
		}
	
		
		
	
	
}
