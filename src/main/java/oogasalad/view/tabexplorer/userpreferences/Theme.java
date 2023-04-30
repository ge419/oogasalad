package oogasalad.view.tabexplorer.userpreferences;

public enum Theme {
  LIGHT("light"),
  DARK("dark"),
  DDMF("ddmf");

  private final String theme;

  Theme(String theme) {
    this.theme = theme;
  }

  public String getThemeValue(){
    return theme;
  }
}
