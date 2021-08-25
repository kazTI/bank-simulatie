package HandmadeGUI.Scenes;

import HandmadeGUI.Account;
import HandmadeGUI.Components.BarstButton;
import HandmadeGUI.Components.BarstListView;
import HandmadeGUI.Controller;

public class SelectAccountScene extends BarstScene {

    BarstListView listView;
    BarstButton loginButton;


    public SelectAccountScene(Controller controller) {
        super(controller);
    }


    @Override
    public void initializeComponents() {
        instructionsLabel.setText("Select account");
        listView = new BarstListView();

        loginButton = new BarstButton("# - Login");
        loginButton.setOnAction(event -> onLoginButton());

        centerLayout.getChildren().addAll(listView, loginButton);
    }


    @Override
    public void onKey(String key) {
        if (key.equals("#")) {
            onLoginButton();
        }
        else {
            String[] forbiddenChars = {"A", "B", "C", "D", "*"};
            for (String temp : forbiddenChars) {
                if (key.equals(temp)) {
                    return;
                }
            }
            int enteredKey = Integer.parseInt(key);
            selectFromListView(enteredKey);
        }
    }


    @Override
    public void onCard(String uid) {

    }


    public void refresh() {
        listView.getItems().clear();
        listView.getItems().addAll(user.getAccounts());
        listView.getSelectionModel().selectFirst();
    }


    private void onLoginButton() {
        for (Account account : user.getAccounts()) {
            if (listView.getSelectionModel().getSelectedItem().equals(account)) {
                log("Account selected: " + account.toString());
                user.setSelectedAccount(user.getAccounts().indexOf(account));
            }
        }

        listView.getItems().clear();
        controller.loadAccountScene();
    }


    public void selectFromListView(int selection) {
        log("Selecting " + selection + "...");
        int size = controller.getUser().getAccounts().size();

        if (size < selection) {
            selection = size;
        }
        else if (selection <= 0) {
            selection = 1;
        }
        listView.getSelectionModel().select(user.getAccounts().get(selection - 1));
    }
}
