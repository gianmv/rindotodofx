package com.move.rindotodofx.controller;

import com.move.rindotodofx.controller.component.AnnotationResumeController;
import com.move.rindotodofx.model.database.Annotation;
import com.move.rindotodofx.repository.AnnotationRepository;
import com.move.rindotodofx.util.SpringJavafxViewsHelper;
import com.move.rindotodofx.util.Views;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AnnotationResumeList implements Initializable {

    private final AnnotationRepository annotationRepository;
    private final SpringJavafxViewsHelper springJavafxViewsHelper;
    private final ObservableList<Annotation> allAnnotations = FXCollections.observableArrayList();

    @FXML
    ListView<Annotation> annotationListView;
    @FXML
    BorderPane borderPane;

    public AnnotationResumeList(AnnotationRepository annotationRepository, SpringJavafxViewsHelper springJavafxViewsHelper) {
        this.annotationRepository = annotationRepository;
        this.springJavafxViewsHelper = springJavafxViewsHelper;
        this.allAnnotations.addAll(annotationRepository.findAll());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.annotationListView.setItems(allAnnotations);
        this.annotationListView.setCellFactory(annotationListView1 -> {
            ListCell<Annotation> lc = new ListCell<>() {
                @Override
                public void updateItem(Annotation annotation, boolean empty) {
                    super.updateItem(annotation, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else if (annotation != null) {
                        setText(null);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        Pair<? extends Node, AnnotationResumeController> pair = springJavafxViewsHelper.loadComponent(Views.ANNOTATION_RESUME);
                        pair.getValue().setDeleteAction(a -> deleteAction(a));
                        pair.getValue().setEditAction(a -> editAction(a));
                        pair.getValue().setSaveAction(a -> saveAction(a));
                        pair.getValue().setAnnotation(annotation);
                        Region region = (Region) pair.getKey();
                        setGraphic(region);
                    }
                }
            };
            return lc;
        });

        annotationListView.sceneProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                annotationListView.prefWidthProperty().bind(newValue.widthProperty());
            }
        });
    }

    private void saveAction(Annotation annotation) {
        this.annotationRepository.save(annotation);
        reloadData();
    }

    private void deleteAction(Annotation annotation) {
        this.annotationRepository.delete(annotation);
        reloadData();
    }

    private void editAction(Annotation annotation) {

        reloadData();

    }

    private void reloadData() {
        this.allAnnotations.clear();
        this.allAnnotations.addAll(this.annotationRepository.findAll());
    }
}
