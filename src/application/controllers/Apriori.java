package application.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

import application.Main;

public class Apriori {
	StartViewController controller;
	private ArrayList<String> candidates = new ArrayList<String>();
	private static ArrayList<String> perm = new ArrayList<String>();
	private static ArrayList<Rules> resRules = new ArrayList<>();
	private ArrayList<String> candidatesProved = new ArrayList<String>();
	private HashMap<String,Double> candidatesHm = new HashMap<String,Double>();
	private int minsup;

	public Apriori(StartViewController controller) {
		this.controller = controller;
	}

	public void apriori(double k) {
		minsup = (int) Math.round(k / 100 * Main.trList.size());

		int itemsetNumber = 0;
		itemsetNumber++;
		generateCandidates(itemsetNumber);
		calculateFrequent(itemsetNumber);

		itemsetNumber++;
		generateCandidates(itemsetNumber);
		calculateFrequent(itemsetNumber);
		while (candidates.size() > 1) {
			itemsetNumber++;
			// generate the candidates
			generateCandidates(itemsetNumber);
			// determine and display frequent itemsets
			calculateFrequent(itemsetNumber);
			if (candidates.size() != 0) {
				//System.out.println("Frequent " + itemsetNumber + "-itemsets");
				// System.out.println(candidates);
			}
		}

		ArrayList<String> str = new ArrayList<String>();
		for (String s : candidatesProved) {
			String[] el = s.split(" ");
			String res = "";
			for (int i = 0; i < el.length; i++) {
				res += Main.goodsTitle[Integer.parseInt(el[i])] + " , ";
			}
			str.add(res);
		}

		associationRules2();
	}

	public static int[] splitConverter(String s, String reg) {
		String[] str = s.split(reg);
		int[] res = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			res[i] = Integer.parseInt(str[i]);
		}
		return res;
	}

	private void associationRules2() {
		resRules.clear();
		if (candidatesProved.size() > 0) {
			for (String cand : candidatesProved) {
				if (cand.length() > 0) {
					int[] ids = splitConverter(cand, " ");
					if (ids.length >= 2) {
						perm.clear();
						permutationGenerate(ids, 0);
						generateAssociationRules(ids);
						// System.out.println("a "+perm.size());
					}
				}
			}

			
			for(Rules rule : resRules){
				
				String all = rule.allToString();
				String from = rule.fromToString();
				rule.setConf(candidatesHm.get(all)/candidatesHm.get(from));
			}
		}
		controller.setRules(resRules);
	}

	private static void generateAssociationRules(int[] ids) {
		if (ids.length > 2) {
			ArrayList<Rules> rules = new ArrayList<Rules>();
			for (String str : perm) {
				int[] el = splitConverter(str, " ");
				ArrayList<Integer> from = new ArrayList<Integer>();
				for (int i = 0; i < el.length - 1; i++) {
					Rules rule = new Rules();
					ArrayList<Integer> to = new ArrayList<Integer>();
					from.add(el[i]);
					from.sort(null);
					rule.setFrom(from);
					for (int j = i + 1; j < el.length; j++) {
						to.add(el[j]);
						to.sort(null); 
					}
					rule.setTo(to);
					boolean containts=false;
					for(Rules r:resRules){
						if( r.getFrom().equals(rule.getFrom()) && r.getTo().equals(rule.getTo()) ){
							containts=true;
						} 
					}
					if(!containts){
						resRules.add(rule);
					}
				}
			}

		} else {
			// ArrayList<Rules> rules =new ArrayList<Rules>();
			for (String str : perm) {
				int[] el = splitConverter(str, " ");
				ArrayList<Integer> from = new ArrayList<Integer>();
				ArrayList<Integer> to = new ArrayList<Integer>();
				from.add(el[0]);
				Rules rule = new Rules();
				rule.setFrom(from);
				to.add(el[1]);
				rule.setTo(to);
				resRules.add(rule);
				from.clear();
				to.clear();

			}

		}
	}
	private static void permutationGenerate(int[] pa, int i) {
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
		for (int i = 0; i < pa.length - 1; i++) {
			s += Integer.toString(pa[i]) + " ";
		}
		s += pa[pa.length - 1];
		perm.add(s);
	}
	
	/*
	 * generate all Permutations
	 */
	public static void generatePermutations(int[] p, int depth) {
		int n = p.length;
		if (depth == n) {
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
	 * proved all candidates 
	 * (if frequency for candidate less as support remove this candidate)
	 */
	private void calculateFrequent(int n) {
		ArrayList<String> tempCandidates = new ArrayList<String>(candidates);
		double k = 0;
		for (String candidat : tempCandidates) {
			k = 0;
			String[] ids;

			if (candidat.length() > 1) {
				ids = candidat.split(" ");
			} else {
				ids = new String[1];
				ids[0] = candidat;
			}
			

			for (Transaction tr : Main.trList) {

							boolean f = true;
							for (int i = 0; i < ids.length; i++) {
								if (tr.getValueGood(Main.goodsTitle[Integer
										.parseInt(ids[i])]) != 1) {
									f = false;
									break;
								}
							}
							if (f) {
								k++;

							}


			}

			if (k < minsup) {
				
				candidates.remove(candidat);
			} else {
				candidatesHm.put(candidat, k);
			}
		}
		if (candidates.size() > 0) {
			candidatesProved.addAll(candidates);
		}
	}
	
	/*
	 *  generated all possible candidates for Apriori algorithm
	 *  
	 */
	private void generateCandidates(int n) {
		ArrayList<String> tempCandidates = new ArrayList<String>(); // temporary candidate string vector
		String str1, str2; // strings that will be used for comparisons
		StringTokenizer st1, st2; // string tokenizers for the two itemsets being compared

		// if its the first set, candidates are just the numbers
		if (n == 1) {
			for (int i = 0; i < Main.goodsAmount; i++) {
				tempCandidates.add(Integer.toString(i));
			}
		} else if (n == 2) // second itemset is just all combinations of itemset 1
		{
			// add each itemset from the previous frequent itemsets together
			for (int i = 0; i < candidates.size(); i++) {
				st1 = new StringTokenizer(candidates.get(i));
				str1 = st1.nextToken();
				for (int j = i + 1; j < candidates.size(); j++) {
					st2 = new StringTokenizer(candidates.get(j));
					str2 = st2.nextToken();
					tempCandidates.add(str1 + " " + str2);
				}
			}
			//System.out.println("2 " + tempCandidates.toString());
		} else {
			// for each itemset
			for (int i = 0; i < candidates.size(); i++) {
				// compare to the next itemset
				for (int j = i + 1; j < candidates.size(); j++) {
					// create the strigns
					str1 = new String();
					str2 = new String();
					// create the tokenizers
					st1 = new StringTokenizer(candidates.get(i));
					st2 = new StringTokenizer(candidates.get(j));

					// make a string of the first n-2 tokens of the strings
					for (int s = 0; s < n - 2; s++) {
						str1 = str1 + " " + st1.nextToken();
						str2 = str2 + " " + st2.nextToken();
					}

					// if they have the same n-2 tokens, add them together
					if (str2.compareToIgnoreCase(str1) == 0)
						tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2
										.nextToken()).trim());
				}
			}
		}
		// clear the old candidates
		candidates.clear();
		// set the new ones
		if (!tempCandidates.isEmpty()) {
			candidates = new ArrayList<String>(tempCandidates);
		} else {
		}
		tempCandidates.clear();
	}
}