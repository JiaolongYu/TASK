package com.example.administrator.task;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageActivity extends ActionBarActivity implements ResultCallback<People.LoadPeopleResult>, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnClickListener {

    static String accountName;
    private GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        Intent intentstream = getIntent();
        accountName = intentstream.getStringExtra("account");
//        TextView mDebug = (TextView) findViewById(R.id.managedebug);
//        mDebug.setText(accountName);
//        System.out.println("account:"+accountName);
        if(accountName != null){
            TextView mDebug = (TextView) findViewById(R.id.managedebug);
            mDebug.setText(accountName);
            Button mSignout = (Button) findViewById(R.id.manage_sign_out);
            Button mCreate = (Button) findViewById(R.id.CreateTask);
            mCreate.setEnabled(true);
            mCreate.setOnClickListener(this);
            mSignout.setEnabled(true);
            mSignout.setOnClickListener(this);

            final TableLayout PTaskTable = (TableLayout)findViewById(R.id.PrivateTask);
            //----get TASK data from server

            final String request_url = "http://task-1123.appspot.com/viewmytask?userid="+accountName;
            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.get(request_url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response) {


                    try {

                        JSONObject jObject = new JSONObject(new String(response));
                        JSONArray CommonTask;
                        JSONArray PrivateTask;
//                        ArrayList<String> CTask = new ArrayList<String>();
//                        ArrayList<String> PTask;
//                        CommonTask = jObject.getJSONArray("taskname");
                        PrivateTask = jObject.getJSONArray("pritaskname");
                        int tasknum;

//                        if(CommonTask.length()>0 && CommonTask.length()<3){
//                            tasknum = CommonTask.length();
//                        }
//                        else tasknum = 3;
//                        TableLayout CTaskTable = (TableLayout)findViewById(R.id.CommonTask);

//                        for(int i = 0; i < tasknum; i++){
//                            if(i == 0){
//                                TextView CT1 = (TextView)findViewById(R.id.Ctask1);
//                                CT1.setText(CommonTask.getString(i));
//                            }else{
//                                TextView CT2 = new TextView(ManageActivity.this);
//                                CT2.setText(CommonTask.getString(i));
//                                CT2.setTextColor(0xFFFFFF);
//                                CT2.setTextSize(23);
//                                TableRow CTR = new TableRow(ManageActivity.this);
//                                CTR.addView(CT2);
//                                CTaskTable.addView(CTR);
//                            }
//                        }

                        if(PrivateTask.length()>0 && PrivateTask.length()<3){
                            tasknum = PrivateTask.length();
                        }else tasknum = 3;
                        System.out.println(tasknum);

                        for(int i = 0; i < tasknum; i++){
                            if(i == 0){
                                TextView PT1 = (TextView)findViewById(R.id.Ptask1);
                                PT1.setText(PrivateTask.getString(i));
                            }else{
                                System.out.println("here");
                                TableRow PTR = new TableRow(context);
                                TableRow PTRR = (TableRow)findViewById(R.id.PTR1);

                                PTR.setBackground(PTRR.getBackground());
                                TextView PT2 = new TextView(context);
//                                PT2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                PT2.setText(PrivateTask.getString(i));
                                PT2.setTextColor(Color.WHITE);
                                PT2.setTextSize(23);
//                                PT2.setPadding(3, 3, 3, 3);

//                                PTR.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                PTR.addView(PT2);
                                PTaskTable.addView(PTR);
                                System.out.println("there");
                            }
                        }





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
        else{
            Intent intent= new Intent(this, ManageActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
//         TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.manage_sign_out:
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                    // updateUI(false);
                    System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
                    Intent intent= new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.CreateTask:
                DialogFragment newFragment = new CreateTaskSelect();
                newFragment.show(getSupportFragmentManager(), "CREATE");
//                Dialog CreateDialog = newFragment.getDialog();
//                Button positiveButton = CreateDialog.getActionBar(DialogInterface.BUTTON_POSITIVE);
        }


    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult arg0) {
        // TODO Auto-generated method stub

    }

    public static class CreateTaskSelect extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogCustom);
            builder.setMessage(R.string.tasktype)
                    .setPositiveButton(R.string.common_task, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })


                    .setNegativeButton(R.string.private_task, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getActivity(), com.example.administrator.task.CreatePrivateTask.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("account", accountName);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

        @Override
        public void onStart()
        {
            super.onStart();

            Button pButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
            Button nButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);
//        ((AlertDialog) getDialog()).getWindow().setTitleColor(getResources().getColor(R.color.orange));
            pButton.setBackgroundColor(getResources().getColor(R.color.dialogcolor));
            pButton.setTextColor(getResources().getColor(R.color.white));
            nButton.setBackgroundColor(getResources().getColor(R.color.dialogcolor));
            nButton.setTextColor(getResources().getColor(R.color.white));
        }
    }

}
