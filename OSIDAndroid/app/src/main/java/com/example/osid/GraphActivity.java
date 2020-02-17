package com.example.osid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.GLOBAL.GLOBAL;
import com.example.osid.POJOs.Glucose;
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
import java.util.Collections;
import java.util.Comparator;
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
        // TODO poner rangos: ver los valores del dia, o del mes o de un rango especifico
        //
        List<Entry> entries = new ArrayList<Entry>();
        List<Entry> entries2 = new ArrayList<Entry>();
        String dtStart;
        for(int i = 1; i <= 30;i++){
            dtStart = "2020-03-" + i +" 17:00:00";
            dbcontroller.InsertInsuline(new Insuline(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((40 - 1) + 1) + 1));
            dbcontroller.InsertGlucose(new Glucose(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((300 - 55) + 1) + 55));
        }

        List<Insuline> insulines = new ArrayList<Insuline>(dbcontroller.GetArrayInsuline());
        List<Glucose> glucoses = new ArrayList<Glucose>(dbcontroller.GetArrayGlucose());
        Collections.sort(insulines, new Comparator<Insuline>() {
            @Override
            public int compare(Insuline insuline, Insuline t1) {
                return insuline.getFecha().compareTo(t1.getFecha());
            }
        });
        Collections.sort(glucoses, new Comparator<Glucose>() {
            @Override
            public int compare(Glucose glucose, Glucose t1) {
                return glucose.getFecha().compareTo(t1.getFecha());
            }
        });

        for(Insuline dataSet : insulines){
            entries.add(new Entry((long)dataSet.getFecha().getTime(),dataSet.getInsuline()));
        }
        for(Glucose dataSet: glucoses){
            entries2.add(new Entry(dataSet.getFecha().getTime(),dataSet.getGlucose()));
        }

        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet> ();

        LineDataSet dataSet = new LineDataSet(entries,"Insulina");
        dataSet.setColor(getColor(R.color.blue));
        dataSet.setValueTextColor(getColor(R.color.black));
        lines.add(dataSet);

        LineDataSet dataSet2 = new LineDataSet(entries2,"Glucosa");
        dataSet2.setColor(getColor(R.color.colorAccent));
        dataSet2.setValueTextColor(getColor(R.color.black));
        dataSet2.setFillColor(getColor(R.color.colorAccent));
        lines.add(dataSet2);


        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);
        lineData.addDataSet(dataSet2);
        lineChart.setData(lineData);
        lineChart.animateY(800);

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
