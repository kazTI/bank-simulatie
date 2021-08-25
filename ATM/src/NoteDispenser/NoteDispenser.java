package NoteDispenser;

import ArduinoEventBasedCommunication.ArduinoDataReceivedListener;
import HandmadeGUI.Controller;

import java.util.HashSet;
import java.util.Set;

public class NoteDispenser implements ArduinoDataReceivedListener, Runnable {

    private Set<Integer> usedNotes = new HashSet<Integer>();
    private int dispensingSucceed = -1;

    private Controller controller;
    private int amountOf5Available = 10;
    private int amountOf10Available = 10;
    private int amountOf20Available = 10;
    private int amountOf50Available = 10;

    private int required50s = 0;
    private int required20s = 0;
    private int required10s = 0;
    private int required5s = 0;

    private int amountToDispense = -1;
    private int preferredNote = -1;


    public void setAmountToDispense(int amount) {
        this.amountToDispense = amount;
    }


    @Override
    public void run() {
        dispense();
    }


    public NoteDispenser(Controller controller) {
        this.controller = controller;
        setup();
    }


    public void setup() {
        usedNotes.add(5);
        usedNotes.add(10);
        usedNotes.add(20);
        usedNotes.add(50);
    }


    /**
     * Dispenses the given amount from the ATM.
     */
    public void dispense() {
        boolean result = false;

        if (isDispenseable(amountToDispense)) {
            if (required50s > 0) {
                for (int i = 0; i < required50s; i++) {
                    logToDispensingScene("Dispensing 50 notes\nAt note  " + (i + 1) + " of " + required50s);
                    if (!dispenseNote(50)) {
                        finish(false);
                    }
                }
            }
            if (required20s > 0) {
                for (int i = 0; i < required20s; i++) {
                    logToDispensingScene("Dispensing 20 notes\nAt note  " + (i + 1) + " of " + required20s);
                    if (!dispenseNote(20)) {
                        finish(false);
                    }
                }
            }
            if (required10s > 0) {
                for (int i = 0; i < required10s; i++) {
                    logToDispensingScene("Dispensing 10 notes\nAt note  " + (i + 1) + " of " + required10s);
                    if (!dispenseNote(10)) {
                        finish(false);
                    }
                }
            }
            if (required5s > 0) {
                for (int i = 0; i < required5s; i++) {
                    controller.getDispensingScene().getInstructionsLabel().setText("Dispensing 5 notes\nAt note  " + (i + 1) + " of " + required5s);
                    if (!dispenseNote(5)) {
                        finish(false);
                    }
                }
            }
            resetRequiredAmountsOfNotes();
            finish(true);
        }
        else {
            finish(false);
        }

    }


    public void finish(boolean result) {
        controller.getDispensingScene().onDispensingFinish(result);
    }


    public void logToDispensingScene(String message) {
        log("Logging to dispensing scene: '" + message + "'");
        controller.getDispensingScene().getInstructionsLabel().setText(message);
    }


    public boolean isMultipleOf5(int amount) {
        if (amount > 0) {
            return amount % 5 == 0;
        }
        return false;
    }


