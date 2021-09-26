package com.helloworld.app;

import android.os.Bundle;

import com.helloworld.hrouter.annotation.Router;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Router("/app/second")
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
