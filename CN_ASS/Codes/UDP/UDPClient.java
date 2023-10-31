import java.io.IOException;
import java.net.*;

public class Receiver {
    public static void main(String[] args) {
        int receiverPort = 54321;

        try (DatagramSocket socket = new DatagramSocket(receiverPort)) {
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress senderAddress = receivePacket.getAddress();
                int senderPort = receivePacket.getPort();

                System.out.println("Received data from " + senderAddress + ":" + senderPort + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

