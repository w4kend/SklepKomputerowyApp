package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


//Klasa główna dziedzicząca
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    //metoda wywołająca okno z logowanie
    @Override
    public void start(Stage primaryStage) throws Exception {
        //podłączenie plików fxml
        Parent root = FXMLLoader.load(getClass().getResource("loginstage.fxml"));
        //nazwa tytułu okna
        primaryStage.setTitle("Winley:Authentication");
        primaryStage.setScene(new Scene(root, 1200, 560));
        primaryStage.show();
    }
}