package example.marco.com.controlyourrobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    //deviceSelected is an int type variable that performs as an identifier
    //for the DataSensors class. It contains the value of each device, where:
    //deviceA = 0       deviceB = 1     deviceC = 2
    public int deviceSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
        //Other way to write the if sentence:
        //return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    //This method lets you know which button was pressed,
    //so yo can know where the device will go
    public void whichDeviceIsSelected(View view) {

        switch(view.getId())
        {
            case R.id.buttonA:
                deviceSelected = 1;
                break;
            case R.id.buttonB:
                deviceSelected = 2;
                break;
            case R.id.buttonC:
                deviceSelected = 3;
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }

        Log.d("Device Selected",""+deviceSelected);

        if(deviceSelected != -1){
            Intent i = new Intent(this, DataSensors.class );
            i.putExtra("deviceSelected",deviceSelected);
            startActivity(i);
        }
    }
}
