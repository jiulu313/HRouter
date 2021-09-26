package com.helloworld.module.user;

import android.os.Bundle;
import android.widget.Button;

import com.helloworld.hrouter.annotation.Route;
import com.helloworld.hrouter.core.HRouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Route("/app/user")
public class UserActivity extends AppCompatActivity {
    Button btnUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user);
        btnUser = findViewById(R.id.btnUser);
        btnUser.setOnClickListener(view -> {
            HRouter.getInstance().with("/app/login").go(UserActivity.this);
        });
    }
}
