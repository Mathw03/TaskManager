package com.mathieu.taskmanager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {

    protected SimpleStringProperty taskName;
    protected SimpleStringProperty taskDescription;
    protected SimpleIntegerProperty id;
    protected Category category;
    private static final Integer startTime = 15;
    private  IntegerProperty timeSeconds = new SimpleIntegerProperty(startTime*100);





    public Task(String taskName, String taskDescription, int id) {
        this.taskName=new SimpleStringProperty(taskName);
        this.taskDescription=new SimpleStringProperty(taskDescription);
        this.id= new SimpleIntegerProperty(id);
    }


    public String getTaskName() {

        return taskName.get();
    }
    public void setTaskName(String taskName) {

        this.taskName = new SimpleStringProperty (taskName);
    }

    public String getTaskDescription() {

        return taskName.get();
    }
    public void setTaskDescription(String taskDescription) {

        this.taskName = new SimpleStringProperty (taskDescription);
    }


    public int getId() {

        return id.get();
    }
    public void setId(int id) {

        this.id = new SimpleIntegerProperty (id);
    }

    public int getTimeSeconds() {

        return timeSeconds.get();
    }
    public void setTimeSeconds(int timeSeconds) {

        this.timeSeconds = new SimpleIntegerProperty (timeSeconds);
    }


    public Category getCategory() {

        return category;
    }
    public void setCategory(Category category) {

        this.category = category;
    }

    @Override
    public String toString()  {
        return  taskName.get();
    }

}
