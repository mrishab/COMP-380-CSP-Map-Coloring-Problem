package ca.ufv.comp380.assignment.a1.view;

import ca.ufv.comp380.assignment.a1.model.ColorOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * Second column on the right in the bottom container.
 * Has a top label 'Colours' and items below it are checkboxes
 * with 7 distinct colour values, each on a new row.
 */
public class ColorSelectionView extends VBox {
    private final Map<ColorOption, CheckBox> colorCheckBoxes = new LinkedHashMap<>();

    public ColorSelectionView() {
        setSpacing(10);
        setPadding(new Insets(16));
        setAlignment(Pos.TOP_LEFT);
        setFillWidth(true);
        setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #e1e4e8; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 8; " +
            "-fx-background-radius: 8;"
        );

        // Top label 'Colours'
        Label coloursLabel = new Label("Colours");
        coloursLabel.setStyle(
            "-fx-font-weight: bold; " +
            "-fx-font-size: 15px; " +
            "-fx-text-fill: #24292e; " +
            "-fx-padding: 0 0 2 0;"
        );
        getChildren().add(coloursLabel);

        // Checkbox container: each checkbox on a new row
        VBox rowsContainer = new VBox(8);
        rowsContainer.setFillWidth(true);

        List<ColorOption> canonicalColors = ColorOption.getCanonicalColors();
        for (ColorOption option : canonicalColors) {
            CheckBox checkBox = new CheckBox(option.getName());
            checkBox.setSelected(true);
            checkBox.setStyle("-fx-font-size: 13px; -fx-text-fill: #24292e; -fx-cursor: hand;");

            // Colored swatch icon next to the checkbox label
            Rectangle colorBadge = new Rectangle(14, 14);
            colorBadge.setFill(option.getColor());
            colorBadge.setArcWidth(4);
            colorBadge.setArcHeight(4);
            colorBadge.setStroke(option.getColor().darker());
            colorBadge.setStrokeWidth(1);

            checkBox.setGraphic(colorBadge);
            checkBox.setGraphicTextGap(8);

            colorCheckBoxes.put(option, checkBox);
            rowsContainer.getChildren().add(checkBox);
        }

        ScrollPane scrollPane = new ScrollPane(rowsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
            "-fx-background: transparent; " +
            "-fx-background-color: transparent; " +
            "-fx-border-color: transparent;"
        );

        getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    /**
     * Returns the list of currently selected ColorOptions.
     */
    public List<ColorOption> getSelectedColors() {
        List<ColorOption> selected = new ArrayList<>();
        for (Map.Entry<ColorOption, CheckBox> entry : colorCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }
}
