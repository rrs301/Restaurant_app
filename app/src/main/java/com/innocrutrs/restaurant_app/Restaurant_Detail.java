package com.innocrutrs.restaurant_app;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ss.com.bannerslider.Slider;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class Restaurant_Detail extends AppCompatActivity implements PaymentResultListener {

    ArrayList<String>R_Image;
    TextView Res_Title,Res_Desc,Res_Contact,Res_Address,Res_OpenTime;
    Button Res_Menu;
    String R_Menu, R_Address,R_Contact,R_BookTable,R_Getway;
    Slider slider;
    ProgressDialog pd;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String UserMobileNumber,R_Title;
    Button BookTableBtn;
    InterstitialAd mAd;
     EditText BookTable,UserName;
     Dialog dialog;
    static String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant__detail);
        getSupportActionBar().hide();
        pref=getApplicationContext().getSharedPreferences("hotelapp",MODE_PRIVATE);
        editor=pref.edit();

        UserMobileNumber=pref.getString("Email","0");
        Username=pref.getString("Username","");
        MobileAds.initialize(getApplicationContext(), String.valueOf(R.string.AdmobApppId));

        R_Image = new ArrayList<String>();
        Intent intent=getIntent();
        R_Image.add(intent.getStringExtra("R_Image1"));
        R_Image.add(intent.getStringExtra("R_Image2"));
        R_Image.add(intent.getStringExtra("R_Image3"));

        Res_Title=(TextView)findViewById(R.id.res_title);
        Res_Address=(TextView)findViewById(R.id.res_address);
        Res_Contact=(TextView)findViewById(R.id.res_contact);
        Res_Desc=(TextView)findViewById(R.id.res_desc);
        Res_Menu=(Button) findViewById(R.id.MenuDownloadBtn);
        Res_OpenTime=(TextView) findViewById(R.id.res_opentime);
        BookTableBtn=(Button) findViewById(R.id.booktableBtn);
        Slider.init(new PicassoImageLoadingService(this));

        R_Title=intent.getStringExtra("R_Title");
        String R_Desc=intent.getStringExtra("R_Desc");
         R_Contact=intent.getStringExtra("R_Contact");
         R_Address=intent.getStringExtra("R_Address");
        String R_OpenTime=intent.getStringExtra("R_OpenTime");
        R_Menu=intent.getStringExtra("R_Menu");
        R_BookTable=intent.getStringExtra("R_BookTable");
        R_Getway=intent.getStringExtra("R_Getway");

        // Log.i("Menu",R_Menu);

        Res_Title.setText(R_Title);
        Res_Address.setText(R_Address);
        Res_Contact.setText(R_Contact);
        Res_Desc.setText(R_Desc);
        Res_OpenTime.setText(R_OpenTime);
        Log.i("BookTableIs",R_BookTable);
        if(R_BookTable.compareTo("No")==0)
        {
            BookTableBtn.setVisibility(View.GONE);
            Log.i("NONONO","Yes");
        }
      //  Res_Title.setText(R_Title);
        Checkout.preload(getApplicationContext());

        AddSlider();

    }

    public void GetAddressOnMap(View view)
    {
        String map = "http://maps.google.co.in/maps?q=" + R_Address;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(intent);
    }
    public void CallBtn(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+R_Contact));
        startActivity(intent);
    }

    public void AddSlider()
    {

        slider = findViewById(R.id.Slider);

        //delay for testing empty view functionality
        slider.postDelayed(new Runnable() {
            @Override
            public void run() {
                slider.setAdapter(new MainSliderAdapter(getApplicationContext(),R_Image.get(0),R_Image.get(1),R_Image.get(2)));
                slider.setSelectedSlide(0);
            }
        }, 1500);



//
    }

    public void DownloadMenu(View view)
    {

        Intent intent=new Intent(this,WebViewActivity.class);
        intent.putExtra("UrlToLoad","http://docs.google.com/viewer?url="+R_Menu);
        startActivity(intent);


    }

    public void BookTable(View view)
    {
         dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        Button dialogButton = (Button) dialog.findViewById(R.id.cancel_btn);
         Button playNowBtn = (Button) dialog.findViewById(R.id.playnowBtn);
        TextView DialogDesc = (TextView) dialog.findViewById(R.id.text_2);
        TextView DialogTitle = (TextView) dialog.findViewById(R.id.text_1);
         BookTable=(EditText)dialog.findViewById(R.id.SeatEditBox);
         UserName=(EditText)dialog.findViewById(R.id.NameEditBox);
         UserName.setText(Username);
         UserName.setEnabled(false);

        DialogTitle.setText("Book Table");
        DialogDesc.setText("Enter Number Of Seat you Want to Book");

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        playNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SeatCount=BookTable.getText().toString();
                if(R_Getway.compareTo("Yes")==0)
                {
                    if (SeatCount.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Enter Number of Seat You Want to Book", Toast.LENGTH_SHORT).show();
                    } else {
                        startPayment();

                    }

                }
                else {
                    if (SeatCount.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Enter Number of Seat You Want to Book", Toast.LENGTH_SHORT).show();
                    } else {
                        ConfirmSeatBooking(BookTable.getText().toString(), UserName.getText().toString());
                    }
                }
            }
        });
        dialog.show();
    }

    public void ConfirmSeatBooking(final String BT, final String UN)
    {
        pd = new ProgressDialog(Restaurant_Detail.this);
        pd.setMessage("Sending Confirmation....");
        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Restaurant_Detail.this);
        String url = "https://incrts.tk/hotel_app/SaveSeatBookData.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();

                Log.i("ResponceClick", response.toString());
                SendSms();
              //  parseResult(response.toString());


            Toast.makeText(getApplicationContext(),"Table Booking Request Send, Thank You!!!",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
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
                MyData.put("Username", UN);//Add the data you'd like to send to the server.
                MyData.put("SeatBook", BT);//Add the data you'd like to send to the server.
                // MyData.put("UserItem", UserItem);//Add the data you'd like to send to the server.
                  MyData.put("Mobile", UserMobileNumber);//Add the data you'd like to send to the server.
                MyData.put("Res_Name",R_Title);



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

    //http://www.smsjust.com/sms/user/urlsms.php?username=Rich@555&pass=Rich@555&type=0&dlr=0&senderid=%20Richsl&message=hi&dest_mobileno=7276387873&response=Y
    public void SendSms()
    {
        pd = new ProgressDialog(Restaurant_Detail.this);
        pd.setMessage("Sending Confirmation....");
        pd.show();
        String Msg="Thanks For Table Booking Your Table Booking Status is under review";
        Log.i("UserNumber",UserMobileNumber);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Restaurant_Detail.this);
        String url = "http://www.smsjust.com/sms/user/urlsms.php?username=Rich@555&pass=Rich@555&type=0&dlr=0&senderid=%20Richsl&message="+Msg.replaceAll(" ","%20")+"&dest_mobileno="+UserMobileNumber+"&response=Y";
        Log.i("Url Is",url);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();

                Log.i("ResponceClick", response.toString());
                //  parseResult(response.toString());
                SendSmsToRest();
              //  ShowFullAd();

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
    public void SendSmsToRest()
    {
        pd = new ProgressDialog(Restaurant_Detail.this);
        pd.setMessage("Sending Confirmation....");
        pd.show();
        String Msg="The User "+Username+" "+UserMobileNumber+" Just Booked the Table , Please Confirm Or Reject the Status";
        Log.i("UserNumber",R_Contact);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Restaurant_Detail.this);
        String url = "http://www.smsjust.com/sms/user/urlsms.php?username=Rich@555&pass=Rich@555&type=0&dlr=0&senderid=%20Richsl&message="+Msg.replaceAll(" ","%20")+"&dest_mobileno="+R_Contact+"&response=Y";
        Log.i("Url Is",url);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();

                Log.i("ResponceClick", response.toString());
                //  parseResult(response.toString());

                ShowFullAd();

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

    @Override
    public void onPaymentSuccess(String s) {
        ConfirmSeatBooking(BookTable.getText().toString(), UserName.getText().toString());

    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    //---------------------Payment Start------------------

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.cake);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", R_Title);

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");

            options.put("currency", "INR");

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", "5000");

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.i("Error", "Error in starting Razorpay Checkout");
        }
    }

    public void ShowFullAd()
    {
        mAd=new InterstitialAd(this);

        mAd.setAdUnitId("ca-app-pub-8574828366345851/5822502047");
        mAd.loadAd(new AdRequest.Builder().addTestDevice("B8391EB6F96DF89F51011980B61E388D").build());
        if (mAd.isLoaded()) {
            mAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        mAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Log.i("Ads", "onAdLoaded");
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("Ads", "onAdOpened");
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
//                int JokeCount=pref.getInt("JokeCount",0);
//
//                if(JokeCount==10)
//                {
//                    UpdateUserBalance("4","Joke");
//                    editor.putInt("JokeCount",0).commit();
//                }
//                else {
//
//                }
            }


            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("Ads", "onAdClosed");
            }
        });

    }
}

