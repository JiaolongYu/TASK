package com.example.administrator.task;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

public class ManageActivity extends ActionBarActivity implements ResultCallback<People.LoadPeopleResult>, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnClickListener {

    String accountName;
    private GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;

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
            mSignout.setEnabled(true);
            mSignout.setOnClickListener(this);

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
                        CommonTask = jObject.getJSONArray("taskname");
                        PrivateTask = jObject.getJSONArray("pritaskname");

//                        if (sub.equals("1")) {
//                            displayImages = jObject.getJSONArray("suburl");
//                            namelists = jObject.getJSONArray("subnamelist");
//                        }
//                        else{
//                            displayImages = jObject.getJSONArray("displayImages");
//                            namelists = jObject.getJSONArray("namelist");
//                        }
//                    JSONObject jObject = new JSONObject(new String(response));
//                    JSONArray displayImages = jObject.getJSONArray("displayImages");
//                    JSONArray namelists = jObject.getJSONArray("namelist");
                        JSONArray locations = jObject.getJSONArray("location");
                        JSONArray imageurl = jObject.getJSONArray("imageurl");
                        JSONArray streamname = jObject.getJSONArray("streamname");


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
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            // updateUI(false);
            System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
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

}
