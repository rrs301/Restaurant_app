package com.innocrutrs.restaurant_app;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ss.com.bannerslider.Slider;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TJConnectListener, TJPlacementListener {

   static ArrayList<String>P_Image;
    private static final String TAG = "RecyclerViewExample";
    public static int condition = 1;
    static List<FeedItem> feedsList;
    //  final String url;
    String urlme;
    ProgressDialog pd;
    private RecyclerView mRecyclerView;
    private ShowAdListAdapter_home adapter;
    static String Cat;
    DrawerLayout drawer;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    Slider slider;
    InterstitialAd mAd;
    static String[] Adcruts_ads,Adcruts_Ads_1;
    String Gender,UserMobileNumber,Username;
    RelativeLayout ActionBar_Dummy;
    String SliderImage1,SliderImage2,SliderImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        pref=getApplicationContext().getSharedPreferences("hotelapp",MODE_PRIVATE);
        editor=pref.edit();

        Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");
        Tapjoy.connect(this.getApplicationContext(), "SVygFM-5RIyegcCDdDgAWAECdbuf7rNYX3B431lb2aZdlVgrEKsIKHINL8IF", connectFlags, this);

        UserMobileNumber=pref.getString("Email","0");
        Username=pref.getString("Username","0");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFFFF")));
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x00FFFFFF));
        getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));

        MobileAds.initialize(getApplicationContext(), String.valueOf(R.string.AdmobApppId));
