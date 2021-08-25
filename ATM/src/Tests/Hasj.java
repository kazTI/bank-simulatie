package Tests;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Hasj {
    public static void main(String args[]) {
        testHashing();
    }


    public static void testHashing() {
        try {
            Scanner scanner = new Scanner(System.in);

            while(true){
                if(scanner.hasNextLine()){
                    String toTest = "55B925C3" + scanner.nextLine();

                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] byteData = digest.digest(toTest.getBytes(StandardCharsets.UTF_8));

                    StringBuffer sb = new StringBuffer();
                    for (byte bit : byteData) {
                        sb.append(Integer.toString((bit & 0xff) + 0x100, 16).substring(1));
                    }
                    System.out.println("Hasj: " + sb.toString());
                }
            }

        } catch (Exception e) {
            System.out.println("Errorr");
        }
    }


    public static void oldHashing() throws NoSuchAlgorithmException {
        String toTest = "55B925C3" + "";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] temp = digest.digest(toTest.getBytes(StandardCharsets.UTF_8));
        String hash = Base64.getEncoder().encodeToString(temp);

        System.out.println(hash);
    }
}
