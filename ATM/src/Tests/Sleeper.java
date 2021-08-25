package Tests;

import javafx.scene.Node;

public class Sleeper implements Runnable {

    Node component;
    int msVisible;


    public Sleeper(Node component, int msVisible) {
        System.out.println("New sleeper for " + component.toString());
        this.component = component;
        this.msVisible = msVisible;
        Thread lmao = new Thread(this);
        lmao.start();
    }


    @Override
    public void run() {
        component.setVisible(true);
        try {
            Thread.sleep(msVisible);
            component.setVisible(false);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted. " + e.getMessage());
        }
    }
}
