package com.mathieu.taskmanager;

import com.mathieu.taskmanager.Category;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {

    protected SimpleStringProperty username;
    protected SimpleStringProperty password;
    protected SimpleStringProperty fullName;
    protected Category category;
    protected SimpleIntegerProperty id;

    public User( String fullName, int id, Category category ) {

        this.category= category;
        this.fullName = new SimpleStringProperty(fullName);
        this.id = new SimpleIntegerProperty(id);
    }



    public void setUsername(String username) {
        this.username = new SimpleStringProperty (username);
    }
    public void setPassword(String password) {
        this.password = new SimpleStringProperty (password);
    }
    public void setFullName(String firstName) {
        this.fullName = new SimpleStringProperty (firstName);
    }

    public void setId(int id) {
        this.id =new SimpleIntegerProperty (id);
    }
    public int getId() {
        return id.get();
    }

    public Category getCategory()
    {

        return this.category;
    }

    public void setCategory(Category category){

        this.category = category;

    }
    public String getUsername() {
        return username.get();
    }
    public String getFullName() {
        return fullName.get();
    }

}
