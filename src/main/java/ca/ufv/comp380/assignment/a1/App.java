package ca.ufv.comp380.assignment.a1;

import ca.ufv.comp380.assignment.a1.view.MainView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();

        // Calculate 70% of the screen's usable visual view area
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double initialWidth = Math.round(visualBounds.getWidth() * 0.70);
        double initialHeight = Math.round(visualBounds.getHeight() * 0.70);

        Scene scene = new Scene(mainView, initialWidth, initialHeight);

        stage.setTitle("Map Coloring CSP - Canonical Maps");
        stage.setWidth(initialWidth);
        stage.setHeight(initialHeight);

        // Standard minimum bounds and resizable enabling full maximize support
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.setResizable(true);

        // Center within the screen viewport
        stage.setX(visualBounds.getMinX() + (visualBounds.getWidth() - initialWidth) / 2.0);
        stage.setY(visualBounds.getMinY() + (visualBounds.getHeight() - initialHeight) / 2.0);

        stage.setScene(scene);
        stage.show();
    }
}