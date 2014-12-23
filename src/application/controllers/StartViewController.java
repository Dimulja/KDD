package application.controllers;



import java.io.File;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class StartViewController {
	
	
	@FXML
	private Button OpenFile;
	
	private Main Main;
	
	
	
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
        TxtReader TReader = new TxtReader(Main);
        TReader.readTXT(file.getPath());
        }else{
        	System.out.println("No file was selected");
        }
	}
	
	
	
	
	}
