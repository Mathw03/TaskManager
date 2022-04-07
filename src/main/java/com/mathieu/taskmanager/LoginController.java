package com.mathieu.taskmanager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;



public class LoginController extends DisplayController implements Initializable {

    @FXML
    private Button btn_signin ;
    @FXML
    private Button btn_signuppage ;
    @FXML
    private Button btn_forgotpassword ;
    @FXML
    private  TextField txt_username ;
    @FXML
    private  PasswordField txt_password ;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        txt_username.setOnKeyPressed((KeyEvent event) -> {
            if (null != event.getCode()) switch (event.getCode()) {
                case ENTER:
                    btn_signin.fire();
                    break;
                case UP:
                    txt_password.requestFocus();
                    break;
                case DOWN:
                    txt_password.requestFocus();
                    break;
                default:
                    break;
            }
        });
        txt_password.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                btn_signin.fire();
            } else if (event.getCode() == KeyCode.UP) {
                txt_username.requestFocus();
            }
        });
    }

    public void signuppage (ActionEvent event) {

        try {
            setSceneContent("SignupView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void signin (ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

        String username = txt_username.getText();
        String password = txt_password.getText();
        User u = new User(null,0,null);

        u.setUsername(username);
        Context.getInstance().setUser(u);


        Connection connection = DbConnection.getMySQLConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT password FROM user WHERE username ='"+username+"'";
        ResultSet rs = statement.executeQuery(sql);

        if(rs.next())
        {
            String motDePasse = rs.getString("password");

            if(BCrypt.checkpw(password, motDePasse))
            {
                setSceneContent("dashboard-view.fxml");
            }
            else {
                notifprobleme("Le mot de passe est incorrect!");
            }
        }
        else {
            notifprobleme("le username n'existe pas!");
        }
        connection.close();
        clear();

    }

    public void clear() {
        txt_username.setText("");
        txt_password.setText("");
    }

    private void notifprobleme(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Il y a un probleme");
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}

