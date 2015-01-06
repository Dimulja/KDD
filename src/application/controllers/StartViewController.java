package application.controllers;



import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;










import java.util.StringTokenizer;
import java.util.Vector;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class StartViewController implements Initializable {
	
	
	@FXML
	private Button OpenFile;
	@FXML
	private Button hist;
	@FXML
	private Button pie;
	@FXML
	private Button aprioriButton;
	@FXML
	private BarChart<String, Integer> chartHist;
	@FXML
	private CategoryAxis xAxis;
	
	private ObservableList<String> goodsNames = FXCollections.observableArrayList();
	private ArrayList<String> candidates=new ArrayList<String>();
	
	public StartViewController(){
		
	}
	

	
	@FXML
	public void OpenFilePress(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        
        if (file!=null){
        System.out.println(file);
        TxtReader TReader = new TxtReader();
        TReader.readTXT(file.getPath());
        hist.setDisable(false);
        }else{
        	System.out.println("No file was selected");
        }
	}
	
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	@FXML
	public void aprioriButtonOnAction(ActionEvent event) {
		System.out.println("apriori");
		apriori();
	}

	@FXML
	public void OnActionHist(ActionEvent event) {
		int[] sum = new int[Main.goodsAmount];
		//String[] titels =new String[Main.goodsAmount];
		
		for (int i = 0; i < Main.goodsAmount; i++) {
			//titels[i]=Main.splittedFieldsLine[i];
			for (Transaction good : Main.trList) {
				sum[i] += good.getValueGood(Main.goodsTitle[i]);
			}
			System.out.println(sum[i]);
		}
		
	    goodsNames.addAll(Arrays.asList(Main.goodsTitle));
	    xAxis.setCategories(goodsNames);        
	    //int[] x = new int[3];
	    System.out.println(xAxis.getCategories().toString());
	    
	    XYChart.Series<String, Integer> series = new XYChart.Series<>();

	    // Create a XYChart.Data object for each month. Add it to the series.
	    for (int i = 0; i < Main.goodsAmount; i++) {
	        series.getData().add(new XYChart.Data<>(Main.goodsTitle[i], Integer.valueOf(sum[i])));
	    }
	    System.out.println(series.getData().toString());
	    //chartHist.getData().add(series);
	    if(chartHist!=null)
	    	chartHist.getData().add(series);
	    chartHist.setVisible(true);
	}
	
	public void apriori(){
		
		int itemsetNumber=0;
		itemsetNumber++;
		generateCandidates(itemsetNumber);
		itemsetNumber++;
		generateCandidates(itemsetNumber);
		while(candidates.size()>1){
			itemsetNumber++;
			 //generate the candidates
            generateCandidates(itemsetNumber);

            //determine and display frequent itemsets
            //calculateFrequentItemsets(itemsetNumber);
            if(candidates.size()!=0)
            {
                System.out.println("Frequent " + itemsetNumber + "-itemsets");
                System.out.println(candidates);
            }
		}
		
	}
	
	private void generateCandidates(int n)
    {
        Vector<String> tempCandidates = new Vector<String>(); //temporary candidate string vector
        String str1, str2; //strings that will be used for comparisons
        StringTokenizer st1, st2; //string tokenizers for the two itemsets being compared

        //if its the first set, candidates are just the numbers
        if(n==1)
        {
            for(int i=1; i<= Main.goodsAmount; i++)
            {
                tempCandidates.add(Integer.toString(i));
            }
            System.out.println("1" + tempCandidates.toString());
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
                    for(int s=0; s<n-2; s++)
                    {
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
        candidates = new ArrayList<String>(tempCandidates);
        tempCandidates.clear();
    }
	
	
}
