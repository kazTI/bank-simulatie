package HandmadeGUI.Scenes;

import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Components.BarstPasswordField;
import HandmadeGUI.Controller;
import HandmadeGUI.Shared;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PinScene extends BarstScene {

    PasswordField passwordField;
    Button doneButton;


    public PinScene(Controller controller) {
        super(controller);
    }


    public void initializeComponents() {
        instructionsLabel.setText("Enter your PIN");

        passwordField = new BarstPasswordField();
        passwordField.setAlignment(Pos.CENTER);

        doneButton = new BarstButton("# - Done");
        doneButton.setOnAction(event -> onDoneButton());

        centerLayout.getChildren().addAll(passwordField, doneButton);
    }


    @Override
    public void onKey(String key) {
        if (key.equals("#")) {
            onDoneButton();
        }
        else if (key.equals("*")) {
            if (!passwordField.getText().isEmpty()) {
                passwordField.setText(passwordField.getText(0, passwordField.getText().length() - 1));
                setCaretToEnd();
            }
        }
        else {
            passwordField.setText(passwordField.getText() + key);
            setCaretToEnd();
        }
    }


    @Override
    public void onCard(String uid) {

    }


    private void onDoneButton() {
        if (checkPin(passwordField.getText())) {
            controller.loadSelectAccountScene();
//            try{
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            resetEntry();
            controller.getDatabaseConnection().updateFailedLoginAttemps(0);
        }
        else {
            onIncorrectPin();
        }
    }


    public boolean checkPin(String pin) {
        log("Checking PIN: " + pin);
        String toCheck = user.getCard() + pin;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteData = digest.digest(toCheck.getBytes(StandardCharsets.UTF_8));

            StringBuffer sb = new StringBuffer();
            for (byte bit : byteData) {
                sb.append(Integer.toString((bit & 0xff) + 0x100, 16).substring(1));
            }
            String hash = sb.toString();

            if (user.getPin().equals(hash)) {
                log("PIN matches, access granted");
                return true;
            }
        } catch (Exception e) {
            log("Error hashing PIN. " + e.getMessage());
        }

        return false;
    }


    public void onIncorrectPin() {
        controller.error("PIN incorrect. Tries remaining: " + (Shared.maxFailedAttempts - user.getFailedLoginAttemps() - 1));
        if (user.getFailedLoginAttemps() >= Shared.maxFailedAttempts) {
            controller.onBackButton();
            controller.error("Card blocked");
        }
        controller.getDatabaseConnection().updateFailedLoginAttemps(user.getFailedLoginAttemps() + 1);
    }


    public void resetEntry() {
        passwordField.setText("");
    }


    public String getCurrentlyEnteredPin() {
        return passwordField.getText();
    }



    public void setCaretToEnd() {
        passwordField.positionCaret(passwordField.getText().length());
    }


    public PasswordField getPasswordField() {
        return passwordField;
    }
}
