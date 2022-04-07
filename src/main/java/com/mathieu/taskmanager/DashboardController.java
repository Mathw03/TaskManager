package com.mathieu.taskmanager;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController extends DisplayController implements Initializable {


    @FXML
    private Button btn_logout ;
    @FXML
    private TableView<Task> taskList;
    @FXML
    private TableColumn<Task, String> taskNames  ;
    @FXML
    private TableColumn<Task, String> category ;
    @FXML
    private TableView<Category> categorylist;
    @FXML
    private TableColumn<Category, String>categoryName  ;
    @FXML
    private TableColumn<Category, String> categoryDescri ;
    @FXML
    private TableView<User> userList;
    @FXML
    private TableColumn<User, String> userfullName  ;
    @FXML
    private TableColumn<User, String> userCategory ;
    @FXML
    private TableView<User> userFreeList;
    @FXML
    private TableColumn<User, String> userFreefullName  ;
    @FXML
    private TableColumn<User, String> userFreeCategory ;
    @FXML
    private Label timerLabel;
    @FXML
    private ProgressBar progressBar;






    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Affiche();
        AfficheCat();
        AfficheUser();
        AfficheFreeUser();
    }

    public ObservableList<Task> getTask() throws ClassNotFoundException, SQLException {
        ObservableList<Task> task = FXCollections.observableArrayList();

        String select = "SELECT * from task";
        String selectcat = "SELECT * from category where id=?";

        Connection connection = DbConnection.getMySQLConnection();

        try {
            Statement statement = connection.createStatement();
            PreparedStatement st = connection.prepareStatement(selectcat);

            ResultSet rs = statement.executeQuery(select);

            while (rs.next()) {
                Task ta = new Task(null, null, 0);
                Category ca = new Category(null, null,0);

                int a= (rs.getInt("category_id" ));
                st.setInt(1, a);
                ResultSet rss = st.executeQuery();
                while (rss.next()){
                    ca.setCategoryDescription(rss.getString("categoryDescription"));
                    ca.setCategoryName(rss.getString("categoryName"));
                    ca.setId(rss.getInt("id"));
                }

                ta.setTaskName(rs.getString("taskName"));
                ta.setTaskDescription(rs.getString("taskDescription"));
                ta.setId(rs.getInt("id"));
                ta.setCategory(ca);

                task.add(ta);
            }
        } catch (SQLException ex) {

        }
        return task;
    }
    public ObservableList<Category> getCategory() throws ClassNotFoundException, SQLException  {
        ObservableList<Category> category= FXCollections.observableArrayList();

        String select = "SELECT * from category";
        Connection connection = DbConnection.getMySQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                Category cat = new Category(null, null, 0);
                cat.setCategoryName(rs.getString("CategoryName"));
                cat.setCategoryDescription(rs.getString("CategoryDescription"));
                cat.setId(rs.getInt("id"));
                category.add(cat);
            }
        } catch (SQLException ex) {

        }
        return category;
    }
    public ObservableList<User> getUser() throws ClassNotFoundException, SQLException  {
        ObservableList<User> user= FXCollections.observableArrayList();

        String select = "SELECT * from user";
        String selectcat = "SELECT * from category where id=?";

        Connection connection = DbConnection.getMySQLConnection();
        PreparedStatement st = connection.prepareStatement(selectcat);

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                User us = new User(null,0,null);
                Category ca = new Category(null,null,0);

                us.setFullName(rs.getString("fullName"));

                int a= (rs.getInt("category_id" ));
                st.setInt(1, a);
                ResultSet rss = st.executeQuery();
                while (rss.next()){
                    ca.setCategoryDescription(rss.getString("categoryDescription"));
                    ca.setCategoryName(rss.getString("categoryName"));
                    ca.setId(rss.getInt("id"));
                }

                us.setCategory(ca);
                us.setId(rs.getInt("id"));
                user.add(us);
            }
        } catch (SQLException ex) {

        }
        return user;
    }

    public ObservableList<User> getFreeUser() throws ClassNotFoundException, SQLException  {
        ObservableList<User> user= FXCollections.observableArrayList();

        String select = "SELECT * from user where task= null";
        Connection connection = DbConnection.getMySQLConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                User us = new User(null,0,null);
                us.setFullName(rs.getString("fullName"));
                //us.setCategory(rs.getInt("category_id"));
                us.setId(rs.getInt("id"));
                user.add(us);
            }
        } catch (SQLException ex) {

        }
        return user;
    }
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNew() {
        Task tempTask = new Task("","", 0);
        boolean okClicked = DisplayController.showTaskEditDialog(tempTask);
        if (okClicked) {
            Affiche();
        }
    }
    @FXML
    private void handleNewCat() {
        Category tempCat = new Category("","", 0);
        boolean okClicked = DisplayController.showCategoryEditDialog(tempCat);
        if (okClicked) {
            AfficheCat();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEdit() {
        Task selectedTask = taskList.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            boolean okClicked = DisplayController.showTaskEditDialog(selectedTask);
            if (okClicked) {
                Affiche();
            }

        } else {
            // Nothing selected.
            Stage currentStage = Context.getInstance().getCurrentStage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(currentStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");

            alert.showAndWait();
        }
    }
    @FXML
    private void handleEditCat() {
        Category selectedCat = categorylist.getSelectionModel().getSelectedItem();
        if (selectedCat != null) {
            boolean okClicked = DisplayController.showCategoryEditDialog(selectedCat);
            if (okClicked) {
                AfficheCat();
            }

        } else {
            // Nothing selected.
            Stage currentStage = Context.getInstance().getCurrentStage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(currentStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Category Selected");
            alert.setContentText("Please select a category in the table.");

            alert.showAndWait();
        }
    }


    @FXML
    private void handleDelete() throws ClassNotFoundException, SQLException {
        int selectedIndex = taskList.getSelectionModel().getSelectedIndex();


        if (selectedIndex >= 0) {
            int id =taskList.getSelectionModel().getSelectedItem().getId();
            Connection connection = DbConnection.getMySQLConnection();
            String delete = "DELETE FROM  task  where task.id = ?";
            try {
                PreparedStatement st = connection.prepareStatement(delete);
                st.setInt(1, id);
                st.executeUpdate();
                Affiche();
            } catch (SQLException ex) {

            }
        } else {
            // Nothing selected.
            Stage currentStage = Context.getInstance().getCurrentStage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(currentStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");

            alert.showAndWait();
        }
    }


    @FXML
    private void handleDeleteUser() throws ClassNotFoundException, SQLException {
        int selectedIndex = userList.getSelectionModel().getSelectedIndex();


        if (selectedIndex >= 0) {
            int id =userList.getSelectionModel().getSelectedItem().getId();
            Connection connection = DbConnection.getMySQLConnection();
            String delete = "DELETE FROM  user  where user.id = ?";
            try {
                PreparedStatement st = connection.prepareStatement(delete);
                st.setInt(1, id);
                st.executeUpdate();
                AfficheUser();
            } catch (SQLException ex) {

            }
        } else {
            // Nothing selected.
            Stage currentStage = Context.getInstance().getCurrentStage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(currentStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No User Selected");
            alert.setContentText("Please select a user in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteCat() throws ClassNotFoundException, SQLException {
        int selectedIndex = categorylist.getSelectionModel().getSelectedIndex();


        if (selectedIndex >= 0) {
            int id =categorylist.getSelectionModel().getSelectedItem().getId();
            Connection connection = DbConnection.getMySQLConnection();
            String delete = "DELETE FROM  category  where category.id = ?";
            try {
                PreparedStatement st = connection.prepareStatement(delete);
                st.setInt(1, id);
                st.executeUpdate();
                AfficheCat();
            } catch (SQLException ex) {

            }
        } else {
            // Nothing selected.
            Stage currentStage = Context.getInstance().getCurrentStage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(currentStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Category Selected");
            alert.setContentText("Please select a category in the table.");

            alert.showAndWait();
        }
    }
    public void Affiche() {
        ObservableList<Task> task = null;
        try {
            task = getTask();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        taskNames.setCellValueFactory(cellData -> cellData.getValue().taskName);
        category.setCellValueFactory(cellData -> cellData.getValue().category.categoryName);

        taskList.setItems(task);
    }

    public void AfficheCat() {
        ObservableList<Category> category = null;
        try {
            category = getCategory();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        categoryName.setCellValueFactory(cellData -> cellData.getValue().categoryName);
        categoryDescri.setCellValueFactory(cellData -> cellData.getValue().categoryDescription);

        categorylist.setItems(category);
    }

    public void AfficheUser() {
        ObservableList<User> user = null;
        try {
            user = getUser();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        userCategory.setCellValueFactory(cellData -> cellData.getValue().category.categoryName);
        userfullName.setCellValueFactory(cellData -> cellData.getValue().fullName);

        userList.setItems(user);
    }

    public void AfficheFreeUser() {
        ObservableList<User> user = null;
        try {
            user = getUser();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        userFreeCategory.setCellValueFactory(cellData -> cellData.getValue().category.categoryName);
        userFreefullName.setCellValueFactory(cellData -> cellData.getValue().fullName);

        userFreeList.setItems(user);
    }
    @FXML
    private void handleDispatch() {
        User selectedUser = userFreeList.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean okClicked = DisplayController.showDispatchDialog(selectedUser);
            if (okClicked) {
            }

        } else {
            // Nothing selected.
            Stage currentStage = Context.getInstance().getCurrentStage();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(currentStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No User Selected");
            alert.setContentText("Please select an user in the table.");

            alert.showAndWait();
        }

    }
    public void logout (ActionEvent event) {

        try {
            setSceneContent("login-view.fxml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
