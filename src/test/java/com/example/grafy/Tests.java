package com.example.grafy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {
	@Test
	void djikstraTest(){
		GraphReader gr = new GraphReader( "Data\\graf3.txt");
		Graph graph = new Graph();
		gr.readGraph( graph);
		Djikstra djikstra = new Djikstra( graph.getNodeAmount(), graph);
		djikstra.calculatePath();
		assertEquals( 9.764631601326196, djikstra.getDistance(4) );

		GraphReader gr2 = new GraphReader( "Data\\graf_w.txt");
		gr2.readGraph( graph);
		djikstra.setDjikstra( graph.getNodeAmount(), graph);
		djikstra.calculatePath();
		assertEquals( 19.063611, djikstra.getDistance(22));
		assertEquals( 16.002466, djikstra.getDistance(13));
		assertEquals( 15.392231, djikstra.getDistance(4));
	}

	@Test
	void testBFS(){
		GraphReader gr = new GraphReader( "Data\\graf.txt");
		Graph graph = new Graph();
		gr.readGraph( graph);
		assertEquals( false, graph.breathFirstSearch());

		GraphReader gr2 = new GraphReader( "Data\\graf2.txt");
		gr2.readGraph(graph);
		assertEquals( true, graph.breathFirstSearch());
	}
}
