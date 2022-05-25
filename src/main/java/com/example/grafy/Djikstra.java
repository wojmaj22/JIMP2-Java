package com.example.grafy;

import javax.sound.sampled.Line;
import java.util.LinkedList;

public class Djikstra {

	private Graph graph;
	private int source;
	private int destination;
	private Integer[] previousNode;
	private Double [] distances;
	private LinkedList<Integer> path;

	public Djikstra( int source, int destination, int nodeAmount, Graph graph){
		this.graph = graph;
		this.source = source;
		this.destination = destination;
		this.previousNode = new Integer[nodeAmount];
		this.distances = new Double[nodeAmount];
	}

	public void calculatePath( int source){
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

	// function used in Djikstra-algorith to find the nearest node( could be optimised a bit)
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

	void printPrecursorNode(){
		int tmp = 0;
		for ( Integer i: this.previousNode) {
			System.out.println( tmp + ": " + i );
			tmp++;
		}
		System.out.println(" ");
		System.out.println(" " + destination + ", " + source);
	}

	void createPath(){
		path = new LinkedList<>();
		int currentNode = destination;
		while( currentNode != source){
			//LinkedList<Integer> tmp = graph.getVertices(currentNode);
			//int indeks_poprzednika = tmp.indexOf(previousNode[currentNode]);
			path.add(currentNode);
			currentNode = previousNode[currentNode];
		}
	}

	public Integer[] getPreviousNode() {
		return previousNode;
	}
	public Double[] getDistances() {
		return distances;
	}
	public Double getDistance( int index){
		return distances[index];
	}
	public LinkedList<Integer> getPath() {
		return path;
	}

}
