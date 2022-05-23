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
		String filename = "Data\\" + writeFileTextField.getText();
		if (!filename.contains(".txt")){
			filename += ".txt";
		}
		Graph graph = Graph.getGraph();
		graphWriter.writeToFile( graph, filename);
	}

	@FXML
	protected void onGenerateButtonClick() { // zmienić w przypadku braku wczytanego argumentu
		String distanceRange = edgeLengthTextField.getText();
		String dimensions = graphDimensionsTextField.getText();
		int minRange = 0;
		int maxRange = 0;
		int xDimension = 0;
		int yDimension = 0;
		String writeFile = null;
		try { // wczytywanie wymiarów
			int tmp = dimensions.indexOf('x'); // oddzielenie wymiarów
			xDimension = Integer.parseInt(dimensions.substring(0, tmp));
			yDimension = Integer.parseInt(dimensions.substring(tmp + 1));
		} catch ( Exception ee) {
			graphDimensionsTextField.setStyle("-fx-background-color:red;");
			graphDimensionsTextField.setPromptText("Brak podanych wymiarów");
		}
		try{ // wczytywanie wag krawędzi
			int length = distanceRange.length();
			int tmp2 = distanceRange.indexOf('-'); // oddzielenie zakresów
			minRange = Integer.parseInt(distanceRange.substring(0, tmp2));
			maxRange = Integer.parseInt(distanceRange.substring(tmp2 + 1, length));
			minEdgeLengthLabel.setText(String.valueOf(minRange));
			maxEdgeLengthLabel.setText(String.valueOf(maxRange));

		} catch (Exception e) {
			edgeLengthTextField.setStyle("-fx-background-color:red;");
			edgeLengthTextField.setPromptText("Brak podanego zakresu");
		}
		int amountOfCuts;
		try {
			amountOfCuts = Integer.parseInt(amountOfCutsTextField.getText());
		} catch (Exception a){
			amountOfCuts = 0;
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

	@FXML
	protected void onBfsButton(){
		Graph graph = Graph.getGraph();
		if (graph.BFS(graph)){
			System.out.println("Graf jest spójny");
		} else {
			System.out.println("Graj nie jest spójny");
		}
	}
}