//        //----------------Load Banner----------------------------
//        AdView adView = new AdView(getApplicationContext());
//        adView.setAdSize(AdSize.SMART_BANNER);
//        adView.setAdUnitId("ca-app-pub-9859005529851767/5352939232");
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        P_Image = new ArrayList<String>();
//        P_Image.add("https://cmkt-image-prd.global.ssl.fastly.net/0.1.0/ps/3379856/910/606/m1/fpnw/wm1/pfhgxchmb2ttmklouoabchtigsyma36lawo3w97ycjnyahr5vku9hequuk1beweg-.jpg?1507495665&s=3b3a3bd99c12fe925ba1524e49db3d7b");
//       P_Image.add("https://image.freepik.com/free-photo/big-sandwich-hamburger-with-juicy-chicken-burger-cheese-cucumber-chili-and-tartar-sauce-on-black-background_2829-181.jpg");
//       P_Image.add("https://dollarsandsense.sg/wp-content/uploads/2016/01/Pizza-2.jpg");


        TJPlacementListener placementListener = this;
        TJPlacement p = Tapjoy.getPlacement("AppLaunch", placementListener);

        if(Tapjoy.isConnected()) {
            p.requestContent();
        } else {
            Log.d("Wapearn", "Tapjoy SDK must finish connecting before requesting content.");
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Slider.init(new PicassoImageLoadingService(this));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        TextView UsernameText=(TextView)headerView.findViewById(R.id.Username);
        TextView UserMobileText=(TextView)headerView.findViewById(R.id.textView);
        UserMobileText.setText(UserMobileNumber);
        UsernameText.setText(Username);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL); // (int spanCount, int orientation)
        mRecyclerView.setLayoutManager(mLayoutManager);
       // AddSlider();
       // SetSlider();
        GetData();
        GetSliderData();
        ShowFullAd();
    }


    //Slider Add
    public void AddSlider()
    {
        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional
        FlipperLayout flipperLayout = (FlipperLayout) findViewById(R.id.flipper_layout);
        int num_of_pages = 3;
        int i = 0;
        for (i = 0; i < num_of_pages; i++) {
            FlipperView view = new FlipperView(getBaseContext());
           // imagePopup.initiatePopupWithPicasso(P_Image.get(0));
            view.setImageUrl(P_Image.get(i))

                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP) //You can use any ScaleType
                    .setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
                        @Override
                        public void onFlipperClick(FlipperView flipperView) {
                            //Handle View Click here

                           // imagePopup.viewPopup();
                        }
                    });
            flipperLayout.setScrollTimeInSec(10); //setting up scroll time, by default it's 3 seconds
            flipperLayout.getScrollTimeInSec(); //returns the scroll time in sec
            //returns the current position of pager
            flipperLayout.addFlipperView(view);
        }
    }

    public void GetAllRestaurant(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","All");
        startActivity(intent);
    }
    public void SetSlider()
    {
        slider = findViewById(R.id.Slider);

        //delay for testing empty view functionality
        slider.postDelayed(new Runnable() {
            @Override
            public void run() {
                slider.setAdapter(new MainSliderAdapter(getApplicationContext(),P_Image.get(0),P_Image.get(1),P_Image.get(2)));
                slider.setSelectedSlide(0);
            }
        }, 1500);
    }
    public void FastFood(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","FastFood");
        startActivity(intent);
    }
    public void Coffee(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","Coffee");
        startActivity(intent);
    }
    public void NonVeg(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","NonVeg");
        startActivity(intent);
    }
    public void Pizza(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","Pizza");
        startActivity(intent);
    }
    public void Chinese(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","Chinese");
        startActivity(intent);
    }
    public void Veg(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","Veg");
        startActivity(intent);
    }
    public void Barbeque(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","Barbeque");
        startActivity(intent);
    }
    public void Events(View view)
    {
        Intent intent=new Intent(this,ShowRestaurant.class);
        intent.putExtra("Cat","Events");
        startActivity(intent);
    }
    //GetRestauntData-------------------

    public void GetData() {
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Sending Confirmation....");
        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://incrts.tk/hotel_app/GetPostAdData.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();

                Log.i("ResponceClick", response.toString());
                parseResult(response.toString());


                adapter = new ShowAdListAdapter_home(MainActivity.this, feedsList, mRecyclerView);
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
                MyData.put("City", "Pune");//Add the data you'd like to send to the server.
                MyData.put("Category", "All");//Add the data you'd like to send to the server.
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

            for (int i = 0; i < 7; i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();

                try {
                    item.setRestaurantImage1(post.optString("image1"));
                    item.setRestaurantImage2(post.optString("image2"));
                    item.setRestaurantImage3(post.optString("image3"));
                    item.setRestaurantTitle(post.optString("name"));
                    item.setRestaurantAddress(post.optString("address"));
                    item.setRestaurantContact(post.optString("contact"));
                    item.setRestaurantDesc(post.optString("desc"));
                    item.setRestarauntPremium(post.optString("premium"));
                    item.setRestaurantMenu(post.optString("menu"));
                    item.setRestaurantOpenTime(post.optString("opentime"));
                    item.setRestaurantBookTable(post.optString("booktable"));
                    item.setRestaurantGetway(post.optString("paymentgetway"));


//
                    feedsList.add(item);
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

    //------Get Slider Image------------------
    public void GetSliderData() {
//        pd = new ProgressDialog(MainActivity.this);
//        pd.setMessage("Sending Confirmation....");
//        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://incrts.tk/hotel_app/GetAdcrutsBanner.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
             //   pd.dismiss();

                Log.i("ResponceClick", response.toString());
                parseSliderResult(response.toString());
                SetSlider();


                //completeclick.setText(String.valueOf(ClickCountIs));


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
               // pd.dismiss();
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
    private void parseSliderResult(String result) {
        Log.i("InParse", "YesNONONO");

        try {
            //   Log.i("InParseIn", "Yes");

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");

            Log.i("Posts", String.valueOf(posts));
            feedsList = new ArrayList();

            for (int i = 0; i < 3; i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();

                try {

                  //  item.setRestaurantOpenTime(post.optString("opentime"));
//                    if(i==0) {
//                        SliderImage1 = post.optString("image");
//                    }
//                    else if(i==1)
//                    {
//                        SliderImage2 = post.optString("image");
//
//                    }
//                    else
//                    {
//                        SliderImage3 = post.optString("image");
//
//                    }

//
                    P_Image.add(post.optString("image"));
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {

            Intent intent=new Intent(this,Booking_History.class);
            startActivity(intent);
        }
        else if (id == R.id.aboutus) {
            Intent intent=new Intent(this,WebViewActivity.class);
                    intent.putExtra("UrlToLoad","https://incrts.tk/hotel_app/aboutus.html");
                    startActivity(intent);

      } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String Body = "Download App BookIn App Now ";
            String shareBody = Body.replaceAll("<[^>]*>", "");

            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Download BookInn App, Book Your Table Now");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "" + shareBody + " Download Link " + "https://bit.ly/2mRFF1z");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_send) {
//            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "about.autotech@gmail.com"));
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding Help");
//            intent.putExtra(Intent.EXTRA_TEXT, "Write Your problem here");
//            startActivity(intent);

            Intent intent=new Intent(this,WebViewActivity.class);
            intent.putExtra("UrlToLoad","https://goo.gl/forms/qejCjeU09HamNFJ63");
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectSuccess() {

    }

    @Override
    public void onConnectFailure() {

    }

    @Override
    public void onRequestSuccess(TJPlacement tjPlacement) {

    }

    @Override
    public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {

    }

    @Override
    public void onContentReady(TJPlacement tjPlacement) {
        if(tjPlacement.isContentReady()) {
            tjPlacement.showContent();
        }
        else {
            //handle situation where there is no content to show, or it has not yet downloaded.
        }
    }

    @Override
    public void onContentShow(TJPlacement tjPlacement) {

    }

    @Override
    public void onContentDismiss(TJPlacement tjPlacement) {

    }

    @Override
    public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {

    }

    @Override
    public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {

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
