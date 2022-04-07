package com.mathieu.taskmanager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

public class DispatchDialogController {
    @FXML
    private Label userName ;
    @FXML
    private ComboBox<Task> combo_box;
    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_canc;


    private Stage dialogStage;
    private User user ;
    private boolean okClicked = false;

    public DispatchDialogController() {
        this.user = new User(null,0,null);
    }

    @FXML
    private void initialize() {
        setUser(user);
        Affiche();
    }

    public void setDialogStage(Stage dialogStage) {

        this.dialogStage = dialogStage;
    }


    public void setUser(User user) {

        System.out.println(user.getId());
        this.user = user;

        userName.setText(user.getFullName());
    }







    public boolean isOkClicked() {
        return okClicked;
    }

    public ObservableList<Task> getTask() throws ClassNotFoundException, SQLException  {
        ObservableList<Task> task= FXCollections.observableArrayList();
        System.out.println(this.user.getId());
           int id = user.getId();
           String select = "SELECT * from task where category_id = ? ";
           Connection connection = DbConnection.getMySQLConnection();

           try {

               PreparedStatement st = connection.prepareStatement(select);
               st.setInt(1, id);
               ResultSet rs = st.executeQuery();
               while (rs.next()) {
                   Task ta = new Task(null, null, 0);
                   ta.setTaskName(rs.getString("TaskName"));
                   ta.setId(rs.getInt("id"));
                   task.add(ta);
               }
           } catch (SQLException ex) {

           }
        return task;
    }
    public void Affiche () {
        ObservableList<Task> task = null;
        try {
            task = getTask();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        combo_box.setItems(task);
    }



    @FXML
    private void handleOk(ActionEvent event ) throws ClassNotFoundException, SQLException {
        if (isInputValid()) {
            int id = user.getId();
            int idTask =combo_box.getSelectionModel().getSelectedItem().getId();
                Connection connection = DbConnection.getMySQLConnection();
                String update = "UPDATE user SET task_id =? where id =?";
                try {
                    PreparedStatement st = connection.prepareStatement(update);
                    st.setInt(1, idTask);
                    st.setInt(2, id);
                    st.executeUpdate();
                } catch (SQLException ex) {

            }
            okClicked = true;
            dialogStage.close();
        }
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";
        int selectedIndex = combo_box.getSelectionModel().getSelectedIndex();
        if (selectedIndex ==0) {
            errorMessage += "No valid Task name!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}


