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

    public void setSensorsMessage(float valueOfX, float valueOfY, float valueOfZ){
        String message = ("Value of X: " +valueOfX
                        +"\n Value of Y: " +valueOfY
                        +"\n Value of Z: " +valueOfZ);
        this.packager.setMessage(message);
    }

    public void sendPackage(){
        if(this.packager.ip.length() > 0 && this.packager.port != 0)
            new Thread(this.packager).start();
    }
}
