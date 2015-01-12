package application.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import application.Main;

public class Apriori {
	StartViewController controller;
	private ArrayList<String> candidates = new ArrayList<String>();
	private ArrayList<String> candidatesProved = new ArrayList<String>();
	private ArrayList<Result> result = new ArrayList<Result>();
	private int minsup;
	//private int numTransactions = Main.goodsAmount;
	//private HashMap<String,String> tree=
	
	public Apriori(StartViewController controller){
		this.controller=controller;
	}
	
	
	public void apriori(int k){
		minsup = k;
		int itemsetNumber = 0;
		itemsetNumber++;
		generateCandidates(itemsetNumber);
		System.out.println("for all cand " + candidates.toString());
		calculateFrequent(itemsetNumber);
		
		itemsetNumber++;
		generateCandidates(itemsetNumber);
		calculateFrequent(itemsetNumber);
		while( candidates.size() > 1 ){
				itemsetNumber++;
				 //generate the candidates
	            generateCandidates(itemsetNumber);
	
	            //determine and display frequent itemsets
	            calculateFrequent(itemsetNumber);
	            if(candidates.size()!=0)
	            {
	                System.out.println("Frequent " + itemsetNumber + "-itemsets");
	                System.out.println(candidates);
	            }
			}
		System.out.println("Ergebniss" + candidatesProved);
		System.out.println("Ergebniss 2 " + candidates);
		ArrayList<String> str=new ArrayList<String>();
		for(String s : candidatesProved ){
			String[] el = s.split(" ");
			String res="";
			for(int i=0; i< el.length; i++){
				System.out.print(Main.goodsTitle[Integer.parseInt(el[i])]+ " , ");
				res += Main.goodsTitle[Integer.parseInt(el[i])]+ " , ";
			}
			str.add(res);
			System.out.println("");
		}
		controller.aprioriResultShow(str);
		//controller.aprioriResultShow();
		associationRules();
		}
	
	private void associationRules(){
		ArrayList<String> allIds = new ArrayList<String>();		
		if(candidatesProved.size() > 0){
			for(String s:candidatesProved){
				String[] ids = s.split(" ");
				for(int i=0;i<ids.length;i++){
					if(!allIds.contains(ids[i])){
						allIds.add(ids[i]);
					}
				}
			}
		}
		controller.associationRulesValues(allIds);
		System.out.println("all ids " + allIds.toString() );
		//for(int i=0;i<allIds.size();i++){
		HashMap<String,Double> res=new HashMap<String,Double>();
		for(String id : allIds){
			double allCount = 0;
			for(String cand:candidatesProved){
				if(cand.contains(id)){
				allCount++;
				}
			}
			for(String id2: allIds){
				double k=0;
				if(id.compareTo(id2) != 0){
					for(String cand2:candidatesProved){
						if (cand2.contains(id + " " + id2)) { // &&cand2.contains(id)){
							k++;
							//System.out.println(".... "+cand2+" , " +id+" , "+id2+ "  k="+ k);
							res.put((Main.goodsTitle[Integer.parseInt(id)] + " to " + Main.goodsTitle[Integer.parseInt(id2)]), (k/allCount*100));
							//break;
						}
					}
			//if (k > 0) {
				} //else { allCount++; }
					
						//}
					
				//}
			}
			System.out.println("count " + id +" "+ allCount);
		}
		System.out.println("x " + res.toString());
		
	}
	
	private void calculateFrequent(int n) {
		//candidates2= new ArrayList<String>();
		ArrayList<String> tempCandidates = new ArrayList<String>(candidates);
		int k=0;		
		for(String candidat : tempCandidates){
			//System.out.println("1 " + candidat.toString());
			//try{
			k=0;
			String[] ids;
			
			if(candidat.length() > 1){
				ids = candidat.split(" ");
			} else{
				ids = new String[1];
				ids[0]=candidat;
			}
			//System.out.println("2 " + ids[0]);
			//while(k < minsup){
			boolean occurringItemsets=false;	
			for(Transaction tr : Main.trList){
					boolean f=true;
					//System.out.println("tr "+ tr.goods.toString() + " candidID " + ids.toString());
					for(int i=0; i < ids.length; i++){
						//System.out.println(" znach " + tr.getValueGood( Main.goodsTitle[ Integer.parseInt(ids[i])-1 ] ));
						//System.out.println(" titel " + Main.goodsTitle[ Integer.parseInt(ids[i])-1 ] );
						if( tr.getValueGood( Main.goodsTitle[ Integer.parseInt(ids[i])  ] ) != 1 ){
							f = false;
							break;
						}
					}
					if( f ){
						//System.out.println(tr.goods.toString());
						k++;
					} //else{
						//System.out.println("delete");
						//candidates.remove(candidat);
					//}
					if(k >= minsup){
						System.out.println("break " + candidat);
						occurringItemsets=true;
						//candidates2.add(candidat);
						break;
					}
					
					
				}
			if(!occurringItemsets){ 
				System.out.println("remove " + candidat);
				candidates.remove(candidat);
			}
			//}
		}
		if(candidates.size() > 0){
			candidatesProved.addAll(candidates);
		}
		System.out.println("all cand " + candidates.toString());
	}
	
	private void generateCandidates(int n)
    {
        ArrayList<String> tempCandidates = new ArrayList<String>(); //temporary candidate string vector
        String str1, str2; //strings that will be used for comparisons
        StringTokenizer st1, st2; //string tokenizers for the two itemsets being compared

        //if its the first set, candidates are just the numbers
        if(n==1)
        {
            for(int i=0; i< Main.goodsAmount; i++)
            {
                tempCandidates.add(Integer.toString(i));
            }
        }
        else if(n==2) //second itemset is just all combinations of itemset 1
        {
            //add each itemset from the previous frequent itemsets together
            for(int i=0; i < candidates.size(); i++)
            {
                st1 = new StringTokenizer(candidates.get(i));
                str1 = st1.nextToken();
                for(int j=i+1; j<candidates.size(); j++)
                {
                    st2 = new StringTokenizer(candidates.get(j));
                    str2 = st2.nextToken();
                    tempCandidates.add(str1 + " " + str2);
                }
            }
            System.out.println("2" + tempCandidates.toString());
        }
        else
        {
            //for each itemset
            for(int i=0; i<candidates.size(); i++)
            {
                //compare to the next itemset
                for(int j=i+1; j<candidates.size(); j++)
                {
                    //create the strigns
                    str1 = new String();
                    str2 = new String();
                    //create the tokenizers
                    st1 = new StringTokenizer(candidates.get(i));
                    st2 = new StringTokenizer(candidates.get(j));

                    //make a string of the first n-2 tokens of the strings
					for (int s = 0; s < n - 2; s++) {
                        str1 = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                    }

                    //if they have the same n-2 tokens, add them together
                    if(str2.compareToIgnoreCase(str1)==0)
                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
                }
            }
        }
        //clear the old candidates
        candidates.clear();
        //set the new ones
        if(!tempCandidates.isEmpty()){
        	candidates = new ArrayList<String>(tempCandidates);
        	System.out.println("Not proved erg " + candidates);
        } else{
        	System.out.println("NUULLLL");
        }
        tempCandidates.clear();
    }
}