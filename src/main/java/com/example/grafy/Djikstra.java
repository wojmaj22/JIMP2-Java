package com.example.grafy;

public class Djikstra {

	//przesunąć do Graph (może)
	public static Integer[] calculatePath( Graph graph, int source){
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

			for(int j = 0; j < graph.distances[u].size(); j++){
				if( distance[u] + graph.distances[u].get(j) < distance[graph.vertices[u].get(j)]){
					distance[graph.vertices[u].get(j)] = distance[u] + graph.distances[u].get(j);
					precursor[graph.vertices[u].get(j)] = u;
				}
			}
		}

		return precursor;
	}

	private static int getLowestNotVisited( int amount, Double[] distance, boolean[] visited){
		int minIndex = 0;
		Double minDistance = Double.MAX_VALUE;
		for( int i = 0; i < amount; i++){
			if( !visited[i] && distance[i] < minDistance){
				minIndex = i;
			}
		}
		return minIndex;
	}
}
