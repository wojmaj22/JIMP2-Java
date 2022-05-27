package com.example.grafy;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class Graph {

	private int xDimension; // the width of graph
	private int yDimension;// the height of graph
	private int maxWeight; // maximium weight of generated edge
	private int minWeight; // minimum weight of generated edge
	private int nodeAmount; // amount of nodes - x * y
	private int cutsAmount; // how many times the graph will be cutted into parts
	private LinkedList<Integer>[] vertices; // adjacency list of graph's vertices
	private LinkedList<Double>[] distances; // adjacency list of graph's distances to vertices from above

	// default constructor, need to use setGraph afterwards
	public Graph() {

	}

	// bigger constructor, not really used
	public Graph ( int xDimension, int yDimension, int maxWeight, int minWeight){
		 this.xDimension = xDimension;
		 this.yDimension = yDimension;
		 this.maxWeight = maxWeight;
		 this.minWeight = minWeight;
		 this.nodeAmount = xDimension * yDimension;
	}

	// use to read or create a new graph in Controller.java, after default contructor
	void setGraph( int xDimension, int yDimension, int maxWeight, int minWeight, int cutsAmount){
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

	// method to generate edges of graph
	void generateGraph(){
		int maxWeight = this.getMaxWeight();
		int minWeight = this.getMinWeight();

		int distanceDiff = maxWeight - minWeight;
		Random rand = new Random();
		int currentNode = 0;
		for( int i = 0; i < xDimension-1; i++){ // dodawanie krawędzi bez prawych i dolnych skrajnych boków
			for ( int j = 0; j < yDimension-1; j++){
				this.vertices[currentNode].add(currentNode+1); // dodawanie prawej krawędzi
				this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);

				this.vertices[currentNode].add(currentNode+xDimension);// dodawanie dolnej krawędzi
				this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);

				if( j == xDimension - 2)
					currentNode += 2;
				else
					currentNode++;
			}
		}

		currentNode = xDimension * yDimension - xDimension; // dodawanie dolnego boku
		for (int i = 0; i < xDimension - 1; i++){
			this.vertices[currentNode].add(currentNode+1);
			this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);
			currentNode++;
		}

		currentNode = xDimension - 1; // dodawanie prawego boku
		for (int i = 0; i < yDimension - 1; i++){
			this.vertices[currentNode].add(currentNode+xDimension);
			this.distances[currentNode].add(rand.nextDouble() * distanceDiff + minWeight);
			currentNode += xDimension;
		}

		currentNode = xDimension*yDimension-1;
		for ( int i = xDimension- 1 ; i >= 0; i--){ // dodawanie "poprzednich" krawędzi
			for ( int j = yDimension - 1 ; j >= 0 ; j--){
				if( j != 0){
					this.vertices[currentNode].add(currentNode-1);
					this.distances[currentNode].add(this.distances[currentNode-1].peekFirst());
				}
				if( i != 0){
					this.vertices[currentNode].add(currentNode-xDimension);
					this.distances[currentNode].add(this.distances[currentNode-xDimension].peekLast());
				}
				currentNode--;
			}
		}
	}

	// method to print adjacency list of generated graph
	void printGraphAdjacencyList(){ // funkcja do sprawdzania działania kodu
		int nodeAmount = this.getNodeAmount();
		for ( int i = 0 ; i < nodeAmount; i++)
		{
			System.out.print( i );
			System.out.print( this.distances[i]);
			System.out.print( this.vertices[i]);
			System.out.println();
		}
	}

	// methos do save prevoiusly generated graph to file
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
			System.out.println("Błąd zapisu");
		}
	}

	// Rysowane grafu jest chyba trochę do zmiany, ale to na kiedy indziej
	public void drawGraph( Pane pane) throws IOException {
		pane.getChildren().removeAll(pane.getChildren());
		File file = new File("src/main/resources/com/example/grafy/Pasek.png");
		BufferedImage img = ImageIO.read(file);
		int xDimension = this.getXdimension();
		int yDimension = this.getYdimension();
		int maxWeight = this.getMaxWeight();

		double xLength = pane.getWidth()/ xDimension;
		double yLength = pane.getHeight()/ yDimension;
		double xPosition = 0.0;
		double yPosition = 0.0;


		int currentVertex = 0;
		for ( int i = 0; i < xDimension-1; i++){
			for( int j = 0; j < yDimension; j++){
				double waga = this.distances[currentVertex].getFirst() / maxWeight;
				int pixel = img.getRGB((int) (waga * img.getWidth()), 5);
				Color color = new Color(pixel, true);
				Line line = new Line( xPosition, yPosition, xPosition+xLength, yPosition);
				line.setStyle("-fx-stroke: rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")" );
				line.setStrokeWidth(3);
				pane.getChildren().add(line);
				yPosition += yLength;
				currentVertex++;
			}
			xPosition += xLength;
			yPosition = 0.0;
		}
		xPosition = 0.0;
		currentVertex = 0;
		// troche do poprawy (a może nie?)
		for ( int i = 0; i < xDimension; i++){
			for( int j = 0; j < yDimension-1; j++){
				System.out.println(currentVertex +  "-" + (currentVertex+yDimension));
				double waga = this.distances[currentVertex].get(this.vertices[currentVertex].indexOf(currentVertex+xDimension)) / maxWeight;
				int pixel = img.getRGB((int) (waga * img.getWidth()), 5);
				Color color = new Color(pixel, true);
				Line line = new Line( xPosition, yPosition, xPosition, yPosition+yLength);
				line.setStyle("-fx-stroke: rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")" );
				line.setStrokeWidth(3);
				pane.getChildren().add(line);
				yPosition += yLength;
				currentVertex++;
			}
			xPosition += xLength;
			yPosition = 0.0;
		}

		xPosition = 0.0;
		yPosition = 0.0;

		for ( int i = 0; i < xDimension; i++){
			for ( int j = 0; j < yDimension; j++){
				Circle circle = new Circle( xPosition, yPosition, 5);
				String id = String.valueOf(i * xDimension + j);
				circle.setId(id);
				pane.getChildren().add(circle);
				xPosition += xLength;
			}
			xPosition = 0.0;
			yPosition += yLength;
		}

	}

	// method to check if the graph if graph is connected
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
			int currentChecked = queue.peekFirst();
			System.out.println( " " + currentChecked);
			LinkedList<Integer> temp = this.vertices[currentChecked];
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

	// all needed getters
	public int getMinWeight() {
		return minWeight;
	}
	public int getMaxWeight() {
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
	public int getCutsAmount() {
		return cutsAmount;
	}
	public LinkedList<Integer> getVertices( int u) {
		return vertices[u];
	}
	public LinkedList<Double> getDistances( int u) {
		return distances[u];
	}

}
