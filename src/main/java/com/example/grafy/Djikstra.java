package com.example.grafy;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.LinkedList;

public class Djikstra {

	private Graph graph; // point which graph are we using
	private int source; // source from which we find the shortest paths
	private int destination; // destination to find the shortest path
	boolean destinationPicked = false;
	boolean sourcePicked = false;
	private Integer[] previousNode; // array of previous nodes in the shortest path from source
	private Double [] distances; // array of the shortest distances from source
	private LinkedList<Integer> path; // a path to our destination

	// Djikstra basic contructor
	public Djikstra(){

	}

	// Djikstra class constructor
	public Djikstra( int nodeAmount, Graph graph){
		this.graph = graph;
		this.previousNode = new Integer[nodeAmount];
		this.distances = new Double[nodeAmount];
	}

	// method to change a djistra to use with new graph
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
		LinkedList<Integer> queue = new LinkedList<>();
		for (int i = 0; i < amount; i++) {
			distance[i] = Double.MAX_VALUE;
			visited[i] = false;
		}
		distance[source] = 0.0;
		visited[source] = true;
		precursor[0] = 0;
		queue.add(source);
		for( int i = 0; i < amount; i++){
			//int u = getLowestNotVisited( amount, distance, visited);
			int u = getLowest( queue, distance);
			//System.out.println(i + " | " + u);
			visited[u] = true;
			LinkedList<Double> distances = graph.getDistances(u);
			LinkedList<Integer> vertices = graph.getVertices(u);
			for(int j = 0; j < distances.size(); j++){
				if ( !visited[vertices.get(j)]){
					queue.add(vertices.get(j));
					visited[vertices.get(j)] = true;
				}
				//System.out.println( distance[u] + "+" + distances.get(j) + " = " + (distance[u] + distances.get(j)) + " || " + distance[vertices.get(j)]);
				if((distance[u] + distances.get(j)) < distance[vertices.get(j)]){
					distance[vertices.get(j)] = distance[u] + distances.get(j);
					precursor[vertices.get(j)] = u;
				}
				//System.out.println( vertices.get(j) + ": " + distance[vertices.get(j)]);
			}
//			if( visited[destination]){
//				break;
//			}
			queue.remove((Integer) u);
		}
		distances = distance;
		previousNode = precursor;
	}

	// additional method to Djikstra algorithm
	private int getLowest( LinkedList<Integer> Nodes, Double[] distances){
		Double minDistance = Double.MAX_VALUE;
		int lowestInd = 0;
		for( int i = 0; i < Nodes.size(); i++){
			if( distances[Nodes.get(i)] < minDistance){
				lowestInd = Nodes.get(i);
				minDistance = distances[Nodes.get(i)];
			}
		}
		return lowestInd;
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
	void createPath( int destination){
		LinkedList<Integer> path = new LinkedList<>();
		int currentNode = destination;
		while( currentNode != source){
			path.add(currentNode);
			currentNode = previousNode[currentNode];
		}
		path.add(currentNode);
		this.path = path;
	}

	// draws a path on pane with a graph already drawed
	void drawPath(LinkedList<Integer> path, Pane pane){
		double xLength;
		double yLength;
		double xStart;
		double yStart;
		if( graph.getXdimension() <= 34){
			pane.setPrefWidth( 680);
			xLength = 660.0 / (this.graph.getXdimension()-1);
		} else {
			xLength = 20;
			pane.setPrefWidth(xLength * this.graph.getXdimension() );
		}
		if ( graph.getYdimension() <= 34){
			pane.setPrefHeight( 680);
			yLength = 660.0 / (this.graph.getYdimension()-1);
		} else {
			yLength = 20;
			pane.setPrefHeight(xLength * this.graph.getYdimension() );
		}
		for( int i = 0; i < path.size()-1; i++){
			yStart = (int) (path.get(i) / this.graph.getXdimension() * yLength) +10;
			xStart = ((path.get(i) % this.graph.getXdimension()) * xLength ) +10;
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
			} else if ( path.get(i+1) + this.graph.getXdimension() == path.get(i)){
				line.setEndX( xStart);
				line.setEndY( yStart - yLength);
			}
			pane.getChildren().add(line);
		}
	}


	// all needed getters and setters
	public Integer[] getPreviousNode() {
		return previousNode;
	}
	public Double[] getDistances() {
		return distances;
	}
	public Double getDistance( int index){
		return distances[index];
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public LinkedList<Integer> getPath() {
		return path;
	}
	public void setPath(LinkedList<Integer> path) {
		this.path = path;
	}

}
