package HandmadeGUI.Scenes;

import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Controller;

public class AccountScene extends BarstScene {

    BarstButton balanceButton;
    BarstButton withdrawButton;
    BarstButton switchAccountButton;


    public AccountScene(Controller controller) {
        super(controller);
    }


    public void initializeComponents() {
        balanceButton = new BarstButton("1 - Balance");
        balanceButton.setOnAction(event -> onBalanceButton());
        balanceButton.makeBig();

        withdrawButton = new BarstButton("2 - Withdraw");
        withdrawButton.setOnAction(event -> onWithdrawButton());
        withdrawButton.makeBig();

        switchAccountButton = new BarstButton("3 - Switch account");
        switchAccountButton.setOnAction(event -> onSwitchAccountButton());
        switchAccountButton.makeBig();

        centerLayout.getChildren().addAll(balanceButton, withdrawButton, switchAccountButton);
    }


    @Override
    public void onKey(String key) {
        if (key.equals("1")) {
            onBalanceButton();
        }
        else if (key.equals("2")) {
            onWithdrawButton();
        }
        else if (key.equals("3")) {
            onSwitchAccountButton();
        }
    }


    @Override
    public void onCard(String card) {

    }


    public void refresh() {
        instructionsLabel.setText("Welcome, " + user.getFirstName());
    }


    private void onSwitchAccountButton() {
        controller.loadSelectAccountScene();
    }


    private void onWithdrawButton() {
        controller.loadWithdrawScene();
    }


    private void onBalanceButton() {
        controller.loadBalanceScene();
    }
}
