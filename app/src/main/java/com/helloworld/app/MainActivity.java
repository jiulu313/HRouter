package com.helloworld.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.helloworld.hrouter.annotation.Route;
import com.helloworld.hrouter.core.HRouter;

@Route("/app/main")
public class MainActivity extends AppCompatActivity {
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            onLogin();
        });
    }

    private void onLogin() {
        HRouter.getInstance()
                .with("/app/login")
                .withString("name","tom")
                .withString("password","123456")
                .go();
    }
}