module com.example.financecontrol {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.financecontrol to javafx.fxml;
    exports com.example.financecontrol;
    exports com.example.financecontrol.expensesview;
    exports com.example.financecontrol.incomeview;
    exports com.example.financecontrol.dbmodels;
    opens com.example.financecontrol.expensesview to javafx.fxml;
    opens com.example.financecontrol.incomeview to javafx.fxml;
    opens com.example.financecontrol.dbmodels to javafx.fxml;
}