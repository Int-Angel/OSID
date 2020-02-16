package com.example.osid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.GLOBAL.GLOBAL;
import com.example.osid.POJOs.Insuline;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GraphActivity extends AppCompatActivity {
    LineChart lineChart;
    DBCONTROLLER dbcontroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = findViewById(R.id.mpChart);
        dbcontroller = new DBCONTROLLER(this);
        Test();
    }
    void Test(){
        // TODO poner rangos: ver los valores de
        List<Insuline> insulines = new ArrayList<Insuline>();
        List<Entry> entries = new ArrayList<Entry>();

        String dtStart;
        for(int i = 1; i<=29;i++){
            dtStart = "2020-02-" + i +" 17:00:00";
            insulines.add(new Insuline(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((40 - 1) + 1) + 1));
        }
        for(int i = 0; i<10;i++){
            dbcontroller.InsertInsuline(insulines.get(i));
        }
        insulines.clear();
        insulines = new ArrayList<>(dbcontroller.GetArrayInsuline());
        /*for(int i = 0; i < insulines.size(); i++){
            entries.add(new Entry(i,1.5f));
        }*/
        int i = 1;
        for(Insuline dataSet : insulines){
            entries.add(new Entry((long)dataSet.getFecha().getTime(),dataSet.getInsuline()));
            i++;
        }

        LineDataSet dataSet = new LineDataSet(entries,"Prueba");
        dataSet.setColor(getColor(R.color.blue));
        dataSet.setValueTextColor(getColor(R.color.black));



        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Date date = new Date((long)value);
                //Specify the format you'd like
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
                return sdf.format(date);
            }
        };

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(valueFormatter);
    }

    public static Date StringToDate(String dateStr, String format){
        try{
            return new SimpleDateFormat(format).parse(dateStr);
        }catch (ParseException e){
            return null;
        }
    }

    public static String DateToString(Date datet, String format){
        try {
           return new SimpleDateFormat(format).format(datet);
        } catch (Exception e) {
            return null;
        }
    }
}
