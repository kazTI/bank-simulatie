package HandmadeGUI.Scenes;

import HandmadeGUI.Controller;

public class BalanceScene extends BarstScene {

    public BalanceScene(Controller controller) {
        super(controller);
    }


    public void initializeComponents() {
        setBalance();
    }


    public void setBalance() {
        instructionsLabel.setText("Your balance is Ѣ" + (double) user.getSelectedAccountObject().getBalance() / 10000);
    }


    @Override
    public void onKey(String key) {

    }


    @Override
    public void onCard(String card) {

    }
}
