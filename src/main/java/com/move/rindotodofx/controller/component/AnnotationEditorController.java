package com.move.rindotodofx.controller.component;

import com.move.rindotodofx.model.database.Annotation;
import com.move.rindotodofx.model.database.Tag;
import com.move.rindotodofx.repository.AnnotationRepository;
import com.move.rindotodofx.repository.TagRepository;
import com.move.rindotodofx.util.SpringJavafxViewsHelper;
import com.move.rindotodofx.util.Views;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.util.StringConverter;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static org.controlsfx.control.textfield.TextFields.bindAutoCompletion;

@Component
@Scope("prototype")
public class AnnotationEditorController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationEditorController.class);

    private final AnnotationRepository annotationRepository;
    private final TagRepository tagRepository;
    private final SpringJavafxViewsHelper springJavafxViewsHelper;

    public AnnotationEditorController(AnnotationRepository annotationRepository, TagRepository tagRepository, SpringJavafxViewsHelper springJavafxViewsHelper) {
        this.annotationRepository = annotationRepository;
        this.tagRepository = tagRepository;
        this.springJavafxViewsHelper = springJavafxViewsHelper;
    }

    @FXML
    public AnchorPane mainAnchorPane;
    private CodeArea codeArea;
    @FXML
    private TextField tagTextField;
    @FXML
    private ListView<Tag> tagListView;
    @FXML
    private DatePicker dueDateDatePicker;
    @FXML
    private TextField priorityTextField;
    private final ObservableList<Tag> allTags = FXCollections.observableArrayList();

    private Annotation annotation;

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
                newValue.getAccelerators().put(kc, () -> {
                    saveAnnotation();
                    LOG.debug("CTRL+S and saveAnnotation");
                });
            }
        });
        this.dueDateDatePicker.valueProperty().setValue(LocalDate.now());
        this.priorityTextField.textProperty().setValue("1");

        this.tagListView.setItems(allTags);
        this.tagListView.setCellFactory(tagListView1 -> new ListCell<>() {
            @Override
            public void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (tag != null) {
                    setText(null);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    Pair<? extends Node, TagViewController> pair = springJavafxViewsHelper.loadComponent(Views.TAG_VIEW);
                    pair.getValue().setTag(tag);
                    pair.getValue().setOnActionButton(a -> deleteTagFromAnnotation(a));
                    Region region = (Region) pair.getKey();
                    setGraphic(region);
                }
            }
        });

        bindAutoCompletion(
                tagTextField,
                iSuggestionRequest -> getTagSuggestion(iSuggestionRequest.getUserText())
                ,
                new StringConverter<>() {
                    @Override
                    public String toString(Tag tag) {
                        return tag.getTagName();
                    }

                    @Override
                    public Tag fromString(String s) {
                        List<Tag> temp = tagRepository.findByTagNameStartsWithIgnoreCaseOrAbbreviationStartsWithIgnoreCase(s, s);
                        return temp.isEmpty() ? null : temp.get(0);
                    }
                }
        ).setOnAutoCompleted(tagAutoCompletionEvent -> {
            Platform.runLater(() -> {
                tagTextField.clear();
                addTagByAutoComplete(tagAutoCompletionEvent.getCompletion());
            });
        });
    }

    private Collection<Tag> getTagSuggestion(String userText) {
        List<Tag> allSuggestion = this.tagRepository.findByTagNameStartsWithIgnoreCaseOrAbbreviationStartsWithIgnoreCase(userText, userText);
        allSuggestion.removeAll(annotation.getTags());
        return allSuggestion;
    }

    private void addTagByAutoComplete(Tag completion) {
        if (!annotation.getTags().contains(completion)) {
            annotation.getTags().add(completion);
            setAnnotation(annotationRepository.save(annotation));
        }

    }

    @FXML
    public void handleNewTag(ActionEvent actionEvent) {
        String tagName = tagTextField.getText();
        Optional<Tag> tagOp = tagRepository.findFirstByTagNameIgnoreCaseOrAbbreviationIgnoreCase(tagName, tagName);
        if (tagOp.isEmpty()) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            tag.setAbbreviation(tagTextField.getText());
            tag.setColor("#C2EFEB");
            tag = tagRepository.save(tag);
            annotation.getTags().add(tag);
            setAnnotation(annotationRepository.save(annotation));
        } else {
            addTagByAutoComplete(tagOp.get());
        }

    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        Platform.runLater(() -> initializeWithAnnotation(annotation));
    }

    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    public String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }

    public void initializeWithAnnotation(Annotation annotation) {
        this.codeArea.replaceText(annotation.getMdText());
        this.allTags.clear();
        this.allTags.addAll(annotation.getTags());
        this.priorityTextField.textProperty().setValue("" + annotation.getPriority());
        this.dueDateDatePicker.valueProperty().setValue(annotation.getDueDate().toLocalDate());
    }

    public void saveAnnotation() {
        if (annotation != null) {
            annotation.setMdText(codeArea.getText());
            annotation.setTags(new LinkedHashSet<>(allTags));
            setAnnotation(annotationRepository.save(annotation));
        }
    }

    public void deleteTagFromAnnotation(Tag tag) {
        annotation.getTags().remove(tag);
        setAnnotation(annotationRepository.save(annotation));
    }
}
