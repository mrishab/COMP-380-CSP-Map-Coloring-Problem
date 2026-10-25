package ca.ufv.comp380.assignment.a1;

import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        URL svgUrl = getClass().getResource("/img/svg/maps/canada.svg");
        if (svgUrl != null) {
            webEngine.load(svgUrl.toExternalForm());
        } else {
            System.err.println("Could not find canada.svg at /img/svg/maps/canada.svg");
        }

        StackPane root = new StackPane(webView);
        Scene scene = new Scene(root, 900, 700);

        stage.setTitle("Canada Map");
        stage.setScene(scene);
        stage.show();
    }
}