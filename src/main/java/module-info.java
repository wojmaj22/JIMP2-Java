module com.example.grafy {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires java.desktop;
	requires org.testng;

	opens com.example.grafy to javafx.fxml;
	exports com.example.grafy;
}