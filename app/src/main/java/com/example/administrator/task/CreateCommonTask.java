package com.example.administrator.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

public class CreateCommonTask extends ActionBarActivity implements View.OnClickListener{
    Context context = this;
    protected GoogleApiClient mGoogleApiClient;

    String accountName;
    String CTaskName;
    String CTaskDescription;
    String CTaskDue;
    Integer CTaskID;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_common_task);


        Intent intent = getIntent();
        accountName = intent.getStringExtra("account");
        TextView User = (TextView)findViewById(R.id.createCdebug);
        User.setText(accountName);


        Button mCreate = (Button)findViewById(R.id.createC);
        mCreate.setOnClickListener(this);
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        c = Calendar.getInstance();

        btn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateCommonTask.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                TextView text = (TextView) findViewById(R.id.taskdue);
                                String tmp2 = String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth);
                                text.setText(tmp2);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(CreateCommonTask.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                TextView text = (TextView) findViewById(R.id.taskdue);
                                String tmp=String.valueOf(c.get(Calendar.YEAR))+"-"+String.valueOf(c.get(Calendar.MONTH))+"-"+String.valueOf(c.get(Calendar.DAY_OF_MONTH))+"-"+String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                                text.setText(tmp);
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();

            }

        });

    }

    @Override
    public void onClick(View v) {
        EditText TaskName =(EditText)findViewById(R.id.taskname);
        EditText TaskDue = (EditText)findViewById(R.id.taskdue);
        EditText TaskDescription = (EditText)findViewById(R.id.taskdiscrip);

        CTaskName = TaskName.getText().toString();
        CTaskDue = TaskDue.getText().toString();
        CTaskDescription = TaskDescription.getText().toString();
        CTaskID = (CTaskName+accountName).hashCode();

        RequestParams params = new RequestParams();
        params.put("taskname", CTaskName);
        params.put("creator", accountName);
        params.put("description", CTaskDescription);
        params.put("due", CTaskDue);
        params.put("taskid", CTaskID);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://task-1123.appspot.com/createcommontask", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response) {
                Log.w("async", "success!!!!");
                Toast.makeText(context, "Create Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] errorResponse, Throwable e) {
                Log.e("Posting_to_blob", "There was a problem in retrieving the url : " + e.toString());
            }
        });

        try {
            Thread.sleep(500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }


        Intent intent= new Intent(this, ManageActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("account", accountName);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
