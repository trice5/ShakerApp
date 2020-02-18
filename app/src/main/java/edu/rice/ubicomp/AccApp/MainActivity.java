package edu.rice.ubicomp.AccApp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import edu.rice.ubicomp.AccApp.HelloApp.R;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView mXAccelerationTextView;
    private TextView text1, text2, text3, text4, textView_x, textView_shake, textView_thsd, textView_count;

    private Button button1, button2;
    boolean status = false;
    double shake_threshold;
    int shake_number = 0;
    int c_1=0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the sensors
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //@Override
    protected void onResume()
    {

        super.onResume();
        //mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Threshold value "getting it"
    public void EnterThreshold()
    {
        shake_threshold = Double.parseDouble(textView_thsd.getText().toString());

    }


    //when button is clicked
    public void changeText(View view)
    {

        mXAccelerationTextView = findViewById(R.id.textView_count);
        shake_threshold = Double.valueOf(String.valueOf(mXAccelerationTextView.getText()));

        //textbox.setText("Button Pressed! Acc start!");
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

//    public void changeText//stop(View view) {
//
//        Log.d("message", "Button Pressed");
//        TextView textbox = findViewById(R.id.text1);
//        textbox.setText("Button Pressed! Acc start!");
//        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//
//    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {

        Sensor sensor = event.sensor;
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            Log.d("x:", Float.toString(x));

            mXAccelerationTextView = findViewById(R.id.textView_x);

            String xyz = String.format("output of xyz: %.2f , %.2f, %.2f", x , y ,z);
            mXAccelerationTextView.setText(xyz);


            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {

                float []acc_values = event.values;

                //mXAccelerationTextView = findViewById(R.id.text2);
                mXAccelerationTextView.setText(Arrays.toString(acc_values));
                mXAccelerationTextView = findViewById(R.id.textView_shake);
                mXAccelerationTextView.setText("Shake");

            }
            else {
                mXAccelerationTextView = findViewById(R.id.textView_shake);
                mXAccelerationTextView.setText("No Shake");
            }


            button_1 =(Button) findViewById(R.id.button1);
            button_1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mXAccelerationTextView = findViewById(R.id.textView_count);
                    mXAccelerationTextView.setText(0);
                }
            });

            //Finding the number of shakes
            long time = System.currentTimeMillis();
            float total = x * x + y * y + z * z;
            double magnitude = Math.sqrt(total);

            if ((magnitude - shake_threshold)>0)
            {
                status = true;
                c_1++;
                shake_number = c_1/4;

                mXAccelerationTextView = findViewById(R.id.textView_count);
                mXAccelerationTextView.setText(String.valueOf(shake_number));
            }

            else
            {
                //Fix ************
                status = false;
                shake_number = 0;
                mXAccelerationTextView = findViewById(R.id.text);
                mXAccelerationTextView.setText(String.valueOf(shake_number));
            }

            Button button_2 = findViewById(R.id.button2);
            button_2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    //I need to fix this******************************************
                    //mSensorManager.unregisterListener(MainActivity.this);
                }
            });
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}




}
