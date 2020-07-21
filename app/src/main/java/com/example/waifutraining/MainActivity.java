package com.example.waifutraining;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.waifutraining.speak.SpeakText;
import com.example.waifutraining.train.ModifyStatisticsActivity;
import com.example.waifutraining.train.TrainHomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNaigatorView;
    private TextView initialMessages_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNaigatorView=findViewById(R.id.bottomNav);
        bottomNaigatorView.setOnNavigationItemSelectedListener(bottomNavMethod);
        PreferenceManager.setDefaultValues(this,R.xml.root_preferences,false);

        initialMessages();

    }
    public BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod= new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.train:
                            goTotrainHome();
                            break;
                        case R.id.settings:
                            goToSettings();
                            break;
                    }
                    return false;
                }
            };
    public void goTotrainHome(){

        Intent i=new Intent(this,TrainHomeActivity.class);

        startActivity(i);
    }
    public void goToSettings(){
        Intent i=new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
    public void initialMessages(){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        initialMessages_TV= (TextView)findViewById(R.id.initial_messages);

        initialMessages_TV.setText("hola "+preferences.getString("username","Prieto-kun"));
    }



}