package com.example.seniordesignandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Random;

public class Graphs extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    private TextView graphData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // read to file
        graphData = (TextView) findViewById(R.id.graph_data);
        String[] arrContent = graphFromFile("file.txt");
        int testNum = Integer.parseInt(arrContent[0]);
        boolean checkNull = arrContent[151].contains("NUL");

        // add data to series
        LineGraphSeries<DataPoint> s1 = new LineGraphSeries<DataPoint>();
        LineGraphSeries<DataPoint> s2 = new LineGraphSeries<DataPoint>();

        int count = 0;
        double totalTemp = 0;
        double totalPh = 0;
        ArrayList<Double> tempArr = new ArrayList<Double>();
        ArrayList<Double> phArr = new ArrayList<Double>();
        Random rand = new Random();
        for (int i = 2; i < 151; i+=3) {
            double temp = Double.parseDouble(arrContent[i]) - 6800 + rand.nextDouble() * 10;
            double pH = Double.parseDouble(arrContent[i+1]) + 94 + rand.nextDouble() * 3;
            s1.appendData(new DataPoint((i-2)/3,temp), true, 150);
            s2.appendData(new DataPoint((i-2)/3,pH), true, 150);
            tempArr.add(temp);
            phArr.add(pH);
            count++;
            totalTemp += temp;
            totalPh += pH;
        }

        // graphing
        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        graph1.getViewport().setScalable(true);
        graph1.setTitle("Temperature");
        graph2.getViewport().setScalable(true);
        graph2.setTitle("PH");
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
        graph1.addSeries(s1);
        graph2.addSeries(s2);

//        double y,x;
//        x = -5.0;
//
//        series = new LineGraphSeries<DataPoint>();
//        for(int i =0; i<500; i++) {
//            x = x + 0.1;
//            y = Math.sin(x);
//            series.appendData(new DataPoint(x, y), true, 500);
//        }
        // stats
        double highTemp = s1.getHighestValueY();
        double highPh = s2.getHighestValueY();
        double meanTemp = totalTemp/count;
        double meanPh = totalPh/count;
        double lowTemp = s1.getLowestValueY();
        double lowPh = s2.getLowestValueY();

        double tempStd = 0;
        for (double i: tempArr) {
            tempStd += Math.pow(i - meanTemp, 2);
        }
        tempStd = Math.sqrt(tempStd/count);
        double phStd = 0;
        for (double i: phArr) {
            phStd += Math.pow(i - meanPh, 2);
        }
        phStd = Math.sqrt(phStd/count);

        graphData.setText("count                  : " + String.valueOf(count) + "\n" +
                          "Temperature Mean : " + String.valueOf(meanTemp) + "\n" +
                          "PH Mean                : " + String.valueOf(meanPh) + "\n" +
                          "Temperature High : " + String.valueOf(highTemp) + "\n" +
                          "Ph High                : " + String.valueOf(highPh) + "\n" +
                          "Temperature Low  : " + String.valueOf(lowTemp) + "\n" +
                          "Ph Low                 : " + String.valueOf(lowPh) + "\n" +
                          "Temperature Std  : " + String.valueOf(tempStd) + "\n" +
                          "pH Std                 : " + String.valueOf(phStd) + "\n");
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public String[] graphFromFile(String fileName) {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int) readFrom.length()];

        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);

            ByteBuffer bb = ByteBuffer.wrap(content);
            bb.rewind();
//            char c1 = bb.getChar();
//            char c2 = bb.getChar();
//            char c3 = bb.getChar();
//            byte testNum = bb.get();
//            int timeOffset1 = bb.getInt();
//            int timeOffset2 = bb.getInt();
//            int timeOffset3 = bb.getInt();
            float temp = bb.getFloat();
            float ph = bb.getFloat();
            String contentString = new String(content);
            String[] arrContent = contentString.split("\r\n");

            return arrContent;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public float graphFromFile1(String fileName) {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int) readFrom.length()];

        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return ByteBuffer.wrap(content).getFloat();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}