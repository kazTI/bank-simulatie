package HandmadeGUI.Scenes;

import HandmadeGUI.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;

public class LoadingScene extends BarstScene {

    ProgressBar progressBar;
    ProgressIndicator progressIndicator;

    public LoadingScene(Controller controller) {
        super(controller);
    }


    @Override
    public void initializeComponents() {
        instructionsLabel.setText("Loading");
        backButton.setVisible(false);
        logoutButton.setVisible(false);

        final Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(50);

        final ProgressBar pb = new ProgressBar(0);
        final ProgressIndicator pi = new ProgressIndicator(0);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                pb.setProgress(new_val.doubleValue()/50);
                pi.setProgress(new_val.doubleValue()/50);
            }
        });

        centerLayout.getChildren().addAll(slider, pb, pi);


    }


    @Override
    public void onKey(String key) {

    }


    @Override
    public void onCard(String uid) {

    }
}
