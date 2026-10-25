package ca.ufv.comp380.assignment.a1.controller;

import ca.ufv.comp380.assignment.a1.algorithm.CspAlgorithm;
import ca.ufv.comp380.assignment.a1.model.AppState;
import ca.ufv.comp380.assignment.a1.model.ColorOption;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import ca.ufv.comp380.assignment.a1.model.Graph;
import ca.ufv.comp380.assignment.a1.view.BottomControlsView;
import ca.ufv.comp380.assignment.a1.view.ColorSelectionView;
import ca.ufv.comp380.assignment.a1.view.ExecutionControlsView;
import ca.ufv.comp380.assignment.a1.view.MainView;
import ca.ufv.comp380.assignment.a1.view.MapImageView;
import ca.ufv.comp380.assignment.a1.view.MapSettingsView;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class that wraps the map and the application.
 * Manages the connection between the UI views, current country graph,
 * available colors, execution state, and the CSP algorithm runner.
 *
 * Algorithms call {@code updateColorState(...)} on this controller to visually
 * update province colors on the map in real time.
 */
public class AppController {
    private final MainView mainView;
    private final MapImageView mapImageView;
    private final BottomControlsView bottomControlsView;
    private final MapSettingsView mapSettingsView;
    private final ColorSelectionView colorSelectionView;
    private final ExecutionControlsView executionControlsView;

    private final AppRunner runner;
    private CspAlgorithm algorithm;
    private CountryConfig currentCountry;
    private final Map<Integer, ColorOption> colorState = new LinkedHashMap<>();

    public AppController(MainView mainView) {
        this.mainView = mainView;
        this.mapImageView = mainView.getMapImageView();
        this.bottomControlsView = mainView.getBottomControlsView();
        this.mapSettingsView = bottomControlsView.getMapSettingsView();
        this.colorSelectionView = bottomControlsView.getColorSelectionView();
        this.executionControlsView = mainView.getExecutionControlsView();

        this.currentCountry = mapSettingsView.getMapComboBox().getValue();
        if (this.currentCountry == null) {
            this.currentCountry = CountryConfig.CANADA;
        }

        this.runner = new AppRunner(this);
        this.runner.setStepDelaySeconds(mapSettingsView.getStepDelay());

        initListeners();
    }

