package oogasalad.view.tabexplorer.userpreferences;

/**
 * Supported themes for app.
 *
 * @author cgd
 */
public enum Theme {
  LIGHT("light"),
  DARK("dark"),
  DDMF("ddmf");

  private final String theme;

  Theme(String theme) {
    this.theme = theme;
  }

  public String getThemeValue() {
    return theme;
  }
}
