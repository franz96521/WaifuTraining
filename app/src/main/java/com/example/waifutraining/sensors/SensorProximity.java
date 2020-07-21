package com.example.waifutraining.sensors;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.example.waifutraining.speak.SpeakText;

public class SensorProximity implements SensorEventListener {
    TextView tv;
    Sensor sensor;
    SpeakText sp;
    int count=0;
    boolean voice =false;
    public  SensorProximity(TextView tv, Sensor sensor, Context context){
        this.tv = tv;
        this.sensor=sensor;
        sp = new SpeakText(context);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        voice= preferences.getBoolean("voice",false);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0]<sensor.getMaximumRange()){
            count++;
            tv.setText(count+"");
            sp.speak(count+"");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public int getCount() {
        return count;
    }
}
