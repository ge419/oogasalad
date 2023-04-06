package oogasalad.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public abstract class Matcher {
    private final List<Entry<String, Pattern>> map;

    public Matcher() {
      map = new ArrayList<>();
    }

    private void setPatterns(String function) {
      ResourceBundle resources = ResourceBundle.getBundle(function);
      for (String key : Collections.list(resources.getKeys())) {
        map.add(new SimpleEntry<>(key,
            Pattern.compile(resources.getString(key), Pattern.CASE_INSENSITIVE)));
      }
    }

    public String getVariableType(String text) {
      for (Entry<String, Pattern> e : map) {
        if (match(text, e.getValue())) {
          return e.getKey();
        }
      }
      return null;
    }

    public Pattern getValue(String text) {
      for (Entry<String, Pattern> e : map) {
        if (text.equals(e.getKey())) {
          return e.getValue();
        }
      }
      return null;
    }

    private boolean match(String text, Pattern regex) {
      return text != null && regex.matcher(text.trim()).matches();
    }

}
