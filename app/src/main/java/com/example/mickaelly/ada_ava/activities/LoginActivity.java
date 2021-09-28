package com.example.mickaelly.ada_ava.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mickaelly.ada_ava.AdaApplication;
import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.model.User;
import com.example.mickaelly.ada_ava.service.DBCommandsUser;
import com.example.mickaelly.ada_ava.util.SnackBarManager;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdtUserLogin;
    private EditText mEdtPasswordLogin;
    private Button mBtnLogin;
    private Button mBtnNewUser;
    private Button mBtnNewPassword;
    private DBCommandsUser commandsUser;
    private SnackBarManager snackBarManager;
    private LinearLayout mRoot;
    private View loading;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligin);
        configureToolbar();
        initUI();
        configureEvent();
        commandsUser = new DBCommandsUser(this);
        snackBarManager = new SnackBarManager(this);
    }

    private void initUI() {
        mEdtUserLogin = (EditText) findViewById(R.id.edt_user_login);
        mEdtPasswordLogin = (EditText) findViewById(R.id.edt_password_login);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnNewUser = (Button) findViewById(R.id.btn_new_user);
        mBtnNewPassword = (Button) findViewById(R.id.btn_new_password);
        mRoot = (LinearLayout) findViewById(R.id.login_root);
        loading = (View) findViewById(R.id.content_loading);
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureEvent() {
        mBtnLogin.setOnClickListener(login());
        mBtnNewUser.setOnClickListener(newUser());
        mBtnNewPassword.setOnClickListener(newPassword());
    }

    private View.OnClickListener login() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                hideKeyboard();
                if (validateFields()){
                    user = commandsUser.login(mEdtUserLogin.getText().toString(), mEdtPasswordLogin.getText().toString());
                    if(user == null){
                        snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_user_not_exist), Snackbar.LENGTH_LONG, errorLogin());

                    }else if(user.getId() == 0){
                        snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_password_invalid), Snackbar.LENGTH_LONG, errorLogin());
                    }else{
                        clearDates();
                        snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_login), Snackbar.LENGTH_LONG, sucessLogin());
                    }
                }
            }
        };
    }

    private void hideKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEdtUserLogin.getWindowToken(), 0);
    }

    public Snackbar.Callback sucessLogin(){
        return new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                AdaApplication.getInstance().setUserSession(user);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                loading.setVisibility(View.GONE);
            }
        };
    }

    public Snackbar.Callback errorLogin(){
        return new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                loading.setVisibility(View.GONE);
            }
        };
    }

    private boolean validateFields() {
        if (mEdtUserLogin.getText().toString().equals("") || mEdtPasswordLogin.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_login), Snackbar.LENGTH_LONG, errorLogin());
            return false;
        }
        return true;
    }

    private void clearDates(){
        mEdtUserLogin.setText("");
        mEdtPasswordLogin.setText("");
    }

    private View.OnClickListener newUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDates();
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                intent.putExtra("NEW_PASSWORD", false);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener newPassword() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDates();
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                intent.putExtra("NEW_PASSWORD", true);
                startActivity(intent);
            }
        };
    }
}
