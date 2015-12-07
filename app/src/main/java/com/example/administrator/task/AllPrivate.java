package com.example.administrator.task;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllPrivate extends ActionBarActivity {
    String accountName;
    private ListView listView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_private);

        Intent intentstream = getIntent();
        accountName = intentstream.getStringExtra("account");

        TextView User = (TextView)findViewById(R.id.allpdebug);
        User.setText(accountName);

        final String request_url = "http://task-1123.appspot.com/viewmytask?userid="+accountName;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(request_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response) {


                try {

                    JSONObject jObject = new JSONObject(new String(response));
                    JSONArray PrivateTask;
                    JSONArray PrivateTaskID;
                    ArrayList<String> PTask = new ArrayList<String>();
                    final ArrayList<Integer> PTaskID = new ArrayList<Integer>();
                    PrivateTask = jObject.getJSONArray("pritaskname");
                    PrivateTaskID =jObject.getJSONArray("pritaskid");
                    System.out.println(PrivateTask.length());

                    for (int i = 0; i < PrivateTask.length(); i++) {
                        PTask.add(PrivateTask.getString(i));
                        PTaskID.add(PrivateTaskID.getInt(i));
                    }

                    listView = (ListView)findViewById(R.id.Tasklist);
                    listView.setAdapter(new ArrayAdapter<String>(AllPrivate.this, android.R.layout.simple_expandable_list_item_1, PTask));

                    final SwipeDetector swipeDetector = new SwipeDetector();
                    listView.setOnTouchListener(swipeDetector);


                    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                long arg3) {
                            if (swipeDetector.swipeDetected()) {
                                if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
//                                    float x = 5/10;
//                                    findViewById((int)arg3).setAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));


                                    Toast.makeText(context, "Right to left", Toast.LENGTH_SHORT).show();
                                } if(swipeDetector.getAction() == SwipeDetector.Action.None){
                                    Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Else", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, com.example.administrator.task.SinglePrivateTask.class);
                                Bundle bundle=new Bundle();
                                int P = position;
                                bundle.putInt("PTaskID", PTaskID.get(P));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    };

                    listView.setOnItemClickListener(listener);

                } catch (JSONException j) {
                    System.out.println("JSON Error");
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] errorResponse, Throwable e) {
                Log.e("ManagePage", "There was a problem in retrieving the url : " + e.toString());
            }
        });
    }

}
