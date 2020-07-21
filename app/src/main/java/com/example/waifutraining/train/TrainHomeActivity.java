package com.example.waifutraining.train;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.waifutraining.R;

public class TrainHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_home);
    }

    public void goToModifyStatistics(View view){
        Intent i=new Intent(this, ModifyStatisticsActivity.class);
        startActivity(i);
    }
    public void goToSquat(View view){
        Intent i=new Intent(this, SquatActivity.class);
        startActivity(i);
    }
    public void goToPushUp(View view){
        Intent i=new Intent(this, PushUpActivity.class);
        startActivity(i);
    }
    public void goToSitUp(View view){
        Intent i=new Intent(this, SitUpActivity.class);
        startActivity(i);
    }
}