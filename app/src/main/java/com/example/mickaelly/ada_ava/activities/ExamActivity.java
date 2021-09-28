package com.example.mickaelly.ada_ava.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mickaelly.ada_ava.R;

public class ExamActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ScrollView mScvExam;
    private TextView mTxvQuestion1;
    private EditText mEdtAnswer1;
    private TextView mTxvQuestion2;
    private EditText mEdtAnswer2;
    private TextView mTxvQuestion3;
    private EditText mEdtAnswer3;
    private TextView mTxvQuestion4;
    private EditText mEdtAnswer4;
    private TextView mTxvQuestion5;
    private EditText mEdtAnswer5;
    private Button mBtnSendAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initUI();
        configureToolbar();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mScvExam = (ScrollView) findViewById(R.id.scv_exam);
        mTxvQuestion1 = (TextView) findViewById(R.id.txv_question1);
        mEdtAnswer1 = (EditText) findViewById(R.id.edt_answer1);
        mTxvQuestion2 = (TextView) findViewById(R.id.txv_question2);
        mEdtAnswer2 = (EditText) findViewById(R.id.edt_answer2);
        mTxvQuestion3 = (TextView) findViewById(R.id.txv_question3);
        mEdtAnswer3 = (EditText) findViewById(R.id.edt_answer3);
        mTxvQuestion4 = (TextView) findViewById(R.id.txv_question4);
        mEdtAnswer4 = (EditText) findViewById(R.id.edt_answer4);
        mTxvQuestion5 = (TextView) findViewById(R.id.txv_question5);
        mEdtAnswer5 = (EditText) findViewById(R.id.edt_answer5);
        mBtnSendAnswer = (Button) findViewById(R.id.btn_send_answer);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
