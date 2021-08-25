package HandmadeGUI.Components;

import HandmadeGUI.Shared;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BarstLabel extends Label {

    public BarstLabel() {
        setup();
    }


    public BarstLabel(String text) {
        setText(text);
        setup();
    }


    public void setup() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File("./lib/RobotoFonts/Roboto-Thin.ttf")), 60));
        } catch (FileNotFoundException e) {
            System.out.println("Error setting font.");
        }
    }


    public void setFontSize(int fontSize) {
        setFont(new Font(fontSize));
    }


    public void makeRed() {
        setTextFill(Color.web(Shared.errorColor, 1.0));
    }


    public void makeGreen() {
        setTextFill(Color.web(Shared.themeColor, 1.0));
    }
}
