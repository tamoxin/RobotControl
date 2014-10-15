package example.marco.com.controlyourrobot;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Marco on 20/07/2014.
 */

//This class prepare the sockets for sending data.
public class SendingInformationOverWifi implements Runnable{

    String ip, message;
    int port = 0;

    public SendingInformationOverWifi(){
        this.ip = "0.0.0.0";
        this.port = 0;
    }

    public SendingInformationOverWifi(String ip, int internetPort){
        this.ip = ip;
        this.port = internetPort;
    }

    public void setMessage(String message)
    {
        this.message = message;
        run();
    }

    @Override
    public void run() {
        try {
            InetAddress packageReadyIP = InetAddress.getByName(this.ip);
            int packageReadyInternetPort = this.port;
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer;
            buffer = this.message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, packageReadyIP, packageReadyInternetPort);
            socket.send(packet);
        }
        catch (Exception e) {
        }
    }
}
