package oogasalad.model.engine.event_loop;

import java.util.List;

public interface Choice {
  List<String> getOptions();
  void callback(String selectedOption);
}
