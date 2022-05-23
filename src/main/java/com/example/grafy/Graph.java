package com.example.grafy;

import java.util.LinkedList;

public class Graph {

	public static Graph graph = null;

	int xDimension;
	int yDimension;
	LinkedList<Integer>[] vertices;
	LinkedList<Double>[] distances;


	private Graph(int xDimension, int yDimension) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		vertices = new LinkedList[xDimension*yDimension];
		distances = new LinkedList[xDimension*yDimension];
		for( int i = 0; i < xDimension * yDimension; i++){
			vertices[i] = new LinkedList();
			distances[i] = new LinkedList();
		}
	}

	public Graph() {

	}

	public static Graph getGraph(){
		if (graph == null){
			graph = new Graph();
		}
		return graph;
	}

}
