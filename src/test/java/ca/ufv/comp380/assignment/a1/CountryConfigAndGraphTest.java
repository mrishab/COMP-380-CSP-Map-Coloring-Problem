package ca.ufv.comp380.assignment.a1;

import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import ca.ufv.comp380.assignment.a1.model.Graph;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

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

            // Validate Graph exists
            Graph graph = config.getGraph();
            assertNotNull(graph, "Country graph must not be null for " + config.getCanonicalName());
            assertEquals(config.getCanonicalName(), graph.getCountryName());
            assertSame(graph.getAdjacencyList(), config.getGraphArray());
        }
    }

    @Test
    public void testGraphSymmetryAndBounds() {
        for (CountryConfig config : CountryConfig.values()) {
            Graph graph = config.getGraph();
            int n = graph.getNumVertices();
            assertTrue(n > 0, "Vertex count should be > 0 for " + config.getCanonicalName());
            assertEquals(n, graph.size());

            int[][] adj = graph.getAdjacencyList();
            assertEquals(n, adj.length);
            assertEquals(n, graph.getProvinceNames().length);
            assertEquals(n, graph.getProvinceIds().length);

            for (int u = 0; u < n; u++) {
                int[] neighbors = graph.getNeighbors(u);
                assertNotNull(neighbors);
                assertEquals(neighbors.length, adj[u].length);

                for (int v : neighbors) {
                    // Check bounds
                    assertTrue(v >= 0 && v < n, "Neighbor index out of bounds: " + v + " in " + config.getCanonicalName());
                    // Check no self-loop
                    assertNotEquals(u, v, "Graph must not have self-loops: " + u + " in " + config.getCanonicalName());
                    // Check isAdjacent method
                    assertTrue(graph.isAdjacent(u, v));
                    // Check symmetry (undirected graph)
                    assertTrue(graph.isAdjacent(v, u), "Adjacency must be symmetric between " + u + " and " + v + " in " + config.getCanonicalName());
                }
            }
        }
    }

    @Test
    public void testCanadaGraphDetails() {
        Graph canada = Graph.CANADA;
        assertEquals("Canada", canada.getCountryName());
        assertEquals(13, canada.getNumVertices());

        // BC (index 1) and Alberta (index 0) should be adjacent
        int abIndex = canada.getProvinceIndexById("CA-AB");
        int bcIndex = canada.getProvinceIndexById("CA-BC");
        assertEquals(0, abIndex);
        assertEquals(1, bcIndex);
        assertTrue(canada.isAdjacent(abIndex, bcIndex));

        // Name lookup
        assertEquals("Alberta", canada.getProvinceName(abIndex));
        assertEquals(abIndex, canada.getProvinceIndexByName("Alberta"));
    }

    @Test
    public void testUSAGraphDetails() {
        Graph usa = Graph.USA;
        assertEquals("USA", usa.getCountryName());
        assertEquals(51, usa.getNumVertices());

        int waIndex = usa.getProvinceIndexById("US-WA");
        int orIndex = usa.getProvinceIndexById("US-OR");
        int caIndex = usa.getProvinceIndexById("US-CA");
        assertTrue(usa.isAdjacent(waIndex, orIndex));
        assertTrue(usa.isAdjacent(orIndex, caIndex));
        assertFalse(usa.isAdjacent(waIndex, caIndex)); // WA and CA do not share a border
    }
}
