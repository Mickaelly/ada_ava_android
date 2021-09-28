package com.example.mickaelly.ada_ava.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mickaelly.ada_ava.AdaApplication;
import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.model.MessageForum;
import com.example.mickaelly.ada_ava.service.DBCommandsForum;
import com.example.mickaelly.ada_ava.util.SnackBarManager;

import java.util.Date;

public class NewDiscussionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText mEdtTitleDiscussion;
    private EditText mEdtMessageDiscussion;
    private Button mBtnNewDiscussion;
    private DBCommandsForum commandsForum;
    private SnackBarManager snackBarManager;
    private LinearLayout mRoot;
    private View loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);
        commandsForum = new DBCommandsForum(this);
        snackBarManager = new SnackBarManager(this);
        initUI();
        configureToolbar();
        configureEvent();
    }

    public void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mEdtTitleDiscussion = (EditText) findViewById(R.id.edt_title_discussion);
        mEdtMessageDiscussion = (EditText) findViewById(R.id.edt_message_discussion);
        mBtnNewDiscussion = (Button) findViewById(R.id.btn_new_discussion);
        mRoot = (LinearLayout) findViewById(R.id.register_discussion_root);
        loading = (View) findViewById(R.id.content_loading);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void configureEvent(){
        mBtnNewDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                hideKeyboard();
                MessageForum lastMessage = new MessageForum();
                lastMessage.setNameUser(AdaApplication.getInstance().getUserSession().getName());
                Date date = new Date();
                lastMessage.setDate(date.toString());
                lastMessage.setMessage(mEdtMessageDiscussion.getText().toString());
                lastMessage.setTheme(mEdtTitleDiscussion.getText().toString());
                long result = commandsForum.insert(lastMessage);
                if(result < 0){
                    snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.error_new_topic), Snackbar.LENGTH_SHORT, errorRegister());
                }else{
                    snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_register_topic), Snackbar.LENGTH_SHORT, sucessRegister());
                }
            }
        });
    }

    private void hideKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEdtMessageDiscussion.getWindowToken(), 0);
    }

    public Snackbar.Callback sucessRegister(){
        return new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                loading.setVisibility(View.INVISIBLE);
                finish();
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
