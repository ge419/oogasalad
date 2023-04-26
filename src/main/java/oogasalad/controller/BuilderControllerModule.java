package oogasalad.controller;

import com.google.inject.AbstractModule;
import oogasalad.view.builder.BuilderModule;

public class BuilderControllerModule extends AbstractModule {

  private String myLanguage;

  BuilderControllerModule(String givenLanguage){
    this.myLanguage = givenLanguage;
  }

  @Override
  protected void configure(){
    bind(GameHolderBuilder.class).toInstance(new GameHolderBuilder());
  }
}
