/**
 * classes, methods and libraries required for the project
 */
module  com.example.financecontrol {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires com.google.gson;
    requires itextpdf;
    requires org.apache.poi.poi;

    opens com.example.financecontrol;
    exports com.example.financecontrol;
    exports com.example.financecontrol.expensesview;
    exports com.example.financecontrol.incomeview;
    exports com.example.financecontrol.dbmodels;
    opens com.example.financecontrol.expensesview;
    opens com.example.financecontrol.incomeview;
    opens com.example.financecontrol.dbmodels;
    exports com.example.financecontrol.settingsview;
    opens com.example.financecontrol.settingsview;
    opens com.example.financecontrol.utils;
}