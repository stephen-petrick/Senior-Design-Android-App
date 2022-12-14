package com.example.seniordesignandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton switchToCloud = (ImageButton) findViewById(R.id.cloud_imageButton);
        ImageButton switchToBT = (ImageButton) findViewById(R.id.data_imageButton);
        ImageButton switchToGraphs = (ImageButton) findViewById(R.id.graph_imageButton);

        switchToCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToCloudActivity = new Intent(MainActivity.this, Cloud.class);
                startActivity(switchToCloudActivity);
            }
        });

        switchToBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToBTActivity = new Intent(MainActivity.this, Bluetooth.class);
                startActivity(switchToBTActivity);
            }
        });

        switchToGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToGraphActivity = new Intent(MainActivity.this, Graphs.class);
                startActivity(switchToGraphActivity);
            }
        });

    }
}