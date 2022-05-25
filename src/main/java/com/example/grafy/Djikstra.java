package com.example.grafy;

import java.util.LinkedList;

public class Djikstra {

	private Graph graph; // point which graph are we using
	private int source; // source from which we finde the shortest paths
	private Integer[] previousNode; // array of prevoius nodes in the shortest path from source
	private Double [] distances; // array of the shortest distances from source

	// Djikstra class constructor
	public Djikstra( int source, int nodeAmount, Graph graph){
		this.graph = graph;
		this.source = source;
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
		return path;
	}

	// all needed getter
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
