package example.marco.com.controlyourrobot;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

//This class gets the necessary data from the gyroscope
public class DataSensors extends Activity implements SensorListener {

    final String sensorLog = "Sensor Log";
    private TextView x,y,z,direction;
    private SensorManager mySensorManager;
    private int whichDeviceWasSelected;
    private float filter = (float)0.7;
    private int port = 61557;
    private String ip;
    private InformationSender packager;
    private Toast ipToaster, portToaster;

    public float xViewPositive;
    public float xViewNegative;
    public float yViewPositive;
    public float yViewNegative;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_data);
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        whichDeviceWasSelected = getIntent().getIntExtra("deviceSelected",0);
        ip = "0.0.0." + whichDeviceWasSelected;
        x = (TextView) findViewById(R.id.xValue);
        y = (TextView) findViewById(R.id.yValue);
        z = (TextView) findViewById(R.id.zValue);
        direction = (TextView) findViewById(R.id.direction);
        ipToaster = Toast.makeText(this,"ipNumber: " + ip,Toast.LENGTH_SHORT);
        ipToaster.show();
        portToaster = Toast.makeText(this,"PortNumber: " + port,Toast.LENGTH_SHORT);
        portToaster.show();
        packager = new InformationSender();
        setInformationSender();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(this, SensorManager.SENSOR_ORIENTATION |
                SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mySensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mySensorManager.unregisterListener(this);
        super.onStop();
    }


    public void setInformationSender(){
        this.packager.prepareForSendingInformationOverWifi(this.ip,this.port);
    }

    @Override
    public void onSensorChanged(int i, float[] floats) {

        synchronized (this){
            Log.d(sensorLog, "On sensor changed: " + i + " X:" + floats[0] + " Y:" + floats[1] + " Z:" + floats[2]);
            //if (i == SensorManager.SENSOR_ORIENTATION){
                x.setText(""+floats[0]);
                y.setText(""+floats[1]);
                z.setText(""+floats[2]);
            //}
        }
    }

    @Override
    public void onAccuracyChanged(int i, int i2) {

    }
}
