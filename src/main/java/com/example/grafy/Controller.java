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
		String weight;
		String dimensions;
		int minWeight = 0;
		int maxWeight = 5;
		int xDimension = 10;
		int yDimension = 10;
		int amountOfCuts = 0;

		try{ // read dimensions of graph from user
			dimensions = graphDimensionsTextField.getText();
			xDimension = Integer.parseInt(dimensions.substring( 0, dimensions.indexOf("x")));
			yDimension = Integer.parseInt(dimensions.substring( dimensions.indexOf("x") + 1));
		} catch ( Exception e){
			graphDimensionsTextField.setText("10x10");
		}
		try { // read weight of edges
			weight = edgeLengthTextField.getText();
			minWeight = Integer.parseInt( weight.substring( 0, weight.indexOf("-")));
			maxWeight = Integer.parseInt( weight.substring( weight.indexOf("-") + 1));
		} catch ( Exception e){
			edgeLengthTextField.setText("0-5");
		}
		try { // read amount of cuts
			amountOfCuts = Integer.parseInt(amountOfCutsTextField.getText());
		} catch (Exception a){
			amountOfCutsTextField.setText("0");
		}
		minEdgeLengthLabel.setText(String.valueOf(minWeight));
		maxEdgeLengthLabel.setText(String.valueOf(maxWeight));

		graph.setGraph( xDimension, yDimension, maxWeight, minWeight, amountOfCuts); // setting graph properties
		graph.generateGraph(); // generating graph edges

		graph.printGraphAdjacencyList(); // use to check if graph is generated correctly

		//graphDrawer.drawGraph( this.graph , drawing);
	}

	@FXML
	protected void onBfsButton(){
		if (graph.breathFirstSearch()){
			System.out.println("Graf jest spójny");
		} else {
			System.out.println("Graf nie jest spójny");
		}
	}

	@FXML
	protected void onCheckRouteButton(){

		// to jest chwilowe, bo potem będzie wybierane z myszki
		int source = Integer.parseInt(firstVertexTextField.getText());
		int destination = Integer.parseInt(secondVertexTextField.getText());

		Djikstra djikstra = new Djikstra( source, destination, graph.getNodeAmount(), graph);

		djikstra.calculatePath( source);
		djikstra.printPrecursorNode();
		djikstra.createPath();
		System.out.println( djikstra.getDistance(destination));
		System.out.println(djikstra.getPath());
	}

}