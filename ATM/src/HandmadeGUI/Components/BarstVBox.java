package HandmadeGUI.Components;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.VBox;

public class BarstVBox extends VBox {
    public BarstVBox() {
        setup();
    }


    public void setup() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setCursor(Cursor.CROSSHAIR);
        //setStyle("-fx-background: #141414;");
        setStyle("-fx-background: #ffffff;");
    }
}
