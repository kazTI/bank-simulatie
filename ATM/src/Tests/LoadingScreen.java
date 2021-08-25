package Tests;

import HandmadeGUI.Scenes.BarstScene;
import HandmadeGUI.Controller;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class LoadingScreen extends BarstScene {

    public static void main(String args[]){
        LoadingScreen scene = new LoadingScreen(new Controller());
    }

    ProgressBar progressBar;
    ProgressIndicator progressIndicator;

    public LoadingScreen(Controller controller) {
        super(controller);
    }


    @Override
    public void initializeComponents() {
        progressBar = new ProgressBar(0.6);
        progressIndicator = new ProgressIndicator(0.6);
        centerLayout.getChildren().addAll(progressBar);
    }


    @Override
    public void onKey(String key) {

    }


    @Override
    public void onCard(String uid) {

    }
}
