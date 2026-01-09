
package ru.example.pms.util;

import javafx.scene.Scene;
import java.net.URL;
import java.util.prefs.Preferences;

public class ThemeManager {
    private static final String DEFAULT_THEME = "light.css";

    public static void applyTheme(Scene scene) {
        Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);
        String theme = prefs.get("theme", DEFAULT_THEME);
        setTheme(scene, theme);
    }

    public static void toggleTheme(Scene scene) {
        String current = scene.getStylesheets().isEmpty() ? "" : scene.getStylesheets().get(0);
        String newTheme = current.contains("light.css") ? "dark.css" : "light.css";

        setTheme(scene, newTheme);
        Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);
        prefs.put("theme", newTheme);
    }

    private static void setTheme(Scene scene, String themeName) {
        URL url = ThemeManager.class.getResource("/css/" + themeName);
        if (url != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(url.toExternalForm());
        } else {
            System.err.println("⚠️ Тема не найдена: " + themeName);
        }
    }
}
