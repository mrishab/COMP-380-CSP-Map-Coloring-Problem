package ca.ufv.comp380.assignment.a1.controller;

import ca.ufv.comp380.assignment.a1.algorithm.CspAlgorithm;
import ca.ufv.comp380.assignment.a1.algorithm.CspMapColoringAlgorithm;
import ca.ufv.comp380.assignment.a1.model.AppState;
import ca.ufv.comp380.assignment.a1.model.ColorOption;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import ca.ufv.comp380.assignment.a1.model.Province;
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
    private final Map<Province, ColorOption> colorState = new LinkedHashMap<>();

    public AppController() {
        this.mainView = null;
        this.mapImageView = null;
        this.bottomControlsView = null;
        this.mapSettingsView = null;
        this.colorSelectionView = null;
        this.executionControlsView = null;
        this.currentCountry = CountryConfig.CANADA;
        this.runner = null;
        this.algorithm = new CspMapColoringAlgorithm();
    }

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
        this.algorithm = new CspMapColoringAlgorithm();
    }

    private void initListeners() {
        // Wire execution controls to the runner
        executionControlsView.setOnStart(() -> runner.start());
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
        if (newCountry == null)
            return;
        this.currentCountry = newCountry;
        if (runner != null) {
            runner.reset();
        }
        resetColorState();
        if (mapImageView != null) {
            mapImageView.loadMap(newCountry.getMapSvg());
        }
        notifyReady();
    }

    /**
     * Ensures the current algorithm is initialized with problem context.
     */
    public void ensureAlgorithmInitialized() {
        if (algorithm != null) {
            algorithm.initialize(this, currentCountry, getAvailableColors());
        }
    }

    // ----------------------------------------------------
    // Color State Updates (called by algorithm in step())
    // ----------------------------------------------------

    /**
     * Updates the color state of a given province using its Province enum and a ColorOption.
     * Extracts the SVG map ID to update the visual map.
     *
     * @param province    the Province enum
     * @param colorOption the chosen ColorOption (or null to uncolor)
     */
    public void updateColorState(Province province, ColorOption colorOption) {
        if (province == null) {
            return;
        }
        if (colorOption != null) {
            colorState.put(province, colorOption);
            String svgMapId = province.getProvinceSvgMapId();
            if (svgMapId != null && mapImageView != null) {
                mapImageView.setProvinceColorById(svgMapId, colorOption.getHexCode());
            }
        } else {
            colorState.remove(province);
            String svgMapId = province.getProvinceSvgMapId();
            if (svgMapId != null && mapImageView != null) {
                mapImageView.setProvinceColorById(svgMapId, null);
            }
        }
    }

    /**
     * Updates the color state of a given province using its Province enum and a hex color code.
     * Extracts the SVG map ID to update the visual map.
     *
     * @param province the Province enum
     * @param hexColor the hex color string (or null/empty to uncolor)
     */
    public void updateColorState(Province province, String hexColor) {
        if (province == null) {
            return;
        }
        if (hexColor != null && !hexColor.isBlank()) {
            ColorOption opt = findColorOptionByHex(hexColor);
            colorState.put(province, opt);
            String svgMapId = province.getProvinceSvgMapId();
            if (svgMapId != null && mapImageView != null) {
                mapImageView.setProvinceColorById(svgMapId, hexColor);
            }
        } else {
            colorState.remove(province);
            String svgMapId = province.getProvinceSvgMapId();
            if (svgMapId != null && mapImageView != null) {
                mapImageView.setProvinceColorById(svgMapId, null);
            }
        }
    }

    /**
     * Updates the color state of a given province using its Province enum and a JavaFX Color object.
     *
     * @param province the Province enum
     * @param color    the JavaFX color (or null to uncolor)
     */
    public void updateColorState(Province province, Color color) {
        if (color != null) {
            String hex = toHexString(color);
            updateColorState(province, hex);
        } else {
            updateColorState(province, (String) null);
        }
    }

    /**
     * Updates the color state of multiple provinces in a single batch.
     *
     * @param newColorState map of Province -> ColorOption
     */
    public void updateColorState(Map<Province, ColorOption> newColorState) {
        if (newColorState == null) {
            return;
        }
        for (Map.Entry<Province, ColorOption> entry : newColorState.entrySet()) {
            updateColorState(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Clears all applied colors from the map and resets the color state.
     */
    public void resetColorState() {
        colorState.clear();
        if (mapImageView != null) {
            mapImageView.clearAllColors();
        }
    }

    /**
     * Returns an unmodifiable snapshot of the current color state.
     */
    public Map<Province, ColorOption> getColorState() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(colorState));
    }

    /**
     * Returns the ColorOption assigned to a given province, or null if unassigned.
     */
    public ColorOption getProvinceColor(Province province) {
        return colorState.get(province);
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
                (int) Math.round(color.getBlue() * 255));
    }

    // ----------------------------------------------------
    // Execution and Algorithm Controls
    // ----------------------------------------------------

    public void start() {
        if (runner != null) {
            runner.start();
        }
    }

    public void run() {
        start();
    }

    public void pause() {
        if (runner != null) {
            runner.pause();
        }
    }

    public void stop() {
        if (runner != null) {
            runner.stop();
        }
    }

    public void reset() {
        if (runner != null) {
            runner.reset();
        }
    }

    public boolean step() {
        if (runner == null) {
            return false;
        }
        boolean hasMore = runner.stepOnce();
        if (!hasMore) {
            runner.onExecutionFinished();
        }
        return hasMore;
    }

    public void notifyReady() {
        if (executionControlsView != null) {
            executionControlsView.showReady();
        }
    }

    public void notifyRunning() {
        if (executionControlsView != null) {
            executionControlsView.showRunning();
        }
    }

    public void notifyPaused() {
        if (executionControlsView != null) {
            executionControlsView.showPaused();
        }
    }

    public void notifySolutionFound() {
        if (executionControlsView != null) {
            int count = colorState.size();
            int total = currentCountry != null ? currentCountry.getProvinces().size() : count;
            executionControlsView.showSolutionFound("Solution found! (" + count + "/" + total + " provinces colored)");
        }
    }

    public void notifyFailed() {
        if (executionControlsView != null) {
            int count = colorState.size();
            int total = currentCountry != null ? currentCountry.getProvinces().size() : 0;
            executionControlsView.showFailed("Failed: No valid solution found (" + count + "/" + total + " colored)");
        }
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

    public List<ColorOption> getAvailableColors() {
        if (colorSelectionView != null) {
            return colorSelectionView.getSelectedColors();
        }
        return ColorOption.getCanonicalColors();
    }

    public double getStepDelay() {
        return mapSettingsView.getStepDelay();
    }

    public AppState getAppState() {
        return executionControlsView != null ? executionControlsView.getState() : AppState.UNSTARTED;
    }

    public void setAppState(AppState state) {
        if (executionControlsView != null) {
            executionControlsView.setState(state);
        }
    }

    public ObjectProperty<AppState> appStateProperty() {
        return executionControlsView != null ? executionControlsView.stateProperty() : null;
    }
}
