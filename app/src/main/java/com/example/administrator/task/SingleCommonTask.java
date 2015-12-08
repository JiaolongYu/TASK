package com.example.administrator.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleCommonTask extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_common_task);

        Intent intent = getIntent();
        int TaskId = intent.getIntExtra("CTaskID", 0);
        System.out.println(TaskId);

        final String request_url = "http://task-1123.appspot.com/viewsinglecommontask?taskid="+TaskId;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(request_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response) {


                try {

                    JSONObject jObject = new JSONObject(new String(response));
                    String CTaskname;
                    String CTaskdue;
                    String CTaskdescription;
                    String CTaskcreatetime;
//                        ArrayList<String> CTask = new ArrayList<String>();
//                        ArrayList<String> PTask;
//                        CommonTask = jObject.getJSONArray("taskname");
//                    System.out.println("here before Ptaskname");
                    CTaskname = jObject.getJSONArray("taskname").getString(0);
//                    System.out.println("here after Ptaskname");
                    CTaskdue = jObject.getJSONArray("due").getString(0);
                    CTaskdescription = jObject.getJSONArray("description").getString(0);
//                    System.out.println("here after Ptaskdescription");
                    CTaskcreatetime = jObject.getJSONArray("create_time").getString(0);
//                    System.out.println("here after createtime");
                    TextView ctaskname = (TextView) findViewById(R.id.ctaskname);
                    TextView ctaskdue = (TextView) findViewById(R.id.ctaskdue);
                    TextView ctaskdescription = (TextView) findViewById(R.id.ctaskdescript);
                    TextView ctaskcreatetime = (TextView) findViewById(R.id.ctaskcreatetime);
                    ctaskname.setText(CTaskname);
                    ctaskdue.setText(CTaskdue);
                    ctaskdescription.setText(CTaskdescription);
                    ctaskcreatetime.setText(CTaskcreatetime);

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