    /**
     * Determines if the ATM can dispense the amount by looking at the amount of available notes hardcoded. Also sets the required amount of notes as global variables.
     *
     * @param lmao The amount of money to dispense.
     * @return Whether the ATM can dispense that amount.
     */
    public boolean isDispenseable(int lmao) {
        boolean result = false;

        int amount = lmao;


        if (isMultipleOf5(amount)) {
            resetRequiredAmountsOfNotes();
            if (preferredNote != -1) {
                if (preferredNote == 5) {
                    required5s += floor(amount / 5);
                    amount = amount - required5s * 5;
                    if (required5s > amountOf5Available) {
                        int amountOf5sMoreNeeded = required5s - amountOf5Available;
                        required5s = required5s - amountOf5sMoreNeeded;
                        amount = amount + amountOf5sMoreNeeded * 5;
                    }
                }
                else if (preferredNote == 10) {
                    required10s += floor(amount / 10);
                    amount = amount - required10s * 10;
                    if (required10s > amountOf10Available) {
                        int amountOf10sMoreNeeded = required10s - amountOf10Available;
                        required10s = required10s - amountOf10sMoreNeeded;
                        amount = amount + amountOf10sMoreNeeded * 10;
                    }
                }
                else if (preferredNote == 20) {
                    required20s += floor(amount / 20);
                    amount = amount - required20s * 20;
                    if (required20s > amountOf20Available) {
                        int amountOf20sMoreNeeded = required20s - amountOf20Available;
                        required20s = required20s - amountOf20sMoreNeeded;
                        amount = amount + amountOf20sMoreNeeded * 20;
                    }
                }
                else if (preferredNote == 50) {
                    required50s += floor(amount / 50);
                    amount = amount - required50s * 50;
                    if (required50s > amountOf50Available) {
                        int amountOf50sMoreNeeded = required50s - amountOf50Available;
                        required50s = required50s - amountOf50sMoreNeeded;
                        amount = amount + amountOf50sMoreNeeded * 50;
                    }
                }

            }



            required50s += floor(amount / 50);
            amount = amount - required50s * 50;
            if (required50s > amountOf50Available) {
                int amountOf50sMoreNeeded = required50s - amountOf50Available;
                required50s = required50s - amountOf50sMoreNeeded;
                amount = amount + amountOf50sMoreNeeded * 50;
            }


            required20s += floor(amount / 20);
            amount = amount - required20s * 20;
            if (required20s > amountOf20Available) {
                int amountOf20sMoreNeeded = required20s - amountOf20Available;
                required20s = required20s - amountOf20sMoreNeeded;
                amount = amount + amountOf20sMoreNeeded * 20;
            }

            required10s += floor(amount / 10);
            amount = amount - required10s * 10;
            if (required10s > amountOf10Available) {
                int amountOf10sMoreNeeded = required10s - amountOf10Available;
                required10s = required10s - amountOf10sMoreNeeded;
                amount = amount + amountOf10sMoreNeeded * 10;
            }

            required5s += floor(amount / 5);
            if (required5s > amountOf5Available) {
                int amountOf5sMoreNeeded = required5s - amountOf5Available;
                required5s = required50s - amountOf5sMoreNeeded;
                if (required5s < 0) {
                    required5s = 0;
                }
                result = false;
            }
            else {
                result = true;
            }

            log(lmao + " is dispensable in: " + required50s + " 50's, " + required20s + " 20's, " + required10s + " 10's and " + required5s + " 5s");
        }
        return result;
    }


    public int getPreferredNote() {
        return preferredNote;
    }


    public void setPreferredNote(int note) {
        if (usedNotes.contains(note)) {
            this.preferredNote = note;
        }
    }


    /**
     * Dispenses one note from the ATM. Blocking method; it waits for a response from the Arduino. Time-out build in.
     *
     * @param note The note to dispense. Accepted notes: 5, 10, 20 and 50's.
     * @return Whether the action succeeded.
     */
    public boolean dispenseNote(int note) {
        boolean result = false;

        //if it isn't a existing note
        if (!usedNotes.contains(note)) {
            log(note + " isn't a dispensable note");
            return false;
        }

        //send dispense command to arduino
        controller.getConnection().sendData("dispense " + note);
        long startTime = System.currentTimeMillis();

        String readData = null;

        //wait for the time-out to finish or a response from Arduino is received. the arduinomessagelistener sets the dispensingSucceed boolean
        while (System.currentTimeMillis() - startTime < 10000 && dispensingSucceed == -1) {
            readData = controller.getConnection().readData();
            if (readData != null) {
                break;
            }
        }

        //if we got a succes response from arduino, decrement the amount of available notes
        if (dispensingSucceed == 1 || "Success".equals(readData)) {
            log("Dispensing " + note + " note succeed");
            if (note == 5) {
                amountOf5Available--;
            }
            else if (note == 10) {
                amountOf10Available--;
            }
            else if (note == 20) {
                amountOf20Available--;
            }
            else if (note == 50) {
                amountOf50Available--;
            }

            result = true;
        }

        //if we got a failed response from arduino, dispensing has failed and dispensing machine is probably out of notes, so set available amount to 0
        else if (dispensingSucceed == 0 || "Failed".equals(readData)) {
            log("Dispensing " + note + " note failed; out of notes");
            if (note == 5) {
                amountOf5Available = 0;
            }
            else if (note == 10) {
                amountOf10Available = 0;
            }
            else if (note == 20) {
                amountOf20Available = 0;
            }
            else if (note == 50) {
                amountOf50Available = 0;
            }
        }

        //the time out happend; we didn't get a response from arduino
        else {
            log("Dispensing " + note + " note failed; no response from Arduino");
        }

        //reset the response from arduino for next time
        dispensingSucceed = -1;
        log("Returned result is " + result);
        return result;
    }


    public void resetRequiredAmountsOfNotes() {
        required50s = 0;
        required20s = 0;
        required10s = 0;
        required5s = 0;
    }


    public int floor(double value) {
        return (int) value;
    }


    public void log(Object object) {
        System.out.println(object.toString());
    }


    @Override
    public void onArduinoDataReceive(String message) {
        if (message.contains("ailed")) {
            log("Dispensing succeeded");
            dispensingSucceed = 0;
        }
        else if (message.contains("ucces")) {
            log("Dispensing failed");
            dispensingSucceed = 1;
        }
    }



}
