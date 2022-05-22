package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

	// Ogarnąć wyjątki itd. sprawdzić, czy to jest OK?
	// Dodać zapisywanie na drugi przycisk, ew. pobieranie pola też

	@FXML
	private Label minEdgeLengthLabel;
	@FXML
	private Label maxEdgeLengthLabel;
	@FXML
	private TextField edgeLengthTextField;
	@FXML
	private TextField graphDimensionsTextField;
	@FXML
	private TextField amountOfCutsTextField;
	@FXML
	private  TextField writeFileTextField;

	@FXML
	protected void onSaveButtonClick(){

	}

	@FXML
	protected void onGenerateButtonClick() { // zmienić w przypadku braku wczytanego argumentu
		String distanceRange = edgeLengthTextField.getText();
		String dimensions = graphDimensionsTextField.getText();
		int minRange = 0;
		int maxRange = 0;
		int xDimension = 0;
		int yDimension = 0;
		int amountOfCuts = 0;
		String writeFile = null;
		try { // wczytywanie wymiarów
			int tmp = dimensions.indexOf('x'); // oddzielenie wymiarów
			xDimension = Integer.valueOf(dimensions.substring(0, tmp));
			yDimension = Integer.valueOf(dimensions.substring(tmp + 1, dimensions.length()));
		} catch ( Exception ee) {
			graphDimensionsTextField.setStyle("-fx-background-color:red;");
			graphDimensionsTextField.setPromptText("Brak podanych wymiarów");
		}
		try{ // wczytywanie wag krawędzi
			int length = distanceRange.length();
			int tmp2 = distanceRange.indexOf('-'); // oddzielenie zakresów
			minRange = Integer.valueOf(distanceRange.substring(0, tmp2));
			maxRange = Integer.valueOf(distanceRange.substring(tmp2 + 1, length));
			minEdgeLengthLabel.setText(String.valueOf(minRange));
			maxEdgeLengthLabel.setText(String.valueOf(maxRange));

		} catch (Exception e) {
			edgeLengthTextField.setStyle("-fx-background-color:red;");
			edgeLengthTextField.setPromptText("Brak podanego zakresu");
		}
		try { // pobieranie ilości podzieleń grafu
			amountOfCuts = Integer.valueOf(amountOfCutsTextField.getText());
		} catch ( Exception a){
			amountOfCutsTextField.setStyle("-fx-background-color:red;");
		}
		try{ // pobieranie nazwy pliku do zapisania grafu
			writeFile = writeFileTextField.getText();
		} catch	( Exception b){
			writeFileTextField.setStyle("-fx-background-color:red;");
		}
		graphGenerator generator = new graphGenerator(minRange, maxRange, xDimension, yDimension, amountOfCuts, writeFile);
		generator.printToTerminal();
		generator.generateGraph();

	}
}