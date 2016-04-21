package com.example.vojche.wifipokusajdrugi;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Button button;
    private Button button_right;
    private Button button_left;

    // public String Message;
    byte[] send_data = new byte[10];

    private UDP_Client Client;
    protected boolean flag1 = false;

    // private TextView tv2;

    // TextView text1 = (TextView) findViewById(R.id.text1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();

        Client = new UDP_Client();

        TextView tv = (TextView) findViewById(R.id.text1);
        tv.setText(Boolean.toString(flag1));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            // tv.setText("Success! we have an accelerometer!");

            accelerometer = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            // fail! we dont have an accelerometer!
            // tv.setText("Fail! we dont have an accelerometer!");
        }

    }
// OVO ODKOMENTUJ AKO NECE DA SLJAKA
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub

        TextView tv2 = (TextView) findViewById(R.id.textView2);
        // tv2.setText("0");
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // String.format("%.2f", x);

        if (flag1 = true) {

            // tv2.setText(Float.toString(x));
            tv2.setText(String.format("%.3f", x) + " , "
                    + String.format("%.3f", y) + " , "
                    + String.format("%.3f", z));

            Client.Message = String.format("%.3f", x) + " "
                    + String.format("%.3f", y) + " " + String.format("%.3f", z);

            // Client.Message = Float.toString(x) + " , " + Float.toString(y)
            // + " , " + Float.toString(z);
            // Send message
            Client.NachrichtSenden();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void addListenerOnButton() {

        // Select a specific button to bundle it with the action you want
        button = (Button) findViewById(R.id.udpbutton);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                TextView tv1 = (TextView) findViewById(R.id.text1);
                tv1.setText(Boolean.toString(flag1));
                EditText port_text = (EditText) findViewById(R.id.port);
                EditText ip_text = (EditText) findViewById(R.id.ipadresa);
                // //Set message

                if (port_text.getText().toString().equals(null)
                        || port_text.getText().toString().equals("")) {
                    // napisi gresku
                } else {
                    Client.port = Integer.parseInt(port_text.getText()
                            .toString());
                }

                if (ip_text.getText().toString().equals(null)
                        || ip_text.getText().toString().equals("")) {
                    // napisi gresku
                } else {
                    Client.IPs = ip_text.getText().toString();
                }

                if (flag1) {
                    onPause();
                } else if (!flag1) {
                    onResume();
                }

                flag1 = !flag1;

            }

        });

        button_right = (Button) findViewById(R.id.rightclick);

        button_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        v.setPressed(true);
                        Client.Message = "hold_right";
                        Client.NachrichtSenden();
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        Client.Message = "right";
                        Client.NachrichtSenden();
                        return true; // if you want to handle the touch event
                }
                return false;
            }


        });

//		button_right.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//
//				Client.Message = "right";
//				Client.NachrichtSenden();
//
//			}
//
//		});
        button_left = (Button) findViewById(R.id.leftclick);

        button_left.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Client.Message = "left";
                Client.NachrichtSenden();

            }

        });



    }

}


//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}
