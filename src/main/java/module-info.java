module com.demo.mazebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.demo.mazebank to javafx.fxml;
    exports com.demo.mazebank;
    exports com.demo.mazebank.Controllers;
    exports com.demo.mazebank.Controllers.Admin;
    exports com.demo.mazebank.Controllers.Client;
    exports com.demo.mazebank.Models;
    exports com.demo.mazebank.Views;
}