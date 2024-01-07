module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires junit;

    opens com.demo to javafx.fxml;
    exports com.demo;
    exports com.demo.test;
    exports com.demo.controller;
    opens com.demo.controller to javafx.fxml;
}