package com.move.rindotodofx.controller.component;

import com.move.rindotodofx.model.database.Annotation;
import com.sandec.mdfx.MarkdownView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationResumeController implements Initializable {

    private Annotation annotation;

    private Consumer<Annotation> deleteAction;
    private Consumer<Annotation> editAction;

    private Consumer<Annotation> saveAction;

    @FXML
    private StackPane mainStackPane;
    @FXML
    private DatePicker dueDateDatePicker;
    @FXML
    private TextField priorityTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    private final MarkdownView markdownView = new MarkdownView();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        markdownView.prefHeightProperty().bind(mainStackPane.heightProperty());
        markdownView.prefWidthProperty().bind(mainStackPane.widthProperty());
        mainStackPane.getChildren().add(markdownView);
        dueDateDatePicker.valueProperty().addListener(ev -> {
            if (annotation != null && !dueDateDatePicker.getValue().atStartOfDay().isEqual(annotation.getDueDate())) {
                annotation.setDueDate(dueDateDatePicker.getValue().atStartOfDay());
                saveAction.accept(annotation);
            }
        });
        priorityTextField.setOnAction(ev -> {
            if (annotation != null) {
                annotation.setPriority(Integer.valueOf(priorityTextField.getText(), 10));
                saveAction.accept(annotation);
            }
        });

        deleteButton.setOnAction(ev -> {
            if (annotation != null) {
                deleteAction.accept(annotation);
            }
        });
        editButton.setOnAction(ev -> {
            if (annotation != null) {
                editAction.accept(annotation);
            }
        });

    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        Platform.runLater(() -> {
            String mdString = Stream.ofNullable(annotation.getMdText())
                    .map(s -> s.split("\\R"))
                    .flatMap(Stream::of)
                    .limit(3)
                    .collect(Collectors.joining("\n"));
            markdownView.setMdString(mdString);
            dueDateDatePicker.valueProperty().setValue(annotation.getDueDate() != null ? annotation.getDueDate().toLocalDate() : LocalDate.now());
            priorityTextField.setText(annotation.getPriority() != null ? String.valueOf(annotation.getPriority()) : "");
        });
    }

    public Consumer<Annotation> getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(Consumer<Annotation> deleteAction) {
        this.deleteAction = deleteAction;
    }

    public Consumer<Annotation> getEditAction() {
        return editAction;
    }

    public void setEditAction(Consumer<Annotation> editAction) {
        this.editAction = editAction;
    }

    public Consumer<Annotation> getSaveAction() {
        return saveAction;
    }

    public void setSaveAction(Consumer<Annotation> saveAction) {
        this.saveAction = saveAction;
    }
}
