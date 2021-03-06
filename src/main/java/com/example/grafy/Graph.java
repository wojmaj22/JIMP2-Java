package com.example.grafy;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;



public class Graph {

	private int xDimension; // the width of graph
	private int yDimension;// the height of graph
	private Double maxWeight; // maximium weight of generated edge
	private Double minWeight; // minimum weight of generated edge
	private int nodeAmount; // amount of nodes - x * y
	private int cutsAmount; // how many times the graph will be cutted into parts
	private LinkedList<Integer>[] vertices; // adjacency list of graph's vertices
	private LinkedList<Double>[] distances; // adjacency list of graph's distances to vertices from above

	// default constructor, need to use setGraph afterwards
	public Graph() {

	}

	// use to read or create a new graph in Controller.java, after default contructor
	void setGraph( int xDimension, int yDimension, Double maxWeight, Double minWeight, int cutsAmount){
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		this.cutsAmount = cutsAmount;
		this.nodeAmount = xDimension * yDimension;
		this.vertices = new LinkedList[xDimension*yDimension];
		this.distances = new LinkedList[xDimension*yDimension];
		for( int i = 0; i < xDimension * yDimension; i++){
			this.vertices[i] = new LinkedList<>();
			this.distances[i] = new LinkedList<>();
		}
	}
	
	// use when reading graph from file, instead of generating it
	void setGraph2 ( int xDimension, int yDimension, Double maxWeight, Double minWeight, LinkedList<Integer>[] vertices, LinkedList<Double>[] distances){
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		this.vertices = vertices;
		this.distances = distances;
		this.nodeAmount = xDimension * yDimension;
	}

