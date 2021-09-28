package com.example.mickaelly.ada_ava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.adapter.ForumAdapter;
import com.example.mickaelly.ada_ava.service.DBCommandsForum;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView mLvTopics;
    private DBCommandsForum commandsForum;
    private Button mBtnInitForum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        commandsForum = new DBCommandsForum(this);
        setSupportActionBar(toolbar);
        initUI();
        configureToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureEvent();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mLvTopics = (ListView) findViewById(R.id.lv_topics);
        mBtnInitForum = (Button) findViewById(R.id.btn_init_forum);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureEvent() {
        List<String> topics = new ArrayList<>();
        topics = commandsForum.selectTopics();

        mBtnInitForum.setOnClickListener(newDiscussion());

        if(topics.isEmpty()){
            mBtnInitForum.setVisibility(View.VISIBLE);
            mLvTopics.setVisibility(View.GONE);
        }else{
            mBtnInitForum.setVisibility(View.GONE);
            mLvTopics.setVisibility(View.VISIBLE);
        }

        ForumAdapter adapter = new ForumAdapter(topics, this);

        mLvTopics.setAdapter(adapter);

        mLvTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ForumActivity.this, DiscussionActivity.class);
                intent.putExtra("theme", (String) parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener newDiscussion() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForumActivity.this, NewDiscussionActivity.class);
                startActivity(i);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(ForumActivity.this, NewDiscussionActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
