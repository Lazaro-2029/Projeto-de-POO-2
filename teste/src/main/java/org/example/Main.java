package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/TelaLogin.fxml"));
        Scene cena = new Scene(root);
        primaryStage.setTitle("Editora Publixy");
        primaryStage.setScene(cena);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}



