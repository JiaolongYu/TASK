package com.example.administrator.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JocelynYu on 12/9/15.
 */
public class ReplyAdapter extends ArrayAdapter<Reply> {
    private final Context context;
    private final ArrayList<Reply> replies;

    public ReplyAdapter(Context context, ArrayList<Reply> replies_) {
        super(context, R.layout.reply_item,replies_);
        this.context = context;
        this.replies = replies_;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.reply_item, parent, false);
        TextView ReplyView = (TextView) rowView.findViewById(R.id.reply_content);
        TextView TimeView = (TextView) rowView.findViewById(R.id.reply_time);
        ReplyView.setText(replies.get(position).Creator+" @"+replies.get(position).ReplyTo+" : "+replies.get(position).Content);
        TimeView.setText(replies.get(position).ReplyTime);
//        rowView.setOnClickListener((View.OnClickListener)context);

        // Change icon based on name

        return rowView;
    }
}