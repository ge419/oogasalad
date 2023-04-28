package oogasalad.view.builder.customTile;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class CustomElementHelper extends VBox {


    public static VBox makeVbox(Node thisCustomElement, List<Node> imageSpecificNodes) {

        // Create buttons to send image to front or back
        Button toFrontButton = new Button("Send to Front");
        toFrontButton.setOnAction(event -> thisCustomElement.toFront());
        Button toBackButton = new Button("Send to Back");
        toBackButton.setOnAction(event -> thisCustomElement.toBack());
        imageSpecificNodes.addAll(Arrays.asList(toFrontButton, toBackButton));

        VBox infoBox = new VBox();
        infoBox.getChildren().addAll(imageSpecificNodes);

        return infoBox;
    }
}
