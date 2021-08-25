package Tests;

public class SubStringing {
    public static void main(String args[]){
        SubStringing main = new SubStringing();
        main.run();

    }

    int maxStringLength = 10;

    public void run(){
        String[] strings = {"BarstBank", "Withdrawal", "$4.20", "From: NL58INGB0758345364", "Date: 20-04-2018", "Following: longStringTest", "12345678910111213141516171819202122232425262728293031323334353637383940"};
        for (String string : strings) {
            if (string.length() < maxStringLength) {
                drawString(string);
            }
            else {
                System.out.println("Startstring '" + string + "' width: " + string.length());

                String toCut = string;
                int counter = 1;
                String currentCut = toCut.substring(0, counter);
                while (string.length() > maxStringLength) {
                    while (string.length() < maxStringLength) {
                        System.out.println("Currentcut counter: " + counter);
                        currentCut = toCut.substring(0, counter++);
                    }
                    System.out.println("Drew string: " + currentCut);
                    drawString(currentCut);
                    toCut = toCut.substring(counter, toCut.length());
                    System.out.println("New toCut: " + toCut);
                    counter = 1;
                }
            }
        }
        String string = "123456789";
        System.out.println(string.substring(2, string.length()));
    }

    public void drawString(String string){
        System.out.println(string);
    }

}
