package com.example.mickaelly.ada_ava.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mickaelly.ada_ava.R;

import java.util.List;

public class ListCoursesAdapter extends BaseAdapter {

    private final List<String> courses;
    private final List<String> classrooms;
    private final Activity act;

    public ListCoursesAdapter(List<String> courses, FragmentActivity act, List<String> classrooms) {
        this.courses = courses;
        this.act = act;
        this.classrooms = classrooms;
    }

    @Override
    public int getCount() {
        if (classrooms == null){
            return courses.size();
        }
        return classrooms.size();
    }

    @Override
    public Object getItem(int position) {
        if (classrooms == null){
            return courses.get(position);
        }
        return classrooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.courses_item, parent, false);
        TextView nameCourseOrClassroom = (TextView) view.findViewById(R.id.txv_name_course);
        ImageView iconArrow = (ImageView) view.findViewById(R.id.imv_arrow);
        String courseOrClassroom;

        if(classrooms == null) {
            courseOrClassroom = courses.get(position);
            iconArrow.setImageResource(R.drawable.ic_arrow_course);
        }else{
            courseOrClassroom = classrooms.get(position);
            iconArrow.setImageResource(R.drawable.ic_arrow);
        }
        nameCourseOrClassroom.setText(courseOrClassroom);

        return view;
    }
}
