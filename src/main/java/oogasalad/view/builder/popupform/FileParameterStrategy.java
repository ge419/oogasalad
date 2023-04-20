package oogasalad.view.builder.popupform;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.inject.assistedinject.Assisted;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.attribute.BooleanMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

import javax.inject.Inject;

//class FileParameterStrategy implements ParameterStrategy, BuilderUtility {
//    private final FileAttribute attr;
//    private final FileMetadata meta;
//    private Optional<File> file;
//    @Inject
//    public FileParameterStrategy(
//            @Assisted Attribute attr,
//            @Assisted Metadata meta) {
//        this.attr = FileAttribute.from(attr);
//        this.meta = FileMetadata.from(meta);
//    }
//    @Override
//    public Node renderInput(ResourceBundle resourceBundle) {
//        String name = meta.getName();
//        Node textLabel = new Text(name);
//        Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                file = fileLoad(resourceBundle, "UploadFileTitle");
//            }
//        });
//        return makeHBox(String.format("%sFileInput", name), textLabel, element);
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
//    private File getFieldValue() {
//        return file;
//    }
//}