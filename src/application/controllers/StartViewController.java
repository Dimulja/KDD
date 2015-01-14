package application.controllers;




import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class StartViewController implements Initializable {
	
	@FXML
    private Label filterLabel;
    @FXML
    private TableColumn<?, ?> fromColumn;
    @FXML
    private TableColumn<?, ?> toColumn;
    @FXML
    private TableColumn<?, ?> confColumn;
	@FXML // fx:id="statButton"
	private Button statButton; // Value injected by FXMLLoader
	@FXML // fx:id="valueBox"
	private ComboBox<String> valueBox; // Value injected by FXMLLoader
	
	@FXML // fx:id="addToListButton"
    private Button addToListButton; // Value injected by FXMLLoader
	
	@FXML
    private ComboBox<Integer> minConf, minSupText;
	
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
	private Label minSupLabel, rulesNumber;
	@FXML
	private Button aprioriButtonCalculate; 
	@FXML
	private NumberAxis numberXis;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private TableView aprioriTable;
	@FXML
	private Button resetButton;
	//@FXML
	//private TableColumn supCol;
	
	@FXML
	private VBox paramVBox;
	
	private ArrayList<String> valuesList;
	
	private ObservableList<String> goodsNames, dataNames;
	private  ArrayList<Rules> resRules;
	private boolean  dataLoaded;
	private String fileNameOfDataFile;
	SimpleStringProperty filter;

	
	
	
	
	public StartViewController(){
		
	}
	
	 /**
	  * Shows chosen Statistic
	  * @param event
	  */
	  @FXML
	  void showStatistic(ActionEvent event) {
		  //System.out.println(box.getValue());
		  if(box.getValue()!=null){
			  xAxis.setLabel(box.getValue());
			  updateHist(box.getValue());
			  box.setValue(null);
			  aprioriTable.setVisible(false);
		  }
	    }
	/**
	 * Adding filter parameter
	 * @param event
	 */
	 @FXML
	public void addToList(ActionEvent event) {
		 if((box.getValue()!=null) &&  valueBox.getValue()!=null){
			 filter.setValue(box.getValue()+":"+valueBox.getValue());
			 filterLabel.setText(filter.getValue());
		 }
	}
	  

	//@FXML
	public void associationRulesValues(ArrayList<String> allIds){
		dataNames=FXCollections.observableArrayList(allIds);
        //System.out.println(dataNames);
        //box = new ComboBox<String>();
        assozFrom.setItems(dataNames);
        assozTo.setItems(dataNames);
        assozFrom.setVisible(true);
        assozTo.setVisible(true);
	}
	/**
	 * Loading File with satndart File chooser
	 * @param event
	 */
	@FXML
	public void OpenFilePress(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        
        if (file!=null){
        //System.out.println(file);
        fileNameOfDataFile=file.getName();
        TxtReader TReader = new TxtReader();
        TReader.readTXT(file.getPath());
        hist.setDisable(false);
        dataLoaded=true;
        paramVBox.setVisible(true);
        dataNames=FXCollections.observableArrayList(Main.dataTitle);
        box.setItems(dataNames);
        box.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                //TODO Update Histogram with chosen Statistic
            	createValueList(t1);
            	ArrayList<String> observableValuesList = new ArrayList<String>(valuesList);
            	observableValuesList.remove(t1);
            	valueBox.setItems(FXCollections.observableArrayList(observableValuesList));
            	
            	//System.out.println(ov);  
                 // System.out.println(t);  // Is the old value
                 // System.out.println(t1); //t1 is a chosen Value
            	
            	//updateHist(t1); // Do something by change;
            	
                  
              }    
          });
        box.setVisible(true);
        }else{
        	System.out.println("No file was selected");
        	dataLoaded=false;
        }
	}
	/**
	 * Initiating values
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataLoaded=false;
		hist.setDisable(true);
		box.setVisible(false);
		 ObservableList<Integer> boxValues=FXCollections.observableArrayList();
		
		numberXis.setLabel("Numbers");
		for(int i=1;i<101;i++){
			boxValues.add(i);
		}
		
		minConf.setItems(boxValues);
		minConf.setValue(20);
		minSupText.setItems(boxValues);
		minSupText.setValue(20);
		filter = new SimpleStringProperty();
		
		
		

	}
	
	/**
	 * On Press calculate Button
	 * @param event
	 */
	
	@FXML
	public void aprioriButtonCalculateOnAction(ActionEvent event) {
		if(filterLabel.getText()!=null && filterLabel.getText()!=""){
		 String[] splitted=	getFilter().split(":");
		 for(int i=0; i<Main.trList.size();i++){
			 if(!Main.trList.get(i).getValueData(splitted[0]).equals(splitted[1])){
				 Main.trList.remove(Main.trList.get(i));
				 
			 }
		 }//end for
		}else{
			
			//all Transactions
			
			Main.trList = new ArrayList<Transaction>(Main.trListAll);
		}
		
		
		Apriori a= new Apriori(this);
		try {
			double supmin = (double)minSupText.getValue();
			System.out.println("supmin " + supmin + " Main.trList.size() " + Main.trList.size());
			a.apriori(supmin);
		} catch (Exception e) {
			//System.out.println("aprioriButtonCalculateOnAction " + e);
		}
		//Adding rules to the table
		if(aprioriTable.getColumns().size()>0)
		aprioriTable.getColumns().remove(0, 3);
		ObservableList<Map> tableData = FXCollections.observableArrayList();
		TableColumn<Map, String> column = new TableColumn<>("From");
		TableColumn<Map, String> column2 = new TableColumn<>("To");
		TableColumn<Map, String> column3 = new TableColumn<>("Conf");
		column.setCellValueFactory(new MapValueFactory(0));
		column2.setCellValueFactory(new MapValueFactory(1));
		column3.setCellValueFactory(new MapValueFactory(2));
		aprioriTable.getColumns().addAll(column,column2, column3);
		//System.out.println(resRules);
		for(Rules rule: resRules){
			if(rule.getConf()>=minConf.getValue()/100){
				
			
			Map<Integer, String> dataRow = new HashMap<>();
			
			
			
			dataRow.put(0, getTitlesFromArrayList(rule.getFrom()));
			
			dataRow.put(1, getTitlesFromArrayList(rule.getTo()));
			dataRow.put(2, String.valueOf(rule.getConf()));
			//System.out.println(confColumn.toString());
			tableData.add(dataRow);
			}
		}
		
////		aprioriTable.getColumns().add(fromColumn);
		aprioriTable.setItems(tableData);
		aprioriTable.setVisible(true);
		rulesNumber.setText("Number of Rules: "+resRules.size());
		
	}

	@FXML
	public void OnActionHist(ActionEvent event) {		
			box.setValue(null);
			loadHist();
			numberXis.setTickUnit(xAxis.getTickLength());
			aprioriTable.setVisible(false);
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
				//System.out.println(""+Main.goodsTitle[i]+" : "+sum[i]);
			}
			//observableArrayList for the Categorie Names
			goodsNames = FXCollections.observableArrayList(Main.goodsTitle);
			
			//Setting the names to the xAxis
			xAxis.setCategories(goodsNames); 
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

		    
		   
		   
		   
		}
	}
	
	/**
	 * Updating the histogram with parameter
	 * @param groupName
	 */
	public void updateHist(String groupName){
		if (!chartHist.isVisible()){
			chartHist.setVisible(true);
		}
		chartHist.getData().clear();
		//valuesList = new ArrayList<String>();
		ArrayList<XYChart.Series<String, Integer>> series = new ArrayList<XYChart.Series<String,Integer>>();
		//valuesList.add(groupName);
		createValueList(groupName);
		//Getting values for chosen Group 
		

			for (int i=1; i<valuesList.size();i++){
				int[] sum = new int[Main.goodsAmount];
				XYChart.Series<String, Integer> serie = new XYChart.Series<>();
				serie.setName(valuesList.get(i));
				//series.add(serie);
				for(Transaction currentTransaction : Main.trList){
				
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
	
	/**
	 * Reseting filter
	 * @param event
	 */
	   @FXML
	    void resetList(ActionEvent event) {
		   filterLabel.setText(null);
	    }
	
		
	public void createValueList(String groupName){
		valuesList = new ArrayList<String>();
		valuesList.add(groupName);
	
		for(Transaction currentTransaction : Main.trList){
			if (!valuesList.contains(currentTransaction.getValueData(groupName))){
				valuesList.add(currentTransaction.getValueData(groupName));
			}
		
		}
	
	}
	
	/**
	 * Gets Names from rule-tuple
	 * @param partRule 
	 * @return Sting with values
	 */
	private String getTitlesFromArrayList(ArrayList<Integer> partRule){
		String s="";
		for(int i=0;i<partRule.size();i++){
			s+=" ["+Main.goodsTitle[partRule.get(i)]+"]";
		}
		return s;
	}
	
	public void setRules(ArrayList<Rules> rules){
		resRules= new ArrayList<Rules>(rules);
	}
	
	/**
	 * 
	 * @return String value of the set filter
	 */
	public String getFilter(){
		//System.out.println(filterLabel.getText());
		return filterLabel.getText();
	}
	
}

	
	
