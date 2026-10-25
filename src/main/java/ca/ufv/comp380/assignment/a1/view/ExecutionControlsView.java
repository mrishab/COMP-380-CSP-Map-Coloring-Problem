package ca.ufv.comp380.assignment.a1.view;

import ca.ufv.comp380.assignment.a1.model.AppState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Third container below the bottom container.
 * Full width, divided into 4 columns containing:
 * 1. Run button with play symbol (▶)
 * 2. Pause button with pause symbol (⏸)
 * 3. Stop button with stop sign symbol (🛑)
 * 4. Reset button with circular arrow symbol (↺)
 *
 * Each button is enabled/disabled according to the application execution state:
 * - Run puts app into running state; disabled while running until pause is pressed.
 * - Pause is disabled unless app is running.
 * - Stop is disabled unless app is running.
 * - Reset is disabled until app is not in unstarted state.
 */
public class ExecutionControlsView extends HBox {
    private final Button runButton;
    private final Button pauseButton;
    private final Button stopButton;
    private final Button resetButton;

    private final ObjectProperty<AppState> stateProperty = new SimpleObjectProperty<>(AppState.UNSTARTED);

    private Runnable onRun;
    private Runnable onPause;
    private Runnable onStop;
    private Runnable onReset;

    public ExecutionControlsView() {
        setSpacing(16);
        setPadding(new Insets(12, 16, 16, 16));
        setAlignment(Pos.CENTER);
        setFillHeight(true);
        setStyle("-fx-background-color: #fafbfc; -fx-border-color: #e1e4e8; -fx-border-width: 1 0 0 0;");

        // ----------------------------------------------------
        // Button 1: Run (play symbol ▶)
        // ----------------------------------------------------
        runButton = createStyledButton("▶  Run", "#2ea44f", "#2c974b");
        runButton.setOnAction(e -> {
            if (getState().isRunEnabled()) {
                setState(AppState.RUNNING);
                if (onRun != null) onRun.run();
            }
        });

        // ----------------------------------------------------
        // Button 2: Pause (pause symbol ⏸)
        // ----------------------------------------------------
        pauseButton = createStyledButton("⏸  Pause", "#d97706", "#b45309");
        pauseButton.setOnAction(e -> {
            if (getState().isPauseEnabled()) {
                setState(AppState.PAUSED);
                if (onPause != null) onPause.run();
            }
        });

        // ----------------------------------------------------
        // Button 3: Stop (stop sign symbol 🛑)
        // ----------------------------------------------------
        stopButton = createStyledButton("🛑  Stop", "#cf222e", "#a40e26");
        stopButton.setOnAction(e -> {
            if (getState().isStopEnabled()) {
                setState(AppState.STOPPED);
                if (onStop != null) onStop.run();
            }
        });

        // ----------------------------------------------------
        // Button 4: Reset (circular arrow symbol ↺)
        // ----------------------------------------------------
        resetButton = createStyledButton("↺  Reset", "#0969da", "#0550ae");
        resetButton.setOnAction(e -> {
            if (getState().isResetEnabled()) {
                setState(AppState.UNSTARTED);
                if (onReset != null) onReset.run();
            }
        });

        // ----------------------------------------------------
        // 4 Columns: each column takes 25% width
        // ----------------------------------------------------
        StackPane col1 = new StackPane(runButton);
        StackPane col2 = new StackPane(pauseButton);
        StackPane col3 = new StackPane(stopButton);
        StackPane col4 = new StackPane(resetButton);

        col1.setAlignment(Pos.CENTER);
        col2.setAlignment(Pos.CENTER);
        col3.setAlignment(Pos.CENTER);
        col4.setAlignment(Pos.CENTER);

        HBox.setHgrow(col1, Priority.ALWAYS);
        HBox.setHgrow(col2, Priority.ALWAYS);
        HBox.setHgrow(col3, Priority.ALWAYS);
        HBox.setHgrow(col4, Priority.ALWAYS);

        // Bind each column to 25% of available width after margins and padding
        col1.prefWidthProperty().bind(widthProperty().subtract(80).multiply(0.25));
        col2.prefWidthProperty().bind(widthProperty().subtract(80).multiply(0.25));
        col3.prefWidthProperty().bind(widthProperty().subtract(80).multiply(0.25));
        col4.prefWidthProperty().bind(widthProperty().subtract(80).multiply(0.25));

        getChildren().addAll(col1, col2, col3, col4);

        // React to state changes and update button enabled/disabled states
        stateProperty.addListener((obs, oldState, newState) -> updateButtonStates(newState));
        updateButtonStates(getState());
    }

    private Button createStyledButton(String text, String bgColor, String hoverColor) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setPrefHeight(42);

        String baseStyle = "-fx-background-color: " + bgColor + "; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-border-radius: 8; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 8 16 8 16; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.08), 4, 0, 0, 1);";

        String hoverStyle = "-fx-background-color: " + hoverColor + "; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-border-radius: 8; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 8 16 8 16; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.15), 6, 0, 0, 2);";

        button.setStyle(baseStyle);

        button.setOnMouseEntered(e -> {
            if (!button.isDisabled()) {
                button.setStyle(hoverStyle);
            }
        });

        button.setOnMouseExited(e -> {
            if (!button.isDisabled()) {
                button.setStyle(baseStyle);
            }
        });

        button.disabledProperty().addListener((obs, wasDisabled, isDisabled) -> {
            button.setStyle(baseStyle);
        });

        return button;
    }

    private void updateButtonStates(AppState state) {
        if (state == null) {
            state = AppState.UNSTARTED;
        }
        runButton.setDisable(!state.isRunEnabled());
        pauseButton.setDisable(!state.isPauseEnabled());
        stopButton.setDisable(!state.isStopEnabled());
        resetButton.setDisable(!state.isResetEnabled());
    }

    public AppState getState() {
        return stateProperty.get();
    }

    public void setState(AppState state) {
        stateProperty.set(state);
    }

    public ObjectProperty<AppState> stateProperty() {
        return stateProperty;
    }

    public Button getRunButton() {
        return runButton;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public void setOnRun(Runnable onRun) {
        this.onRun = onRun;
    }

    public void setOnPause(Runnable onPause) {
        this.onPause = onPause;
    }

    public void setOnStop(Runnable onStop) {
        this.onStop = onStop;
    }

    public void setOnReset(Runnable onReset) {
        this.onReset = onReset;
    }
}
