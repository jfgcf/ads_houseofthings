package ads.houseofthings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        Parent root = FXMLLoader.load(getClass().getResource("HomeView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("House Of Things - ADS Group 2");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}