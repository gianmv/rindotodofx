package com.move.rindotodofx.listener;

import com.move.rindotodofx.RindoTodoFxJavaFx;
import com.move.rindotodofx.util.SpringJavafxViewsHelper;
import com.move.rindotodofx.util.Views;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<RindoTodoFxJavaFx.StageReadyEvent> {

    private final SpringJavafxViewsHelper springJavafxViewsHelper;

    public StageInitializer(SpringJavafxViewsHelper springJavafxViewsHelper) {
        this.springJavafxViewsHelper = springJavafxViewsHelper;
    }

    @Override
    public void onApplicationEvent(RindoTodoFxJavaFx.StageReadyEvent event) {
        Stage stage = event.getStage();
        this.springJavafxViewsHelper.setMainStage(stage);
        this.springJavafxViewsHelper.initScene(Views.ANNOTATION_LIST);
    }
}
