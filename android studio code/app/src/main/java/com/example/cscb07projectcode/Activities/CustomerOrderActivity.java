package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07projectcode.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CustomerOrderActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);
        Intent intent = getIntent();
        String value= getIntent().getStringExtra("getData");
        TextView textView = (TextView) findViewById(R.id.textView13);
        textView.setText(value);

    }
}