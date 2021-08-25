package HandmadeGUI.Scenes;

import HandmadeGUI.Controller;

public class CardScene extends BarstScene {

    public CardScene(Controller controller) {
        super(controller);
    }


    public void initializeComponents() {
        backButton.setDisable(true);
        backButton.setVisible(false);
        logoutButton.setDisable(true);
        logoutButton.setVisible(false);

        instructionsLabel.setText("Scan your card");
    }


    @Override
    public void onKey(String key) {

    }


    @Override
    public void onCard(String uid) {
        if (checkRFID(uid)) {
            controller.loadPinScene();
        }
    }


    public boolean checkRFID(String rfid) {
        log("Checking card with UID: " + rfid);
        if (controller.getDatabaseConnection().setUserByRfid(rfid)) {
            log("Succesfully set user");
            if (user.getBlocked() == 1 || user.getFailedLoginAttemps() >= 3) {
                controller.error("Card is blocked");
                return false;
            }
            return true;
        }
        else {
            controller.error("Card not recognized");
            return false;
        }
    }
}
