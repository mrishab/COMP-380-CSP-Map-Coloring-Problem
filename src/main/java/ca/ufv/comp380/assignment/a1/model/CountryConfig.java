package ca.ufv.comp380.assignment.a1.model;

import java.util.List;

/**
 * Country configuration enum representing each canonical country with:
 * - Canonical country name
 * - Map SVG resource path
 * - List of Province enums
 */
public enum CountryConfig {
    CANADA("Canada", "/img/svg/maps/canada.svg"),
    CHINA("China", "/img/svg/maps/china.svg"),
    INDIA("India", "/img/svg/maps/india.svg"),
    RUSSIA("Russia", "/img/svg/maps/russia.svg"),
    UNITED_KINGDOM("United Kingdom", "/img/svg/maps/united-kingdom.svg"),
    USA("USA", "/img/svg/maps/usa.svg");

    private final String canonicalName;
    private final String mapSvg;

    CountryConfig(String canonicalName, String mapSvg) {
        this.canonicalName = canonicalName;
        this.mapSvg = mapSvg;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public String getMapSvg() {
        return mapSvg;
    }

    /**
     * Returns an unmodifiable list of Province enums belonging to this country.
     */
    public List<Province> getProvinces() {
        return Province.getProvincesByCountry(this);
    }

    @Override
    public String toString() {
        return canonicalName;
    }

    public static List<CountryConfig> getCanonicalCountries() {
        return List.of(values());
    }
}
