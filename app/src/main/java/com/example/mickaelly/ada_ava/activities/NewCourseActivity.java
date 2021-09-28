package com.example.mickaelly.ada_ava.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.VideoView;

import com.example.mickaelly.ada_ava.AdaApplication;
import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.model.Course;
import com.example.mickaelly.ada_ava.service.DBCommandsCourse;
import com.example.mickaelly.ada_ava.util.SnackBarManager;

import java.util.ArrayList;
import java.util.List;

public class NewCourseActivity extends AppCompatActivity {

    private EditText mEdtName;
    private Spinner mSpnType;
    private Button mBtnRegister;
    private Toolbar toolbar;
    private AlertDialog alerta;
    private SnackBarManager snackBarManager;
    private LinearLayout mRoot;
    private View loading;
    private DBCommandsCourse commandsCourse;
    private Course course;
    private List<String> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        initUI();
        configureToolbar();
        configureListener();
        snackBarManager = new SnackBarManager(this);
        commandsCourse = new DBCommandsCourse(this);
        course = new Course();
        configSpinnerType();
    }

    private void configSpinnerType() {
        types = new ArrayList<>();
        types.add("Básico");
        types.add("Intermediário");
        types.add("Avançado");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        mSpnType.setAdapter(adapter);
    }

    private void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mEdtName = (EditText) findViewById(R.id.edt_name_course);
        mSpnType = (Spinner) findViewById(R.id.spn_type_course);
        mBtnRegister = (Button) findViewById(R.id.btn_new_course);
        mRoot = (LinearLayout) findViewById(R.id.register_course_root);
        loading = (View) findViewById(R.id.loading);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureListener(){
        mBtnRegister.setOnClickListener(registerCourse());
    }

    public View.OnClickListener registerCourse(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                hideKeyboard();
                if(verifyFields()){

                    long result = commandsCourse.insert(course);
                    if(result > 0){
                        snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_new_course), Snackbar.LENGTH_SHORT, sucessRegister());
                    }else{
                        snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_new_course), Snackbar.LENGTH_SHORT, errorRegister());
                    }
                }
            }
        };
    }

    private void hideKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEdtName.getWindowToken(), 0);
    }

    public boolean verifyFields(){
        if(mEdtName.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_name), Snackbar.LENGTH_SHORT, errorRegister());
            return false;
        }else{
            course.setName(mEdtName.getText().toString());
            course.setLevel(mSpnType.getSelectedItem().toString());
            return true;
        }
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

    private void ConfirmationCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage("Tem certeza que deseja cancelar a inclusão do curso?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!mEdtName.getText().toString().equals("")){
                    ConfirmationCancel();
                }else{
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
