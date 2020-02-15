package com.example.osid;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osid.DB.DBCONTROLLER;
import com.example.osid.DB.DBCREATOR;
import com.github.mikephil.charting.charts.LineChart;

public class GraphActivity extends AppCompatActivity {
    LineChart lineChart;
    SQLiteDatabase sqLiteDatabase;
    DBCONTROLLER dbcontroller;
    DBCREATOR dbcreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = findViewById(R.id.mpChart);
        sqLiteDatabase = dbcreator.getWritableDatabase();
    }
}
