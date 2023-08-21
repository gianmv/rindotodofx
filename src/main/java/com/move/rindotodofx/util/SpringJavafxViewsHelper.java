package com.move.rindotodofx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpringJavafxViewsHelper {
    private Stage mainStage;
    private final ApplicationContext applicationContext;

    public SpringJavafxViewsHelper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void changeViewFromSpringContext(Views view) {
        try {
            FXMLLoader loader = new FXMLLoader(view.getFxmlUrl());
            loader.setControllerFactory(this.applicationContext::getBean);
            Parent root = loader.load();
            this.mainStage.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeView(Views view) {
        try {
            FXMLLoader loader = new FXMLLoader(view.getFxmlUrl());
            loader.setControllerFactory(this.applicationContext::getBean);
            Parent root = loader.load();
            this.mainStage.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Pair<? extends Node, T>  loadComponentFromSpringContext(Views view) {
        try {
            FXMLLoader loader = new FXMLLoader(view.getFxmlUrl());
            loader.setControllerFactory(this.applicationContext::getBean);
            Parent root = loader.load();
            T controller = loader.getController();
            Pair<? extends Node, T> ans = new Pair<>(root, controller);
            return ans;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Pair<? extends Node, T>  loadComponent(Views view) {
        try {
            FXMLLoader loader = new FXMLLoader(view.getFxmlUrl());
            Parent root = loader.load();
            T controller = loader.getController();
            Pair<? extends Node, T> ans = new Pair<>(root, controller);
            return ans;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initScene(Views view) {
        try {
            FXMLLoader loader = new FXMLLoader(view.getFxmlUrl());
            loader.setControllerFactory(this.applicationContext::getBean);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            this.mainStage.setTitle("RindoTodoFx App");
            this.mainStage.setScene(scene);
            this.mainStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
