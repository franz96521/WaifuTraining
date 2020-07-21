package com.example.waifutraining.sensors;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.example.waifutraining.R;
import com.example.waifutraining.speak.SpeakText;

import java.util.prefs.Preferences;

public class SensorAccelerometer implements SensorEventListener {
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static int SHAKE_THRESHOLD = 20;
    SpeakText sp;
    float x,y,z;
    int count=0,delay;
    TextView tv;
    Context context;
    boolean voice=false;
    public SensorAccelerometer(int threshold, int  delay, float x, float y , float z, TextView tv, Context context){

        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        voice= preferences.getBoolean("voice",false);
        this.x=x;
        this.y=y;
        this.z=z;
        this.delay=delay;
        this.SHAKE_THRESHOLD=threshold;
        this.tv=tv;
        sp = new SpeakText(context);
        this.context=context;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            actions(event,x,y,z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void actions(SensorEvent event, float x,float y,float z ){
        float speed=0;
        if(x==1){
            x=event.values[0];
            speed+=x-last_x;
        }
        if(y==1){
            y=event.values[1];
            speed+=y-last_y;
        }
        if(z==1){
            z=event.values[2];
            speed+=z-last_z;
        }
        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > delay) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;
            speed=Math.abs(speed) / diffTime * 10000;
            System.out.println(speed+"  "+count);
            if (speed > SHAKE_THRESHOLD) {

                count++;
                if(voice)sp.speak(count+"");
                tv.setText(count+"");
            }
            last_x = x;
            last_y = y;
            last_z = z;

        }
    }

    public int getCount() {
        return count;
    }
}
