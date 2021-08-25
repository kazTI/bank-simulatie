package HandmadeGUI.Components;

import HandmadeGUI.Shared;
import com.jfoenix.controls.JFXListView;

public class BarstListView extends JFXListView {
    public BarstListView() {

        String themeColor = Shared.themeColor;

        setStyle("-fx-selection-bar: " + themeColor +
                "; -fx-selection-bar-non-focused: " + themeColor +
                "; -fx-border-color: " + themeColor +
                "; -fx-border-width: 2;");

        setMaxWidth(200);
        setMinWidth(200);
    }
}
