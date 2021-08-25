package HandmadeGUI;

import ArduinoEventBasedCommunication.ArduinoCommunicator;
import ArduinoEventBasedCommunication.ArduinoDataReceivedListener;
import DatabaseCommunicator.DatabaseCommunicator;
import HandmadeGUI.Scenes.BarstScene;
import HandmadeGUI.Scenes.*;
import LabelWriter.Printer;
import NoteDispenser.NoteDispenser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller extends Application implements ArduinoDataReceivedListener {

    ArduinoCommunicator connection;
    User user;
    DatabaseCommunicator databaseConnection;
    Printer printer;
    NoteDispenser dispenser = new NoteDispenser(this);
    Stage primaryStage;

    LoadingScene loadingScene;

    CardScene cardScene;
    PinScene pinScene;
    AccountScene accountScene;
    BalanceScene balanceScene;
    WithdrawScene withdrawScene;
    DispensingScene dispensingScene;
    SelectAccountScene selectAccountScene;
    BarstScene currentScene;
    ArrayList<BarstScene> scenes = new ArrayList<>();


    public static void main(String args[]) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        connection = new ArduinoCommunicator(this);
        databaseConnection = new DatabaseCommunicator(this);
        user = new User(this);
        printer = new Printer();

        this.primaryStage = stage;
        primaryStage.setOnCloseRequest(event -> {
            primaryStage.close();
            Platform.exit();
            System.exit(420);
        });

        loadingScene = new LoadingScene(this);

        cardScene = new CardScene(this);
        pinScene = new PinScene(this);
        accountScene = new AccountScene(this);
        balanceScene = new BalanceScene(this);
        withdrawScene = new WithdrawScene(this);
        dispensingScene = new DispensingScene(this);
        selectAccountScene = new SelectAccountScene(this);

        scenes.add(loadingScene);
        scenes.add(cardScene);
        scenes.add(pinScene);
        scenes.add(accountScene);
        scenes.add(balanceScene);
        scenes.add(withdrawScene);
        scenes.add(selectAccountScene);

        loadCardScene();
        //loadLoadingScene();
        primaryStage.show();
    }


    public void onLogoutButton() {
        pinScene.resetEntry();
        user.clear();
        loadCardScene();
    }


    public void onBackButton() {
        if (currentScene == accountScene) {
            loadSelectAccountScene();
        }
        else if (currentScene == balanceScene) {
            loadAccountScene();
        }
        else if (currentScene == withdrawScene) {
            loadAccountScene();
        }
        else if (currentScene == pinScene) {
            onLogoutButton();
        }
        else if (currentScene == selectAccountScene) {
            loadPinScene();
        }
        else if(currentScene == dispensingScene){
            loadWithdrawScene();
        }
        else {
            log("Back button pressed in " + currentScene);
        }
    }


    public void error(String message) {
        log("Error: " + message);
        if (currentScene != null) {
            currentScene.getLogLabel().setText(message);
            currentScene.getLogLabel().makeRed();
            new NodeVisibilitySleeper(currentScene.getLogLabel(), 2000, 0);
        }
    }


    public void confirm(String message) {
        log("Confirmation: " + message);
        if (currentScene != null) {
            currentScene.getLogLabel().setText(message);
            currentScene.getLogLabel().makeGreen();
            new NodeVisibilitySleeper(currentScene.getLogLabel(), 2000, 0);
        }
    }


    @Override
    public void onArduinoDataReceive(String message) {
        log("Arduino: " + message);
//        for (BarstScene scene : scenes) {
//            scene.onArduinoDataReceive(message);
//        }
        currentScene.onArduinoDataReceive(message);
        dispenser.onArduinoDataReceive(message);
    }


    public void log(String message) {
        System.out.println(message);
    }


    public void loadScene(Scene scene) {
        primaryStage.setScene(scene);
    }


    public void setTitle(String title) {
        primaryStage.setTitle(title + " - BarstBank");
    }


    public void loadLoadingScene() {
        setTitle("Loading");
        loadScene(loadingScene.getScene());
        currentScene = loadingScene;
    }


    public void loadCardScene() {
        setTitle("Card check");
        loadScene(cardScene.getScene());
        currentScene = cardScene;
    }


    public void loadPinScene() {
        setTitle("PIN entry");
        loadScene(pinScene.getScene());
        getPinScene().getPasswordField().requestFocus();
        currentScene = pinScene;
    }


    public void loadSelectAccountScene() {
        setTitle("Select user");
        selectAccountScene.refresh();
        loadScene(selectAccountScene.getScene());
        currentScene = selectAccountScene;
    }


    public void loadAccountScene() {
        setTitle("User");
        accountScene.refresh();
        loadScene(accountScene.getScene());
        currentScene = accountScene;
    }


    public void loadBalanceScene() {
        setTitle("Balance");
        balanceScene.setBalance();
        loadScene(balanceScene.getScene());
        currentScene = balanceScene;
    }


    public void loadWithdrawScene() {
        setTitle("Withdraw");
        loadScene(withdrawScene.getScene());
        currentScene = withdrawScene;
    }

    public void loadDispensingScene(){
        setTitle("Money dispensing");
        loadScene(dispensingScene.getScene());
        currentScene = dispensingScene;

    }

    public void sleep(int millis){
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log("Error sleeping");
        }
    }

    public DatabaseCommunicator getDatabaseConnection() {
        return databaseConnection;
    }


    public BarstScene getCurrentScene() {
        return currentScene;
    }


    public User getUser() {
        return user;
    }


    public CardScene getCardScene() {
        return cardScene;
    }


    public PinScene getPinScene() {
        return pinScene;
    }


    public AccountScene getAccountScene() {
        return accountScene;
    }


    public BalanceScene getBalanceScene() {
        return balanceScene;
    }


    public WithdrawScene getWithdrawScene() {
        return withdrawScene;
    }

    public DispensingScene getDispensingScene(){
        return dispensingScene;
    }

    public ArduinoCommunicator getConnection() {
        return connection;
    }

    public NoteDispenser getDispenser(){
        return dispenser;
    }

    public Printer getPrinter(){
        return printer;
    }
}
