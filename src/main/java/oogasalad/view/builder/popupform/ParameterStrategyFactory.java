package oogasalad.view.builder.popupform;

import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;

public interface ParameterStrategyFactory {

  BindingParameterStrategy buildBindingParameter(Attribute attr, Metadata meta);

  TextParameterStrategy buildTextParameter(Attribute attr, Metadata meta);

  IntegerParameterStrategy buildIntegerParameter(Attribute attr, Metadata meta);

  DoubleParameterStrategy buildDoubleParameter(Attribute attr, Metadata meta);

  PositionParameterStrategy buildPositionParameter(Attribute attr, Metadata meta);

  TileParameterStrategy buildTileParameter(Attribute attr, Metadata meta);

  TileListParameterStrategy buildTileListParameter(Attribute attr, Metadata meta);

  ColorParameterStrategy buildColorParameter(Attribute attr, Metadata meta);

  ImageParameterStrategy buildImageParameter(Attribute attr, Metadata meta);
}
