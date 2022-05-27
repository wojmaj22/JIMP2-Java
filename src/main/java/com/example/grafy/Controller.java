package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.color.ColorSpace;
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
	// change it?
	@FXML
	private Pane pane;

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
	protected void onResetSourceDestination(){
		djikstra.destinationPicked = false;
		djikstra.sourcePicked = false;
		//usunąć białe linie tylko jak xd
	}

	@FXML
	protected void chooseNodeOnGraph(){
		pane.setOnMousePressed(mouseEvent -> {
			Circle circle = (Circle) mouseEvent.getTarget();
			if( !djikstra.sourcePicked){
				djikstra.setSource(Integer.parseInt(circle.getId()));
				circle.setFill(Color.WHITE);
				djikstra.sourcePicked = true;
			} else if ( !djikstra.destinationPicked ){
				djikstra.setDestination(Integer.parseInt(circle.getId()));
				circle.setFill(Color.WHITE);
				djikstra.destinationPicked = true;
			}

		});
	}

	// Button to generate a graph
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

		try {
			graph.drawGraph(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Button to check if graph is connected
	@FXML
	protected void onBfsButton(){
		if (graph.breathFirstSearch()){
			System.out.println("Graf jest spójny");
		} else {
			System.out.println("Graf nie jest spójny");
		}
	}

	// Button to calculate the shortest path between Nodes
	@FXML
	protected void onCheckRouteButton(){

		int source = djikstra.getSource();
		int destination = djikstra.getDestination();
		djikstra.setDjikstra( graph.getNodeAmount(), graph);
		System.out.println( source + " " + destination);
		djikstra.calculatePath();
		//djikstra.printPrecursorNode();
		djikstra.setPath(djikstra.createPath(destination));
		System.out.println( djikstra.getDistance(destination));
		System.out.println(djikstra.getPath());
		djikstra.drawPath( djikstra.getPath(), pane);
	}

}