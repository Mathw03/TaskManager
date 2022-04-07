package com.mathieu.taskmanager;

import javafx.application.Application;
import javafx.stage.Stage;

public class TaskManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        try {

            DisplayController.setSceneContentStartup(stage);


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}