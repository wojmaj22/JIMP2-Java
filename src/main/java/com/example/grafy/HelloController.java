package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.concurrent.ExecutionException;

public class HelloController {

	// Ogarnąć wyjątki itd., spawdzić czy to jest OK?
	// Dodać zapisywanie na drugi przycisk, ew. pobieranie pola też

	@FXML
	private Label wagaMinKrawedzi;
	@FXML
	private Label wagaMaxKrawedzi;
	@FXML
	private TextField wagaKrawedzi;
	@FXML
	private TextField wymiarGrafu;
	@FXML
	private TextField ilePodzialow;
	@FXML
	private  TextField polePlikDoZapisu;

	@FXML
	protected void onGenerateButtonClick() {
		String wagaZakres = wagaKrawedzi.getText();
		String wymiary = wymiarGrafu.getText();
		int wagaMin = 0;
		int wagaMax = 0;
		int wymiarX = 0;
		int wymiarY = 0;
		int iloscPodzialow = 0;
		String plikDoZapisu = null;
		try { // wczytywanie wymiarów
			int indeksX = wymiary.indexOf('x');
			wymiarX = Integer.valueOf(wymiary.substring(0, indeksX));
			wymiarY = Integer.valueOf(wymiary.substring(indeksX + 1, wymiary.length()));
		} catch ( Exception ee) {
			wymiarGrafu.setStyle("-fx-background-color:red;");
			wymiarGrafu.setPromptText("Brak podanych wymiarów");
		}
		try{ // wczytywanie wag krawędzi
			int length = wagaZakres.length();
			int myslnik = wagaZakres.indexOf('-');
			wagaMin = Integer.valueOf(wagaZakres.substring(0, myslnik));
			wagaMax = Integer.valueOf(wagaZakres.substring(myslnik + 1, length));
			wagaMinKrawedzi.setText(String.valueOf(wagaMin));
			wagaMaxKrawedzi.setText(String.valueOf(wagaMax));

		} catch (Exception e) {
			wagaKrawedzi.setStyle("-fx-background-color:red;");
			wagaKrawedzi.setPromptText("Brak podanego zakresu");
		}
		try { // pobieranie ilości podzieleń grafu
			iloscPodzialow = Integer.valueOf(ilePodzialow.getText());
		} catch ( Exception a){
			ilePodzialow.setStyle("-fx-background-color:red;");
		}
		try{ // pobieranie nazwy pliku do zapisania grafu
			plikDoZapisu = polePlikDoZapisu.getText();
		} catch	( Exception b){
			polePlikDoZapisu.setStyle("-fx-background-color:red;");
		}
		generatorGrafu graf = new generatorGrafu(wagaMin, wagaMax, wymiarX, wymiarY, iloscPodzialow, plikDoZapisu);
		graf.wypiszNaWyjscie();


	}
}