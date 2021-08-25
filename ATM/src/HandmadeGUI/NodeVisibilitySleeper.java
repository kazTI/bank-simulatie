package HandmadeGUI;

import javafx.scene.Node;

public class NodeVisibilitySleeper implements Runnable {

    Node component;
    int msVisible;
    int interval;


    public NodeVisibilitySleeper(Node component, int msVisible, int interval) {
        this.component = component;
        this.msVisible = msVisible;
        this.interval = interval;
        if(component != null){
            Thread lmao = new Thread(this);
            lmao.start();
        }
        else{
            log("Component is null");
        }
    }


    @Override
    public void run() {
        try {
            component.setVisible(true);
            int slept = 0;
            long timer = System.currentTimeMillis();
            boolean toggle = false;
            if (interval < 1) {
                Thread.sleep(msVisible);
            }
            else {
                while (slept < msVisible) {
                    if (System.currentTimeMillis() - timer > interval) {
                        component.setVisible(toggle);
                        toggle = !toggle;
                        timer = System.currentTimeMillis();
                    }
                    Thread.sleep(1);
                    slept++;
                }
            }

            component.setVisible(false);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    public void log(String message){
        System.out.println("NodeVisibilitySleeper: " + message);
    }
}

