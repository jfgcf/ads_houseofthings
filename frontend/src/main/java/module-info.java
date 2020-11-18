module ads.houseofthings {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires com.google.gson;
    requires java.net.http;
    requires guava;

    opens ads.houseofthings to javafx.fxml, javafx.base, com.google.gson;
    exports ads.houseofthings.model.domain;
    exports ads.houseofthings;
}