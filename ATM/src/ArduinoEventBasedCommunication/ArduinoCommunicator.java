package ArduinoEventBasedCommunication;

import HandmadeGUI.Controller;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.application.Platform;

import java.io.IOException;

public class ArduinoCommunicator implements SerialPortDataListener {

    private Controller controller;
    private SerialPort comPort;


    public ArduinoCommunicator(Controller controller) {
        this.controller = controller;
        initialize();
    }


    public void initialize() {
        log("Connecting to Arduino...");
        try {
            comPort = SerialPort.getCommPorts()[0];
            comPort.setBaudRate(2000000);
            comPort.openPort();
            comPort.addDataListener(this);
            log("Connected to Arduino.");
        } catch (Exception e) {
            controller.error("Connection to Arduino failed.");
        }
    }

    public void sendData(String data){
        try {
            comPort.getOutputStream().write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readData(){
        String result = null;
        try {
            if(comPort.getInputStream().available() > 0){
                result = parseData();
                log("Read from Arduino: '" + result + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }


    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            return;
        }
        Platform.runLater(() -> controller.onArduinoDataReceive(parseData()));
    }



    private String parseData() {
        byte[] receivedData = new byte[comPort.bytesAvailable()];
        comPort.readBytes(receivedData, receivedData.length);
        String result = "";
        for (byte temp : receivedData) {
            result = result + (char) temp;
        }
        return result;
    }


    public void log(String message) {
        controller.log(message);
    }
}
