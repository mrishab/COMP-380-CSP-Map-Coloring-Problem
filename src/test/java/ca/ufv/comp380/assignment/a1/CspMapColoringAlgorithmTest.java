package ca.ufv.comp380.assignment.a1;

import ca.ufv.comp380.assignment.a1.algorithm.CspMapColoringAlgorithm;
import ca.ufv.comp380.assignment.a1.controller.AppController;
import ca.ufv.comp380.assignment.a1.model.ColorOption;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import ca.ufv.comp380.assignment.a1.model.Province;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CspMapColoringAlgorithmTest {

    private void runAndVerifyCspForCountry(CountryConfig country) {
        AppController controller = new AppController();
        CspMapColoringAlgorithm algorithm = new CspMapColoringAlgorithm();
        List<ColorOption> colors = ColorOption.getCanonicalColors();

        algorithm.initialize(controller, country, colors);
        assertFalse(algorithm.isFinished(), "Algorithm should start in an unfinished state for " + country);

        // Step through just like AppRunner does
        int maxSteps = 10000;
        int steps = 0;
        while (!algorithm.isFinished() && steps < maxSteps) {
            boolean continueRunning = algorithm.step();
            steps++;
            if (!continueRunning) {
                break;
            }
        }

        assertTrue(algorithm.isFinished(), "Algorithm should finish execution within " + maxSteps + " steps for " + country);
        assertTrue(algorithm.isSolved(), "Algorithm should successfully solve CSP for " + country);

        Map<Province, ColorOption> colorState = controller.getColorState();
        List<Province> provinces = country.getProvinces();

        // 1. Verify every province in the country has been assigned a color (no uncolored provinces)
        assertEquals(provinces.size(), colorState.size(),
                "All " + provinces.size() + " provinces in " + country + " must be colored. Actually colored: " + colorState.size());

        for (Province p : provinces) {
            ColorOption assigned = colorState.get(p);
            assertNotNull(assigned,
                    "Province " + p.getProvinceName() + " (" + p.getProvinceSvgMapId() + ") was left uncolored in " + country);
        }

        // 2. Verify CSP Constraint: No two adjacent provinces share the same color
        for (Province p : provinces) {
            ColorOption pColor = colorState.get(p);
            for (Province neighbor : p.getNeighbors()) {
                ColorOption neighborColor = colorState.get(neighbor);
                assertNotNull(neighborColor,
                        "Neighbor " + neighbor.getProvinceName() + " of " + p.getProvinceName() + " must have a color");
                assertNotEquals(pColor, neighborColor,
                        "Constraint violation in " + country + ": Adjacent provinces " + p.getProvinceName() +
                                " and " + neighbor.getProvinceName() + " both have color " + pColor.getName());
            }
        }
    }

    @Test
    @DisplayName("Test CSP Map Coloring for Canada")
    public void testCanadaColoring() {
        runAndVerifyCspForCountry(CountryConfig.CANADA);
    }

    @Test
    @DisplayName("Test CSP Map Coloring for China")
    public void testChinaColoring() {
        runAndVerifyCspForCountry(CountryConfig.CHINA);
    }

    @Test
    @DisplayName("Test CSP Map Coloring for India")
    public void testIndiaColoring() {
        runAndVerifyCspForCountry(CountryConfig.INDIA);
    }

    @Test
    @DisplayName("Test CSP Map Coloring for Russia")
    public void testRussiaColoring() {
        runAndVerifyCspForCountry(CountryConfig.RUSSIA);
    }

    @Test
    @DisplayName("Test CSP Map Coloring for United Kingdom")
    public void testUnitedKingdomColoring() {
        runAndVerifyCspForCountry(CountryConfig.UNITED_KINGDOM);
    }

    @Test
    @DisplayName("Test CSP Map Coloring for USA")
    public void testUsaColoring() {
        runAndVerifyCspForCountry(CountryConfig.USA);
    }

    @Test
    @DisplayName("Test CSP Map Coloring iterates and solves all countries")
    public void testAllCountriesColoringLoop() {
        for (CountryConfig country : CountryConfig.values()) {
            runAndVerifyCspForCountry(country);
        }
    }
}
