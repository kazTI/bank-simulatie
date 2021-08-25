package HandmadeGUI.Scenes;

import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Components.BarstTextField;
import HandmadeGUI.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WithdrawScene extends BarstScene {

    Button withdraw5Button;
    Button withdraw10Button;
    Button withdraw20Button;
    Button withdraw50Button;
    Button withdrawCostumButton;
    TextField withdrawCustomField;


    public WithdrawScene(Controller controller) {
        super(controller);
    }


    public void initializeComponents() {
        instructionsLabel.setText("Select amount to withdraw");

        withdraw5Button = new BarstButton("1 - Ѣ5");
        withdraw5Button.setOnAction(event -> onWithDrawButton(5));

        withdraw10Button = new BarstButton("2 - Ѣ10");
        withdraw10Button.setOnAction(event -> onWithDrawButton(10));

        withdraw20Button = new BarstButton("3 - Ѣ20");
        withdraw20Button.setOnAction(event -> onWithDrawButton(20));

        withdraw50Button = new BarstButton("4 - Ѣ50");
        withdraw50Button.setOnAction(event -> onWithDrawButton(50));

        withdrawCostumButton = new BarstButton("# - Custom amount");
        ((BarstButton) withdrawCostumButton).makeBig();
        withdrawCostumButton.setOnAction(event -> onCustomWithdrawButton());

        withdrawCustomField = new BarstTextField("");
        withdrawCustomField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                withdrawCustomField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        withdrawCustomField.setAlignment(Pos.CENTER);
//withdraw5Button, withdraw10Button, withdraw20Button, withdraw50Button,
        centerLayout.getChildren().addAll(withdrawCustomField, withdrawCostumButton);
        withdrawCustomField.requestFocus();
    }


    public void setCaretToEnd() {
        withdrawCustomField.positionCaret(withdrawCustomField.getText().length());
    }

    @Override
    public void onKey(String key) {
        if (key.equals("#")) {
            onCustomWithdrawButton();
            return;
        }
        else if(key.equals("*")){
            if (!withdrawCustomField.getText().isEmpty()) {
                withdrawCustomField.setText(withdrawCustomField.getText(0, withdrawCustomField.getText().length() - 1));
                setCaretToEnd();
            }
        }

        try {
            Integer.parseInt(key);
        } catch (Exception e) {
            //dont append if it isnt an integer lmao
            log("lmao");
            return;
        }

        withdrawCustomField.appendText(key);
//        if (key.equals("1")) {
//            onWithDrawButton(5);
//        }
//        else if (key.equals("2")) {
//            onWithDrawButton(10);
//        }
//        else if (key.equals("3")) {
//            onWithDrawButton(20);
//        }
//        else if (key.equals("4")) {
//            onWithDrawButton(50);
//        }
//        else if (key.equals("5")) {
//            onCustomWithdrawButton();
//        }

    }


    @Override
    public void onCard(String card) {

    }


    private void onWithDrawButton(int amount) {
        if (controller.getDispenser().isDispenseable(amount)) {
            controller.loadDispensingScene();
            controller.getDispensingScene().setAmountToDispense(amount);
        }
        else {
            controller.error("You can only withdraw multiples of 5");
        }

    }


    private void showComponents(boolean shouldShow) {
        withdraw5Button.setVisible(shouldShow);
        withdraw10Button.setVisible(shouldShow);
        withdraw20Button.setVisible(shouldShow);
        withdraw50Button.setVisible(shouldShow);
        withdrawCostumButton.setVisible(shouldShow);
        withdrawCustomField.setVisible(shouldShow);
    }


    private void onCustomWithdrawButton() {
        onWithDrawButton(Integer.parseInt(withdrawCustomField.getText()));
    }
}
