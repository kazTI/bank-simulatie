package HandmadeGUI.Scenes;

import ArduinoEventBasedCommunication.ArduinoDataReceivedListener;
import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Components.BarstLabel;
import HandmadeGUI.Components.BarstVBox;
import HandmadeGUI.Controller;
import HandmadeGUI.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public abstract class BarstScene implements ArduinoDataReceivedListener {

    public Controller controller;
    public GridPane layout;
    public BarstVBox centerLayout;
    public Scene scene;
    public User user;
    public int windowWidth = 1080;
    public int windowHeight = 720;

    public BarstLabel instructionsLabel;
    public BarstButton backButton;
    public BarstLabel logLabel;
    public BarstButton logoutButton;


    public BarstScene(Controller controller) {
        this.controller = controller;

        layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
//        layout.setGridLinesVisible(true);
//        layout.setStyle("-fx-background: #141414;");
//        layout.setVgap(20);
//        layout.setHgap(20);
//        layout.setGridLinesVisible(true);

        centerLayout = new BarstVBox();
        centerLayout.setAlignment(Pos.CENTER);

        instructionsLabel = new BarstLabel("Instructions");
        GridPane.setHalignment(instructionsLabel, HPos.CENTER);

        backButton = new BarstButton("B - Back");
        backButton.setOnAction(event -> controller.onBackButton());
        GridPane.setValignment(backButton, VPos.BOTTOM);

        logLabel = new BarstLabel();
        logLabel.setFontSize(20);
        logLabel.makeRed();
        GridPane.setValignment(logLabel, VPos.BOTTOM);
        GridPane.setHalignment(logLabel, HPos.CENTER);

        logoutButton = new BarstButton("C - Logout");
        logoutButton.setOnAction(event -> controller.onLogoutButton());
        GridPane.setHalignment(logoutButton, HPos.RIGHT);
        GridPane.setValignment(logoutButton, VPos.BOTTOM);

        GridPane.setConstraints(instructionsLabel, 1, 0);
        GridPane.setConstraints(centerLayout, 1, 1);

        GridPane.setConstraints(backButton, 0, 2);
        GridPane.setConstraints(logLabel, 1, 2);
        GridPane.setConstraints(logoutButton, 2, 2);

        user = controller.getUser();

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(15);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(70);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(15);
        layout.getColumnConstraints().addAll(column0, column1, column2);

        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(30);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(60);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        layout.getRowConstraints().addAll(row0, row1, row2);

        layout.getChildren().addAll(instructionsLabel, backButton, logLabel, logoutButton, centerLayout);
        scene = new Scene(layout, windowWidth, windowHeight);
        initializeComponents();

    }


    public abstract void initializeComponents();

    public abstract void onKey(String key);

    public abstract void onCard(String uid);


    public void onArduinoDataReceive(String message) {
        if (isCurrentScene()) {
            String bits[] = message.split(" ");
            String data;
            if (bits.length > 1) {
                data = bits[1];
                if (message.contains("RFID")) {
                    onCard(data);
                }
                else if (message.contains("KEY")) {
                    if (data.equals("B")) {
                        controller.onBackButton();
                    }
                    else if (data.equals("C")) {
                        controller.onLogoutButton();
                    }
                    else {
                        onKey(data);
                    }
                }
            }
        }
    }


    public void log(String message) {
        controller.log(message);
    }


    public boolean isCurrentScene() {
        return this == controller.getCurrentScene();
    }


    public Scene getScene() {
        return scene;
    }


    public void setScene(Scene scene) {
        this.scene = scene;
    }


    public Controller getController() {
        return controller;
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }


    public GridPane getLayout() {
        return layout;
    }


    public void setLayout(GridPane layout) {
        this.layout = layout;
    }


    public BarstVBox getCenterLayout() {
        return centerLayout;
    }


    public void setCenterLayout(BarstVBox centerLayout) {
        this.centerLayout = centerLayout;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public int getWindowWidth() {
        return windowWidth;
    }


    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }


    public int getWindowHeight() {
        return windowHeight;
    }


    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }


    public BarstLabel getInstructionsLabel() {
        return instructionsLabel;
    }


    public void setInstructionsLabel(BarstLabel instructionsLabel) {
        this.instructionsLabel = instructionsLabel;
    }


    public BarstButton getBackButton() {
        return backButton;
    }


    public void setBackButton(BarstButton backButton) {
        this.backButton = backButton;
    }


    public BarstLabel getLogLabel() {
        return logLabel;
    }


    public void setLogLabel(BarstLabel logLabel) {
        this.logLabel = logLabel;
    }


    public BarstButton getLogoutButton() {
        return logoutButton;
    }


    public void setLogoutButton(BarstButton logoutButton) {
        this.logoutButton = logoutButton;
    }
}