	// method to generate edges of graph
	void generateGraph(){
		Double maxWeight = this.getMaxWeight();
		Double minWeight = this.getMinWeight();

		double distanceDiff = maxWeight - minWeight;
		Random rand = new Random();
		int currentNode = 0;
		for( int i = 0; i < yDimension-1; i++){
			for ( int j = 0; j < xDimension-1; j++){
				this.vertices[currentNode].add(currentNode+1); // adding right edge of node
				this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);

				this.vertices[currentNode].add(currentNode+xDimension);// adding bottom edge of node
				this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);

				if( j == xDimension - 2)
					currentNode += 2;
				else
					currentNode++;
			}
		}

		currentNode = xDimension * yDimension - xDimension; // adding bottom line of edges
		for (int i = 0; i < xDimension - 1; i++){
			this.vertices[currentNode].add(currentNode+1);
			this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);
			currentNode++;
		}

		currentNode = xDimension - 1; // adding right line of edges
		for (int i = 0; i < yDimension - 1; i++){
			this.vertices[currentNode].add(currentNode+xDimension);
			this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);
			currentNode += xDimension;
		}
		currentNode = xDimension*yDimension-1;
		for ( int i = xDimension- 1 ; i >= 0; i--){ // adding already generated edges to the "previous" nodes
			for ( int j = yDimension - 1 ; j >= 0 ; j--){
				if( currentNode % xDimension != 0){
					this.vertices[currentNode].add(currentNode-1);
					this.distances[currentNode].add(this.distances[currentNode-1].peekFirst());
				}
				if( currentNode / xDimension != 0){
					this.vertices[currentNode].add(currentNode-xDimension);
					this.distances[currentNode].add(this.distances[currentNode-xDimension].peekLast());
				}
				currentNode--;
			}
		}

		// cutting the graph
		for( int i = 0; i < cutsAmount; i++){
			int firstNode;
			int secondNode;
			int tmp = 0;
			while( true ){ // selecting first node to cut between
				firstNode = rand.nextInt(nodeAmount);
				if( vertices[firstNode].size() != 4){
					break;
				}
			}
			while( true ){ // second node to cut between them
				secondNode = rand.nextInt(nodeAmount);
				if( vertices[secondNode].size() != 4 && secondNode % xDimension != firstNode % xDimension && secondNode/xDimension != firstNode/xDimension){
					break;
				}
			}
			currentNode = firstNode;
			do {
				try {
					if (vertices[currentNode].getFirst() - 1 == currentNode) {
						int temp = vertices[currentNode + 1].indexOf(currentNode);
						vertices[currentNode + 1].remove(temp);
						distances[currentNode + 1].remove(temp);
						vertices[currentNode].removeFirst();
						distances[currentNode].removeFirst();
					}
					if (vertices[currentNode].getFirst() - xDimension == currentNode) {
						int temp = vertices[currentNode + xDimension].indexOf(currentNode);
						vertices[currentNode + xDimension].remove(temp);
						distances[currentNode + xDimension].remove(temp);
						vertices[currentNode].removeFirst();
						distances[currentNode].removeFirst();
					}
				} catch ( Exception e){
					e.printStackTrace();
				}
				try {
					vertices[secondNode].remove();
					distances[secondNode].remove();
					vertices[secondNode+1].remove((Integer) secondNode);
					distances[secondNode+1].remove(vertices[secondNode+1].indexOf( secondNode));
					vertices[secondNode+xDimension].remove((Integer) secondNode);
					distances[secondNode+xDimension].remove(vertices[secondNode+xDimension].indexOf( secondNode));
				} catch ( Exception exception){
					exception.printStackTrace();
				}
				if( tmp % 2 == 0){
					if( secondNode / xDimension > currentNode / xDimension){
						currentNode += xDimension;
					} else if ( secondNode / xDimension < currentNode / xDimension) {
						currentNode -= xDimension;
					}
				} else{
					if ( secondNode % xDimension > currentNode % xDimension){
						currentNode++;
					} else if ( secondNode % xDimension < currentNode % xDimension) {
						currentNode--;
					}
				}
				tmp++;
			} while ( currentNode != secondNode);
		}
	}

	// method to print adjacency list of generated graph
	void printGraphAdjacencyList(){
		int nodeAmount = this.getNodeAmount();
		for ( int i = 0 ; i < nodeAmount; i++)
		{
			System.out.print( i );
			System.out.print( distances[i]);
			System.out.print( vertices[i]);
			System.out.println();
		}
	}

	// methods to save previously generated graph to file
	public void writeToFile( String Filename){
		int xDimension = this.getXdimension();
		int yDimension = this.getYdimension();
		File file = new File(Filename);
		FileWriter out;
		try {
			out = new FileWriter(file);
			out.write( xDimension + " " + yDimension + "\n");
			StringBuilder buf = new StringBuilder();
			int currentVertex = 0;
			for (int i = 0; i < xDimension; i++){
				for (int j = 0; j < yDimension; j++){
					int amount = this.vertices[currentVertex].size();
					for ( int k = 0; k < amount; k++){
						buf.append(" ").append(this.vertices[currentVertex].get(k)).append(" :").append(this.distances[currentVertex].get(k)).append(" ");
					}
					out.write(buf.toString());
					out.write( "\n");
					buf = new StringBuilder();
					currentVertex++;
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("B????d zapisu");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("B????d");
			alert.setContentText("Nie mo??na zapisa?? pliku.");
			alert.show();
		}
	}

	public void DrawGraph( Pane pane) {
		pane.getChildren().removeAll(pane.getChildren());
		Image image = new Image(getClass().getResourceAsStream("Pasek.png"));
		int xDimension = this.getXdimension();
		int yDimension = this.getYdimension();
		Double maxWeight = this.getMaxWeight();
		PixelReader pixelReader = image.getPixelReader();

		double xLength;
		double yLength;
		double xPosition;
		double yPosition;
		if( xDimension <= 34){
			pane.setPrefWidth( 680);
			xLength = 660.0 / (xDimension-1);
		} else {
			xLength = 20;
			pane.setPrefWidth(xLength * xDimension );
		}
		if( yDimension <= 34 ){
			pane.setPrefHeight( 680);
			yLength = 660.0 / (yDimension-1);
		} else {
			yLength = 20;
			pane.setPrefHeight(yLength * yDimension );
		}
		pane.setStyle("-fx-background-color: Gray");
		xPosition = 10.0;
		yPosition = 10.0;
		int currentVertex = 0;
		for ( int i = 0; i < yDimension; i++){
			for( int j = 0; j < xDimension; j++){
				for ( int k = 0; k < vertices[currentVertex].size(); k++){
					if( vertices[currentVertex].get(k) == currentVertex + 1){ // drawing right edges of graph
						double waga = this.distances[currentVertex].getFirst() / maxWeight;
						Color color = pixelReader.getColor((int) (waga * image.getWidth()), 5 );
						Line line = new Line(xPosition, yPosition, xPosition + xLength, yPosition);
						line.setStyle("-fx-stroke: rgb(" + color.getRed()*255 + "," + color.getGreen()*255 + "," + color.getBlue()*255 + ")");
						line.setStrokeWidth(4);
						pane.getChildren().add(line);
					} else if ( vertices[currentVertex].get(k) == currentVertex + xDimension){ // drawing bottom edges of the graph
						double waga = this.distances[currentVertex].get(this.vertices[currentVertex].indexOf(currentVertex + xDimension)) / maxWeight;
						Color color = pixelReader.getColor((int) (waga * image.getWidth()), 5 );
						Line line = new Line(xPosition, yPosition, xPosition, yPosition + yLength);
						line.setStyle("-fx-stroke: rgb(" + color.getRed()*255 + "," + color.getGreen()*255 + "," + color.getBlue()*255 + ")");
						line.setStrokeWidth(4);
						pane.getChildren().add(line);
					}

				}
				xPosition += xLength;
				currentVertex++;
			}
			yPosition += yLength;
			xPosition = 10;
		}
		yPosition = 10.0;

		for ( int i = 0; i < yDimension; i++){
			for ( int j = 0; j < xDimension; j++){ // drawing nodes of the graph
				Circle circle = new Circle( xPosition, yPosition, 5);
				String id = String.valueOf(i * xDimension + j);
				circle.setStyle("-fx-color: Black");
				circle.setId(id);
				pane.getChildren().add(circle);
				xPosition += xLength;
			}
			xPosition = 10.0;
			yPosition += yLength;

		}
	}

	// method to check if the graph is connected - BFS algorith
	public boolean breathFirstSearch(){
		int size = this.getNodeAmount();
		boolean[] visited = new boolean[size];
		for( int i = 0; i < size ; i++){
			visited[i] = false;
		}
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(0);
		visited[0] = true;
		while( queue.size() != 0 ){
			int tmp = 0;
			int currentChecked = queue.peekFirst();
			LinkedList<Integer> temp = vertices[currentChecked];
			while( tmp != temp.size()){
				int v = temp.get(tmp);
				if( !visited[v]){
					visited[v] = true;
					queue.add(v);
				}
				tmp++;
			}

			visited[currentChecked] = true;
			queue.removeFirst();
		}
		for( int i = 0 ; i < size; i++){
			if( !visited[i]){
				return false;
			}
		}
		return true;
	}

	// all needed getters and setters
	public Double getMinWeight() {
		return minWeight;
	}
	public Double getMaxWeight() {
		return maxWeight;
	}
	public int getXdimension() {
		return xDimension;
	}
	public int getYdimension() {
		return yDimension;
	}
	public int getNodeAmount() {
		return nodeAmount;
	}
	public LinkedList<Integer> getVertices( int u) {
		return vertices[u];
	}
	public LinkedList<Double> getDistances( int u) {
		return distances[u];
	}
}
