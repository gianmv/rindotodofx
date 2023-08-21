package com.move.rindotodofx.util;

import java.net.URL;

public enum Views {
    MAIN("/views/Main.fxml"),
    ANNOTATION_EDITOR("/views/component/AnnotationEditor.fxml"),
    ANNOTATION_RESUME("/views/component/AnnotationResume.fxml"),
    ANNOTATION_LIST("/views/AnnotationResumeList.fxml");

    private final URL fxmlUrl;
    Views(String fxmlResource) {
        this.fxmlUrl = Views.class.getResource(fxmlResource);
    }

    public URL getFxmlUrl() {
        return fxmlUrl;
    }
}
