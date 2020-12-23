package edu.mward.finalgameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayScreen extends AppCompatActivity implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 6.0;      // lower mag limit
    private final double WEST_MOVE_LEFT = 9.0;
    private final double WEST_MOVE_RIGHT = 6.0;
    private final double SOUTH_MOVE_BACKWARD = -9.0;
    private final double SOUTH_MOVE_FORWARD = -6.0;
    private final double EAST_MOVE_RIGHT = -9.0;
    private final double EAST_MOVE_LEFT = -6.0;
    boolean highLimit = false;      // detect high limit
    int counter = 0;                // step counter

    TextView tvx, tvy, tvz ;
    Button btnG, btnR, btnB, btnY;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //int[] arrayB = extras.getIntArray("numbers");
        // 1 2 1 1  (N N W N)

        tvx = findViewById(R.id.tvX);
        tvy = findViewById(R.id.tvY);
        tvz = findViewById(R.id.tvZ);

        btnG = findViewById(R.id.btnGreen);
        btnR = findViewById(R.id.btnRed);
        btnB = findViewById(R.id.btnBlue);
        btnY = findViewById(R.id.btnYellow);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        tvx.setText(String.valueOf(x));
        tvy.setText(String.valueOf(y));
        tvz.setText(String.valueOf(z));


        // Can we get a north movement
        // you need to do your own mag calculating
        if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {
            highLimit = true;
        }
        if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true)) {
            // we have a tilt to the north
            counter++;
            btnG.setText(String.valueOf(counter));
            highLimit = false;
        }

        if((y > WEST_MOVE_LEFT) && (highLimit == false)) {
            highLimit = true;
        }
        if ((y < WEST_MOVE_RIGHT) && (highLimit == true)) {
            counter++;
            btnB.setText(String.valueOf(counter));
            highLimit = false;
        }

        if ((x > SOUTH_MOVE_BACKWARD) && (highLimit == false)) {
            highLimit = true;
        }
        if ((x < SOUTH_MOVE_FORWARD) && (highLimit == true)) {
            counter++;
            btnR.setText(String.valueOf(counter));
            highLimit = false;
        }

        if ((y > EAST_MOVE_RIGHT) && (highLimit == false)) {
            highLimit = true;
        }
        if ((y < EAST_MOVE_LEFT) && (highLimit == true)) {
            counter++;
            btnY.setText(String.valueOf(counter));
            highLimit = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    //public void onFinish() {

        //Intent i = new Intent(PlayScreen.this, GameOverScreen.class);
       // startActivity(i);
    //}

    public void doFinish(View view) {
        Intent GameOverScreenActivity = new Intent(view.getContext(), GameOverScreen.class);

        //Starts new activity
        startActivity(GameOverScreenActivity);
    }
}
