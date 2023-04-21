package oogasalad.view.builder.popupform;

import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;

public interface ParameterStrategyFactory {

  TextParameterStrategy buildTextParameter(Attribute attr, Metadata meta);

  IntegerParameterStrategy buildIntegerParameter(Attribute attr, Metadata meta);

  DoubleParameterStrategy buildDoubleParameter(Attribute attr, Metadata meta);
  PositionParameterStrategy buildPositionParameter(Attribute attr, Metadata meta);
  TileParameterStrategy buildTileParameter(Attribute attr, Metadata meta);
}
