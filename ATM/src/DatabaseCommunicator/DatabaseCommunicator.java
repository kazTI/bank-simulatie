package DatabaseCommunicator;

import HandmadeGUI.Account;
import HandmadeGUI.Controller;
import HandmadeGUI.User;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCommunicator {

    Controller controller;
    Connection connection;
    User user;


    public DatabaseCommunicator(Controller controller) {
        this.controller = controller;
        connect();
    }


    public void connect() {
        try {
            log("Connecting to database...");
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "root", "");
            connection = DriverManager.getConnection("jdbc:mysql://145.24.222.39:8008/bank", "bank", "dPZgvJT5yq4P4ERc");
            log("Connected to database.");
        } catch (Exception e) {
            controller.error("Connection to database failed");
            log("Connection to database failed. " + e.getMessage());
        }
    }


    public boolean withdraw(long amount) {
        long balanceToSet = user.getSelectedAccountObject().getBalance() - amount;
        if (balanceToSet < 0) {
            log("Not enough balance to withdraw");
            return false;
        }

        if (executeQuery("UPDATE account SET amount = " + balanceToSet + " WHERE id = " + user.getSelectedAccountObject().getId() + ";")) {
            log(user.getSelectedAccountObject().getId() + " account id updated");
            user.getSelectedAccountObject().setBalance(balanceToSet);
            log("Succesfully updated balance");
            return true;
        }
        log("Error setting balance");
        return false;
    }


    public boolean blockCard(boolean block) {
        if (block) {
            return executeQuery("UPDATE card SET blocked = 1 WHERE user_id = " + user.getUserID() + ";");
        }
        return executeQuery("UPDATE card SET blocked = 0 WHERE user_id = " + user.getUserID() + ";");
    }


    public boolean updateFailedLoginAttemps(int newValue) {
        user.setFailedLoginAttemps(newValue);
        if (executeQuery("UPDATE card SET failed_login_attempts = " + user.getFailedLoginAttemps() + " WHERE user_id = " + user.getUserID() + ";")) {
            if (user.getFailedLoginAttemps() > 3) {
                log("Too many failed attemps, blocking card...");
                if (blockCard(true)) {
                    log("Card blocked");
                    return true;
                }
                else {
                    log("Couldn't block card");
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    public boolean setUserByRfid(String rfid) {
        try {
            user = controller.getUser();
            if (user == null) {
                log("User object is null, cancelling...");
                return false;
            }

            ArrayList<String> results;

            log("Retrieving user id with RFID " + rfid + "...");
            results = executeQuery("SELECT user_id FROM card WHERE card.id = \"" + rfid + "\";", "user_id");
            if (results.get(0) == null) {
                log("User with RFID " + rfid + " does not exist, cancelling...");
                return false;
            }
            user.setCard(rfid);
            user.setUserID(Integer.parseInt(results.get(0)));

            log("Setting data from user...");
            results = executeQuery("SELECT first_name, last_name, role FROM user WHERE user.id = " + user.getUserID() + ";", "first_name", "last_name", "role");
            user.setFirstName(results.get(0));
            user.setLastName(results.get(1));
            user.setName(user.getFirstName() + " " + user.getLastName());
            user.setRole(Integer.parseInt(results.get(2)));

            log("Setting data from card...");
            results = executeQuery("SELECT password, failed_login_attempts, blocked FROM card WHERE user_id = " + user.getUserID() + ";", "password", "failed_login_attempts", "blocked");
            user.setPin(results.get(0));
            user.setFailedLoginAttemps(Integer.parseInt(results.get(1)));
            user.setBlocked(Integer.parseInt(results.get(2)));

            log("Setting data from accounts...");
            int counter = 1;
            for (String accountID : executeQueryMultipleRows("SELECT account_id FROM user_has_account WHERE user_id = " + user.getUserID() + ";", "account_id")) {
                log("Retrieving account with id " + accountID + "...");
                results = executeQuery("SELECT number, name, amount FROM account WHERE id = " + accountID + ";", "number", "name", "amount");
                Account toAdd = new Account(controller, Integer.parseInt(accountID), results.get(0), results.get(1), Long.parseLong(results.get(2)), counter++);
                user.getAccounts().add(toAdd);
            }

            log("Done retrieving user. Created " + user.toString());

        } catch (Exception e) {
            log("Error setting user. " + e.getMessage());

            return false;
        }
        return true;
    }


    public boolean executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            controller.error("Error executing noResult query: " + query);
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public ArrayList<String> executeQuery(String query, String... columns) throws SQLException {
        ArrayList<String> result = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        for (String column : columns) {
            result.add(resultSet.getString(column));
        }

        return result;
    }


    public ArrayList<String> executeQueryMultipleRows(String query, String column) throws SQLException {
        ArrayList<String> result = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            result.add(resultSet.getString(column));
        }

        return result;
    }


    public boolean isEmptyResult(String result) {
        if (result == null || "NoResult".equals(result)) {
            return true;
        }
        return false;
    }


    private void log(String message) {
        System.out.println(message);
    }
}
