package ca.ufv.comp380.assignment.a1.view;

import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * First column on the left in the bottom container.
 * Contains two items that each take up the full width of the column:
 * 1. Drop down menu with List of Canonical Maps (Canada, India, China, Russia, United Kingdom, USA).
 * 2. Slider bar for 'Step Delay' ranging from 0s to 5s.
 */
public class MapSettingsView extends VBox {
    private final ComboBox<CountryConfig> mapComboBox;
    private final Slider stepDelaySlider;
    private final Label delayValueLabel;

    public MapSettingsView() {
        setSpacing(24);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_LEFT);
        setFillWidth(true);
        setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #e1e4e8; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 8; " +
            "-fx-background-radius: 8;"
        );

        // ----------------------------------------------------
        // Item 1: Dropdown Menu with List of Canonical Maps (Full Width)
        // ----------------------------------------------------
        VBox mapSelectionBox = new VBox(8);
        mapSelectionBox.setFillWidth(true);
        mapSelectionBox.setMaxWidth(Double.MAX_VALUE);

        Label mapLabel = new Label("Canonical Map");
        mapLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #24292e;");

        mapComboBox = new ComboBox<>();
        mapComboBox.getItems().addAll(CountryConfig.getCanonicalCountries());
        mapComboBox.setMaxWidth(Double.MAX_VALUE);
        mapComboBox.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 6 12 6 12; " +
            "-fx-background-radius: 6; " +
            "-fx-border-radius: 6; " +
            "-fx-border-color: #d1d5db;"
        );

        // Select Canada by default
        if (!mapComboBox.getItems().isEmpty()) {
            mapComboBox.getSelectionModel().select(0);
        }

        mapSelectionBox.getChildren().addAll(mapLabel, mapComboBox);

        // ----------------------------------------------------
        // Item 2: Slider bar for 'Step Delay' (0s to 5s) (Full Width)
        // ----------------------------------------------------
        VBox sliderBox = new VBox(8);
        sliderBox.setFillWidth(true);
        sliderBox.setMaxWidth(Double.MAX_VALUE);

        delayValueLabel = new Label("Step Delay: 1.0s");
        delayValueLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #24292e;");

        stepDelaySlider = new Slider(0.0, 5.0, 1.0);
        stepDelaySlider.setMaxWidth(Double.MAX_VALUE);
        stepDelaySlider.setShowTickMarks(true);
        stepDelaySlider.setShowTickLabels(true);
        stepDelaySlider.setMajorTickUnit(1.0);
        stepDelaySlider.setMinorTickCount(3);
        stepDelaySlider.setBlockIncrement(0.5);
        stepDelaySlider.setSnapToTicks(false);

        stepDelaySlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double val) {
                if (val == null) return "0s";
                return String.format("%.0fs", val);
            }

            @Override
            public Double fromString(String str) {
                if (str == null) return 0.0;
                return Double.parseDouble(str.replace("s", "").trim());
            }
        });

        stepDelaySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            delayValueLabel.setText(String.format("Step Delay: %.1fs", newVal.doubleValue()));
        });

        sliderBox.getChildren().addAll(delayValueLabel, stepDelaySlider);

        // Add both full-width items to the column
        getChildren().addAll(mapSelectionBox, sliderBox);
        VBox.setVgrow(mapSelectionBox, Priority.NEVER);
        VBox.setVgrow(sliderBox, Priority.NEVER);
    }

    /**
     * Registers a listener to be called when the selected country/map changes.
     *
     * @param onCountrySelected consumer taking the newly selected CountryConfig
     */
    public void setOnCountrySelected(Consumer<CountryConfig> onCountrySelected) {
        mapComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && onCountrySelected != null) {
                onCountrySelected.accept(newVal);
            }
        });
    }

    public ComboBox<CountryConfig> getMapComboBox() {
        return mapComboBox;
    }

    public Slider getStepDelaySlider() {
        return stepDelaySlider;
    }

    public double getStepDelay() {
        return stepDelaySlider.getValue();
    }
}
