package ca.ufv.comp380.assignment.a1.model;

import java.util.List;
import java.util.Objects;
import javafx.scene.paint.Color;

public class ColorOption implements Comparable<ColorOption> {
    private final String name;
    private final String hexCode;
    private final Color color;

    public ColorOption(String name, String hexCode) {
        this.name = name;
        this.hexCode = hexCode;
        this.color = Color.web(hexCode);
    }

    public String getName() {
        return name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int compareTo(ColorOption other) {
        if (other == null)
            return 1;
        if (this.name == null)
            return other.name == null ? 0 : -1;
        if (other.name == null)
            return 1;
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ColorOption that = (ColorOption) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(hexCode != null ? hexCode.toUpperCase() : null,
                        that.hexCode != null ? that.hexCode.toUpperCase() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hexCode != null ? hexCode.toUpperCase() : null);
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<ColorOption> getCanonicalColors() {
        return List.of(
                new ColorOption("Red", "#E53935"),
                new ColorOption("Green", "#43A047"),
                new ColorOption("Blue", "#1E88E5"),
                new ColorOption("Yellow", "#FDD835"),
                new ColorOption("Orange", "#FB8C00"),
                new ColorOption("Purple", "#8E24AA"),
                new ColorOption("Cyan", "#00ACC1"));
    }
}
