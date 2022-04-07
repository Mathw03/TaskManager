package com.mathieu.taskmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.sql.*;

public class TaskEditDialogController {

    @FXML
    private TextField taskName;
    @FXML
    private TextField taskDescri;
    @FXML
    private ComboBox<Category> combo_box;
    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_canc;

    private Stage dialogStage;
    private Task task ;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        Affiche();
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setTask(Task task) {
        this.task = task;

        taskName.setText(task.getTaskName());
        taskDescri.setText(task.getTaskDescription());
        combo_box.getSelectionModel().select(task.getCategory());
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
                cat.setId(rs.getInt("id"));
                category.add(cat);
            }
        } catch (SQLException ex) {

        }
        return category;
    }
    public void Affiche() {
        ObservableList<Category> category = null;
        try {
            category = getCategory();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        combo_box.setItems(category);
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOk(ActionEvent event ) throws ClassNotFoundException, SQLException {
        if (isInputValid()) {
            int id =task.getId();
            String taskNam = taskName.getText();
            String taskDesc = taskDescri.getText();
            int idCat =combo_box.getSelectionModel().getSelectedItem().getId();
            if (id==0) {

                Connection connection = DbConnection.getMySQLConnection();
                Statement statement = connection.createStatement();
                String sql = "Insert into task (taskName,taskDescription,category_id)"
                        + " values ('"+taskNam+"','"+taskDesc+"','"+idCat+"') ";
                statement.executeUpdate(sql);
            }else {

                Connection connection = DbConnection.getMySQLConnection();
                String update = "UPDATE task SET taskName =? ,taskDescription=? ,category_id=? where id =?";
                try {
                    PreparedStatement st = connection.prepareStatement(update);
                    st.setString(1, taskNam);
                    st.setString(2, taskDesc);
                    st.setInt(3, idCat);
                    st.setInt(4, id);
                    st.executeUpdate();
                } catch (SQLException ex) {

                }
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

        if (taskName.getText() == null || taskName.getText().length() == 0) {
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
