package com.example.mickaelly.ada_ava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mickaelly.ada_ava.AdaApplication;
import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.adapter.TabAdapter;
import com.example.mickaelly.ada_ava.fragments.AdvancedFragment;
import com.example.mickaelly.ada_ava.fragments.BeginnerFragment;
import com.example.mickaelly.ada_ava.fragments.IntermediaryFragment;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Button mBtnNewCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        configureToolbar();
        configureTabs();
        mBtnNewCourse.setOnClickListener(newCourse());

        if(AdaApplication.getInstance().getUserSession().getTypeUser().equalsIgnoreCase("Tutor")){
            mBtnNewCourse.setVisibility(View.VISIBLE);
        }else{
            mBtnNewCourse.setVisibility(View.GONE);
        }
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.abas_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.abas);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnNewCourse = (Button) findViewById(R.id.btn_newCourse);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_singout);
    }

    public View.OnClickListener newCourse(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NewCourseActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(HomeActivity.this, RegisterUserActivity.class);
                intent.putExtra("NEW_PASSWORD", false);
                startActivity(intent);
                return true;
            case android.R.id.home:
                AdaApplication.getInstance().setUserSession(null);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void configureTabs() {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.insert(new BeginnerFragment(), "Iniciante");
        adapter.insert(new IntermediaryFragment(), "Intermediário");
        adapter.insert(new AdvancedFragment(), "Avançado");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
