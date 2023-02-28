package com.example.myapplication.Models;





import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerModule extends Activity implements SensorEventListener {

    private  SensorManager mSensorManager;
    public   Sensor mAccelerometer;

    public AccelerometerModule(){
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public boolean startListening = false;
    public boolean endListening = false;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values.clone();
        if(startListening){
            Log.d("Sensor-App", "x:" + values[0] + " y:" + values[1] + " z:" + values[2]);
            startListening = false;
        }
        if(endListening){
            Log.d("Sensor-App", "x:" + values[0] + " y:" + values[1] + " z:" + values[2]);
            endListening = false;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
