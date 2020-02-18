package com.example.osid;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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

import java.text.DateFormat;
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
    Button btn_Hoy, btn_Semana, btn_Mes;
    TextView fechaInicio, fechaFin;
    TextView testFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = findViewById(R.id.mpChart);
        dbcontroller = new DBCONTROLLER(this);

        fechaInicio = findViewById(R.id.fechaInicio);
        fechaFin = findViewById(R.id.fechaFin);
        testFecha = findViewById(R.id.testFecha);

        btn_Hoy = findViewById(R.id.btn_hoy);
        btn_Mes = findViewById(R.id.btn_mes);
        btn_Semana = findViewById(R.id.btn_semana);
        TestData();
        Initialization();
        //Test();
    }

    void Initialization(){

        Date fechaActual = new Date();
        fechaInicio.setText(DateToString(fechaActual, "dd/MM/YYYY"));
        fechaFin.setText(DateToString(fechaActual, "dd/MM/YYYY"));

        //ChartCalculator(StringToDate("2020-02-18 0:00",GLOBAL.DATE_FORMAT),StringToDate("2020-02-18 23:00",GLOBAL.DATE_FORMAT));

        ChartCalculator(fechaActual,fechaActual);
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
            dtStart = "2020-03-" + i +" 11:00:00";
            dbcontroller.InsertGlucose(new Glucose(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((300 - 55) + 1) + 55));
            dtStart = "2020-03-" + i +" 21:00:00";
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
        dataSet.setFillAlpha(100);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(getColor(R.color.blue));
        lines.add(dataSet);

        LineDataSet dataSet2 = new LineDataSet(entries2,"Glucosa");
        dataSet2.setColor(getColor(R.color.colorAccent));
        dataSet2.setValueTextColor(getColor(R.color.black));
        dataSet2.setFillAlpha(100);
        dataSet2.setDrawFilled(true);
        dataSet2.setFillColor(getColor(R.color.colorAccent));
        lines.add(dataSet2);


        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);
        lineData.addDataSet(dataSet2);
        lineChart.setData(lineData);
        lineChart.animateY(1000);

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

    void TestData(){
        String dtStart;
        for(int i = 1; i <= 29;i++){
            dtStart = "2020-02-" + i +" 17:00:00";
            dbcontroller.InsertInsuline(new Insuline(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((40 - 1) + 1) + 1));
            dbcontroller.InsertGlucose(new Glucose(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((300 - 55) + 1) + 55));
            dtStart = "2020-02-" + i +" 11:00:00";
            dbcontroller.InsertGlucose(new Glucose(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((300 - 55) + 1) + 55));
            dtStart = "2020-02-" + i +" 21:00:00";
            dbcontroller.InsertGlucose(new Glucose(i,StringToDate(dtStart, GLOBAL.DATE_FORMAT),new Random().nextInt((300 - 55) + 1) + 55));
        }
    }

    void ChartCalculator(Date startDate, Date endDate){
        lineChart.clear();

        List<Entry> glucosaEntries = new ArrayList<Entry>();
        List<Entry> insulinaEntries = new ArrayList<Entry>();

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

        String dateFormat = "YYYYMMdd";
        Date startDateFinal = StringToDate(DateToString(startDate,dateFormat),dateFormat);
        Date endDateFinal = StringToDate(DateToString(endDate,dateFormat),dateFormat);
        //List<Integer> removeIndexInsulines = new ArrayList<>();

        List<Insuline> removeInsulines = new ArrayList<>();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateFormat);
        for(int i = 0; i < insulines.size();i++){
            if(StringToDate(DateToString(insulines.get(i).getFecha(),dateFormat),dateFormat).before(startDateFinal) || StringToDate(DateToString(insulines.get(i).getFecha(),dateFormat),dateFormat).after(endDateFinal)){
                removeInsulines.add(insulines.get(i));
            }
        }
        for(int i = 0; i<removeInsulines.size();i++){
            try{
                insulines.remove(removeInsulines.get(i));
            }catch (Exception e){
                break;
            }
        }

        //List<Integer> removeIndexGlucoses = new ArrayList<>();
      /*  List<Glucose> removeGlucose = new ArrayList<>();
        for(int i = 0; i < glucoses.size();i++){
            if(glucoses.get(i).getFecha().before(startDateFinal) || glucoses.get(i).getFecha().after(endDateFinal)){
                removeGlucose.add(glucoses.get(i));
            }
        }
        for(int i = 0; i<removeGlucose.size();i++){
            try{
                glucoses.remove(removeGlucose.get(i));
            }catch (Exception e ){
                break;
            }
        }*/


        for(Insuline dataSet : insulines){
            insulinaEntries.add(new Entry((long)dataSet.getFecha().getTime(),dataSet.getInsuline()));
        }
        for(Glucose dataSet: glucoses){
            glucosaEntries.add(new Entry(dataSet.getFecha().getTime(),dataSet.getGlucose()));
        }

        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet> ();

        LineDataSet dataSetInsulina = new LineDataSet(insulinaEntries,"Insulina");
        dataSetInsulina.setColor(getColor(R.color.blue));
        dataSetInsulina.setValueTextColor(getColor(R.color.black));
        dataSetInsulina.setFillAlpha(100);
        dataSetInsulina.setDrawFilled(true);
        dataSetInsulina.setFillColor(getColor(R.color.blue));
        lines.add(dataSetInsulina);

        LineDataSet dataSetGlucosa = new LineDataSet(glucosaEntries,"Glucosa");
        dataSetGlucosa.setColor(getColor(R.color.colorAccent));
        dataSetGlucosa.setValueTextColor(getColor(R.color.black));
        //dataSetGlucosa.setFillAlpha(100);
        //dataSetGlucosa.setDrawFilled(true);
        //dataSetGlucosa.setFillColor(getColor(R.color.colorAccent));
        lines.add(dataSetGlucosa);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSetInsulina);
        lineData.addDataSet(dataSetGlucosa);
        lineChart.setData(lineData);
        lineChart.animateY(1000);

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
