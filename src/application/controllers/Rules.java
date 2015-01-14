package application.controllers;
import java.util.ArrayList;

public class Rules {
	ArrayList<Integer> from = new ArrayList<Integer>();
	ArrayList<Integer> to = new ArrayList<Integer>();
	
	public Rules(){
		
	}
	public Rules(ArrayList<Integer> f,ArrayList<Integer> t){
		from.addAll(f);
		to.addAll(t);
	}
	
	public void setFrom(ArrayList<Integer> f){
		from.addAll(f);
	}
	
	public void setTo(ArrayList<Integer> t){
		to.addAll(t);
	}
	
	public String allToString(){
		String s = "";
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.addAll(to);
		arr.addAll(from);
		arr.sort(null);
		System.out.println("arr "+arr.toString());
		for(int i = 0; i < arr.size()-1; i++) {
			 s += Integer.toString(arr.get(i)) + " ";
		}
		s += Integer.toString(arr.get(arr.size()-1));
		
		return s;
	}
	
	public String fromToString(){
		String s="";
		System.out.println("fromToString " + from.toString() +" "+ from.size());
		if (from.size() > 2) {
			for (int i = 0; i < from.size() - 1; i++ ) {
				s += Integer.toString(from.get(i)) + " ";
			}
			s += Integer.toString(from.get(from.size()-1));
		} else{
			s += Integer.toString(from.get(0));
		}
		System.out.println("fromToString" + s);
		return s;
	}
	
	public ArrayList<Integer> getFrom(){
		return from;
	}
	
	public ArrayList<Integer> getTo(){
		return to;
	}
}