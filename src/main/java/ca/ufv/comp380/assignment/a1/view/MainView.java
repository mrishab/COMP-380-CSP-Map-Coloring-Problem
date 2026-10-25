package ca.ufv.comp380.assignment.a1.view;

import ca.ufv.comp380.assignment.a1.controller.AppController;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Root application view with three-level container hierarchy:
 * 1. Top container (MapImageView): Dedicated solely for the vector map image,
 *    occupying the full application width and 60% of the remaining height with padding.
 * 2. Bottom container (BottomControlsView): Divided into two columns occupying the
 *    remaining 40% of the remaining height (MapSettingsView + ColorSelectionView).
 * 3. Third container (ExecutionControlsView): Below the bottom container, occupying
 *    the full application width and divided into 4 columns (Run, Pause, Stop, Reset).
 */
public class MainView extends VBox {
    private final MapImageView mapImageView;
    private final BottomControlsView bottomControlsView;
    private final ExecutionControlsView executionControlsView;
    private final AppController controller;

    public MainView() {
        setFillWidth(true);
        setStyle("-fx-background-color: #f4f5f7;");

        // Third container (action controls toolbar: Run, Pause, Stop, Reset)
        executionControlsView = new ExecutionControlsView();
        executionControlsView.prefWidthProperty().bind(widthProperty());
        executionControlsView.setMinHeight(68);
        executionControlsView.setPrefHeight(68);
        executionControlsView.setMaxHeight(68);
        VBox.setVgrow(executionControlsView, Priority.NEVER);

        // Top container: Only for image, takes full width and 60% of remaining app height
        mapImageView = new MapImageView();
        mapImageView.prefWidthProperty().bind(widthProperty());
        mapImageView.prefHeightProperty().bind(heightProperty().subtract(68).multiply(0.60));
        mapImageView.minHeightProperty().bind(heightProperty().subtract(68).multiply(0.60));
        mapImageView.maxHeightProperty().bind(heightProperty().subtract(68).multiply(0.60));
        VBox.setVgrow(mapImageView, Priority.ALWAYS);

        // Bottom container: 2 columns, takes full width and 40% of remaining app height
        bottomControlsView = new BottomControlsView();
        bottomControlsView.prefWidthProperty().bind(widthProperty());
        bottomControlsView.prefHeightProperty().bind(heightProperty().subtract(68).multiply(0.40));
        bottomControlsView.minHeightProperty().bind(heightProperty().subtract(68).multiply(0.40));
        bottomControlsView.maxHeightProperty().bind(heightProperty().subtract(68).multiply(0.40));
        VBox.setVgrow(bottomControlsView, Priority.ALWAYS);

        getChildren().addAll(mapImageView, bottomControlsView, executionControlsView);

        // Controller wrapping the map and application controls
        controller = new AppController(this);

        // Load the initial map (Canada)
        CountryConfig defaultMap = bottomControlsView.getMapSettingsView().getMapComboBox().getValue();
        if (defaultMap != null) {
            mapImageView.loadMap(defaultMap.getMapSvg());
        } else {
            mapImageView.loadMap(CountryConfig.CANADA.getMapSvg());
        }
    }

    public MapImageView getMapImageView() {
        return mapImageView;
    }

    public BottomControlsView getBottomControlsView() {
        return bottomControlsView;
    }

    public ExecutionControlsView getExecutionControlsView() {
        return executionControlsView;
    }

    public AppController getController() {
        return controller;
    }
}
