package ca.ufv.comp380.assignment.a1.view;

import ca.ufv.comp380.assignment.a1.model.AppState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Bottom action controls toolbar.
 * Full width, divided into 2 columns:
 * 1. Left column: Start (▶), Pause (⏸), and Reset (↺) action buttons.
 * 2. Right column: Status bar indicating execution state, including when a solution
 *    has been found or when the algorithm has failed.
 *
 * Each button is enabled/disabled according to the application execution state:
 * - Start puts app into running state; disabled while running until pause is pressed.
 * - Pause is disabled unless app is running.
 * - Reset is disabled until app is not in unstarted state.
 */
public class ExecutionControlsView extends HBox {

    /**
     * Visual status states for the execution status bar.
     */
    public enum StatusType {
        READY,
        RUNNING,
        PAUSED,
        SOLUTION_FOUND,
        FAILED
    }

    private final Button startButton;
    private final Button pauseButton;
    private final Button resetButton;
    private final Button stopButton; // Kept for backward compatibility

    private final HBox statusBar;
    private final Label statusIconLabel;
    private final Label statusTextLabel;

    private StatusType currentStatusType = StatusType.READY;
    private final ObjectProperty<AppState> stateProperty = new SimpleObjectProperty<>(AppState.UNSTARTED);

    private Runnable onStart;
    private Runnable onPause;
    private Runnable onReset;
    private Runnable onStop;

    public ExecutionControlsView() {
        setSpacing(16);
        setPadding(new Insets(12, 16, 12, 16));
        setAlignment(Pos.CENTER);
        setFillHeight(true);
        setStyle("-fx-background-color: #fafbfc; -fx-border-color: #e1e4e8; -fx-border-width: 1 0 0 0;");

        // ----------------------------------------------------
        // Column 1 (Left): Buttons Start, Pause, Reset
        // ----------------------------------------------------
        startButton = createStyledButton("▶  Start", "#2ea44f", "#2c974b");
        startButton.setOnAction(e -> {
            if (getState().isStartEnabled()) {
                setState(AppState.RUNNING);
                showRunning();
                if (onStart != null) onStart.run();
            }
        });

        pauseButton = createStyledButton("⏸  Pause", "#d97706", "#b45309");
        pauseButton.setOnAction(e -> {
            if (getState().isPauseEnabled()) {
                setState(AppState.PAUSED);
                showPaused();
                if (onPause != null) onPause.run();
            }
        });

        resetButton = createStyledButton("↺  Reset", "#0969da", "#0550ae");
        resetButton.setOnAction(e -> {
            if (getState().isResetEnabled()) {
                setState(AppState.UNSTARTED);
                showReady();
                if (onReset != null) onReset.run();
            }
        });

        // Dummy stop button retained for backwards compatibility
        stopButton = createStyledButton("🛑  Stop", "#cf222e", "#a40e26");
        stopButton.setOnAction(e -> {
            if (getState().isStopEnabled()) {
                setState(AppState.STOPPED);
                if (onStop != null) onStop.run();
            }
        });

        HBox leftButtonsBox = new HBox(10, startButton, pauseButton, resetButton);
        leftButtonsBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(pauseButton, Priority.ALWAYS);
        HBox.setHgrow(resetButton, Priority.ALWAYS);

        StackPane col1 = new StackPane(leftButtonsBox);
        col1.setAlignment(Pos.CENTER);
        HBox.setHgrow(col1, Priority.ALWAYS);

        // ----------------------------------------------------
        // Column 2 (Right): Status Bar
        // ----------------------------------------------------
        statusIconLabel = new Label("●");
        statusIconLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        statusTextLabel = new Label("Ready to start");
        statusTextLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        statusBar = new HBox(10, statusIconLabel, statusTextLabel);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(8, 16, 8, 16));
        statusBar.setPrefHeight(42);
        statusBar.setMaxHeight(42);
        statusBar.setMaxWidth(Double.MAX_VALUE);

        StackPane col2 = new StackPane(statusBar);
        col2.setAlignment(Pos.CENTER);
        HBox.setHgrow(col2, Priority.ALWAYS);

        // 2 Columns: each column takes 50% width
        col1.prefWidthProperty().bind(widthProperty().subtract(48).multiply(0.50));
        col2.prefWidthProperty().bind(widthProperty().subtract(48).multiply(0.50));

        getChildren().addAll(col1, col2);

        // Set initial status to READY
        showReady();

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
            "-fx-font-size: 13px; " +
            "-fx-background-radius: 8; " +
            "-fx-border-radius: 8; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 8 16 8 16; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.08), 4, 0, 0, 1);";

