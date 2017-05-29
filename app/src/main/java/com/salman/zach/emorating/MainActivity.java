package com.salman.zach.emorating;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView rateMe = (TextView) findViewById(R.id.rateMe);
        rateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RatingDialog(MainActivity.this).show();
            }
        });
    }
}
