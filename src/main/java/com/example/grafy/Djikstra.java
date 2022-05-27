package com.example.grafy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.LinkedList;

public class Djikstra {

	// tu cała struktura jest do zmiany tych metod itd.
	private Graph graph; // point which graph are we using

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	private int source; // source from which we finde the shortest paths

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	private int destination;
	boolean destinationPicked = false;
	boolean sourcePicked = false;
	private Integer[] previousNode; // array of prevoius nodes in the shortest path from source
	private Double [] distances; // array of the shortest distances from source

	public LinkedList<Integer> getPath() {
		return path;
	}

	public void setPath(LinkedList<Integer> path) {
		this.path = path;
	}
	// do zmiany
	private LinkedList<Integer> path;

	// Djikstra basic contructor
	public Djikstra(){

	}

	// Djikstra class constructor
	public Djikstra( int nodeAmount, Graph graph){
		this.graph = graph;
		this.previousNode = new Integer[nodeAmount];
		this.distances = new Double[nodeAmount];
	}

	public void setDjikstra(int nodeAmount, Graph graph){
		this.graph = graph;
		this.previousNode = new Integer[nodeAmount];
		this.distances = new Double[nodeAmount];
	}

	// algorith to find the shortest path from our source
	public void calculatePath( ){
		int amount = graph.getNodeAmount();
		Double[] distance = new Double[amount];
		boolean[] visited = new boolean[amount];
		Integer[] precursor = new Integer[amount];
		for (int i = 0; i < amount; i++) {
			distance[i] = Double.MAX_VALUE;
			visited[i] = false;
		}
		distance[source] = 0.0;
		precursor[0] = 0;
		for( int i = 0; i < amount; i++){
			int u = getLowestNotVisited( amount, distance, visited);
			visited[u] = true;
			LinkedList<Double> distances = graph.getDistances(u);
			LinkedList<Integer> vertices = graph.getVertices(u);
			for(int j = 0; j < distances.size(); j++){
				if((distance[u] + distances.get(j)) < distance[vertices.get(j)]){
					distance[vertices.get(j)] = distance[u] + distances.get(j);
					precursor[vertices.get(j)] = u;
				}
			}
		}
		distances = distance;
		previousNode = precursor;
	}

	// method used in Djikstra-algorith to find the nearest node( could be optimised a bit)
	private int getLowestNotVisited( int amount, Double[] distance, boolean[] visited){
		int minIndex = 0;
		Double minDistance = Double.MAX_VALUE;
		for( int i = 0; i < amount; i++){
			if( !visited[i] && distance[i] < minDistance){
				minIndex = i;
			}
		}
		return minIndex;
	}

	// method to print previousNode array
	void printPrecursorNode(){
		int tmp = 0;
		for ( Integer i: this.previousNode) {
			System.out.println( tmp + ": " + i );
			tmp++;
		}
	}

	// method to create a path to our destination node
	LinkedList<Integer> createPath( int destination){
		LinkedList<Integer> path = new LinkedList<>();
		int currentNode = destination;
		while( currentNode != source){
			path.add(currentNode);
			currentNode = previousNode[currentNode];
		}
		path.add(currentNode);
		return path;
	}

	void drawPath(LinkedList<Integer> path, Pane pane){
		double xLength = pane.getWidth()/ this.graph.getXdimension();
		double yLength = pane.getHeight()/ this.graph.getYdimension();
		for( int i = 0; i < path.size()-1; i++){
			int yStart = (int) (path.get(i) / this.graph.getXdimension() * yLength);
			double xStart = path.get(i) % this.graph.getYdimension() * xLength;
			System.out.println( xStart + " | " + yStart);
			Line line = new Line();
			line.setStyle("-fx-stroke: White");
			line.setStrokeWidth(2);
			line.setStartX( xStart);
			line.setStartY( yStart);
			if( path.get(i+1) - 1 == path.get(i)){
				line.setEndX( xStart + xLength);
				line.setEndY( yStart);
			} else if ( path.get(i+1) + 1 == path.get(i)){
				line.setEndX( xStart - xLength);
				line.setEndY( yStart);
			} else if ( path.get(i+1) - this.graph.getXdimension() == path.get(i)){
				line.setEndX( xStart);
				line.setEndY( yStart + yLength);
				System.out.println("3" + ( yStart + yLength));
			} else if ( path.get(i+1) + this.graph.getXdimension() == path.get(i)){
				line.setEndX( xStart);
				line.setEndY( yStart - yLength);
				System.out.println("4");
			}
			pane.getChildren().add(line);
		}

	}

	// all needed getters
	public Integer[] getPreviousNode() {
		return previousNode;
	}
	public Double[] getDistances() {
		return distances;
	}
	public Double getDistance( int index){
		return distances[index];
	}

}
