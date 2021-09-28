package com.example.mickaelly.ada_ava.adapter;

import android.app.Activity;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.model.MessageForum;

import java.util.List;

public class DuscussionAdapter extends BaseAdapter {

    private final List<MessageForum> messages;
    private final Activity act;

    public DuscussionAdapter(List<MessageForum> messages, Activity act) {
        this.messages = messages;
        this.act = act;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.message_item, parent, false);
        TextView authorName = (TextView) view.findViewById(R.id.txv_author_topic);
        TextView messageDate = (TextView) view.findViewById(R.id.txv_date);
        ImageView imageProfile = (ImageView) view.findViewById(R.id.imv_photo_author);
        TextView message = (TextView) view.findViewById(R.id.txv_messagem_sent);
        MessageForum item = messages.get(position);

        authorName.setText(item.getNameUser());
        messageDate.setText(item.getDate());
        message.setText(item.getMessage());

        return view;
    }
}
