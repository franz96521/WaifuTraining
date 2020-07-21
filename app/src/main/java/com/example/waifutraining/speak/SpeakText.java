package com.example.waifutraining.speak;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.example.waifutraining.sensors.SensorAccelerometer;

import java.util.Locale;

public class SpeakText {
    private TextToSpeech mTTs;
    private Context context;
    public SpeakText(Context context){
        mTTs=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTTs.setLanguage(Locale.getDefault());
                }
            }
        });
    }
    public void  speak(String str){

        mTTs.speak(str, TextToSpeech.QUEUE_FLUSH,null);
    }


}
