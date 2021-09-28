package com.example.mickaelly.ada_ava.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mickaelly.ada_ava.R;

import java.util.List;

public class ForumAdapter extends BaseAdapter {

    private final List<String> content;
    private final Activity act;

    public ForumAdapter(List<String> content, Activity act) {
        this.content = content;
        this.act = act;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int position) {
        return content.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.forum_item, parent, false);
        TextView titleTopic = (TextView) view.findViewById(R.id.txv_name_topic);
        TextView creationDate = (TextView) view.findViewById(R.id.txv_name_date);
        String dateTopic = "Criado em 25/03/2019";
        String valueTopic = content.get(position);
        titleTopic.setText(valueTopic);
        creationDate.setText(dateTopic);

        return view;
    }
}
