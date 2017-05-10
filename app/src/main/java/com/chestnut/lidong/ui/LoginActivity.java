package com.chestnut.lidong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chestnut.lidong.R;
import com.chestnut.lidong.constant.AppConstant;
import com.chestnut.lidong.mvp.presenter.LoginActivityPresenter;
import com.chestnut.lidong.mvp.view.LoginActivityView;
import com.chestnut.lidong.utils.LogUtil;

import org.json.JSONObject;

import java.sql.SQLException;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    private LoginActivityPresenter presenter;

    private LinearLayout loginLayout;
    private EditText loginUsernameET;
    private EditText loginPasswordET;
    private Button loginBT;

    private LinearLayout registerLayout;
    private EditText registerUsernameET;
    private EditText registerPasswordET;
    private EditText registerPasswordET2;

    private AlertDialog progressDialog;
    private int dialogTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            presenter = new LoginActivityPresenter(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initView();
        creatDialog();
    }

    private void initView() {
        loginLayout = (LinearLayout) findViewById(R.id.activity_login_login_layout);
        loginUsernameET = (EditText) findViewById(R.id.activity_login_username_et);
        loginPasswordET = (EditText) findViewById(R.id.activity_login_password_et);
        loginBT = (Button) findViewById(R.id.activity_login_login_bt);

        registerLayout = (LinearLayout) findViewById(R.id.activity_login_register_layout);
        registerUsernameET = (EditText) findViewById(R.id.activity_login_register_username_et);
        registerPasswordET = (EditText) findViewById(R.id.activity_login_register_password_et);
        registerPasswordET2 = (EditText) findViewById(R.id.activity_login_register_password_et2);

        findViewById(R.id.activity_login_newuser_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.activity_login_register_back_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
            }
        });

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                presenter.login(loginUsernameET.getText().toString(), loginPasswordET.getText().toString());
            }
        });
        findViewById(R.id.activity_login_register_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                presenter.register(registerUsernameET.getText().toString(), registerPasswordET.getText().toString(), registerPasswordET2.getText().toString());

            }
        });
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login(int loginResult) {
        dismissProgressDialog();
        printLog("loginResult : " + loginResult);

        switch (loginResult) {
            case AppConstant.LOGIN_SUCCESS:
                showToast("登录成功！");

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                break;
            case AppConstant.LOGIN_WRONG_PASSWORD:

                loginPasswordET.setText("");

                showToast("密码错误，请重新输入！");
                break;
            case AppConstant.LOGIN_NO_USER:
                loginUsernameET.setText("");
                loginPasswordET.setText("");

                showToast("用户名错误！");
                break;

            default:
                showToast("未知错误！");
                break;
        }
    }

    @Override
    public void register(int registerResult) {
        dismissProgressDialog();
        printLog("registerResult : " + registerResult);

        switch (registerResult) {
            case AppConstant.REGISTER_SUCCESS:
                showToast("注册成功，正在登陆!");

                loginUsernameET.setText(registerUsernameET.getText());
                loginPasswordET.setText(registerPasswordET.getText());

                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);

                loginBT.callOnClick();

                break;
            case AppConstant.REGISTER_DIFFERENT_PASSWORD:
                showToast("两次输入的密码不一致，请重新输入！");

                registerPasswordET.setText("");
                registerPasswordET2.setText("");

                break;
            case AppConstant.REGISTER_SAME_USERNAME:
                showToast("用户名已存在!");

                registerUsernameET.setText("");
                registerPasswordET.setText("");
                registerPasswordET2.setText("");

                break;

            case AppConstant.REGISTER_ILLEGAL_PASSWORD:
                showToast("密码过于简单，请输入6位以上的密码!");
                registerPasswordET.setText("");
                registerPasswordET2.setText("");
                break;
            default:
                showToast("未知错误!错误代码：" + registerResult);
                break;
        }

    }

    private void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.progress_dialog_layout, null);
        builder.setView(dialogView);
        builder.setTitle("提示信息");
        builder.setCancelable(false);
        progressDialog = builder.create();
    }

    public void showProgressDialog() {
        //开启计时器
        dialogTime = 0;
        progressDialog.show();
        timerHandler.postDelayed(runnable, 0);
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
        timerHandler.removeCallbacks(runnable);

    }

    //计时器
    private Handler timerHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            timerHandler.postDelayed(this, 1000);

            if (dialogTime > 6) {
                dismissProgressDialog();
                showToast("请求超时，请检查网络状态！");
                //销毁计时器
                timerHandler.removeCallbacks(runnable);
            } else {
                dialogTime++;
            }
        }
    };
}
