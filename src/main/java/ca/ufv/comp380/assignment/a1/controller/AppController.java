package ca.ufv.comp380.assignment.a1.controller;

import ca.ufv.comp380.assignment.a1.algorithm.CspAlgorithm;
import ca.ufv.comp380.assignment.a1.algorithm.CspMapColoringAlgorithm;
import ca.ufv.comp380.assignment.a1.model.AppState;
import ca.ufv.comp380.assignment.a1.model.ColorOption;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import ca.ufv.comp380.assignment.a1.model.Province;
import ca.ufv.comp380.assignment.a1.view.ColorSelectionView;
import ca.ufv.comp380.assignment.a1.view.ExecutionControlsView;
import ca.ufv.comp380.assignment.a1.view.MainView;
import ca.ufv.comp380.assignment.a1.view.MapImageView;
import ca.ufv.comp380.assignment.a1.view.MapSettingsView;

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
    private final MapImageView mapImageView;
    private final MapSettingsView mapSettingsView;
    private final ColorSelectionView colorSelectionView;
    private final ExecutionControlsView executionControlsView;

    private final AppRunner runner;
    private final CspAlgorithm algorithm;
    private CountryConfig currentCountry;
    private final Map<Province, ColorOption> colorState = new LinkedHashMap<>();

    public AppController() {
        this.mapImageView = null;
        this.mapSettingsView = null;
        this.colorSelectionView = null;
        this.executionControlsView = null;
        this.currentCountry = CountryConfig.CANADA;
        this.runner = null;
        this.algorithm = new CspMapColoringAlgorithm();
    }

    public AppController(MainView mainView) {
        this.mapImageView = mainView.getMapImageView();
        this.mapSettingsView = mainView.getBottomControlsView().getMapSettingsView();
        this.colorSelectionView = mainView.getBottomControlsView().getColorSelectionView();
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

    // ----------------------------------------------------
    // Status Notifications (called by AppRunner)
    // ----------------------------------------------------

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

    public CspAlgorithm getAlgorithm() {
        return algorithm;
    }

    public CountryConfig getCurrentCountry() {
        return currentCountry;
    }

    public List<ColorOption> getAvailableColors() {
        if (colorSelectionView != null) {
            return colorSelectionView.getSelectedColors();
        }
        return ColorOption.getCanonicalColors();
    }

    public AppState getAppState() {
        return executionControlsView != null ? executionControlsView.getState() : AppState.UNSTARTED;
    }

    public void setAppState(AppState state) {
        if (executionControlsView != null) {
            executionControlsView.setState(state);
        }
    }
}
