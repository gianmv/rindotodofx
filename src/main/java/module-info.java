open module rindotodofx {
    requires spring.boot;
    requires javafx.graphics;
    requires spring.context;
    requires javafx.fxml;
    requires javafx.controls;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.beans;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires com.sandec.mdfx;
    requires org.fxmisc.flowless;
    requires org.fxmisc.richtext;
    requires org.fxmisc.undo;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires reactfx;

    exports com.move.rindotodofx;
}