    private void initListeners() {
        // Wire execution controls to the runner
        executionControlsView.setOnRun(() -> runner.start());
        executionControlsView.setOnPause(() -> runner.pause());
        executionControlsView.setOnStop(() -> runner.stop());
        executionControlsView.setOnReset(() -> runner.reset());

        // Wire country selection changes
        mapSettingsView.setOnCountrySelected(this::onCountryChanged);

        // Wire step delay slider changes to runner
        mapSettingsView.getStepDelaySlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                runner.setStepDelaySeconds(newVal.doubleValue());
            }
        });
    }

    /**
     * Handles country changes from the dropdown.
     */
    public void onCountryChanged(CountryConfig newCountry) {
        if (newCountry == null) return;
        this.currentCountry = newCountry;
        runner.reset();
        resetColorState();
        mapImageView.loadMap(newCountry.getMapSvg());
    }

    /**
     * Ensures the current algorithm is initialized with problem context.
     */
    public void ensureAlgorithmInitialized() {
        if (algorithm != null) {
            algorithm.initialize(this, getCurrentGraph(), getAvailableColors());
        }
    }

    // ----------------------------------------------------
    // Color State Updates (called by algorithm in step())
    // ----------------------------------------------------

    /**
     * Updates the color state of a given province using a ColorOption.
     *
     * @param provinceIndex the 0-indexed province corresponding to the graph row / SVG path
     * @param colorOption the chosen ColorOption (or null to uncolor)
     */
    public void updateColorState(int provinceIndex, ColorOption colorOption) {
        if (colorOption != null) {
            colorState.put(provinceIndex, colorOption);
            mapImageView.setProvinceColor(provinceIndex, colorOption.getHexCode());
        } else {
            colorState.remove(provinceIndex);
            mapImageView.setProvinceColor(provinceIndex, null);
        }
    }

    /**
     * Updates the color state of a given province using a hex color code (e.g. "#E53935").
     *
     * @param provinceIndex the 0-indexed province corresponding to the graph row / SVG path
     * @param hexColor the hex color string (or null/empty to uncolor)
     */
    public void updateColorState(int provinceIndex, String hexColor) {
        if (hexColor != null && !hexColor.isBlank()) {
            ColorOption opt = findColorOptionByHex(hexColor);
            colorState.put(provinceIndex, opt);
            mapImageView.setProvinceColor(provinceIndex, hexColor);
        } else {
            colorState.remove(provinceIndex);
            mapImageView.setProvinceColor(provinceIndex, null);
        }
    }

    /**
     * Updates the color state of a given province using a JavaFX Color object.
     *
     * @param provinceIndex the 0-indexed province
     * @param color the JavaFX color (or null to uncolor)
     */
    public void updateColorState(int provinceIndex, Color color) {
        if (color != null) {
            String hex = toHexString(color);
            updateColorState(provinceIndex, hex);
        } else {
            updateColorState(provinceIndex, (String) null);
        }
    }

    /**
     * Updates the color state of a given province using its SVG element ID (e.g., "CA-AB").
     */
    public void updateColorState(String provinceId, String hexColor) {
        Graph graph = getCurrentGraph();
        if (graph != null) {
            int idx = graph.getProvinceIndexById(provinceId);
            if (idx >= 0) {
                updateColorState(idx, hexColor);
                return;
            }
        }
        mapImageView.setProvinceColorById(provinceId, hexColor);
    }

    /**
     * Updates the color state of multiple provinces in a single batch.
     *
     * @param newColorState map of provinceIndex -> ColorOption
     */
    public void updateColorState(Map<Integer, ColorOption> newColorState) {
        if (newColorState == null) return;
        for (Map.Entry<Integer, ColorOption> entry : newColorState.entrySet()) {
            updateColorState(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Clears all applied colors from the map and resets the color state.
     */
    public void resetColorState() {
        colorState.clear();
        mapImageView.clearAllColors();
    }

    /**
     * Returns an unmodifiable snapshot of the current color state.
     */
    public Map<Integer, ColorOption> getColorState() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(colorState));
    }

    /**
     * Returns the ColorOption assigned to a given province, or null if unassigned.
     */
    public ColorOption getProvinceColor(int provinceIndex) {
        return colorState.get(provinceIndex);
    }

    private ColorOption findColorOptionByHex(String hexColor) {
        for (ColorOption opt : ColorOption.getCanonicalColors()) {
            if (opt.getHexCode().equalsIgnoreCase(hexColor)) {
                return opt;
            }
        }
        return new ColorOption("Custom", hexColor);
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
            (int) Math.round(color.getRed() * 255),
            (int) Math.round(color.getGreen() * 255),
            (int) Math.round(color.getBlue() * 255)
        );
    }

    // ----------------------------------------------------
    // Execution and Algorithm Controls
    // ----------------------------------------------------

    public void run() {
        runner.start();
    }

    public void pause() {
        runner.pause();
    }

    public void stop() {
        runner.stop();
    }

    public void reset() {
        runner.reset();
    }

    public boolean step() {
        return runner.stepOnce();
    }

    // ----------------------------------------------------
    // Getters & Setters
    // ----------------------------------------------------

    public MainView getMainView() {
        return mainView;
    }

    public MapImageView getMapImageView() {
        return mapImageView;
    }

    public BottomControlsView getBottomControlsView() {
        return bottomControlsView;
    }

    public MapSettingsView getMapSettingsView() {
        return mapSettingsView;
    }

    public ColorSelectionView getColorSelectionView() {
        return colorSelectionView;
    }

    public ExecutionControlsView getExecutionControlsView() {
        return executionControlsView;
    }

    public AppRunner getRunner() {
        return runner;
    }

    public CspAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(CspAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public CountryConfig getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(CountryConfig country) {
        onCountryChanged(country);
    }

    public Graph getCurrentGraph() {
        return currentCountry != null ? currentCountry.getGraph() : null;
    }

    public List<ColorOption> getAvailableColors() {
        return colorSelectionView.getSelectedColors();
    }

    public double getStepDelay() {
        return mapSettingsView.getStepDelay();
    }

    public AppState getAppState() {
        return executionControlsView.getState();
    }

    public void setAppState(AppState state) {
        executionControlsView.setState(state);
    }

    public ObjectProperty<AppState> appStateProperty() {
        return executionControlsView.stateProperty();
    }
}
