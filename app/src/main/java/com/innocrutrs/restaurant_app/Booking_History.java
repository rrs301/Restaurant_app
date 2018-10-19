package com.innocrutrs.restaurant_app;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.AutoCompleteTextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Booking_History extends AppCompatActivity {

    ArrayList<String> P_Image;
    private static final String TAG = "RecyclerViewExample";
    public static int condition = 1;
    static List<FeedItem> feedsList;
    //  final String url;
    String urlme;
    ProgressDialog pd;
    private RecyclerView mRecyclerView;
    private ShowAdListAdapter_BookSeat adapter;
    static String Cat;
    DrawerLayout drawer;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    AutoCompleteTextView SearchEditbox;
    static String[] Adcruts_ads,Adcruts_Ads_1;
    String Gender,UserMobileNumber;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__history);

        pref=getApplicationContext().getSharedPreferences("hotelapp",MODE_PRIVATE);
        editor=pref.edit();

        UserMobileNumber=pref.getString("Email","0");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        mRecyclerView.setLayoutManager(mLayoutManager);
        GetData();

    }

    public void GetData() {
        pd = new ProgressDialog(Booking_History.this);
        pd.setMessage("Sending Confirmation....");
        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Booking_History.this);
        String url = "https://incrts.tk/hotel_app/GetBookingHistory.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();

                Log.i("ResponceClick", response.toString());
                parseResult(response.toString());


                adapter = new ShowAdListAdapter_BookSeat(Booking_History.this, feedsList, mRecyclerView);
                mRecyclerView.setAdapter(adapter);

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
                MyData.put("MobileNumber", UserMobileNumber);//Add the data you'd like to send to the server.
             //   MyData.put("Category", Cat);//Add the data you'd like to send to the server.
                // MyData.put("UserItem", UserItem);//Add the data you'd like to send to the server.
                //  MyData.put("UserMobile", UserMobileNumber);//Add the data you'd like to send to the server.



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
    private void parseResult(String result) {
        Log.i("InParse", "YesNONONO");

        try {
            //   Log.i("InParseIn", "Yes");

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");

            Log.i("Posts", String.valueOf(posts));
            feedsList = new ArrayList();

            for (int i = 0; i < 50; i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();

                try {
                    item.setR_Name(post.optString("r_name"));
                    item.setR_SeatBook(post.optString("seatbook"));
                    item.setR_Time(post.optString("time"));
                    item.setR_Status(post.optString("status"));
                    //item.setRestaurantAddress(post.optString("address"));


//
                    feedsList.add(item);
                   // list.add(post.optString("address"));
                }
                catch ( Exception e)
                {

                }
                // list.add(post.optString("title"));
                //  Log.i("AppImage:", post.optString("title"));
                // Log.i("Appurl:", post.optString("url"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
