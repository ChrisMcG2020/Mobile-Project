package com.example.googlemaps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time extends  MainActivity {

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => "+c.getTime());

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
            String time=format.format(calendar.getTime());

            TextView textView=findViewById(R.id.time);
            textView.setText(time);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            // formattedDate have current date/time
            Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();


            // Now we display formattedDate value in TextView
            TextView txtView = new TextView(this);
            txtView.setText("Current Date and Time : "+formattedDate);
            txtView.setGravity(Gravity.BOTTOM);
            txtView.setTextSize(20);
            setContentView(txtView);
        }


}
