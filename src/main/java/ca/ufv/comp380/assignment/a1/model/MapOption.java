package ca.ufv.comp380.assignment.a1.model;

import java.util.List;

public class MapOption {
    private final String name;
    private final String resourcePath;

    public MapOption(String name, String resourcePath) {
        this.name = name;
        this.resourcePath = resourcePath;
    }

    public String getName() {
        return name;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<MapOption> getCanonicalMaps() {
        return List.of(
            new MapOption("Canada", "/img/svg/maps/canada.svg"),
            new MapOption("China", "/img/svg/maps/china.svg"),
            new MapOption("India", "/img/svg/maps/india.svg"),
            new MapOption("Russia", "/img/svg/maps/russia.svg"),
            new MapOption("United Kingdom", "/img/svg/maps/united-kingdom.svg"),
            new MapOption("USA", "/img/svg/maps/usa.svg")
        );
    }
}
