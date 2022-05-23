package com.example.grafy;

// Dopisać generowanie grafu i zapis do pliku

import java.util.Random;

public class graphGenerator {
	int minDistance;
	int maxDistance;
	int xDimension;
	int yDimension;
	int amountOfCuts;
	String writeFile;

	// nazwy w konstruktorze do zmiany
	graphGenerator(int minDistance, int maxDistance, int xDimension, int yDimension, int cutsAmount, String writeFile){
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.amountOfCuts = cutsAmount;
		this.writeFile = writeFile;
	}

	void printToTerminal(){ // metoda testowa do sprawdzania poprawności przetwarzania danych
		System.out.println( minDistance + " spacja " + maxDistance);
		System.out.println( xDimension + " X " + yDimension);
		System.out.println( "Graf dzielony " + amountOfCuts + " razy");
		System.out.println( "Plik do zapisu: " + writeFile);
	}

	void generateGraph(){
		Graph graph = Graph.getGraph();
		graph.setGraph( xDimension, yDimension);
		int distanceDiff = maxDistance - minDistance;
		Random rand = new Random();
		int currentVertex = 0;
		for( int i = 0; i < xDimension-1; i++){ // dodawanie krawędzi bez prawych i dolnych skrajnych boków
			for ( int j = 0; j < yDimension-1; j++){
				graph.vertices[currentVertex].add(currentVertex+1); // dodawanie prawej krawędzi
				graph.distances[currentVertex].add(rand.nextDouble() * distanceDiff + minDistance);

				graph.vertices[currentVertex].add(currentVertex+xDimension);// dodawanie dolnej krawędzi
				graph.distances[currentVertex].add(rand.nextDouble() * distanceDiff + minDistance);

				if( j == xDimension - 2)
					currentVertex += 2;
				else
					currentVertex++;
			}
		}

		currentVertex = xDimension * yDimension - xDimension; // dodawanie dolnego boku
		for (int i = 0; i < xDimension - 1; i++){
			graph.vertices[currentVertex].add(currentVertex+1);
			graph.distances[currentVertex].add(rand.nextDouble() * distanceDiff + minDistance);
			currentVertex++;
		}

		currentVertex = xDimension - 1; // dodawanie prawego boku
		for (int i = 0; i < yDimension - 1; i++){
			graph.vertices[currentVertex].add(currentVertex+xDimension);
			graph.distances[currentVertex].add(rand.nextDouble() * distanceDiff + minDistance);
			currentVertex += xDimension;
		}

		currentVertex = xDimension*yDimension-1;
		for ( int i = xDimension- 1 ; i >= 0; i--){ // dodawanie "poprzednich" krawędzi
			for ( int j = yDimension - 1 ; j >= 0 ; j--){
				if( j != 0){
					graph.vertices[currentVertex].add(currentVertex-1);
					graph.distances[currentVertex].add(graph.distances[currentVertex-1].peekFirst());
				}
				if( i != 0){
					graph.vertices[currentVertex].add(currentVertex-xDimension);
					graph.distances[currentVertex].add(graph.distances[currentVertex-xDimension].peekLast());
				}
				currentVertex--;
			}
		}

		printAdjacencyList( graph);
	}

	void printAdjacencyList(Graph graph){ // funkcja do sprawdzania działania kodu
		for ( int i = 0 ; i < graph.xDimension * graph.yDimension; i++)
		{
			System.out.print( i );
			System.out.print( graph.distances[i]);
			System.out.print( graph.vertices[i]);
			System.out.println();
		}
	}
}

