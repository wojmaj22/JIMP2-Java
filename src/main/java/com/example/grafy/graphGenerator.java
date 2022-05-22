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
	graphGenerator(int waga1, int waga2, int wymiarX, int wymiarY, int ilePodzialow, String plikDoZapisu){
		this.minDistance = waga1;
		this.maxDistance = waga2;
		this.xDimension = wymiarX;
		this.yDimension = wymiarY;
		this.amountOfCuts = ilePodzialow;
		this.writeFile = plikDoZapisu;
	}

	void printToTerminal(){ // metoda testowa do sprawdzania poprawności przetwarzania danych
		System.out.println( minDistance + " spacja " + maxDistance);
		System.out.println( xDimension + " X " + yDimension);
		System.out.println( "Graf dzielony " + amountOfCuts + " razy");
		System.out.println( "Plik do zapisu: " + writeFile);
	}

	void generateGraph(){
		Graph graph = new Graph(xDimension, yDimension);
		int distanceDiff = maxDistance - minDistance;
		Random rand = new Random();
		int currentVertice = 0;
		for( int i = 0; i < xDimension-1; i++){ // dodawanie krawędzi bez prawych i dolnych skrajnych boków
			for ( int j = 0; j < yDimension-1; j++){
				graph.vertices[currentVertice].add(currentVertice+1); // dodawanie prawej krawędzi
				graph.distances[currentVertice].add(rand.nextDouble() * distanceDiff + minDistance);

				graph.vertices[currentVertice].add(currentVertice+xDimension);// dodawanie dolnej krawędzi
				graph.distances[currentVertice].add(rand.nextDouble() * distanceDiff + minDistance);

				if( j == xDimension - 2)
					currentVertice += 2;
				else
					currentVertice++;
			}
		}

		currentVertice = xDimension * yDimension - xDimension; // dodawanie dolnego boku
		for (int i = 0; i < xDimension - 1; i++){
			graph.vertices[currentVertice].add(currentVertice+1);
			graph.distances[currentVertice].add(rand.nextDouble() * distanceDiff + minDistance);
			currentVertice++;
		}

		currentVertice = xDimension - 1; // dodawanie prawego boku
		for (int i = 0; i < yDimension - 1; i++){
			graph.vertices[currentVertice].add(currentVertice+xDimension);
			graph.distances[currentVertice].add(rand.nextDouble() * distanceDiff + minDistance);
			currentVertice += xDimension;
		}

		currentVertice = xDimension*yDimension-1;
		for ( int i = xDimension- 1 ; i >= 0; i--){ // dodawanie "poprzednich" krawędzi
			for ( int j = yDimension - 1 ; j >= 0 ; j--){
				if( j != 0){
					graph.vertices[currentVertice].add(currentVertice-1);
					graph.distances[currentVertice].add(graph.distances[currentVertice-1].peekFirst());
				}
				if( i != 0){
					graph.vertices[currentVertice].add(currentVertice-xDimension);
					graph.distances[currentVertice].add(graph.distances[currentVertice-xDimension].peekLast());
				}
				currentVertice--;
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

