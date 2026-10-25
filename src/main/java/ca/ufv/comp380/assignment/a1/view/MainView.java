package ca.ufv.comp380.assignment.a1.view;

import ca.ufv.comp380.assignment.a1.model.MapOption;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Root application view with two-level container hierarchy:
 * 1. Top container (MapImageView): Dedicated solely for the 1080p map image,
 *    occupying the full application width and 60% of the app height with padding.
 * 2. Bottom container (BottomControlsView): Divided into two columns occupying the
 *    remaining 40% of the app height.
 */
public class MainView extends VBox {
    private final MapImageView mapImageView;
    private final BottomControlsView bottomControlsView;

    public MainView() {
        setFillWidth(true);
        setStyle("-fx-background-color: #f4f5f7;");

        // Top container: Only for image, takes full width and 60% of app height
        mapImageView = new MapImageView();
        mapImageView.prefWidthProperty().bind(widthProperty());
        mapImageView.prefHeightProperty().bind(heightProperty().multiply(0.60));
        mapImageView.minHeightProperty().bind(heightProperty().multiply(0.60));
        mapImageView.maxHeightProperty().bind(heightProperty().multiply(0.60));
        VBox.setVgrow(mapImageView, Priority.ALWAYS);

        // Bottom container: 2 columns, takes full width and 40% of app height
        bottomControlsView = new BottomControlsView();
        bottomControlsView.prefWidthProperty().bind(widthProperty());
        bottomControlsView.prefHeightProperty().bind(heightProperty().multiply(0.40));
        bottomControlsView.minHeightProperty().bind(heightProperty().multiply(0.40));
        bottomControlsView.maxHeightProperty().bind(heightProperty().multiply(0.40));
        VBox.setVgrow(bottomControlsView, Priority.ALWAYS);

        // Connect dropdown map selection to top image view
        bottomControlsView.getMapSettingsView().setOnMapSelected(selectedMap -> {
            if (selectedMap != null) {
                mapImageView.loadMap(selectedMap.getResourcePath());
            }
        });

        // Load the initial map (Canada)
        MapOption defaultMap = bottomControlsView.getMapSettingsView().getMapComboBox().getValue();
        if (defaultMap != null) {
            mapImageView.loadMap(defaultMap.getResourcePath());
        } else {
            mapImageView.loadMap("/img/svg/maps/canada.svg");
        }

        getChildren().addAll(mapImageView, bottomControlsView);
    }

    public MapImageView getMapImageView() {
        return mapImageView;
    }

    public BottomControlsView getBottomControlsView() {
        return bottomControlsView;
    }
}
