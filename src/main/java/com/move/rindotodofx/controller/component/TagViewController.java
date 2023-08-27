package com.move.rindotodofx.controller.component;

import com.move.rindotodofx.model.database.Tag;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TagViewController implements Initializable {

    @FXML
    private Button actionButton;
    @FXML
    private Label tagLabel;
    @FXML
    private HBox containerHbox;

    private Tag tag;
    private Consumer<Tag> onActionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.actionButton.setOnAction(ev -> onActionButton.accept(tag));
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        Platform.runLater(() -> initializeObject(tag));
    }

    public Consumer<Tag> getOnActionButton() {
        return onActionButton;
    }

    public void setOnActionButton(Consumer<Tag> onActionButton) {
        this.onActionButton = onActionButton;
    }

    private void initializeObject(Tag tag) {
        tagLabel.setText(tag.getAbbreviation());
        containerHbox.setStyle("-fx-background-color: " + tag.getColor());
    }
}
