package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;


public class Controller {

	// Ogarnąć wyjątki itd. sprawdzić, czy to jest OK?

	// a graph we will use later on;
	private Graph graph = new Graph();
	private Djikstra djikstra = new Djikstra();

	// these are well described by their names
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
	private Button resetSourceDestination;
	@FXML
	private Label graphConnected;
	@FXML
	private Label sourceNode;
	@FXML
	private Label destinationNode;
	@FXML
	private Label distance;
	@FXML
	private Pane graphDrawing;

	// Button to save graph to file
	@FXML
	protected void onSaveButtonClick(){
		String filename = "Data\\" + writeFileTextField.getText();
		if (!filename.contains(".txt")){
			filename += ".txt";
		}
		graph.writeToFile( filename);
	}

	// Button to read a graph from file
	@FXML
	protected void onReadButtonClick(){

	}

	@FXML
	protected void onResetSourceDestination() throws IOException {
		djikstra.destinationPicked = false;
		djikstra.sourcePicked = false;
		graph.DrawGraph(graphDrawing);
		destinationNode.setText("");
		sourceNode.setText("");
		distance.setText("");
	}

	@FXML
	protected void chooseNodeOnGraph(){
		graphDrawing.setOnMousePressed(mouseEvent -> {
			Circle circle = (Circle) mouseEvent.getTarget();
			if( !djikstra.sourcePicked){
				djikstra.setSource(Integer.parseInt(circle.getId()));
				circle.setFill(Color.WHITE);
				sourceNode.setText(circle.getId());
				djikstra.sourcePicked = true;
			} else if ( !djikstra.destinationPicked ){
				djikstra.setDestination(Integer.parseInt(circle.getId()));
				circle.setFill(Color.WHITE);
				destinationNode.setText(circle.getId());
				djikstra.destinationPicked = true;
			}

		});
	}

	// Button to generate a graph
	@FXML
	protected void onGenerateButtonClick( ) throws IOException { // zmienić w przypadku braku wczytanego argumentu
		String weight;
		String dimensions;
		Double minWeight = 0.0;
		Double maxWeight = 5.0;
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
			minWeight = Double.parseDouble( weight.substring( 0, weight.indexOf("-")));
			maxWeight = Double.parseDouble( weight.substring( weight.indexOf("-") + 1));
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

		djikstra.destinationPicked = false;
		djikstra.sourcePicked = false;
		graph.DrawGraph(graphDrawing);
		destinationNode.setText("");
		sourceNode.setText("");
		distance.setText("");
		graphConnected.setText("");

		graph.setGraph( xDimension, yDimension, maxWeight, minWeight, amountOfCuts); // setting graph properties
		graph.generateGraph(); // generating graph edges

		graph.printGraphAdjacencyList(); // use to check if graph is generated correctly

		graph.DrawGraph(graphDrawing);
		/*try {
			graph.drawGraph(graphDrawing);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}

	// Button to check if graph is connected
	@FXML
	protected void onBfsButton(){
		if (graph.breathFirstSearch()){
			//System.out.println("Graf jest spójny");
			graphConnected.setText("Spójny");
		} else {
			//System.out.println("Graf nie jest spójny");
			graphConnected.setText("Niespójny");
		}
		graph.printGraphAdjacencyList();
	}

	// Button to calculate the shortest path between Nodes
	@FXML
	protected void onCheckRouteButton(){

		int destination = djikstra.getDestination();
		djikstra.setDjikstra( graph.getNodeAmount(), graph);
		//System.out.println( source + " " + destination);
		try {

			djikstra.calculatePath();
			//djikstra.printPrecursorNode();
			djikstra.createPath(destination);
			//System.out.println(djikstra.getPath());
			djikstra.drawPath(djikstra.getPath(), graphDrawing);
		} catch ( Exception e){
			distance.setText("Operacja niemożliwa");
		}
		distance.setText(String.valueOf(djikstra.getDistance(djikstra.getDestination())));
	}

}