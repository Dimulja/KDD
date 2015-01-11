package application.controllers;

import java.util.ArrayList;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceBoxBuilder;
import javafx.scene.control.ComboBox;

@SuppressWarnings("deprecation")
public class Statistic {
 final	ComboBox<String> choiceStatistic;
	

	
	
	
	public Statistic( ComboBox<String> choiceStatistic){
		choiceStatistic= new ComboBox<String>(FXCollections.observableArrayList(Main.dataTitle));
		this.choiceStatistic=choiceStatistic;
		this.choiceStatistic.getItems().addAll(FXCollections.observableArrayList(Main.dataTitle));
	
	}
	
	public ComboBox getDataForCheckBox(){
		return choiceStatistic;
	}
	
	
	public void fillTheBox(){
	choiceStatistic.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number number, Number number2) {
				// TODO Auto-generated method stub
				System.out.println(choiceStatistic.getItems().get( (int) number2));
			}

			
			
		});
	}
	
	
}
