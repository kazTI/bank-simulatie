package Tests;

import HandmadeGUI.Components.BarstButton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SecondTestGUI extends Application {

    Stage window;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("thenewboston - JavaFX");

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        BarstButton logoutButton = new BarstButton("Logout");
        BarstButton backButton = new BarstButton("Back");

        GridPane.setConstraints(backButton, 0, 0);
        GridPane.setConstraints(logoutButton, 1, 0);

        grid.getChildren().addAll(logoutButton, backButton);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.show();
    }


}