package ca.ufv.comp380.assignment.a1.view;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Top container displaying the SVG map image.
 * Takes the full width of the application and 60% of the app height.
 * The container has padding and automatically fits the vector map responsively
 * within the available viewport without overflowing.
 */
public class MapImageView extends StackPane {
    private final WebView webView;
    private final WebEngine webEngine;

    public MapImageView() {
        // Container has padding around the image
        setPadding(new Insets(16));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #f4f5f7;");

        webView = new WebView();
        webEngine = webView.getEngine();

        // Responsive frame around the image with clean border and drop shadow
        StackPane imageWrapper = new StackPane(webView);
        imageWrapper.setAlignment(Pos.CENTER);
        imageWrapper.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #d0d7de; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 8; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.08), 8, 0, 0, 2);"
        );

        // Bind WebView to dynamically fit the padded image wrapper
        webView.minWidthProperty().set(0);
        webView.minHeightProperty().set(0);
        webView.prefWidthProperty().bind(imageWrapper.widthProperty());
        webView.prefHeightProperty().bind(imageWrapper.heightProperty());

        getChildren().add(imageWrapper);
    }

    /**
     * Loads an SVG map from the classpath, ensuring appropriate viewBox so that
     * the vector graphic scales smoothly to fit the container at any window size.
     *
     * @param resourcePath classpath path to the SVG map (e.g., "/img/svg/maps/canada.svg")
     */
    public void loadMap(String resourcePath) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Could not find map resource at: " + resourcePath);
                return;
            }

            String svgContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // Ensure the SVG element has a viewBox so WebKit scales the vectors to fit the container
            if (!svgContent.contains("viewBox=") && !svgContent.contains("viewBox =")) {
                Pattern wPattern = Pattern.compile("width\\s*=\\s*\"([0-9.]+)\"");
                Pattern hPattern = Pattern.compile("height\\s*=\\s*\"([0-9.]+)\"");
                Matcher wMatcher = wPattern.matcher(svgContent);
                Matcher hMatcher = hPattern.matcher(svgContent);
                if (wMatcher.find() && hMatcher.find()) {
                    String width = wMatcher.group(1);
                    String height = hMatcher.group(1);
                    svgContent = svgContent.replaceFirst("<svg", "<svg viewBox=\"0 0 " + width + " " + height + "\"");
                }
            }

            String html = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">" +
                "<style>" +
                "  * { box-sizing: border-box; }" +
                "  html, body {" +
                "    margin: 0; padding: 0; width: 100%; height: 100%;" +
                "    overflow: hidden; display: flex; justify-content: center; align-items: center;" +
                "    background-color: #ffffff;" +
                "  }" +
                "  svg {" +
                "    width: 100%; height: 100%; max-width: 100%; max-height: 100%;" +
                "    object-fit: contain;" +
                "  }" +
                "</style></head><body>" +
                svgContent +
                "</body></html>";

            webEngine.loadContent(html, "text/html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the fill color of a province by its SVG element ID (e.g., "CA-AB").
     */
    public void setProvinceColorById(String id, String hexColor) {
        if (id == null) return;
        if (hexColor == null || hexColor.isBlank()) {
            applyScript(String.format(
                "var el = document.getElementById('%s'); if (el) { el.style.fill = ''; }",
                id
            ));
        } else {
            applyScript(String.format(
                "var el = document.getElementById('%s'); if (el) { el.style.fill = '%s'; el.style.transition = 'fill 0.25s ease'; }",
                id, hexColor
            ));
        }
    }

    /**
     * Clears all province fill colors back to default.
     */
    public void clearAllColors() {
        applyScript(
            "var paths = document.querySelectorAll('path');" +
            "for (var i = 0; i < paths.length; i++) { paths[i].style.fill = ''; }"
        );
    }

    private void applyScript(String script) {
        Platform.runLater(() -> {
            try {
                webEngine.executeScript(script);
            } catch (Exception ignored) {
                // Ignore if document not fully ready yet
            }
        });
    }
}
