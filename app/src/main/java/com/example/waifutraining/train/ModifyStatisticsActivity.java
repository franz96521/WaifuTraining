package com.example.waifutraining.train;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waifutraining.AdminSQLite;
import com.example.waifutraining.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ModifyStatisticsActivity extends AppCompatActivity {
    private PieChart pieChart;
    private TextView squats_tv, sit_up_tv, push_up_tv;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_statistics);
        AdminSQLite admin = new AdminSQLite(this,"DB",null,1);
            SQLiteDatabase DB =admin.getWritableDatabase();
            date= admin.getTodayDate();
            admin.close();
        firstTable();
        updateTableByDate(date);
    }
    public void add(View view){
        AdminSQLite admin = new AdminSQLite(this,"DB",null,1);
        SQLiteDatabase DB =admin.getWritableDatabase();
        switch (view.getId()){
            case R.id.push_up_add:
                admin.modifyData(date,"push_up",1);
                break;
            case R.id.sit_up_add:
                admin.modifyData(date,"sit_up",1);
                break;
            case R.id.squats_add:
                admin.modifyData(date,"squat",1);
                break;
        }
        admin.close();
        updateTableByDate(date);
        updateLabel();
    }
    public void quit(View view){
        AdminSQLite admin = new AdminSQLite(this,"DB",null,1);
        SQLiteDatabase DB =admin.getWritableDatabase();
        switch (view.getId()){
            case R.id.push_up_quit:
                admin.modifyData(date,"push_up",-1);
                break;
            case R.id.sit_up_quit:
                admin.modifyData(date,"sit_up",-1);
                break;
            case R.id.squats_quit:
                admin.modifyData(date,"squat",-1);
                break;
        }
        admin.close();
        updateTableByDate(date);
        updateLabel();
    }
    public void selectDate(View view){
        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dateString=year+"-";
                monthOfYear++;
                if(monthOfYear<10) dateString+="0"+monthOfYear+"-";
                    else dateString+=(monthOfYear+1)+"-";
                if(dayOfMonth<10) date+="0"+dayOfMonth;
                    else dateString+=dayOfMonth;
                System.out.println(dateString);
                date= dateString;
                updateTableByDate(date);
                updateLabel();
            }
        };
        Calendar c = Calendar.getInstance();
        DatePickerDialog mDate = new DatePickerDialog(ModifyStatisticsActivity.this, dateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        mDate.show();
    }
    public void updateTableByDate(String date){
        createTable(createDataByDate(date));
        updateLabel();
        pieChart.setCenterText(getResources().getText(R.string.data_of)+" "+date);
    }
    public void updateLabel(){
        AdminSQLite admin = new AdminSQLite(this,"DB",null,1);
        SQLiteDatabase DB =admin.getWritableDatabase();
            ArrayList<Integer> data=admin.getData(this.date);
                squats_tv=(TextView)findViewById(R.id.squats_TV);
                squats_tv.setText(""+data.get(2));
                sit_up_tv=(TextView)findViewById(R.id.sit_up_TV);
                sit_up_tv.setText(""+data.get(1));
                push_up_tv=(TextView)findViewById(R.id.push_up_TV);
                push_up_tv.setText(""+data.get(0));
        admin.close();

    }
    public void firstTable(){
        pieChart =(PieChart)findViewById(R.id.piCharOneDay);
        pieChart.setUsePercentValues(false);
        pieChart.setDrawEntryLabels(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setCenterText(getResources().getText(R.string.todayData));
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
    }
    public void createTable(ArrayList<PieEntry> Values){
        PieDataSet dataSet= new PieDataSet(Values,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);

        data.setValueTextColor(Color.YELLOW);

        pieChart.clear();
        pieChart.setData(data);
        pieChart.invalidate();
    }
    public ArrayList<PieEntry> createDataByDate(String  date){
        AdminSQLite admin = new AdminSQLite(this,"DB",null,1);
            SQLiteDatabase DB =admin.getWritableDatabase();
                ArrayList<Integer> data=admin.getData(date);
                ArrayList<PieEntry> values;
                    values = new ArrayList<>();
                    values.add(new PieEntry(data.get(0), "PUSH_UP"));
                    values.add(new PieEntry(data.get(1), "SIT_UP"));
                    values.add(new PieEntry(data.get(2), "SQUAT"));
                    admin.close();
            return values;
    }

}