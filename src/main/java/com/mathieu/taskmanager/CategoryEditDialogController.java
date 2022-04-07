package com.mathieu.taskmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryEditDialogController {
    @FXML
    private TextField catName;
    @FXML
    private TextField catDescri;
    @FXML
    private Button btn_ok;
    @FXML
    private Button btn_canc;

    private Stage dialogStage;
    private Category category ;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setCategory(Category category) {
        this.category = category;

        catName.setText(category.getCategoryName());
        catDescri.setText(category.getCategoryDescription());

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOk(ActionEvent event ) throws ClassNotFoundException, SQLException {
        if (isInputValid()) {
            int id =category.getId();
            String cateName =catName.getText();
            String cateDescri = catDescri.getText();

            if (id==0) {

                Connection connection = DbConnection.getMySQLConnection();
                Statement statement = connection.createStatement();
                String sql = "Insert into category (categoryName,categoryDescription)"
                        + " values ('"+cateName+"', '"+cateDescri+"' ) ";
                statement.executeUpdate(sql);
            }else {

                Connection connection = DbConnection.getMySQLConnection();
                String update = "UPDATE category SET categoryName =?,categoryDescription = ? where id =?";
                try {
                    PreparedStatement st = connection.prepareStatement(update);
                    st.setString(1, cateName);
                    st.setString(2, cateDescri);
                    st.setInt(3, id);
                    st.executeUpdate();
                } catch (SQLException ex) {

                }
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (catName.getText() == null || catName.getText().length() == 0) {
            errorMessage += "No valid category name!\n";
        }
        if (catDescri.getText() == null || catDescri.getText().length() == 0) {
            errorMessage += "No valid category Description!\n";
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
