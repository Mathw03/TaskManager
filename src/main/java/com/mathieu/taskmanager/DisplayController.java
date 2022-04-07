package com.mathieu.taskmanager;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class DisplayController implements Initializable {


    public static void setSceneContentStartup(Stage stage) throws IOException {
        Context.getInstance().setCurrentStage(stage);
        setSceneContent("login-view.fxml");
    }
    public static Parent setSceneContent(String pageName) throws IOException {
        Stage currentStage = Context.getInstance().getCurrentStage();
        Scene scene = currentStage.getScene();
        Parent page = FXMLLoader.load(DisplayController.class.getResource( pageName ));

        if (scene == null) {
            scene = new Scene(page);
            currentStage.setScene(scene);
            currentStage.setTitle("Gestion de Tache");
        } else {
            currentStage.getScene().setRoot(page);
        }

        currentStage.centerOnScreen();
        currentStage.sizeToScene();
        currentStage.setResizable(false);
        currentStage.show();
        return page;
    }


    public static boolean showTaskEditDialog(Task task) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            Stage currentStage = Context.getInstance().getCurrentStage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DisplayController.class.getResource("task-edit-dialog-view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(currentStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TaskEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTask(task);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean showCategoryEditDialog(Category category) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            Stage currentStage = Context.getInstance().getCurrentStage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DisplayController.class.getResource("category-edit-dialog-view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Category");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(currentStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the cat into the controller.
            CategoryEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategory(category);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean showDispatchDialog(User user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            Stage currentStage = Context.getInstance().getCurrentStage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DisplayController.class.getResource("dispatch-dialog-view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Dispatch");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(currentStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the cat into the controller.
            DispatchDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public abstract void initialize(URL arg0, ResourceBundle arg1);
}
