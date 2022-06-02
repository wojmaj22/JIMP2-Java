package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class Controller {

	// a graph we will use later on;
	private Graph graph = new Graph();
	private Djikstra djikstra = new Djikstra();

	// these are well described by their names
	@FXML
	private Label minEdgeLengthLabel;
	@FXML
	private Label maxEdgeLengthLabel;
	@FXML
	private TextField edgeLengthTextField;
	@FXML
	private TextField graphDimensionsTextField;
	@FXML
	private TextField amountOfCutsTextField;
	@FXML
	private  TextField writeFileTextField;
	@FXML
	private TextField readFileTextField;
	@FXML
	private Label graphConnected;
	@FXML
	private Label sourceNode;
	@FXML
	private Label destinationNode;
	@FXML
	private Label distance;
	@FXML
	private Pane graphDrawing;
	// Button to save graph to file
	@FXML
	protected void onSaveButtonClick(){
		String filename = "Data\\" + writeFileTextField.getText();
		if (!filename.contains(".txt")){
			filename += ".txt";
		}
		graph.writeToFile( filename);
	}

	// Button to read a graph from file
	@FXML
	protected void onReadButtonClick(){
		String filename = "Data\\" + readFileTextField.getText();
		GraphReader graphReader = new GraphReader( filename);
		graphReader.readGraph( graph);
		try {
			graph.DrawGraph(graphDrawing);
		} catch (IOException e) {
			e.printStackTrace();
		}
		minEdgeLengthLabel.setText(String.valueOf(graph.getMinWeight()));
		maxEdgeLengthLabel.setText(String.valueOf(graph.getMaxWeight()));
		graph.printGraphAdjacencyList();
	}

	@FXML
	protected void onResetSourceDestination() throws IOException {
		djikstra.destinationPicked = false;
		djikstra.sourcePicked = false;
		graph.DrawGraph(graphDrawing);
		destinationNode.setText("");
		sourceNode.setText("");
		distance.setText("");
	}

	@FXML
	protected void chooseNodeOnGraph(){

		graphDrawing.setOnMousePressed(mouseEvent -> {
			Circle circle = new Circle();
			try {
				circle = (Circle) mouseEvent.getTarget();
			} catch ( Exception exception){
				System.out.println("Nie wybrano wierzchołka");
			}
			if( !djikstra.sourcePicked){
				djikstra.setSource(Integer.parseInt(circle.getId()));
				circle.setFill(Color.WHITE);
				sourceNode.setText(circle.getId());
				djikstra.sourcePicked = true;
			} else if ( !djikstra.destinationPicked ){
				djikstra.setDestination(Integer.parseInt(circle.getId()));
				circle.setFill(Color.WHITE);
				destinationNode.setText(circle.getId());
				djikstra.destinationPicked = true;
			}

		});
	}

	// Button to generate a graph
	@FXML
	protected void onGenerateButtonClick( ) throws IOException { // zmienić w przypadku braku wczytanego argumentu
		String weight;
		String dimensions;
		Double minWeight = 0.0;
		Double maxWeight = 5.0;
		int xDimension = 10;
		int yDimension = 10;
		int amountOfCuts = 0;

		try{ // read dimensions of graph from user
			dimensions = graphDimensionsTextField.getText();
			xDimension = Integer.parseInt(dimensions.substring( 0, dimensions.indexOf("x")));
			yDimension = Integer.parseInt(dimensions.substring( dimensions.indexOf("x") + 1));
		} catch ( Exception e){
			graphDimensionsTextField.setText("10x10");
		}
		try { // read weight of edges
			weight = edgeLengthTextField.getText();
			minWeight = Double.parseDouble( weight.substring( 0, weight.indexOf("-")));
			maxWeight = Double.parseDouble( weight.substring( weight.indexOf("-") + 1));
		} catch ( Exception e){
			edgeLengthTextField.setText("0-5");
		}
		try { // read amount of cuts
			amountOfCuts = Integer.parseInt(amountOfCutsTextField.getText());
		} catch (Exception a){
			amountOfCutsTextField.setText("0");
		}
		minEdgeLengthLabel.setText(String.valueOf(minWeight));
		maxEdgeLengthLabel.setText(String.valueOf(maxWeight));

		djikstra.destinationPicked = false;
		djikstra.sourcePicked = false;
		graph.DrawGraph(graphDrawing);
		destinationNode.setText("");
		sourceNode.setText("");
		distance.setText("");
		graphConnected.setText("");
		graph.setGraph( xDimension, yDimension, maxWeight, minWeight, amountOfCuts); // setting graph properties
		graph.generateGraph(); // generating graph edges

		//graph.printGraphAdjacencyList(); // use to check if graph is generated correctly

		//scrollPane.setContent( graphDrawing);

		try {
			graph.DrawGraph(graphDrawing);
			//graph.drawGraph(graphDrawing);
		} catch ( Exception e){
			e.printStackTrace();
		}
		/*try {
			graph.drawGraph(graphDrawing);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	// Button to check if graph is connected
	@FXML
	protected void onBfsButton(){
		if (graph.breathFirstSearch()){
			//System.out.println("Graf jest spójny");
			graphConnected.setText("Spójny");
		} else {
			//System.out.println("Graf nie jest spójny");
			graphConnected.setText("Niespójny");
		}
		//graph.printGraphAdjacencyList();
	}

	// Button to calculate the shortest path between Nodes
	@FXML
	protected void onCheckRouteButton(){

		int destination = djikstra.getDestination();
		djikstra.setDjikstra( graph.getNodeAmount(), graph);
		//System.out.println( source + " " + destination);
		try {

			djikstra.calculatePath();
			//djikstra.printPrecursorNode();
			djikstra.createPath(destination);
			System.out.println(djikstra.getPath());
			djikstra.drawPath(djikstra.getPath(), graphDrawing);
		} catch ( Exception e){
			distance.setText("Operacja niemożliwa");
		}
		if( djikstra.getDistance(djikstra.getDestination()) != Double.MAX_VALUE) {
			String dist = String.valueOf(djikstra.getDistance(djikstra.getDestination()));
			dist = dist.substring(0, dist.indexOf(".") + 3);
			distance.setText(dist);
		} else {
			distance.setText("Brak połączenia");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Błąd");
			alert.setContentText("Nie można obliczyć drogi pomiędzy wybranymi wierzchołkami, ponieważ graf jest niespójny");
			alert.show();
		}
	}
	@FXML
	private void showManual(){
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Pomoc programu");
		alert.setHeight(500);
		alert.setContentText("Program do generowania grafów, sprawdzania spójności grafów oraz oblicznia ścieżek pomiędzy wierzchołkami.\n" +
				"W celu wygenerowania grafu należy podać jego wymiar, zakres wag krawędzi oraz ilość cięć grafu.\n" +
				"W przypadku braku argumentów zostaną użyte wartości domyślne - wymiary 5x5, zakres wag 0-5, ilość cięć 0.\n" +
				"Nastęnie można zapisać wygenerowany graf, wpisująć nazwę pliku do zapisu w odpowiednim polu i kliknąć \"zapisz\"\n" +
				"Aby sprawdzić spójność należy najpierw wygenerować graf lub wczytać go za pomocą odpowiedniego pola, a nastepnie kliknąć \"Sprawdź spójność\".\n" +
				"Aby obliczyć ścieżkę pomiędzy wierzchołkami należy najpierw wygenerować graf lub wczytać go za pomocą odpowiedniego pola,\n" +
				"a następnie wybrać na graficznej ilustacji grafu wierzchołki, i kliknąć \"Oblicz drogę\".\n" +
				"Aby obliczyć drogę pomiędzy innymi wierzchołkami wczytanego grafu należy zresetować wybór za pomocą odpowiedniego przycisku.\n");
		// tutaj poprawić

		alert.show();
	}

}