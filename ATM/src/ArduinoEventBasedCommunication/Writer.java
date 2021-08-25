package ArduinoEventBasedCommunication;

import Tests.ArduinoCommunicatorTester;

import java.util.Scanner;

public class Writer implements ArduinoDataReceivedListener {
    public static void main(String args[]) {
        Writer writer = new Writer();
        writer.run();
    }


    public void run() {
        ArduinoCommunicatorTester connection = new ArduinoCommunicatorTester(this);
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(scanner.hasNextLine()){
                String input = scanner.nextLine();
                connection.sendData(input);
                System.out.println("Send '" + input + "' to Arduino");
            }
        }
    }


    @Override
    public void onArduinoDataReceive(String message) {
        System.out.println("Received from Arduino: '" + message + "'");
    }
}
