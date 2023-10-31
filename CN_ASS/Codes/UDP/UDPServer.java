import java.io.IOException;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        String receiverIP = "192.168.29.222";
        int receiverPort = 54321;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress receiverAddress = InetAddress.getByName(receiverIP);

            String message = "Aditya Suryawanshi is VIT Pune Student!!";
            byte[] sendData = message.getBytes();

            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, receiverAddress, receiverPort);
            socket.send(packet);

            System.out.println("Message sent to receiver: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

