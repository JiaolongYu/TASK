package com.example.administrator.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.task.myComment;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by JocelynYu on 12/8/15.
 */
public class CommentAdapter extends ArrayAdapter<myComment> {
    private final Context context;
    private final ArrayList<myComment> comments;

    public CommentAdapter(Context context, ArrayList<myComment> comments_) {
        super(context, R.layout.comment_item,comments_);
        this.context = context;
        this.comments = comments_;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.comment_item, parent, false);
        TextView CommentView = (TextView) rowView.findViewById(R.id.comment);
        TextView CreatorView = (TextView) rowView.findViewById(R.id.comment_creator);
        TextView TimeView = (TextView) rowView.findViewById(R.id.comment_time);
        ListView ReplyList = (ListView) rowView.findViewById(R.id.replys);
        System.out.println("adaptor:"+comments.get(position).Content+" has "+comments.get(position).Replies.size());
        ReplyList.setAdapter(new ReplyAdapter(context, comments.get(position).Replies));
        ImageView imageView = (ImageView) rowView.findViewById(R.id.reply);
        CommentView.setText(comments.get(position).Content);
        CreatorView.setText(comments.get(position).Creator+":");
        TimeView.setText(comments.get(position).CommentTime);
        imageView.setOnClickListener((View.OnClickListener)context);

        // Change icon based on name

        return rowView;
    }
}
