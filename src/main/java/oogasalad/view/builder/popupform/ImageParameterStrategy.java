package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.attribute.BooleanMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.ResourceBundle;

//class ImageParameterStrategy implements ParameterStrategy, BuilderUtility {
//    private final ImageAttribute attr;
//    private final ImageMetadata meta;
//    private Image image;
//    @Inject
//    public ImageParameterStrategy(
//            @Assisted Attribute attr,
//            @Assisted Metadata meta) {
//        this.attr = ImageAttribute.from(attr);
//        this.meta = ImageMetadata.from(meta);
//    }
//    @Override
//    public Node renderInput(ResourceBundle resourceBundle) {
//        String name = meta.getName();
//        Node textLabel = new Text(name);
//        Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Optional<File> file = fileLoad(resourceBundle, "UploadFileTitle");
//                try {
//                    image = new Image(new FileInputStream(file.get().getPath()));
//                } catch (FileNotFoundException e) {
//                    System.out.println("File for image not found");
//                }
//            }
//        });
//        return makeHBox(String.format("%sImageInput", name), textLabel, element);
//    }
//    @Override
//    public void saveInput() {
//        attr.setValue(getFieldValue());
//    }
//
//    @Override
//    public boolean isInputValid() {
//        return meta.isValidValue(getFieldValue());
//    }
//
//    @Override
//    public Metadata getMetadata() {
//        return meta;
//    }
//
//    @Override
//    public Attribute getAttribute() {
//        return attr;
//    }
//
//    private Image getFieldValue() {
//        return image;
//    }
//}
