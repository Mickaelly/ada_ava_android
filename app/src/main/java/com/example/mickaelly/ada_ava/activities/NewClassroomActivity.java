package com.example.mickaelly.ada_ava.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.model.Classroom;
import com.example.mickaelly.ada_ava.model.Course;
import com.example.mickaelly.ada_ava.service.DBCommandsClassRoom;
import com.example.mickaelly.ada_ava.util.SnackBarManager;

public class NewClassroomActivity extends AppCompatActivity {

    private EditText mEdtName;
    private EditText mEdtUrl;
    private Button mBtnRegister;
    private Course course;
    private DBCommandsClassRoom commandsClassRoom;
    private SnackBarManager snackBarManager;
    private LinearLayout mRoot;
    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_classroom);
        commandsClassRoom = new DBCommandsClassRoom(this);
        snackBarManager = new SnackBarManager(this);

        configureToolbar();
        initUI();
        configureListener();
        getExtras();
    }

    private void getExtras() {
        course = (Course) getIntent().getExtras().getSerializable("course");
    }

    private void configureListener() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                hideKeyboard();
                if(validationFields()){
                    Classroom classroom = new Classroom(mEdtName.getText().toString(), course.getId(), mEdtUrl.getText().toString());
                    long result = commandsClassRoom.insert(classroom);
                    if(result > 0){
                        snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_register_classroom), Snackbar.LENGTH_SHORT, sucessRegister());
                    }else{
                        snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_new_classroom), Snackbar.LENGTH_SHORT, errorRegister());
                    }
                }
            }
        });
    }

    private void hideKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEdtName.getWindowToken(), 0);
    }

    private void initUI() {
        mEdtName = (EditText) findViewById(R.id.edt_name_classroom);
        mEdtUrl = (EditText) findViewById(R.id.edt_name_url);
        mBtnRegister = (Button) findViewById(R.id.btn_register_classroom);
        mRoot = (LinearLayout) findViewById(R.id.register_classroom_root);
        loading = (View) findViewById(R.id.loading);
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean validationFields(){
        if(mEdtName.getText().toString().equals("") || mEdtUrl.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_new_classroom_field), Snackbar.LENGTH_SHORT, errorRegister());
            return false;
        }
        return true;
    }

    public Snackbar.Callback sucessRegister(){
        return new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                finish();
                loading.setVisibility(View.INVISIBLE);
            }
        };
    }

    public Snackbar.Callback errorRegister(){
        return new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                loading.setVisibility(View.GONE);
            }
        };
    }
}
