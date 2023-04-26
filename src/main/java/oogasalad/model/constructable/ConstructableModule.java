package oogasalad.model.constructable;

import com.google.inject.AbstractModule;
import java.nio.file.Path;


public class ConstructableModule extends AbstractModule {

  private final Path saveDir;

  public ConstructableModule(Path saveDir) {
    this.saveDir = saveDir;
  }

  @Override
  protected void configure() {
    bind(Path.class)
        .annotatedWith(SaveDirectory.class)
        .toInstance(saveDir);
  }
}
