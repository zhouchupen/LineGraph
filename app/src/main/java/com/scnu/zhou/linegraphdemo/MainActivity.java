package com.scnu.zhou.linegraphdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scnu.zhou.widget.LineGraph;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineGraph lineGraph = (LineGraph) findViewById(R.id.lineGraph);

        LineGraph.GraphData data1 = new LineGraph.GraphData("2010", 50);
        LineGraph.GraphData data2 = new LineGraph.GraphData("2011", 120);
        LineGraph.GraphData data3 = new LineGraph.GraphData("2012", 100);
        LineGraph.GraphData data4 = new LineGraph.GraphData("2013", 110);
        LineGraph.GraphData data5 = new LineGraph.GraphData("2014", 150);
        LineGraph.GraphData data6 = new LineGraph.GraphData("2015", 80);
        LineGraph.GraphData data7 = new LineGraph.GraphData("2016", 100);
        LineGraph.GraphData data8 = new LineGraph.GraphData("2017", 170);
        List<LineGraph.GraphData> datas = new ArrayList<>();
        datas.add(data1);
        datas.add(data2);
        datas.add(data3);
        datas.add(data4);
        datas.add(data5);
        datas.add(data6);
        datas.add(data7);
        datas.add(data8);

        lineGraph.setGraphData(datas);
    }
}
