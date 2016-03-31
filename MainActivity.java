package com.example.androidtest;

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener{

	//����ҡһҡ����Сʱ����
	private final int SHAKE_SHORTEST_TIME_INTERVAL = 5;
	//������ֵ�仯�ķ�ֵ
	private final int SHAKE_SHORTEST_SENSOR_VALUE = 10;
	private long lastShakeTime = 0;
	private SensorManager sensorManager;
	private Sensor sensor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	
    	sensorManager.unregisterListener(this, sensor);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		long currentTime = System.currentTimeMillis();
		int type = event.sensor.getType();
		if (((currentTime-lastShakeTime) <= SHAKE_SHORTEST_TIME_INTERVAL) ||
				(type != Sensor.TYPE_ACCELEROMETER)) {
			return;
		}
		lastShakeTime = currentTime;
		float[] values = event.values;
		if ((Math.abs(values[0]) > SHAKE_SHORTEST_SENSOR_VALUE || 
				Math.abs(values[1]) > SHAKE_SHORTEST_SENSOR_VALUE || 
				Math.abs(values[2]) > SHAKE_SHORTEST_SENSOR_VALUE)){  
             Toast.makeText(this, "ҡһҡ�ɹ�", Toast.LENGTH_SHORT).show();  
        } 
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

}
