package HandmadeGUI.Components;

import com.jfoenix.controls.JFXTextField;

public class BarstTextField extends JFXTextField {
    public BarstTextField() {
        setup();
    }


    public BarstTextField(String text) {
        setText(text);
        setup();
    }


    public void setup() {
        setMinWidth(100);
        setMaxWidth(100);
        setStyle("-jfx-focus-color: #2e7d32;");
    }
}
