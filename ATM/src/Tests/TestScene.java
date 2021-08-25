package Tests;

import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Components.BarstLabel;
import HandmadeGUI.Scenes.BarstScene;
import HandmadeGUI.Controller;

public class TestScene extends BarstScene {

    private BarstLabel instructions;
    private BarstButton doneButton;


    public TestScene(Controller controller) {
        super(controller);
    }


    @Override
    public void initializeComponents() {
        instructions = new BarstLabel("Show yourself");
        doneButton = new BarstButton("ok");
        doneButton.setOnAction(event -> onDoneButton());

        layout.getChildren().addAll(instructions, doneButton);
    }


    @Override
    public void onKey(String key) {

    }


    @Override
    public void onCard(String card) {

    }


    private void onDoneButton() {
        controller.loadPinScene();
    }



}
