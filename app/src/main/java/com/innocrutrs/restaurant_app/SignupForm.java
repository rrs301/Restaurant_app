package com.innocrutrs.restaurant_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SignupForm extends AppCompatActivity {

    private Spinner spinner,spinner_2;
    private static final String[]Collage = {"Select Your Collage","COEP", "MMCOE", "PCCOE"};
    private static final String[]Field = {"Select Your Collage","COEP", "MMCOE", "PCCOE"};
   static List<String> list ;
    ImageView Male,Female;
    String Gendar;
    String UserMobileNumber,Refercode,currentDayIs,UserName,CollageName,RandomReferCode;
    ProgressDialog pd;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    EditText UserNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        getSupportActionBar().hide();

        pref=getApplicationContext().getSharedPreferences("campusstore",MODE_PRIVATE);
        editor=pref.edit();

        list= new ArrayList<String>();
//        list.add("item_1");
//        list.add("item_2");
        Male=(ImageView)findViewById(R.id.male);
        Female=(ImageView)findViewById(R.id.female);
        UserNameText=(EditText) findViewById(R.id.Name);
        UserMobileNumber=pref.getString("Email",null);
        Refercode=pref.getString("ReferCode",null);
        currentDayIs = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
      //  UserMobileNumber="917709030860";
        GetCollegeName();
        spinner = (Spinner)findViewById(R.id.spinner_1);

       // spinner.setOnItemSelectedListener(this);

        RandomReferCode=getSaltString();

    }

    public void GetCollegeName() {
        pd = new ProgressDialog(SignupForm.this);
        pd.setMessage("Sending Confirmation....");
        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(SignupForm.this);
        String url = "https://incrts.tk/campusstore/GetCollegeName.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();

                Log.i("ResponceClick", response.toString());
                parseResult_College(response.toString());

                ArrayAdapter<String>adapter = new ArrayAdapter<String>(SignupForm.this,
                        android.R.layout.simple_spinner_item,list);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                //completeclick.setText(String.valueOf(ClickCountIs));


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //This code is executed if there is an error.

                Log.i("ErrorFound",error.getMessage());
                // Toast.makeText(getApplicationContext(), "Try Again...", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                ;//Add the data you'd like to send to the server.
                //    Log.i("VType=",V_Type);
              //Add the data you'd like to send to the server.



                return MyData;
            }
        };
//                if(status)
//                {

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(2000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyRequestQueue.add(MyStringRequest);


    }
    private void parseResult_College(String result) {
        Log.i("InParse", "YesNONONO");

        try {
            //   Log.i("InParseIn", "Yes");

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");

            Log.i("Posts", String.valueOf(posts));
           // feedsList = new ArrayList();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();

                list.add(post.optString("college"));


//
               // feedsList.add(item);
             //   list.add(post.optString("title"));
                Log.i("AppImage:", post.optString("college"));
                // Log.i("Appurl:", post.optString("url"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void SelectMale(View view)
    {
       Male.setBackgroundColor(Color.parseColor("#223e53"));
       Female.setBackgroundColor(Color.parseColor("#305571"));
       Gendar="Male";

    }
    public void SelectFemale(View view)
    {
        Male.setBackgroundColor(Color.parseColor("#305571"));
        Female.setBackgroundColor(Color.parseColor("#223e53"));
        Gendar="Female";

    }


    public void SubmutBtn(View view)
    {
        UserName=UserNameText.getText().toString();
        CollageName=String.valueOf(spinner.getSelectedItem());
        Log.i("Name",String.valueOf(spinner.getSelectedItem()));
        VollyMethod();

    }

    public void VollyMethod() {

        pd = new ProgressDialog(SignupForm.this);
        pd.setMessage("Verification...");
        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://incrts.tk/campusstore/CheckUserAndSave.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();
                Log.i("Responce Is",response.toString());
                if(response.toString().compareTo("1")==0)
                {
                    //editor.putFloat("Balance", 0);
                    editor.putString("Email",UserMobileNumber);
                    editor.putString("UserName",UserName);
                    editor.putString("ReferCode",RandomReferCode);
                    editor.putString("Gender",Gendar);
                    editor.putString("Collage",CollageName);
                    editor.putInt("Login",1);
                    editor.commit();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Welcome ...", Toast.LENGTH_SHORT).show();

                }
                else if(response.toString().compareTo("2")==0)
                {
                    Toast.makeText(getApplicationContext(),"User With This Model Already Regsiter", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    parseResult(response.toString());
                    //editor.putFloat("Balance", Balance);
                    editor.putString("Email",UserMobileNumber);
                    editor.putString("UserName",UserName);
                    editor.putString("ReferCode",Refercode);
                    editor.putString("Gender",Gendar);
                    editor.putString("Collage",CollageName);

                    editor.putInt("Login",1);
                    editor.commit();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Welcome Back!!!", Toast.LENGTH_SHORT).show();
                }
//                Log.i("This Random", String.valueOf(rand));
//                Intent intent=new Intent(getApplicationContext(),OTP_Check.class);
//                intent.putExtra("OTPCODE",String.valueOf(rand));
//                intent.putExtra("MobileNumber",String.valueOf(MobileNo));
//                intent.putExtra("UserName",String.valueOf(User_Name));
//                intent.putExtra("UserReferCode",String.valueOf(UserRefer));
//                intent.putExtra("DeviceId",String.valueOf(deviceId));

                //   startActivity(intent);
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //This code is executed if there is an error.
                Log.i("Error:",error.getMessage());
                Toast.makeText(SignupForm.this, "Try Again...", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", UserName.replace(" ","%20"));
                MyData.put("mobile", UserMobileNumber);
                MyData.put("code", RandomReferCode);//Add the data you'd like to send to the server.
                MyData.put("userrefer", Refercode);
                MyData.put("joindate", currentDayIs);
                MyData.put("gendar", Gendar);
                MyData.put("collage",CollageName);

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

    }

    private void parseResult(String result) {
        Log.i("InParse", "YesNONONO");
        //result=result.substring(1);
        try {
            Log.i("InParseIn", result);

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");

            //feedsList = new ArrayList();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                //FeedItem item = new FeedItem();

                UserName=post.optString("name");
                UserMobileNumber=post.optString("mobile");
                Refercode=post.optString("refercode");
                Gendar=post.optString("gendar");
                CollageName=post.optString("collage");
               // Balance= Float.parseFloat((post.optString("balance")));

                //feedsList.add(item);
                Log.i("C_Title:", post.optString("name"));
                // Log.i("Appurl:", post.optString("url"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        Log.i("RandomString Is",saltStr);
        return saltStr;

    }
}
