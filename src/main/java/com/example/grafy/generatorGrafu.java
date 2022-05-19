package com.example.grafy;

import javafx.scene.control.TextField;

	// Dopisać generowanie grafu i zapis do pliku

public class generatorGrafu {
	int wagaMin;
	int wagaMax;
	int wymiarX;
	int wymiarY;
	int iloscPodzialow;
	String plikDoZapisu;

	generatorGrafu(int waga1, int waga2, int wymiarX, int wymiarY, int ilePodzialow, String plikDoZapisu){
		this.wagaMin = waga1;
		this.wagaMax = waga2;
		this.wymiarX = wymiarX;
		this.wymiarY = wymiarY;
		this.iloscPodzialow = ilePodzialow;
		this.plikDoZapisu = plikDoZapisu;
	}

	void wypiszNaWyjscie(){ // metoda testowa do sprawdzania poprawności przetwarzania danych
		System.out.println( wagaMin + " spacja " + wagaMax);
		System.out.println( wymiarX + " X " + wymiarY);
		System.out.println( "Graf dzielony " + iloscPodzialow + " razy");
		System.out.println( "Plik do zapisu: " + plikDoZapisu);
	}

	void generujGraf( int waga1, int waga2, int wymiarX, int wymiarY, int ilePodzialow, String plikDoZapisu){

	}
}
