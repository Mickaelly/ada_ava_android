package com.example.mickaelly.ada_ava.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.mickaelly.ada_ava.AdaApplication;
import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.adapter.DuscussionAdapter;
import com.example.mickaelly.ada_ava.model.MessageForum;
import com.example.mickaelly.ada_ava.service.DBCommandsForum;
import com.example.mickaelly.ada_ava.util.SnackBarManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscussionActivity extends AppCompatActivity {

    private ListView mLvDiscussionMessage;
    private EditText mEdtMessage;
    private Button mBtnSendMessage;
    private Toolbar toolbar;
    private DBCommandsForum commandsForum;
    private SnackBarManager snackBarManager;
    private LinearLayout mRoot;
    private View loading;
    private String theme;
    private List<MessageForum> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        commandsForum = new DBCommandsForum(this);
        snackBarManager = new SnackBarManager(this);
        initUI();
        loading.setVisibility(View.VISIBLE);
        configureToolbar();
        getExtras();
        configureEvent();
    }

    private void getExtras() {
        theme = getIntent().getStringExtra("theme");
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mLvDiscussionMessage = (ListView) findViewById(R.id.lv_discussion_message);
        mEdtMessage = (EditText) findViewById(R.id.edt_message);
        mBtnSendMessage = (Button) findViewById(R.id.btn_send_message);
        mRoot = (LinearLayout) findViewById(R.id.discussion_root);
        loading = (View) findViewById(R.id.content_loading);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureEvent() {
        messages = new ArrayList<>();
        messages = commandsForum.selectAll(theme);

        DuscussionAdapter adapter = new DuscussionAdapter(messages, this);
        mLvDiscussionMessage.setAdapter(adapter);
        mBtnSendMessage.setOnClickListener(sendMessage());
        loading.setVisibility(View.GONE);
    }

    public View.OnClickListener sendMessage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                if(!mEdtMessage.getText().toString().equals("")){
                    MessageForum messageForum = new MessageForum();
                    messageForum.setNameUser(AdaApplication.getInstance().getUserSession().getName());
                    messageForum.setMessage(mEdtMessage.getText().toString());
                    messageForum.setTheme(theme);
                    Date date = new Date();
                    messageForum.setDate(date.toString());

                    long result = commandsForum.insert(messageForum);
                    if (result < 0) {
                        snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_new_message), Snackbar.LENGTH_SHORT, errorRegister());
                    }else{
                        mEdtMessage.setText("");
                        configureEvent();
                    }
                }
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
