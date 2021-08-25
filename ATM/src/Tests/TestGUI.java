package Tests;

import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Components.BarstLabel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TestGUI extends Application {

    Stage primaryStage;
    Scene scene;

    HBox topHBox;
    HBox bottomHBox;
    GridPane grid;
    BorderPane layout;



    BarstLabel instructionsLabel;
    BarstLabel logMessage;
    BarstButton doneButton;
    BarstButton button1;
    BarstButton button2;
    BarstButton logoutButton;
    BarstButton backButton;



    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setOnCloseRequest(event -> {
            primaryStage.close();
            Platform.exit();
            System.exit(420);
        });

        BarstLabel instructionsLabel = new BarstLabel("Instructions");
        GridPane.setHalignment(instructionsLabel, HPos.CENTER);
        BarstLabel logMessage = new BarstLabel("Error message");
        logMessage.setFontSize(20);
        logMessage.makeRed();
        GridPane.setHalignment(logMessage, HPos.CENTER);
        GridPane.setValignment(logMessage, VPos.BOTTOM);
        //logMessage.setFont(20);
        BarstButton doneButton = new BarstButton("Done");
        BarstButton button1 = new BarstButton("Function1");
        BarstButton button2 = new BarstButton("Function nr 2");
        BarstButton logoutButton = new BarstButton("Logout");
        GridPane.setHalignment(logoutButton, HPos.RIGHT);
        GridPane.setValignment(logoutButton, VPos.BOTTOM);
        BarstButton backButton = new BarstButton("Back");
        GridPane.setValignment(backButton, VPos.BOTTOM);

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
//        grid.setPadding(new Insets(10, 10, 10, 10));
//        grid.setVgap(20);
//        grid.setHgap(20);
//        grid.setGridLinesVisible(true);

        VBox contentVBox = new VBox(10);
        contentVBox.getChildren().addAll(button1, button2, doneButton);
        contentVBox.setAlignment(Pos.CENTER);


        GridPane.setConstraints(instructionsLabel, 1, 0);

        GridPane.setConstraints(contentVBox, 1, 1);

        GridPane.setConstraints(backButton, 0, 2);
        GridPane.setConstraints(logMessage, 1, 2);
        GridPane.setConstraints(logoutButton, 2, 2);



        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(20);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(60);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        grid.getColumnConstraints().addAll(column0, column1, column2);

        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(30);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(20);
        grid.getRowConstraints().addAll(row0, row1, row2);


        grid.getChildren().addAll(instructionsLabel, contentVBox, backButton, logMessage, logoutButton);


        scene = new Scene(grid, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
        //new Sleeper(logMessage, 5000);
    }



}
