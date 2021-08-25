package Tests;

import LabelWriter.Painter;

import javax.swing.*;
import java.awt.*;

/*
Class to test printer results in a GUI
 */
public class TestPrinterInGUI extends JPanel {

    LabelWriter.Painter painter;

    Graphics2D g;


    public TestPrinterInGUI() {
        painter = new Painter();
        painter.setStringsToDraw("BarstBankBarstBankBarstBankBarstBank", "Withdrawal", "$4.20", "NL58INGB0758345364", "20-04-2018", "BarstBankBarstBankBarstBankBarstBankabcdefghijklmnopqrstuvwxqz");
    }


    public void paint(Graphics graphics) {
        this.g = (Graphics2D) graphics;
        painter.paint(g);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new TestPrinterInGUI());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(20, 20, 500, 500);
        frame.setVisible(true);
    }
}
