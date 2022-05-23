package com.example.grafy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class graphWriter {

	public static void writeToFile( Graph graph, String Filename){
		File file = new File(Filename);
		FileWriter out = null;
		try {
			out = new FileWriter(file);
			out.write( graph.xDimension + " " + graph.yDimension + "\n");
			String buf = "";
			int currentVertex = 0;
			for (int i = 0; i < graph.xDimension; i++){
				for (int j = 0; j < graph.yDimension; j++){
					int amount = graph.vertices[currentVertex].size();
					for ( int k = 0; k < amount; k++){
						buf = buf + " " + graph.vertices[currentVertex].get(k) + " :" + graph.distances[currentVertex].get(k) + " ";
					}
					out.write( buf);
					out.write( "\n");
					buf = "";
					currentVertex++;
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Błąd zapisu");
		}
	}
}
