package com.mathieu.taskmanager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Category {

    protected SimpleStringProperty categoryName;
    protected SimpleStringProperty categoryDescription;
    protected SimpleIntegerProperty id;


    public Category(String categoryName, String categoryDescription, int id) {
        this.categoryName=new SimpleStringProperty(categoryName);
        this.categoryDescription=new SimpleStringProperty(categoryDescription);
        this.id= new SimpleIntegerProperty(id);
    }
    public String getCategoryName() {
        return categoryName.get();
    }
    public String getCategoryDescription() {
        return categoryDescription.get();
    }
    public int getId() {
        return id.get();
    }



    public void setCategoryName(String categoryName) {
        this.categoryName = new SimpleStringProperty (categoryName);
    }
    public void setId(int id) {
        this.id = new SimpleIntegerProperty (id);
    }


    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = new SimpleStringProperty (categoryDescription);
    }

    @Override
    public String toString()  {
      return  categoryName.get();
    }


}
