package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private BorderPane rootLayout;
	
	private Stage primaryStage;
	//private AnchorPane startView;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		initRootLayout();
		initStartView();
		
	}
	
	
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
	
	public void initStartView(){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation((getClass().getResource("/application/views/StartView.fxml")));
			AnchorPane startView  = (AnchorPane)loader.load();
			primaryStage.setTitle("KDD Projekt WS2014/15");
			rootLayout.setCenter(startView);
			//startView.addAll(startView.getChildren());
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
