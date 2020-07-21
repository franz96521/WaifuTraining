package com.example.waifutraining.train;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.waifutraining.AdminSQLite;
import com.example.waifutraining.R;
import com.example.waifutraining.sensors.SensorAccelerometer;

public class SquatActivity extends AppCompatActivity {
    TextView squatCount;
    SensorManager sensorManager;
    Sensor sensor;
    SensorAccelerometer sensorEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squat);
        squatCount=(TextView)findViewById(R.id.count_squats);
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorEventListener= new SensorAccelerometer(12,1500,0,1,0,squatCount,this);
    }
    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_NORMAL);
    }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
        AdminSQLite admin = new AdminSQLite(this,"DB",null,1);
        SQLiteDatabase DB =admin.getWritableDatabase();
        admin.modifyData(admin.getTodayDate(),"squat",sensorEventListener.getCount());
        admin.close();
    }
    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }
    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}