package com.example.grafy;

public class generatorGrafu {
	int wagaMin;
	int wagaMax;

	generatorGrafu( int waga1, int waga2){
		this.wagaMin = waga1;
		this.wagaMax = waga2;
	}

	void wypiszNaWyjscie(){
		System.out.println( wagaMin + " spacja " + wagaMax);
	}
}
