package HandmadeGUI;

import java.util.ArrayList;

public class User {

    private Controller controller;

    private ArrayList<Account> accounts = new ArrayList<>();
    private int userID;
    private String card;
    private String pin;
    private String name;
    private int failedLoginAttemps;
    private int blocked;
    private String firstName;
    private String lastName;
    private int role;
    private int selectedAccount;


    public User(Controller controller) {
        this.controller = controller;
    }


    @Override
    public String toString() {
        return "User{" +
                "controller=" + controller +
                ", accounts=" + accounts +
                ", userID=" + userID +
                ", card='" + card + '\'' +
                ", pin='" + pin + '\'' +
                ", name='" + name + '\'' +
                ", failedLoginAttemps=" + failedLoginAttemps +
                ", blocked=" + blocked +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", loggedInAccount=" + selectedAccount +
                '}';
    }


    public boolean withdraw(long amount) {
        if (controller.getDatabaseConnection().withdraw(amount)) {
            controller.getBalanceScene().setBalance();
            controller.confirm("Withdrawal succes!");
            return true;
        }
        controller.error("Insufficient balance");
        return false;
    }


    public Account getSelectedAccountObject() {
        if (accounts.size() > 0) {
            Account result = accounts.get(selectedAccount);
            if (result != null) {
                return accounts.get(selectedAccount);
            }
        }
        return new Account(controller, -1, "NoNumber", "NoName", -1, -1);
    }


    public Controller getController() {
        return controller;
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }


    public int getUserID() {
        return userID;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }


    public int getFailedLoginAttemps() {
        return failedLoginAttemps;
    }


    public void setFailedLoginAttemps(int failedLoginAttemps) {
        this.failedLoginAttemps = failedLoginAttemps;
    }


    public int getBlocked() {
        return blocked;
    }


    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public int getRole() {
        return role;
    }


    public void setRole(int role) {
        this.role = role;
    }


    public String getCard() {
        return card;
    }


    public void setCard(String card) {
        this.card = card;
    }


    public String getPin() {
        return pin;
    }


    public void setPin(String pin) {
        this.pin = pin;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    private void log(String message) {
        System.out.println(message);
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }


    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }


    public int getSelectedAccount() {
        return selectedAccount;
    }


    public void setSelectedAccount(int loggedInAccount) {
        this.selectedAccount = loggedInAccount;
    }


    public void clear() {
        accounts = new ArrayList<>();
        userID = -1;
        card = null;
        pin = null;
        name = null;
        failedLoginAttemps = -1;
        blocked = -1;
        firstName = null;
        String lastName = null;
        role = -1;
        selectedAccount = -1;
        log("Cleared user variables");
    }
}
