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
    int port = 7777;

    public SendingInformationOverWifi(){
        this.port = 7777;
    }

    public SendingInformationOverWifi(int internetPort){
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
            InetAddress packageReadyIP = InetAddress.getByName("localhost");
            int packageReadyInternetPort = this.port;
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer;
            buffer = this.message.getBytes();
            //Checar si funciona con el length del buffer
            int msg_length = message.length();

            //Checar parámetro de buffer. Si no, pasar el mensaje como parámetro.
            DatagramPacket packet = new DatagramPacket(buffer, msg_length, packageReadyIP, packageReadyInternetPort);
            socket.send(packet);
        }
        catch (Exception e) {
        }
    }
}
