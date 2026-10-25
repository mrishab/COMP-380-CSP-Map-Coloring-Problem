package ca.ufv.comp380.assignment.a1.model;

import java.util.List;
import javafx.scene.paint.Color;

public class ColorOption {
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

    public static List<ColorOption> getCanonicalColors() {
        return List.of(
            new ColorOption("Red", "#E53935"),
            new ColorOption("Green", "#43A047"),
            new ColorOption("Blue", "#1E88E5"),
            new ColorOption("Yellow", "#FDD835"),
            new ColorOption("Orange", "#FB8C00"),
            new ColorOption("Purple", "#8E24AA"),
            new ColorOption("Cyan", "#00ACC1")
        );
    }
}
