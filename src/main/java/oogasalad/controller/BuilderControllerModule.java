package oogasalad.controller;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import oogasalad.view.BuilderFactory;
import oogasalad.view.builder.BuilderModule;

public class BuilderControllerModule extends AbstractModule {

  private String myLanguage;

  public BuilderControllerModule(String givenLanguage){
    this.myLanguage = givenLanguage;
  }

  @Override
  protected void configure(){
    //bind(GameHolderBuilder.class).toInstance(new GameHolderBuilder());
    bind(String.class).toInstance(myLanguage);
    install(new FactoryModuleBuilder().build(BuilderFactory.class));
//    install(new BuilderModule(myLanguage, this));
  }
}
