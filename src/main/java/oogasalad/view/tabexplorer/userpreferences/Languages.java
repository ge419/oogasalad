package oogasalad.view.tabexplorer.userpreferences;

import java.util.Locale;

public enum Languages {

  ENGLISH("en-US"),
  FRENCH("fr-FR"),
  SPANISH("es-ES"),
  KOREAN("ko-KR");

  private final String localeStr;

  Languages(String localeStr) {
    this.localeStr = localeStr;
  }

  public String getLocaleStr() {
    return Locale.forLanguageTag(localeStr).toLanguageTag();
  }
}

