package com.move.rindotodofx.controller.component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AnnotationEditorController implements Initializable {

    @FXML
    public AnchorPane mainAnchorPane;
    private CodeArea codeArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codeArea = new CodeArea();
        codeArea.setStyle("-fx-font-family: consolas; -fx-font-size: 11pt;");
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        Region editor = new VirtualizedScrollPane<>(codeArea);
        editor.prefWidthProperty().bind(mainAnchorPane.widthProperty());
        editor.prefHeightProperty().bind(mainAnchorPane.heightProperty());
        mainAnchorPane.getChildren().add(editor);
        mainAnchorPane.sceneProperty().addListener((observableValue, oldValue, newValue) -> {
            KeyCombination kc = KeyCombination.keyCombination("CTRL+S");
            if (newValue != null) {
                newValue.getAccelerators().put(kc, () -> System.out.println("CTRL + S presionado"));
            } else {
                System.out.println("");
            }
        });
    }

    public void setText(String s) {
        this.codeArea.replaceText(s);
    }

    public String getText() {
        return this.codeArea.getText();
    }
}
