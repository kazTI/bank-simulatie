package LabelWriter;

import java.awt.*;

public class Painter {

    private Graphics2D g;
    private String[] stringsToDraw;
    private Font font;
    private int stringCount;
    private int whiteSpaceHeight;
    private int availableWidth;
    private int topMargin;
    private int leftMargin;


    public Painter(){
        restoreDefaults();
    }

    /**
     * Method that paint the Graphics2D instance.
     *
     * @param g2d The Graphics2D instance to paint on.
     */
    public void paint(Graphics2D g2d) {
        if (g2d == null) {
            throw new IllegalArgumentException("Couldn't paint; Graphics2D instance can't be null.");
        }
        this.g = g2d;
        g.setFont(font);
        drawStrings(stringsToDraw);
    }


    /**
     * Draws the given Strings on the local Graphics2D variable. Fits the Strings in the given width, cutting Strings of and drawing them on the next line when too large.
     *
     * @param strings The Strings to draw on the Graphics2D instance.
     */
    public void drawStrings(String... strings) {
        stringCount = 0;    //without this it prints whitespace for some reason
        for (String string : strings) {
            //if the string fits, just draw it
            if (g.getFontMetrics().stringWidth(string) <= availableWidth) {
                drawString(string);
            }
            //else the string doens't fit
            else {
                String toCut = string;
                int counter = 1;
                String currentCut = toCut.substring(0, counter);

                //while the string left to print is too big
                while (g.getFontMetrics().stringWidth(toCut) >= availableWidth) {
                    //take a part of the string and keep making that part bigger until it takes all the available space
                    while (g.getFontMetrics().stringWidth(currentCut) <= availableWidth) {
                        currentCut = toCut.substring(0, counter++);
                    }
                    //draw the part of the string and cut it off the string left to print
                    drawString(currentCut);
                    toCut = toCut.substring(currentCut.length(), toCut.length());

                    //reset for the next part
                    counter = 1;
                    currentCut = "";
                }
                drawString(toCut);
            }
        }
    }


    /**
     * Resets all the variables except the Graphics2D instance to default.
     */
    public void restoreDefaults() {
        setStringsToDraw("PainterError: no Strings set to draw");
        setFont(new Font("Arial", Font.PLAIN, 10));
        setAvailableWidth(100);
        setStringCount(0);
        setWhiteSpaceHeight(0);
        setTopMargin(0);
        setLeftMargin(0);
    }


    /**
     * Draws a single String on the Graphics2D instance. Also increases the String counter so the next String will be on the next line.
     *
     * @param string The string to draw on the Graphics2D instance.
     */
    private void drawString(String string) {
        g.drawString(string, leftMargin, whiteSpaceHeight * stringCount++ + topMargin);
    }


    public Graphics2D getGraphics2D() {
        return g;
    }


    public void setGraphics2D(Graphics2D g2d) {
        if (g2d == null) {
            throw new IllegalArgumentException();
        }
        this.g = g2d;
    }


    public String[] getStringsToDraw() {
        return stringsToDraw;
    }


    public void setStringsToDraw(String... stringsToDraw) {
        if (stringsToDraw == null) {
            throw new IllegalArgumentException();
        }
        this.stringsToDraw = stringsToDraw;
    }


    public Font getFont() {
        return font;
    }


    public void setFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException();
        }
        this.font = font;
    }


    public int getStringCount() {
        return stringCount;
    }


    public void setStringCount(int stringCount) {
        this.stringCount = stringCount;
    }


    public int getWhiteSpaceHeight() {
        return whiteSpaceHeight;
    }


    public void setWhiteSpaceHeight(int whiteSpaceHeight) {
        this.whiteSpaceHeight = whiteSpaceHeight + font.getSize();
    }


    public int getAvailableWidth() {
        return availableWidth;
    }


    public void setAvailableWidth(int availableWidth) {
        this.availableWidth = availableWidth;
    }


    public int getTopMargin() {
        return topMargin;
    }


    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin + font.getSize();
    }


    public int getLeftMargin() {
        return leftMargin;
    }


    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }
}
