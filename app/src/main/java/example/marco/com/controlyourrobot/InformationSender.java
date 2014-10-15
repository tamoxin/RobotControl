package example.marco.com.controlyourrobot;

import android.util.Log;

/**
 * Created by Marco on 18/08/2014.
 */

//This class sends the information to the client
public class InformationSender {

    private SendingInformationOverWifi packager;

    public InformationSender(){
        this.packager = new SendingInformationOverWifi();
    }

    public void prepareForSendingInformationOverWifi(String ip, int port){
        Log.d("IP: ",ip);
        Log.d("Port: ",""+port);
        this.packager = new SendingInformationOverWifi(ip,port);
    }

    public void setSensorsMessage(String messageReceived){
        String message = ""+messageReceived;
        this.packager.setMessage(message);
        sendPackage();
    }

    public void sendPackage(){
        if(this.packager.ip.length() > 0 && this.packager.port != 0)
            new Thread(this.packager).start();
    }
}