        String hoverStyle = "-fx-background-color: " + hoverColor + "; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 13px; " +
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
        startButton.setDisable(!state.isRunEnabled());
        pauseButton.setDisable(!state.isPauseEnabled());
        stopButton.setDisable(!state.isStopEnabled());
        resetButton.setDisable(!state.isResetEnabled());
    }

    // ----------------------------------------------------
    // Status Bar Controls
    // ----------------------------------------------------

    /**
     * Updates the status bar type, icon, text message, and styling.
     *
     * @param type    the status type
     * @param message the user-facing text message
     */
    public void setStatus(StatusType type, String message) {
        this.currentStatusType = type;
        statusTextLabel.setText(message);

        switch (type) {
            case READY -> {
                statusIconLabel.setText("●");
                statusIconLabel.setStyle("-fx-text-fill: #57606a; -fx-font-size: 14px; -fx-font-weight: bold;");
                statusTextLabel.setStyle("-fx-text-fill: #57606a; -fx-font-size: 13px; -fx-font-weight: bold;");
                statusBar.setStyle("-fx-background-color: #f6f8fa; -fx-border-color: #d0d7de; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;");
            }
            case RUNNING -> {
                statusIconLabel.setText("▶");
                statusIconLabel.setStyle("-fx-text-fill: #0969da; -fx-font-size: 14px; -fx-font-weight: bold;");
                statusTextLabel.setStyle("-fx-text-fill: #0969da; -fx-font-size: 13px; -fx-font-weight: bold;");
                statusBar.setStyle("-fx-background-color: #ddf4ff; -fx-border-color: #54aeff; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;");
            }
            case PAUSED -> {
                statusIconLabel.setText("⏸");
                statusIconLabel.setStyle("-fx-text-fill: #9a6700; -fx-font-size: 14px; -fx-font-weight: bold;");
                statusTextLabel.setStyle("-fx-text-fill: #9a6700; -fx-font-size: 13px; -fx-font-weight: bold;");
                statusBar.setStyle("-fx-background-color: #fff8c5; -fx-border-color: #d4a72c; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;");
            }
            case SOLUTION_FOUND -> {
                statusIconLabel.setText("✔");
                statusIconLabel.setStyle("-fx-text-fill: #1a7f37; -fx-font-size: 15px; -fx-font-weight: bold;");
                statusTextLabel.setStyle("-fx-text-fill: #1a7f37; -fx-font-size: 13px; -fx-font-weight: bold;");
                statusBar.setStyle("-fx-background-color: #dafbe1; -fx-border-color: #4ac26b; -fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(46, 164, 79, 0.15), 4, 0, 0, 1);");
            }
            case FAILED -> {
                statusIconLabel.setText("✖");
                statusIconLabel.setStyle("-fx-text-fill: #cf222e; -fx-font-size: 15px; -fx-font-weight: bold;");
                statusTextLabel.setStyle("-fx-text-fill: #cf222e; -fx-font-size: 13px; -fx-font-weight: bold;");
                statusBar.setStyle("-fx-background-color: #ffebe9; -fx-border-color: #ff8182; -fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(207, 34, 46, 0.15), 4, 0, 0, 1);");
            }
        }
    }

    public void showReady() {
        setStatus(StatusType.READY, "Ready to start");
    }

    public void showReady(String message) {
        setStatus(StatusType.READY, message);
    }

    public void showRunning() {
        setStatus(StatusType.RUNNING, "Running... searching for solution");
    }

    public void showRunning(String message) {
        setStatus(StatusType.RUNNING, message);
    }

    public void showPaused() {
        setStatus(StatusType.PAUSED, "Paused");
    }

    public void showPaused(String message) {
        setStatus(StatusType.PAUSED, message);
    }

    public void showSolutionFound() {
        setStatus(StatusType.SOLUTION_FOUND, "Solution found! All provinces colored");
    }

    public void showSolutionFound(String message) {
        setStatus(StatusType.SOLUTION_FOUND, message);
    }

    public void showFailed() {
        setStatus(StatusType.FAILED, "Failed: No valid solution found");
    }

    public void showFailed(String message) {
        setStatus(StatusType.FAILED, message);
    }

    public StatusType getStatusType() {
        return currentStatusType;
    }

    public String getStatusText() {
        return statusTextLabel.getText();
    }

    public HBox getStatusBar() {
        return statusBar;
    }

    public Label getStatusIconLabel() {
        return statusIconLabel;
    }

    public Label getStatusTextLabel() {
        return statusTextLabel;
    }

    // ----------------------------------------------------
    // State and Button Getters & Listeners
    // ----------------------------------------------------

    public AppState getState() {
        return stateProperty.get();
    }

    public void setState(AppState state) {
        stateProperty.set(state);
    }

    public ObjectProperty<AppState> stateProperty() {
        return stateProperty;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getRunButton() {
        return startButton;
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

    public void setOnStart(Runnable onStart) {
        this.onStart = onStart;
    }

    public void setOnRun(Runnable onRun) {
        this.onStart = onRun;
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
