package com.move.rindotodofx;

import com.move.rindotodofx.util.HostServicesProvider;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;


public class RindoTodoFxJavaFx extends Application  {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(RindotodofxApplication.class).run();
    }


    @Override
    public void start(Stage stage) throws Exception {
        HostServicesProvider.INSTANCE.init(getHostServices());
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    public static class StageReadyEvent extends ApplicationEvent {

        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
