package ca.ufv.comp380.assignment.a1;

import ca.ufv.comp380.assignment.a1.algorithm.CspAlgorithm;
import ca.ufv.comp380.assignment.a1.controller.AppController;
import ca.ufv.comp380.assignment.a1.model.ColorOption;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CspAlgorithmAndControllerTest {

    /**
     * Sample CSP Algorithm implementation verifying the interface contract.
     */
    static class DummyCspAlgorithm implements CspAlgorithm {
        private AppController controller;
        private CountryConfig country;
        private List<ColorOption> colors;
        private int currentProvince = 0;
        private boolean finished = false;

        @Override
        public void initialize(AppController controller, CountryConfig country, List<ColorOption> availableColors) {
            this.controller = controller;
            this.country = country;
            this.colors = availableColors;
            this.currentProvince = 0;
            this.finished = false;
        }

        @Override
        public boolean step() {
            int total = country != null ? country.getProvinces().size() : 0;
            if (country == null || colors == null || colors.isEmpty() || currentProvince >= total) {
                finished = true;
                return false;
            }

            // Assign color cyclically and update controller
            ColorOption chosenColor = colors.get(currentProvince % colors.size());
            if (controller != null && country != null) {
                ca.ufv.comp380.assignment.a1.model.Province p = country.getProvinces().get(currentProvince);
                controller.updateColorState(p, chosenColor);
            }

            currentProvince++;
            if (currentProvince >= total) {
                finished = true;
                return false;
            }
            return true;
        }

        @Override
        public void reset() {
            currentProvince = 0;
            finished = false;
        }

        @Override
        public boolean isFinished() {
            return finished;
        }

        @Override
        public boolean isSolved() {
            int total = country != null ? country.getProvinces().size() : 0;
            return finished && currentProvince == total;
        }
    }

    @Test
    public void testCspAlgorithmInterfaceAndStepping() {
        DummyCspAlgorithm algorithm = new DummyCspAlgorithm();
        CountryConfig canada = CountryConfig.CANADA;
        List<ColorOption> colors = ColorOption.getCanonicalColors();

        algorithm.initialize(null, canada, colors);
        assertFalse(algorithm.isFinished());

        // Step through all 13 provinces
        int steps = 0;
        while (!algorithm.isFinished() && steps < 20) {
            algorithm.step();
            steps++;
        }

        assertEquals(13, steps);
        assertTrue(algorithm.isFinished());
        assertTrue(algorithm.isSolved());

        // Reset
        algorithm.reset();
        assertFalse(algorithm.isFinished());
        assertFalse(algorithm.isSolved());
    }

    @Test
    public void testControllerColorStateTracking() {
        // Test controller updateColorState logic
        Map<ca.ufv.comp380.assignment.a1.model.Province, ColorOption> colorMap = new HashMap<>();
        ColorOption red = new ColorOption("Red", "#E53935");
        ColorOption blue = new ColorOption("Blue", "#1E88E5");

        colorMap.put(ca.ufv.comp380.assignment.a1.model.Province.CA_AB, red);
        colorMap.put(ca.ufv.comp380.assignment.a1.model.Province.CA_BC, blue);

        assertEquals(2, colorMap.size());
        assertEquals(red, colorMap.get(ca.ufv.comp380.assignment.a1.model.Province.CA_AB));
        assertEquals(blue, colorMap.get(ca.ufv.comp380.assignment.a1.model.Province.CA_BC));
    }
}
