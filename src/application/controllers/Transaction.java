package application.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import application.Main;

public class Transaction {
	/*
	int goodsAmount;
	int dataAmount;
	ArrayList<String> goodsTitle;
	ArrayList<ArrayList<Integer>> goods;
	ArrayList<String> dataTitle;
	ArrayList<ArrayList<String>> data;
	*/	
	//String[] splittedFieldsLine, splittedDataLine;
	
	HashMap<String, Integer> goods;
	HashMap<String, String> data;
	
	/*
	public Transaction()
	{
		goodsAmount=0;
		dataAmount=0;
		goodsTitle = new ArrayList<String>();
		goods = new ArrayList<ArrayList<Integer>>();
		dataTitle = new ArrayList<String>();
		data = new ArrayList<ArrayList<String>>();
	}
	
	public void setGoodAmount(Integer x){ this.goodsAmount = x; }
	
	public void setDataAmount(Integer x){ this.dataAmount = x; }
	*/
	public Transaction(String[] splittedDataLine) {	
		goods = new HashMap<String, Integer>();
		for (int i = 0; i < Main.goodsAmount; i++) {
			try {
				goods.put(Main.goodsTitle[i], Integer.parseInt(splittedDataLine[i]));				
			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO File format is incorrect
				//e.printStackTrace();
				log("File is corrupted please select another File1");
				Main.trList = null;
			}
		}
		data = new HashMap<String, String>();
		//System.out.println(Main.goodsAmount);
		//System.out.println(Main.goodsAmount + Main.dataAmount);
		
		for (int i = 0 ; i < Main.dataAmount ; i++) {
			try {
				data.put(Main.dataTitle[i], splittedDataLine[ i+ Main.goodsAmount ]);				
			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO File format is incorrect
				//e.printStackTrace();
				log("File is corrupted please select another File2");
				Main.trList = null;
			}
		}
		System.out.println(data.toString());
	}
	
	/**
	 * Get value from key
	 * @param String 
	 * @return String value
	 */
	public String getValueData(String key) {
		return data.get(key);
	}
	
	public int getValueGood(String key) {
		//if(data.get(key))
		return goods.get(key);
	}
	
	public int getValueGood(String key,int val) {
		//if(data.get(key))
		//goods.get
		return goods.get(key);
	}
	
	/**
	 * Simple method for printing to stdOUT
	 * @param s String
	 */
	public static void log(String s) {
		System.out.println(s);
	}

}
