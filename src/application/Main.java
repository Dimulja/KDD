package application;
	
import java.io.IOException;
import java.util.ArrayList;

import application.controllers.Transaction;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
/**
 * 
 * @author Polina Tagirova 2291231
 * @author Dmytro Shlyakhov 2064332
 * 
 *
 */

public class Main extends Application {
	
	//FXML Variables 
	private BorderPane rootLayout;
	private Stage primaryStage;
	
	public static ArrayList<Transaction> trList = new ArrayList<Transaction>();
	public static ArrayList<Transaction> trListAll = new ArrayList<Transaction>();
	public static int goodsAmount, dataAmount; 
	public static String[] goodsTitle;
	public static String[] dataTitle;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		initRootLayout();
		initStartView();
		
	}
	
	/**
	 * Loading root layout
	 */
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            
            
            //rootLayout = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/RootView.fxml"));
            loader.setLocation((getClass().getResource("/application/views/RootView.fxml")));
            rootLayout =(BorderPane)loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * Loading Startview
	 */
	public void initStartView(){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation((getClass().getResource("/application/views/StartView.fxml")));
			AnchorPane startView  = (AnchorPane)loader.load();
			primaryStage.setTitle("KDD Projekt WS2014/15");
			startView.setScaleShape(true);
			rootLayout.setCenter(startView);
			//startView.addAll(startView.getChildren());
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void menuClose(){
		Platform.exit();
	}






	
	
	
}
