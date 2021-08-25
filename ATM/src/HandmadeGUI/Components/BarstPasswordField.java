package HandmadeGUI.Components;

import com.jfoenix.controls.JFXPasswordField;

public class BarstPasswordField extends JFXPasswordField {
    public BarstPasswordField() {
        setup();
    }


    public BarstPasswordField(String text) {
        setText(text);
        setup();
    }


    public void setup() {
        setMinWidth(100);
        setMaxWidth(100);
        setStyle("-jfx-focus-color: #2e7d32;");
    }
}
