package application.controllers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

import application.Main;

public class copy {
	StartViewController controller;
	private ArrayList<String> candidates = new ArrayList<String>();
	private static ArrayList<String> perm = new ArrayList<String>();
	private static ArrayList<Rules> resRules=new ArrayList<>();
	private ArrayList<String> candidatesProved = new ArrayList<String>();
	private int minsup;
	//private int numTransactions = Main.goodsAmount;
	//private HashMap<String,String> tree=
	
	public copy(StartViewController controller){
		this.controller=controller;
	}
	
	
	public void apriori(int k){
		//generateSubSets();
		//Permutations perm=new Permutations();
		//perm.generatePermutations(p, 1);
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
	                //System.out.println(candidates);
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
		associationRules2();
		//test();
		
	}
	
	private static int[] splitConverter(String s,String reg){
		String[] str = s.split(reg);
		int[] res= new int[str.length];
		for(int i=0;i<str.length;i++){
			res[i] = Integer.parseInt(str[i]);
		}
		return res;
	}

	private void associationRules2(){
		resRules.clear();
		ArrayList<String> allIds = new ArrayList<String>();	
		ArrayList<Rules> rules =new ArrayList<Rules>();
		ArrayList<String> res=new ArrayList<String>();
		HashMap<String,Double> candidadesAllCount = new HashMap<String,Double>();
		if(candidatesProved.size() > 0){			
			for(String cand:candidatesProved){
				double allCount=0;
				int[] ids = splitConverter(cand," ");
				for(Transaction tr : Main.trList){
					boolean constaint=true;
					for( int i=0; i< ids.length;i++){
						if(tr.getValueGood(Main.goodsTitle[ids[i]]) == 0){
							constaint = false;
							//break;
						}
					}
					if(constaint){
						allCount++;
						//System.out.println(cand +"   "+tr.goods.toString() + " " + allCount);
					}
				}
				candidadesAllCount.put(cand, allCount);				
			}
			int k=0;
			
			for (String cand : candidatesProved) {
				if (cand.length() > 0) {
					int[] ids = splitConverter(cand," ");
					if (ids.length >= 2) {	
						perm.clear();
						permutationGenerate(ids, 0);
						test(ids);
						//System.out.println("a "+perm.size());
					}
				}
			}
			System.out.println("res.size() "+ resRules.size());
			
		}
	}
	
	private static void test(int[] ids) {	
		if (ids.length > 2) {
			ArrayList<Rules> rules =new ArrayList<Rules>();
			for(String str : perm){
				//System.out.println(str);
				int[] el=splitConverter(str," ");
				ArrayList<Integer> from=new ArrayList<Integer>();
				for(int i=0;i<el.length-1;i++){		
					Rules rule=new Rules();					
					ArrayList<Integer> to=new ArrayList<Integer>();
					from.add(el[i]);
					rule.setFrom(from);
					for(int j=i+1;j<el.length;j++){
						to.add(el[j]);
					}
					rule.setTo(to);
					rules.add(rule);
				}
			}
			
			ArrayList<Rules> rulesProved=new ArrayList<>(rules);
			ArrayList<Rules> rul=new ArrayList<>(rules);
			for(Rules r:rules){
				//System.out.println(" " + r.getFrom().toString()+" " +r.getTo());
				for(Rules r2:rul) { 
					if(!r.equals(r2) && r.getFrom().size()==r2.getFrom().size()){
						ArrayList<Integer> temp = new ArrayList<Integer>(r.getFrom());
						temp.removeAll(r2.getFrom());
						if(temp.size()==0){
							rul.remove(r2);
							rul.remove(r);
							rulesProved.remove(r2);
							break;
						}
					}
				}
			}
						
//			for(Rules r:rules){
//				System.out.println("rules" + r.getFrom().toString()+" " +r.getTo());			
//			}
			for(Rules r:rulesProved){
				System.out.println("rulesProved" + r.getFrom().toString()+" " +r.getTo());			
			}
			resRules.addAll(rulesProved);
			//System.out.println("rulesProved   Si" +rulesProved.size());	
		} else {
			//ArrayList<Rules> rules =new ArrayList<Rules>();
			for(String str : perm){
				int[] el=splitConverter(str," ");				
				ArrayList<Integer> from=new ArrayList<Integer>();				
				ArrayList<Integer> to=new ArrayList<Integer>();
				from.add(el[0]);
				Rules rule=new Rules();	
				rule.setFrom(from);
				to.add(el[1]);
				rule.setTo(to);
				//rules.add(rule);
				resRules.add(rule);
				//System.out.println("ruln  " + rule.getFrom()+ " to " + rule.getTo());
				from.clear();
				to.clear();
				/*
				from.add(el[1]);
				rule=new Rules();	
				rule.setFrom(from);
				to.add(el[0]);
				rule.setTo(to);
				//rules.add(rule);
				resRules.add(rule);
				System.out.println("ruln  " + rule.getFrom()+ " to " + rule.getTo());
				*/
			}
			
		}
	}

	private static void permutationGenerate(int[] pa, int i) {
		// System.out.println("hgf");
		if (i == pa.length - 1) {
			arraout(pa);
		} else {
			for (int j = i; j < pa.length; j++) {
				aswap(pa, i, j);
				permutationGenerate(pa, i + 1);
				aswap(pa, i, j);
			}
		}
	}

	private static void aswap(int[] pa, int i, int j) {
		int k = pa[i];
		pa[i] = pa[j];
		pa[j] = k;
	}

	private static void arraout(int[] pa) {
		String s = "";

		for (int i = 0; i < pa.length-1; i++) {
			s += Integer.toString(pa[i]) + " ";
		}
		s += pa[pa.length-1];
		//s = s.substring(0, s.length() - 1);

		perm.add(s);
		//System.out.println("arrout '" + s + "'");
	}

	 public static void generatePermutations(int[] p, int depth) {
		    int n = p.length;
		    if (depth == n) {
		      //System.out.println(Arrays.toString(p));
		     //arr.add(Arrays.toString(p));
		      return;
		    }
		    for (int i = 0; i < n; i++) {
		      if (p[i] == 0) {
		        p[i] = depth;
		        generatePermutations(p, depth + 1);
		        p[i] = 0;
		      }
		    }
		  }

		/*
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
			res.put(id,allCount);
		}
		for(String cand:candidatesProved){
			double k=0;
			for (String cand2 : candidatesProved) {
				if (cand.compareTo(cand2) != 0) {
					if (cand.contains(cand2)) { // &&cand2.contains(id)){
						k++;
						res.put((cand + " to " + cand2), (k));
					}
				}
			}
		}
		*/
		//System.out.println("x (count "+candidadesAllCount.size()+" ) " + candidadesAllCount.toString());
	

	
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
						//System.out.println("break " + candidat);
						occurringItemsets=true;
						//candidates2.add(candidat);
						break;
					}
					
					
				}
			if(!occurringItemsets){ 
				//System.out.println("remove " + candidat);
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
            System.out.println("2 " + tempCandidates.toString());
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