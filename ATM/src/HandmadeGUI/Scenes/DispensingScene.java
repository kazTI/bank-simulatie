package HandmadeGUI.Scenes;

import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * alles wat met de dispenser te maken heeft is tering smerige code, exuses ik had welloe tijd
 */
public class DispensingScene extends BarstScene {

    BarstButton prefer5Button;
    BarstButton prefer10Button;
    BarstButton prefer20Button;
    BarstButton prefer50Button;
    BarstButton noPreferenceButton;

    BarstButton acceptButton;
    BarstButton denyButton;
    int amountToDispense = -1;


    public DispensingScene(Controller controller) {
        super(controller);
    }


    @Override
    public void initializeComponents() {
        instructionsLabel.setText("Select preferred note");

        prefer5Button = new BarstButton("1 - 5's");
        prefer5Button.setOnAction(event -> onPreferenceButton(5));
        prefer10Button = new BarstButton("2 - 10's");
        prefer10Button.setOnAction(event -> onPreferenceButton(10));
        prefer20Button = new BarstButton("3 - 20's");
        prefer20Button.setOnAction(event -> onPreferenceButton(20));
        prefer50Button = new BarstButton("4 - 50's");
        prefer50Button.setOnAction(event -> onPreferenceButton(50));
        noPreferenceButton = new BarstButton("5 - none");
        noPreferenceButton.setOnAction(event -> onPreferenceButton(-1));

        acceptButton = new BarstButton("1 - Accept");
        acceptButton.setOnAction(event -> onAcceptButton());

        denyButton = new BarstButton("2 - Deny");
        denyButton.setOnAction(event -> onDenyButton());

        centerLayout.getChildren().addAll(prefer5Button, prefer10Button, prefer20Button, prefer50Button, noPreferenceButton, acceptButton, denyButton);

        showComfirmationButtons(false);
    }


    public void onPreferenceButton(int preference) {
        instructionsLabel.setText("Dispensing notes...");
        showPreferenceButtons(false);
        if (preference == 5) {
            controller.getDispenser().setPreferredNote(5);
        }
        else if (preference == 10) {
            controller.getDispenser().setPreferredNote(10);
        }
        else if (preference == 20) {
            controller.getDispenser().setPreferredNote(20);
        }
        else if (preference == 50) {
            controller.getDispenser().setPreferredNote(50);
        }
        else if (preference == -1) {
            controller.getDispenser().setPreferredNote(-1);
        }

        dispense(amountToDispense);

    }


    public void dispense(int amount) {
        log("Dispensing " + amount);
        amountToDispense = amount;

        showComfirmationButtons(false);

        controller.getDispenser().setAmountToDispense(amountToDispense);
        Thread thread = new Thread(controller.getDispenser());
        thread.run();
    }


    public void onAcceptButton() {
        if (amountToDispense != -1) {
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());

            controller.getPrinter().print("BarstBank", getUser().getName(), "Amount: " + amountToDispense + "Ѣ", "Preferred note: " + controller.getDispenser().getPreferredNote(), date);
            onButton();
        }
    }


    public void onDispensingFinish(boolean result) {
        if (result) {
            user.withdraw(amountToDispense * 10000);
            showComfirmationButtons(true);
            instructionsLabel.setText("Would you like a receipt?");
        }
        else {
            showPreferenceButtons(false);
            showComfirmationButtons(false);
            controller.loadWithdrawScene();
            controller.error("Error dispensing your money");
        }

    }


    public void onDenyButton() {
        onButton();
    }


    public void onButton() {
        instructionsLabel.setText("Select preferred note");
        controller.loadWithdrawScene();
        showPreferenceButtons(true);
        showComfirmationButtons(false);
    }


    public void showComfirmationButtons(boolean show) {
        acceptButton.setVisible(show);
        denyButton.setVisible(show);
    }


    public void showPreferenceButtons(boolean show) {
        prefer5Button.setVisible(show);
        prefer10Button.setVisible(show);
        prefer20Button.setVisible(show);
        prefer50Button.setVisible(show);
        noPreferenceButton.setVisible(show);
    }


    public void setAmountToDispense(int amountToDispense) {
        this.amountToDispense = amountToDispense;
    }


    @Override
    public void onKey(String key) {
        if (noPreferenceButton.isVisible()) {
            if (key.equals("1")) {
                onPreferenceButton(Integer.parseInt(key));
            }
            else if (key.equals("2")) {
                onPreferenceButton(Integer.parseInt(key));
            }
            else if (key.equals("3")) {
                onPreferenceButton(Integer.parseInt(key));
            }
            else if (key.equals("4")) {
                onPreferenceButton(Integer.parseInt(key));
            }
            else if (key.equals("5")) {
                onPreferenceButton(Integer.parseInt(key));
            }
        }
        else if (acceptButton.isVisible()) {
            if (key.equals("1")) {
                onAcceptButton();
            }
            else if (key.equals("2")) {
                onDenyButton();
            }
        }

    }


    @Override
    public void onCard(String uid) {

    }
}
