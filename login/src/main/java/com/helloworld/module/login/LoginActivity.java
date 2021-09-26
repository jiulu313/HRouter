package com.helloworld.module.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.helloworld.hrouter.annotation.Route;
import com.helloworld.hrouter.core.HRouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Route("/app/login")
public class LoginActivity extends AppCompatActivity {
    Button bthLogin;
    Button btnJump2User;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        bthLogin = findViewById(R.id.btnLogin);
        btnJump2User = findViewById(R.id.btnJump2User);

        String name = getIntent().getStringExtra("name");
        String pass = getIntent().getStringExtra("password");
        Log.d("zh88","name=" + name + "  pass=" + pass);


        bthLogin.setOnClickListener(view -> {
            HRouter.getInstance().with("/app/main").go(LoginActivity.this);
        });
        btnJump2User.setOnClickListener(view -> {
            HRouter.getInstance().with("/app/user").go(LoginActivity.this);
        });


    }
}
