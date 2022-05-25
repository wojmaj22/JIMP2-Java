package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class Controller {

	// Ogarnąć wyjątki itd. sprawdzić, czy to jest OK?
	// Dodać zapisywanie na drugi przycisk, ew. pobieranie pola też

	private Graph graph = new Graph();

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
	private TextField firstVertexTextField;
	@FXML
	private TextField secondVertexTextField;

	@FXML
	private Pane drawing;

	@FXML
	protected void onSaveButtonClick(){
		String filename = "Data\\" + writeFileTextField.getText();
		if (!filename.contains(".txt")){
			filename += ".txt";
		}
		graph.writeToFile( filename);
	}

	@FXML
	protected void onGenerateButtonClick( ) { // zmienić w przypadku braku wczytanego argumentu
		String distanceRange = edgeLengthTextField.getText();
		String dimensions = graphDimensionsTextField.getText();
		int minWeight = 0;
		int maxWeight = 0;
		int xDimension = 0;
		int yDimension = 0;
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
			minWeight = Integer.parseInt(distanceRange.substring(0, tmp2));
			maxWeight = Integer.parseInt(distanceRange.substring(tmp2 + 1, length));
			minEdgeLengthLabel.setText(String.valueOf(minWeight));
			maxEdgeLengthLabel.setText(String.valueOf(maxWeight));

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

		graph.setGraph( xDimension, yDimension, maxWeight, minWeight, amountOfCuts);
		graph.generateGraph();
		graph.printGraphAdjacencyList();

		//graphDrawer.drawGraph( this.graph , drawing);
	}

	@FXML
	protected void onBfsButton(){
		if (graph.breathFirstSearch()){
			System.out.println("Graf jest spójny");
		} else {
			System.out.println("Graj nie jest spójny");
		}
	}

	@FXML
	protected void onCheckRouteButton(){
		int source = Integer.parseInt(firstVertexTextField.getText());
		int destination = Integer.parseInt(secondVertexTextField.getText());

		Integer[] precursors = Djikstra.calculatePath( graph, source);
		Double distance = 0.0;
		int currentVertex = destination;
		int tmp = 0;
		for ( Integer i: precursors) {
			System.out.print( tmp + ": " + i + " ");
			tmp++;
		}
		System.out.println(" ");
		System.out.println(" " + destination + ", " + source);

		// tego while zapisać jako funkcja
		while( currentVertex != source){
			int indeks_poprzednika = graph.vertices[currentVertex].indexOf(precursors[currentVertex]);
			distance += graph.distances[currentVertex].get(indeks_poprzednika);
			System.out.print(" " + currentVertex);
			currentVertex = precursors[currentVertex];
		}
		System.out.format("\n %f \n", distance);

	}

}