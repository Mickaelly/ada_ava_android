package com.example.mickaelly.ada_ava.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.activities.CoursePresentationActivity;
import com.example.mickaelly.ada_ava.adapter.ListCoursesAdapter;
import com.example.mickaelly.ada_ava.model.Course;
import com.example.mickaelly.ada_ava.service.DBCommandsCourse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IntermediaryFragment extends Fragment {

    private ListView mLvCoursesIntermediary;
    private List<String> coursesNames;
    List<Course> courses;
    private DBCommandsCourse commandsCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intermediary, container, false);
        commandsCourse = new DBCommandsCourse(getContext());

        mLvCoursesIntermediary = view.findViewById(R.id.lv_courses_intermediary);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        coursesNames = new ArrayList<>();
        courses = new ArrayList<>();
        courses = commandsCourse.selectAll();

        if (!courses.isEmpty()){
            for (Course c : courses){
                if(c.getLevel().equals("Intermediário")){
                    coursesNames.add(c.getName());
                }
            }
        }

        ListCoursesAdapter adapter = new ListCoursesAdapter(coursesNames, getActivity(), null);

        mLvCoursesIntermediary.setAdapter(adapter);
        mLvCoursesIntermediary.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CoursePresentationActivity.class);
                for(Course c : courses){
                    if(c.getName().equals(parent.getAdapter().getItem(position))){
                        intent.putExtra("course", (Serializable) c);
                    }
                }
                startActivity(intent);
            }
        });
    }
}
