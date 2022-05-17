package com.example.grafy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

	@FXML
	private Label wagaMinKrawedzi;
	@FXML
	private Label wagaMaxKrawedzi;
	@FXML
	private TextField wagaKrawedzi;

	@FXML
	protected void onGenerateButtonClick() {
		String characters = wagaKrawedzi.getText();
		try {
			wagaKrawedzi.setStyle("-fx-background-color:white;");
			int length = characters.length();
			int myslnik = characters.indexOf('-');
			String wagaMin = characters.substring(0, myslnik);
			String wagaMax = characters.substring(myslnik + 1, length);
			wagaMinKrawedzi.setText(wagaMin);
			wagaMaxKrawedzi.setText(wagaMax);
			generatorGrafu graf = new generatorGrafu(Integer.valueOf(wagaMin), Integer.valueOf(wagaMax));
			graf.wypiszNaWyjscie();
		} catch ( Exception e) {
			wagaKrawedzi.setStyle("-fx-background-color:red;");
			wagaKrawedzi.setPromptText("Brak podanego zakresu");
		}
	}
}