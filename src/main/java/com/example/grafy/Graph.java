package com.example.grafy;

import java.util.LinkedList;

public class Graph {

	public static Graph graph = null;

	int xDimension;
	int yDimension;
	LinkedList<Integer>[] vertices;
	LinkedList<Double>[] distances;

	private Graph() {

	}

	// usage: Graph graph = Graph.gestGraph();
	public static Graph getGraph(){
		if (graph == null){
			graph = new Graph();
		}
		return graph;
	}

	public void setGraph(int xDimension, int yDimension){
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		graph.vertices = new LinkedList[xDimension*yDimension];
		graph.distances = new LinkedList[xDimension*yDimension];
		for( int i = 0; i < xDimension * yDimension; i++){
			graph.vertices[i] = new LinkedList<>();
			graph.distances[i] = new LinkedList<>();
		}
	}

	public boolean BFS( Graph graph){
		int size = graph.yDimension * graph.xDimension;
		boolean[] visited = new boolean[size];
		for( int i = 0; i < size ; i++){
			visited[i] = false;
		}
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(0);
		visited[0] = true;
		while( queue.size() != 0 ){
			int currentChecked = queue.peekFirst();
			System.out.println( " " + currentChecked);
			LinkedList<Integer> temp = new LinkedList<>();
			temp = graph.vertices[currentChecked];
			while( temp.size() != 0){
				System.out.print( temp);
				int v = temp.getFirst();
				temp.removeFirst();
				if( !visited[v]){
					visited[v] = true;
					queue.add(v);
				}
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
}
