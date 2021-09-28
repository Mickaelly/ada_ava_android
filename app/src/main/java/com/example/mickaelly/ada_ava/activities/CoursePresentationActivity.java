package com.example.mickaelly.ada_ava.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.VideoView;

import com.example.mickaelly.ada_ava.AdaApplication;
import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.adapter.ListCoursesAdapter;
import com.example.mickaelly.ada_ava.model.Classroom;
import com.example.mickaelly.ada_ava.model.Course;
import com.example.mickaelly.ada_ava.service.DBCommandsClassRoom;
import com.example.mickaelly.ada_ava.service.DBCommandsCourse;
import com.example.mickaelly.ada_ava.util.SnackBarManager;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CoursePresentationActivity extends AppCompatActivity {

    private VideoView mVvCoursePresentation;
    private ListView mLvCoursePresentation;
    private Toolbar toolbar;
    private AlertDialog alerta;
    private DBCommandsCourse commandsCourse;
    private DBCommandsClassRoom commandsClassRoom;
    private Course course;
    private Classroom classroom;
    private SnackBarManager snackBarManager;
    private LinearLayout mRoot;
    private View loading;
    private List<String> classroomsNames;
    private List<Classroom> classrooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_presentation);
        commandsCourse = new DBCommandsCourse(this);
        commandsClassRoom = new DBCommandsClassRoom(this);
        snackBarManager = new SnackBarManager(this);
        initUI();
        configureToolbar();
        getExtras();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureEvent();
    }

    private void getExtras() {
        course = (Course) getIntent().getExtras().getSerializable("course");
    }

    private void initUI() {
        mLvCoursePresentation = (ListView) findViewById(R.id.lv_course_presentation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRoot = (LinearLayout) findViewById(R.id.course_presentation_root);
        loading = (View) findViewById(R.id.content_loading);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureEvent() {
        classroomsNames = new ArrayList<>();
        classrooms = new ArrayList<>();
        classrooms = commandsClassRoom.selectAll(course.getId());

        if(!classrooms.isEmpty()){
            for (Classroom c : classrooms){
                classroomsNames.add(c.getName());
            }
        }

        ListCoursesAdapter adapter = new ListCoursesAdapter(null, CoursePresentationActivity.this, classroomsNames);

        mLvCoursePresentation.setAdapter(adapter);

        mLvCoursePresentation.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(AdaApplication.getInstance().getUserSession().getTypeUser().equalsIgnoreCase("Tutor")){
                    for(Classroom c : classrooms){
                        if(c.getName().equals(parent.getAdapter().getItem(position))){
                            classroom = c;
                        }
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(CoursePresentationActivity.this);
                    builder.setTitle("Escolha de tutor");
                    builder.setMessage("O que deseja fazer?");
                    builder.setPositiveButton("Assistir", watch(CoursePresentationActivity.this,classroom.getUrl()));
                    builder.setNegativeButton("Excluir", deleteClassroom(classroom));

                    alerta = builder.create();
                    alerta.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(AdaApplication.getInstance().getUserSession().getTypeUser().equalsIgnoreCase("Tutor")){
            getMenuInflater().inflate(R.menu.tutor_menu, menu);
        }else{
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(CoursePresentationActivity.this, ExamActivity.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent it = new Intent(CoursePresentationActivity.this, ForumActivity.class);
                startActivity(it);
                return true;
            case R.id.item3:
                loading.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Atenção!");
                builder.setMessage("Tem certeza que deseja excluir o curso?");
                builder.setPositiveButton("Sim", confirmDelete());
                builder.setNegativeButton("Não", cancelDelete());

                alerta = builder.create();
                alerta.show();
                return true;
            case R.id.item4:
                Intent itClassroom = new Intent(CoursePresentationActivity.this, NewClassroomActivity.class);
                itClassroom.putExtra("course", (Serializable) course);
                startActivity(itClassroom);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public DialogInterface.OnClickListener confirmDelete(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                commandsCourse.delete(course);
                finish();
                loading.setVisibility(View.GONE);
            }
        };
    }

    public DialogInterface.OnClickListener cancelDelete(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                loading.setVisibility(View.GONE);
                alerta.dismiss();
            }
        };
    }

    public DialogInterface.OnClickListener watch(final Context context, final String id) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //emulador
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")));

                //dispositvo
                /*Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM"));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }*/
            }
        };
    }

    public DialogInterface.OnClickListener deleteClassroom(final Classroom classroom){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                long result = commandsClassRoom.delete(classroom);
                if(result > 0){
                    snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_delete_user), Snackbar.LENGTH_SHORT, new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            loading.setVisibility(View.GONE);
                        }
                    });
                }
                configureEvent();
            }
        };
    }
}
