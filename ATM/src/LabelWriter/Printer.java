package LabelWriter;

import java.awt.*;
import java.awt.print.*;

public class Printer implements Printable {

    private Painter painter;
    private boolean enablePrintingDialog = false;


    public static void main(String args[]) {
        Printer printer = new Printer();
        printer.setEnablePrintingDialog(false);
        printer.print("BarstBank", "Withdrawal", "$4.20", "From: NL58INGB0758345364", "Date: 20-04-2018", "Following: longStringTest", "12345678910111213141516171819202122232425262728293031323334353637383940");
    }


    public Printer() {
        painter = new Painter();
    }


    /**
     * Do not use this method. Copied from https://docs.oracle.com/javase/tutorial/2d/printing/printable.html.
     *
     * @param graphics Graphics instance to paint.
     * @param pf       The PageFormat whatever that is.
     * @param page     The page number I think.
     * @return Whether the page exist or something.
     */
    @Override
    public int print(Graphics graphics, PageFormat pf, int page) {

        //there is only 1 page
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g = (Graphics2D) graphics;
        g.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        painter.paint(g);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }


    /**
     * Draws the given Strings. Fits the Strings in the given width, cutting Strings of and drawing them on the next line when too large.
     *
     * @param stringsToPrint The Strings to print.
     */
    public void print(String... stringsToPrint) {
        painter.setStringsToDraw(stringsToPrint);
        paint();
    }


    /**
     * Prints out the Strings you should've set.
     * Method based of the code from https://docs.oracle.com/javase/tutorial/2d/printing/printable.html.
     */
    public void paint() {
        PrinterJob job = PrinterJob.getPrinterJob();

        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        double margin = 0; // half inch
        paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
        pf.setPaper(paper);
        job.setPrintable(this, pf);

        if (enablePrintingDialog) {
            if (!job.printDialog()) {
                System.out.println("Printer: print dialog error.");
                return;
            }
        }

        try {
            job.print();
        } catch (PrinterException ex) {
            System.out.println("Printer: error in printing process.");
        }
    }


    public Painter getPainter() {
        return painter;
    }


    public void setEnablePrintingDialog(boolean enablePrintingDialog) {
        this.enablePrintingDialog = enablePrintingDialog;
    }
}