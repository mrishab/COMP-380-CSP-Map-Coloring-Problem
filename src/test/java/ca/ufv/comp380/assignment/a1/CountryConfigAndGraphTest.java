package ca.ufv.comp380.assignment.a1;

import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import ca.ufv.comp380.assignment.a1.model.Province;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CountryConfigAndGraphTest {

    @Test
    public void testCountryConfigValues() {
        CountryConfig[] countries = CountryConfig.values();
        assertEquals(6, countries.length);

        List<String> expectedNames = List.of(
            "Canada", "China", "India", "Russia", "United Kingdom", "USA"
        );

        for (CountryConfig config : countries) {
            assertTrue(expectedNames.contains(config.getCanonicalName()));
            assertEquals(config.getCanonicalName(), config.toString());

            // Validate SVG resource exists in classpath
            assertNotNull(config.getMapSvg());
            try (InputStream is = getClass().getResourceAsStream(config.getMapSvg())) {
                assertNotNull(is, "Map SVG resource must exist on classpath: " + config.getMapSvg());
            } catch (Exception e) {
                fail("Failed to load map SVG: " + config.getMapSvg(), e);
            }

            // Validate Provinces exist
            List<Province> provinces = config.getProvinces();
            assertNotNull(provinces, "Country provinces must not be null for " + config.getCanonicalName());
            assertFalse(provinces.isEmpty(), "Country provinces must not be empty for " + config.getCanonicalName());
        }
    }

    @Test
    public void testCountryProvinceSymmetryAndBounds() {
        for (CountryConfig config : CountryConfig.values()) {
            List<Province> provinces = config.getProvinces();
            int n = provinces.size();
            assertTrue(n > 0, "Province count should be > 0 for " + config.getCanonicalName());

            for (int u = 0; u < n; u++) {
                Province pu = provinces.get(u);
                assertEquals(u, pu.getIndex());
                assertSame(config, pu.getCountry());

                int[] neighborIndices = pu.getNeighborIndices();
                List<Province> neighbors = pu.getAdjacencyList();
                assertEquals(neighbors.size(), neighborIndices.length);

                for (int v : neighborIndices) {
                    // Check bounds
                    assertTrue(v >= 0 && v < n, "Neighbor index out of bounds: " + v + " in " + config.getCanonicalName());
                    // Check no self-loop
                    assertNotEquals(u, v, "Graph must not have self-loops: " + u + " in " + config.getCanonicalName());
                }

                for (Province pv : neighbors) {
                    assertSame(config, pv.getCountry());
                    assertNotSame(pu, pv);
                    assertTrue(pu.isAdjacentTo(pv));
                    assertTrue(pv.isAdjacentTo(pu), "Adjacency must be symmetric between " + pu + " and " + pv);
                }
            }
        }
    }

    @Test
    public void testCanadaDetails() {
        CountryConfig canada = CountryConfig.CANADA;
        assertEquals("Canada", canada.getCanonicalName());
        assertEquals(13, canada.getProvinces().size());

        Province ab = Province.CA_AB;
        Province bc = Province.CA_BC;
        assertEquals(0, ab.getIndex());
        assertEquals(1, bc.getIndex());
        assertTrue(ab.isAdjacentTo(bc));
        assertTrue(bc.isAdjacentTo(ab));

        assertEquals("Alberta", ab.getProvinceName());
        assertSame(ab, Province.fromName("Alberta"));
        assertSame(ab, Province.fromSvgMapId("CA-AB"));
    }

    @Test
    public void testUSADetails() {
        CountryConfig usa = CountryConfig.USA;
        assertEquals("USA", usa.getCanonicalName());
        assertEquals(51, usa.getProvinces().size());

        Province wa = Province.US_WA;
        Province or = Province.US_OR;
        Province ca = Province.US_CA;
        assertTrue(wa.isAdjacentTo(or));
        assertTrue(or.isAdjacentTo(ca));
        assertFalse(wa.isAdjacentTo(ca)); // WA and CA do not share a border
    }

    @Test
    public void testProvinceToAdjacencyConnections() {
        // 1. Validate Province is an enum
        assertTrue(Province.class.isEnum());
        assertEquals(233, Province.values().length);

        // 2. Get Province enum constant by index, ID, and Name
        Province ab = Province.CA_AB;
        assertNotNull(ab);
        assertEquals(0, ab.getIndex());
        assertEquals("CA-AB", ab.getProvinceSvgMapId());
        assertEquals("CA-AB", ab.getId());
        assertEquals("Alberta", ab.getProvinceName());
        assertEquals("Alberta", ab.getName());
        assertEquals(CountryConfig.CANADA, ab.getCountry());

        assertSame(ab, Province.fromSvgMapId("CA-AB"));
        assertSame(ab, Province.fromName("Alberta"));

        // 3. Adjacency list from Province enum (using the enum itself)
        List<Province> abAdj = ab.getAdjacencyList();
        assertEquals(
            List.of(
                Province.CA_BC,
                Province.CA_NT,
                Province.CA_SK
            ),
            abAdj
        );
        assertEquals(abAdj, ab.getAdjacentProvinces());
        assertEquals(abAdj, ab.getNeighbors());

        // 4. Neighbor indices from Province
        int[] abNeighborIndices = ab.getNeighborIndices();
        assertArrayEquals(new int[] { 1, 6, 11 }, abNeighborIndices);

        // 5. Neighbor IDs and Names from Province
        List<String> neighborIds = ab.getNeighborIds();
        assertEquals(List.of("CA-BC", "CA-NT", "CA-SK"), neighborIds);
        assertEquals(List.of("CA-BC", "CA-NT", "CA-SK"), ab.getAdjacentIds());

        List<String> neighborNames = ab.getNeighborNames();
        assertEquals(List.of("British Columbia", "Northwest Territories", "Saskatchewan"), neighborNames);
        assertEquals(neighborNames, ab.getAdjacentNames());

        // 6. Adjacency predicates
        assertTrue(ab.isAdjacentTo(Province.CA_BC));
        assertTrue(ab.isAdjacentTo("CA-BC"));
        assertTrue(ab.isAdjacentTo(1));
        assertFalse(ab.isAdjacentTo(Province.CA_ON));
    }

    @Test
    public void testAllProvincesAcrossAllCountries() {
        Province[] allProvinces = Province.values();
        assertEquals(233, allProvinces.length);

        Map<CountryConfig, Integer> expectedCounts = Map.of(
            CountryConfig.CANADA, 13,
            CountryConfig.CHINA, 34,
            CountryConfig.INDIA, 36,
            CountryConfig.RUSSIA, 83,
            CountryConfig.UNITED_KINGDOM, 16,
            CountryConfig.USA, 51
        );

        for (Map.Entry<CountryConfig, Integer> entry : expectedCounts.entrySet()) {
            List<Province> countryProvinces = entry.getKey().getProvinces();
            assertEquals(entry.getValue().intValue(), countryProvinces.size(), "Province count mismatch for " + entry.getKey());
            assertEquals(entry.getValue().intValue(), Province.getProvincesByCountry(entry.getKey()).size());
        }

        for (Province p : allProvinces) {
            assertNotNull(p.getProvinceSvgMapId());
            assertFalse(p.getProvinceSvgMapId().isBlank());
            assertNotNull(p.getProvinceName());
            assertFalse(p.getProvinceName().isBlank());
            assertNotNull(p.getCountry());
            assertTrue(p.getIndex() >= 0);

            // Fast lookup tests
            assertSame(p, Province.fromSvgMapId(p.getProvinceSvgMapId()));
            Province byName = Province.fromName(p.getProvinceName());
            assertNotNull(byName);
            assertEquals(p.getProvinceName().toLowerCase(), byName.getProvinceName().toLowerCase());

            List<Province> neighbors = p.getAdjacencyList();
            assertNotNull(neighbors);
            assertEquals(neighbors.size(), p.getDegree());
            assertEquals(neighbors.size(), p.getNeighborIndices().length);
            assertEquals(neighbors.size(), p.getNeighborIds().size());
            assertEquals(neighbors.size(), p.getNeighborNames().size());

            for (Province neighbor : neighbors) {
                // Must be in the same country
                assertSame(p.getCountry(), neighbor.getCountry(), "Neighbor must be from same country");
                // No self loops
                assertNotSame(p, neighbor, "No self-loops allowed");
                // Symmetry test
                assertTrue(neighbor.getAdjacencyList().contains(p), "Adjacency must be symmetric: " + p + " <-> " + neighbor);
                assertTrue(p.isAdjacentTo(neighbor));
                assertTrue(neighbor.isAdjacentTo(p));
            }
        }
    }
}
