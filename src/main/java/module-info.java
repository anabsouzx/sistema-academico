module com.aninha.sistemaacademicojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.aninha.sistemaacademicojavafx to javafx.fxml;
    opens com.aninha.sistemaacademicojavafx.visao to javafx.fxml;
    opens com.aninha.sistemaacademicojavafx.modelo to javafx.base, javafx.fxml;
    exports com.aninha.sistemaacademicojavafx;
}