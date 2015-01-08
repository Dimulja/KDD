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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
//import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class StartViewController implements Initializable {
	
	@FXML
	private AnchorPane anchorPaneStart; 
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
	
	private ObservableList<String> goodsNames;
	private ArrayList<String> candidates=new ArrayList<String>();
	
	private boolean histShowed;
	
	
	
	public StartViewController(){
		histShowed=false;
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
	
	
	@FXML
	public void onZoom(){
		anchorPaneStart.autosize();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	@FXML
	public void aprioriButtonOnAction(ActionEvent event) {
		System.out.println("apriori : ");
		Apriori a= new Apriori();
		a.apriori();
	}

	@FXML
	public void OnActionHist(ActionEvent event) {
		
		if(!histShowed){
			histShowed=true;
			int[] sum = new int[Main.goodsAmount];
			//String[] titels =new String[Main.goodsAmount];
			
			for (int i = 0; i < Main.goodsAmount; i++) {
				//titels[i]=Main.splittedFieldsLine[i];
				for (Transaction good : Main.trList) {
					sum[i] += good.getValueGood(Main.goodsTitle[i]);
				}
				System.out.println(sum[i]);
			}
			
			goodsNames = FXCollections.observableArrayList(Main.goodsTitle);
			System.out.println(goodsNames);
			xAxis.setCategories(goodsNames); 
			//goodsNames.addListener((ListChangeListener) change -> System.out.println("Detected a change! "));
		    //goodsNames.addAll(Arrays.asList(Main.goodsTitle));
		   
		   //xAxis.autosize();
		    //int[] x = new int[3];
		    System.out.println(xAxis.getCategories().toString());
		    
		    XYChart.Series<String, Integer> series = new XYChart.Series<>();
	
		    // Create a XYChart.Data object for each month. Add it to the series.
		    for (int i = 0; i < Main.goodsAmount; i++) {
		        series.getData().add(new XYChart.Data<>(Main.goodsTitle[i], Integer.valueOf(sum[i])));
		    }
		    System.out.println(series.getData());
		    
		    if(chartHist!=null)
		    	chartHist.getData().add(series);
		    chartHist.setVisible(true);
		}
	}
	
	
	
	
}
