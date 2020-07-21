package com.example.waifutraining.train;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.waifutraining.AdminSQLite;
import com.example.waifutraining.R;
import com.example.waifutraining.sensors.SensorProximity;

public class PushUpActivity extends AppCompatActivity {
    int count=0;
    TextView pushUpCount;
    SensorManager sensorManager;
    Sensor sensor;
    SensorProximity sensorEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);
        pushUpCount=(TextView)findViewById(R.id.count_push_up);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(sensor==null){
            finish();
        }
        sensorEventListener =new SensorProximity(pushUpCount,sensor,this);
        start();

    }

    public void start(){
        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_NORMAL);
    }



    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
        AdminSQLite admin = new AdminSQLite(this,"DB",null,1);
        SQLiteDatabase DB =admin.getWritableDatabase();
        admin.modifyData(admin.getTodayDate(),"push_up",sensorEventListener.getCount());
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