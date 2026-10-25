package ca.ufv.comp380.assignment.a1.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Bottom container divided into two columns:
 * - Left column: MapSettingsView (dropdown menu of canonical maps + step delay slider)
 * - Right column: ColorSelectionView ('Colours' label + 7 color checkboxes)
 */
public class BottomControlsView extends HBox {
    private final MapSettingsView mapSettingsView;
    private final ColorSelectionView colorSelectionView;

    public BottomControlsView() {
        setSpacing(16);
        setPadding(new Insets(14, 16, 16, 16));
        setAlignment(Pos.TOP_CENTER);
        setFillHeight(true);
        setStyle("-fx-background-color: #fafbfc;");

        mapSettingsView = new MapSettingsView();
        colorSelectionView = new ColorSelectionView();

        // Both columns expand equally across the container
        HBox.setHgrow(mapSettingsView, Priority.ALWAYS);
        HBox.setHgrow(colorSelectionView, Priority.ALWAYS);

        // Ensure equal 50/50 proportion across the bottom container
        mapSettingsView.prefWidthProperty().bind(widthProperty().subtract(48).multiply(0.50));
        colorSelectionView.prefWidthProperty().bind(widthProperty().subtract(48).multiply(0.50));

        getChildren().addAll(mapSettingsView, colorSelectionView);
    }

    public MapSettingsView getMapSettingsView() {
        return mapSettingsView;
    }

    public ColorSelectionView getColorSelectionView() {
        return colorSelectionView;
    }
}
