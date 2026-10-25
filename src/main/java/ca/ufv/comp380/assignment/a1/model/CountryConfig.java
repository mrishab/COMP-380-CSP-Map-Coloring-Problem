package ca.ufv.comp380.assignment.a1.model;

import java.util.List;

/**
 * Country configuration enum representing each canonical country with:
 * - Canonical country name
 * - Map SVG resource path
 * - Graph representation created from the 2-D adjacency array
 */
public enum CountryConfig {
    CANADA("Canada", "/img/svg/maps/canada.svg", Graph.CANADA),
    CHINA("China", "/img/svg/maps/china.svg", Graph.CHINA),
    INDIA("India", "/img/svg/maps/india.svg", Graph.INDIA),
    RUSSIA("Russia", "/img/svg/maps/russia.svg", Graph.RUSSIA),
    UNITED_KINGDOM("United Kingdom", "/img/svg/maps/united-kingdom.svg", Graph.UNITED_KINGDOM),
    USA("USA", "/img/svg/maps/usa.svg", Graph.USA);

    private final String canonicalName;
    private final String mapSvg;
    private final Graph graph;

    CountryConfig(String canonicalName, String mapSvg, Graph graph) {
        this.canonicalName = canonicalName;
        this.mapSvg = mapSvg;
        this.graph = graph;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public String getMapSvg() {
        return mapSvg;
    }

    public Graph getGraph() {
        return graph;
    }

    /**
     * Convenience method to retrieve the raw 2-D adjacency array of this country's graph.
     */
    public int[][] getGraphArray() {
        return graph != null ? graph.getAdjacencyList() : new int[0][0];
    }

    @Override
    public String toString() {
        return canonicalName;
    }

    public static List<CountryConfig> getCanonicalCountries() {
        return List.of(values());
    }
}
