package HandmadeGUI.Components;

import HandmadeGUI.Shared;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//jfxbutton?
public class BarstButton extends Button {

    public BarstButton() {
        setup();
    }


    public BarstButton(String text) {
        setText(text);
        setup();
    }


    public void setup() {
        setMaxSize(120, 60);
        setMinSize(120, 60);
        setStyle(
                "-fx-background-color: " + Shared.themeColor +
                        "; -fx-text-fill: #FFFFFF;" //F4F4F4
        );

        try {
            setFont(Font.loadFont(new FileInputStream(new File("./lib/RobotoFonts/Roboto-Thin.ttf")), 20));
        } catch (FileNotFoundException e) {
            System.out.println("Error setting font.");
        }
    }

    public void makeBig(){
        setMaxSize(200, 100);
        setMinSize(200, 100);
    }
}
