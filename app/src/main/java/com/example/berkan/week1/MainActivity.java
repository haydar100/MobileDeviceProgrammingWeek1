package com.example.berkan.week1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity implements SensorEventListener {
    Button buttonSubmit, buttonShare;
    TextView textView;
    int iterator = 0;
    private SensorManager mSensorManager;
    private Sensor mTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        buttonSubmit = (Button) findViewById(R.id.submitButton);
        buttonShare = (Button) findViewById(R.id.share);
        textView = (TextView) findViewById(R.id.textViewIterator);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "I Pressed the submit button for " + iterator + " Times !");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterator++;
                textView.setText(Integer.toString(iterator));
                System.out.println(iterator);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("textViewSaved", iterator);
        Log.d("onSaveInstanceState", "Saving instanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        iterator = savedInstanceState.getInt("textViewSaved");
        Log.d("onRestoreInstanceState", "Restored instance State");
        textView.setText(Integer.toString(iterator));
        System.out.println("onRestoreState called");

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
    double temperature = event.values[0];
    System.out.println(temperature);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
