package example.marco.com.controlyourrobot;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

//This class gets the necessary data from the gyroscope
public class DataSensors extends Activity implements SensorEventListener{
    private TextView x,y,z;
    private Sensor gyroscope;
    private int whichDeviceWasSelected;
    private float valueOfX = 0, valueOfY = 0, valueOfZ = 0;
    private float previousValueOfX = 0, previousValueOfY = 0, previousValueOfZ = 0;
    private float currentValueOfX = 0, currentValueOfY = 0, currentValueOfZ = 0;
    private float filter = (float)0.7;
    private int port = 61557;
    private String ip;
    private InformationSender packager;
    Toast toaster, gyroscopeDetected, ipToaster, portToaster;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_data);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        whichDeviceWasSelected = getIntent().getIntExtra("deviceSelected",0);
        ip = "0.0.0." + whichDeviceWasSelected;
        x = (TextView)findViewById(R.id.xValue);
        y = (TextView)findViewById(R.id.yValue);
        z = (TextView)findViewById(R.id.zValue);
        ipToaster = Toast.makeText(this,"ipNumber: " + ip,Toast.LENGTH_SHORT);
        ipToaster.show();
        portToaster = Toast.makeText(this,"PortNumber: " + port,Toast.LENGTH_SHORT);
        portToaster.show();
        packager = new InformationSender();
        setInformationSender();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorManager gyroscopeSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorsList = gyroscopeSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        if (sensorsList.size() == 0) {
            toaster = Toast.makeText(this,"@string/gyroscopeError",Toast.LENGTH_SHORT);
            toaster.show();
            return;
        }
        gyroscope = gyroscopeSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeSensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, gyroscope);
        super.onPause();
    }

    @Override
    protected void onStop() {
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, gyroscope);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //The variable "filter" tries to reduce abrupt changes in accelerometer readings.
        valueOfX = previousValueOfX*(1-filter) + currentValueOfX*filter;
        valueOfY = previousValueOfY*(1-filter) + currentValueOfY*filter;
        valueOfZ = previousValueOfZ*(1-filter) + currentValueOfZ*filter;

        currentValueOfX = event.values[0];
        currentValueOfY = event.values[1];
        currentValueOfZ = event.values[2];

        packager.setSensorsMessage(valueOfX,valueOfY,valueOfZ);
        packager.sendPackage();
        this.x.setText(String.valueOf(valueOfX));
        this.y.setText(String.valueOf(valueOfY));
        this.z.setText(String.valueOf(valueOfZ));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void setInformationSender(){
        this.packager.prepareForSendingInformationOverWifi(this.ip,this.port);
    }
}
