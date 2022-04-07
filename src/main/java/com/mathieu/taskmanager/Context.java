package com.mathieu.taskmanager;

import javafx.stage.Stage;

public class Context {

    //Create a constant of the same type as the class and initialize it
    private static Context instance = new Context();

    //Create a public static function to retrieve said constant
    public static Context getInstance(){
        return instance == null ? new Context() : instance;
    }


    //creation d'un constant de type stage
    private static Stage currentStage = new Stage();

    //getter de stage
    public Stage getCurrentStage() {
        return currentStage;
    }
    //setter de stage
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }


    private User user;

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser() {
        return this.user;
    }

}
