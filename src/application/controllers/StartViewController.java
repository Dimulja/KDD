package application.controllers;




import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;










import java.util.StringTokenizer;
import java.util.Vector;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
//import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class StartViewController implements Initializable {
	
	@FXML
	private AnchorPane anchorPaneStart; 
	@FXML
	private Button OpenFile;
	@FXML
	private ComboBox<String> box;
	@FXML
	private ComboBox<String> assozFrom;
	@FXML
	private ComboBox<String> assozTo;
	@FXML
	private Button hist;
	@FXML
	private Button pie;
	@FXML
	private Button aprioriButton;
	@FXML
	private BarChart<String, Integer> chartHist;
	@FXML
	private Label minSupLabel;
	@FXML 
	private TextField minSupText;
	@FXML
	private Button aprioriButtonCalculate; 
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis numberXis;
	

	
	private ObservableList<String> goodsNames, dataNames, allIds;
	private ArrayList<String> candidates=new ArrayList<String>();
	
	private boolean  dataLoaded;
	private String fileNameOfDataFile;
	
	
	
	
	public StartViewController(){
		
	}
	
	@FXML
	public void associationRulesValues(ArrayList<String> allIds){
		dataNames=FXCollections.observableArrayList(allIds);
        System.out.println(dataNames);
        //box = new ComboBox<String>();
        assozFrom.setItems(dataNames);
        assozTo.setItems(dataNames);
        assozFrom.setVisible(true);
        assozTo.setVisible(true);
	}
	
	@FXML
	public void OpenFilePress(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        
        if (file!=null){
        System.out.println(file);
        fileNameOfDataFile=file.getName();
        TxtReader TReader = new TxtReader();
        TReader.readTXT(file.getPath());
        hist.setDisable(false);
        dataLoaded=true;
        
        //dataNames= FXCollections.observableArrayList(Main.dataTitle);
       // Statistic st = new Statistic(choiceStatistic);
      // choiceStatistic=st.getDataForCheckBox();
      // st.fillTheBox();
        dataNames=FXCollections.observableArrayList(Main.dataTitle);
        System.out.println(dataNames);
        //box = new ComboBox<String>();
        box.setItems(dataNames);
        //System.out.println( box.getItems());
        //box.show();
        box.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                //TODO Update Histogram with chosen Statistic
            	
            	//System.out.println(ov);  
                 // System.out.println(t);  // Is the old value
                 // System.out.println(t1); //t1 is a chosen Value
                  updateHist(t1);
              }    
          });
        box.setVisible(true);
        aprioriButton.setDisable(false);
        }else{
        	System.out.println("No file was selected");
        	dataLoaded=false;
        }
	}
	
	



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataLoaded=false;
		hist.setDisable(true);
		box.setVisible(false);
		

	}
	
	@FXML
	public void aprioriButtonOnAction(ActionEvent event) {
		minSupText.setVisible(true);
		minSupLabel.setVisible(true);
		aprioriButtonCalculate.setVisible(true);
		chartHist.setVisible(false);
		
	}
	
	@FXML
	public void aprioriButtonCalculateOnAction(ActionEvent event) {
		//choiceStatistic.		
		System.out.println("apriori : ");
		Apriori a= new Apriori(this);
		try {
			double supmin = Double.parseDouble(minSupText.getText());
			supmin = Math.round(supmin / 100 * Main.trList.size());
			System.out.println("supmin " + supmin + " Main.trList.size() " + Main.trList.size());
			a.apriori((int) supmin);
		} catch (Exception e) {
			System.out.println("aprioriButtonCalculateOnAction " + e);
		}
	}

	@FXML
	public void OnActionHist(ActionEvent event) {

		
			box.setValue(null);
			loadHist();
//			numberXis.autosize();
//			numberXis.getLowerBound();
//			numberXis.setAnimated(false);
//			numberXis.scaleProperty();
			
	
	}
	
	/**
	 * Loading the Histogramm
	 */
	public void loadHist(){
		chartHist.getData().clear();
		if(dataLoaded){
			
			int[] sum = new int[Main.goodsAmount];
			//String[] titels =new String[Main.goodsAmount];
			
			for (int i = 0; i < Main.goodsAmount; i++) {
				//titels[i]=Main.splittedFieldsLine[i];
				for (Transaction good : Main.trList) {
					sum[i] += good.getValueGood(Main.goodsTitle[i]);
				}
				System.out.println(""+Main.goodsTitle[i]+" : "+sum[i]);
			}
			//observableArrayList for the Categorie Names
			goodsNames = FXCollections.observableArrayList(Main.goodsTitle);
			
			//Setting the names to the xAxis
			xAxis.setCategories(goodsNames); 
			//.setTickLabelRotation(45);
		   
		   
		    //int[] x = new int[3];
		    //System.out.println(xAxis.getCategorySpacing());
		    
		    XYChart.Series<String, Integer> series = new XYChart.Series<>();
		    series.setName("Sums of foods from "+fileNameOfDataFile+"  ");
		    
	
		    // Create a XYChart.Data object for each month. Add it to the series.
		    for (int i = 0; i < Main.goodsAmount; i++) {
		        series.getData().add(new XYChart.Data<>(Main.goodsTitle[i], Integer.valueOf(sum[i])));
		    }
		    //System.out.println(series.getData());
		    chartHist.getData().add(series);
		    
		    if(!chartHist.isVisible()){
		    	chartHist.setVisible(true);
		    }
		    	
//		    if(chartHist!=null)
//		    	chartHist.getData().add(series);
//		    chartHist.setVisible(true);
		    
		   
		   
		   
		}
	}
	
	public void updateHist(String groupName){
		if (!chartHist.isVisible()){
			chartHist.setVisible(true);
		}
		chartHist.getData().clear();
		ArrayList<String> valuesList = new ArrayList<String>();
		ArrayList<XYChart.Series<String, Integer>> series = new ArrayList<XYChart.Series<String,Integer>>();
		valuesList.add(groupName);
		
		//Getting values for chosen Group 
		
		for(Transaction currentTransaction : Main.trList){
			if (!valuesList.contains(currentTransaction.getValueData(groupName))){
				valuesList.add(currentTransaction.getValueData(groupName));
			}
		
		}
		

			for (int i=1; i<valuesList.size();i++){
				int[] sum = new int[Main.goodsAmount];
				XYChart.Series<String, Integer> serie = new XYChart.Series<>();
				serie.setName(valuesList.get(i));
				//series.add(serie);
				for(Transaction currentTransaction : Main.trList){
				
				
				//System.out.println(valuesList.get(i));
					if(currentTransaction.data.containsValue(valuesList.get(i))){
				
						for (int j=0; j<Main.goodsAmount;j++){
							sum[j]+=currentTransaction.getValueGood(Main.goodsTitle[j]);
						}
					}
			

				}
				for(int k=0;k<Main.goodsAmount; k++){
					serie.getData().add(new XYChart.Data<>(Main.goodsTitle[k], Integer.valueOf(sum[k])));
				}
			
			series.add(serie);
			}
			
			
			chartHist.getData().addAll(series);		
	}
		//System.out.println(valuesList);
}
	
	

