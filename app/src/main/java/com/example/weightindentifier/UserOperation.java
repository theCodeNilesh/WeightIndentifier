package com.example.weightindentifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weightindentifier.DbHelper.DbHelper;
import com.example.weightindentifier.DbHelper.ProjectContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserOperation extends AppCompatActivity {

    DbHelper mDbHelper;
    String url = "https://api.thingspeak.com/channels/985867/fields/1.json?api_key=BGCOSMYE2D04AX2J&results=2";
    String sendSms = "http://10xmbaonline.com/androidApp/RestAPIs/sendVehicalOverloadMessageToUser";
    String sqlMobile;

    Button addUserButton, deleteUserButton, updateUserButton, reportGenerationButton;
    String latitude, longitude, userName, vehicleNumber, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operation);
        getSupportActionBar().setTitle("Vehicle Overload Detection System");

        addUserButton = (Button) findViewById(R.id.addUserButton);
        deleteUserButton = (Button) findViewById(R.id.deleteUserButton);
        //updateUserButton = (Button) findViewById(R.id.updateUserButton);
        reportGenerationButton = (Button) findViewById(R.id.reportGenerationButton);

        mDbHelper = new DbHelper(this);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AddUser.class);
                startActivity(i);

            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), DeleteUser.class);
                startActivity(i);

            }
        });

       /* updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), UpdateUser.class);
                startActivity(i);

            }
        });
*/
        reportGenerationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUserDetails();


            }
        });

    }


    public void getUserDetails() {


        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ProjectContract.Project.USERNAME,
                ProjectContract.Project.V_NUMBER,
                ProjectContract.Project.MOBILENUMBER,


        };

        /*String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { partyId };*/

        Cursor cursor = db.query(
                ProjectContract.Project.TBL_USER,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        if (cursor != null) {

            while (cursor.moveToNext()) {
                userName = cursor.getString(0);
                vehicleNumber = cursor.getString(1);

                sqlMobile = cursor.getString(2);
            }


            if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(vehicleNumber)) {

                Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("dbValue:", "userName" + userName + "VName:" + vehicleNumber);

                if (isNetworkConnected()) {
                    getServerData();

                } else {

                    Toast.makeText(this, "Please Check Internet Connection....!", Toast.LENGTH_SHORT).show();
                }


            }


            cursor.close();

        } else {

            Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show();
        }
    }


    private void getServerData() {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("ServerResponse", response);
                try {
                    JSONObject object = null;
                    object = new JSONObject(response);

                    JSONObject jsonObject = object.getJSONObject("channel");

                    latitude = jsonObject.getString("latitude");
                    longitude = jsonObject.getString("longitude");

                    JSONArray obj = object.getJSONArray("feeds");
                    JSONObject lastRecord = obj.getJSONObject(obj.length() - 1);
                    int vehicleWeight = Integer.parseInt(lastRecord.getString("field1"));

                    String vehicleWeightStr = lastRecord.getString("field1");


                    if (vehicleWeight < 50) {
                        Toast.makeText(UserOperation.this, "Vehicle is UnderLoad", Toast.LENGTH_SHORT).show();
                        status = "undrLoad";
                    } else {
                        Toast.makeText(UserOperation.this, "Vehicle is OverLoad", Toast.LENGTH_SHORT).show();
                        String msg = "Hello " + userName + " Your Vehicle is Overload and the weight of Your Vehicle is:" + vehicleWeightStr + " gms";

                        sendSms(sqlMobile, msg);

                        /*String API_KEY = "DoNA6d4T7UelHye9Bk5zfA";
                        String sendrId = "TESTIN";
                        String mobileNo = sqlMobile;
                        String msg = "Hello " + userName + " Your Vehicle is Overload and the weight of Your Vehicle is:" + vehicleWeightStr + " gms";

                        String sendSms = "http://sms.innovationshub.in/api/mt/SendSMS?APIKey=" + API_KEY + "&senderid=" + sendrId + "&channel=2&DCS=0&flashsms=0&number=" + mobileNo + "&text=" + msg + "&route=1";

                        Log.i("sendSms", "" + sendSms);*/

                        status = "overlaod";
                    }

                    Log.i("responseData", "" + latitude + "longitude" + longitude + "vehicleWeight" + vehicleWeight);
                    Intent i = new Intent(getApplicationContext(), ReportGeneration.class);
                    i.putExtra("latitude", latitude);
                    i.putExtra("longitude", longitude);
                    i.putExtra("vehicleWeight", vehicleWeightStr);
                    i.putExtra("status", status);
                    i.putExtra("userName", userName);
                    i.putExtra("vehicleNumber", vehicleNumber);
                    startActivity(i);
                    finish();

                } catch (JSONException e) {

                    Log.i("exception", "" + e);
                    Toast.makeText(getApplicationContext(), "Server error, please try after some time", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Server error, please try after some time", Toast.LENGTH_SHORT).show();


            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(UserOperation.this);
        requestQueue.add(request);
    }


    public void sendSms(final String mobileNumber, final String message) {
        StringRequest request = new StringRequest(Request.Method.POST, sendSms, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("smsResponse", response);

                try {
                    JSONObject object = new JSONObject(response);

                } catch (JSONException e) {
                    Log.d("error", "" + e);
                    Toast.makeText(getApplicationContext(), "SOMTHING WENT WRONG", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "SERVER ERROR TRY AFTER SOME TIME", Toast.LENGTH_SHORT).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();

                hashMap.put("mobile_no", mobileNumber);
                hashMap.put("text_message", message);
                Log.d("hashMapLog", "" + hashMap);

                return hashMap;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(UserOperation.this);
        requestQueue.add(request);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
