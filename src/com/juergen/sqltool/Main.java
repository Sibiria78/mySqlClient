/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juergen.sqltool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This project was created with help of:
 * +++++ youtube.com/watch?v=kpnnXit2br0
 * 
 * Download mysqlconnector for Java: https://downloads.mysql.com/archives/c-j/
 * Download of mySQL: chip.de
 * @author Juergen
 */
public class Main extends Application {
    
    private MainController controller;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("JavaFX SQL Tool");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
