package com.chestnut.lidong.ui;

import android.content.Intent;
import android.os.Bundle;

import com.chestnut.lidong.R;
import com.chestnut.lidong.mvp.dao.UserRunningDataLocalDao;
import com.chestnut.lidong.entity.UserRunningDataLocal;

import java.sql.SQLException;
import java.util.List;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
