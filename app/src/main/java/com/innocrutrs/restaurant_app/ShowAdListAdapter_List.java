package com.innocrutrs.restaurant_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rahul on 1/14/2017.
 */
public class ShowAdListAdapter_List extends RecyclerView.Adapter<CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    SQLiteDatabase eventsDB;
    RecyclerView recyclerView;
    String Ad;
    String currentDayIs;
    ProgressDialog pd;
    String UserMobileNumber;
    SharedPreferences pref;
  //  InterstitialAd mAd;
    SharedPreferences.Editor editor;
    //AdView adView1;
    int layout=1;


    public ShowAdListAdapter_List(Context context, List<FeedItem> feedItemList, RecyclerView rv) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.recyclerView=rv;
    }
 //   int conditionn=HomePage.condition;
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       // MobileAds.initialize((mContext), String.valueOf(R.string.Admob_App_Id));


            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_list_layout, null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);

            return viewHolder;


    }


    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final FeedItem feedItem = feedItemList.get(i);
       // final ImagePopup imagePopup = new ImagePopup(mContext);

        pref = mContext.getSharedPreferences("campusstore", mContext.MODE_PRIVATE);
        editor = pref.edit();
        UserMobileNumber=pref.getString("Email",null);



        try {

            Picasso.with(mContext).load(feedItem.getRestaurantImage1())
            .error(R.drawable.image_placeholder)
            .placeholder(R.drawable.image_placeholder)
            .into(customViewHolder.RestaurantImage);

            customViewHolder.RestaurantTitle.setText(feedItem.getRestaurantTitle());
            customViewHolder.RestaurantAddress.setText(feedItem.getRestaurantAddress());
//            customViewHolder.ProdcutSp.setText(feedItem.getProductSp());
//            customViewHolder.SellRent.setText(feedItem.getProductOldNew());

            if(feedItem.getRestarauntPremium().compareTo("Yes")==0)
            {
                customViewHolder.NewTag.setVisibility(View.VISIBLE);
            }
//            imagePopup.setWindowHeight(1200); // Optional
//            imagePopup.setWindowWidth(1200); // Optional
//            imagePopup.setBackgroundColor(Color.BLACK);  // Optional
//            imagePopup.setFullScreen(true);
//            imagePopup.initiatePopupWithPicasso(feedItem.getInspiImageUrl());
//            Typeface type = Typeface.createFromAsset(mContext.getAssets(),"fonts/ABeeZee-Regular.ttf");
//            customViewHolder.VideoTitle.setTypeface(type);

//            customViewHolder.InspiImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   int QuotesCount = 0;
//
//                   Intent intent=new Intent(mContext,Play_Video.class);
//                    intent.putExtra("VideoLink",feedItem.getVideoUrl());
//                    intent.putExtra("VideoTitle",feedItem.getVideoTitle());
//                    intent.putExtra("VideoViews",feedItem.getVideoViews());
//                    Log.i("VideoCLick","Click");
//                    mContext.startActivity(intent);
//
//                }
//            });
            customViewHolder.Res_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,Restaurant_Detail.class);
                    intent.putExtra("R_Title",feedItem.getRestaurantTitle());
                    intent.putExtra("R_Desc",feedItem.getRestaurantDesc());
                    intent.putExtra("R_Contact",feedItem.getRestaurantContact());
                    intent.putExtra("R_Address",feedItem.getRestaurantAddress());
                    intent.putExtra("R_Menu",feedItem.getRestaurantMenu());
              //      Log.i("Menu",feedItem.getRestaurantMenu());
                    intent.putExtra("R_Image1",feedItem.getRestaurantImage1());
                    intent.putExtra("R_Image2",feedItem.getRestaurantImage2());
                    intent.putExtra("R_Image3",feedItem.getRestaurantImage3());
                    intent.putExtra("R_OpenTime",feedItem.getRestaurantOpenTime());
                    intent.putExtra("R_BookTable",feedItem.getRestaurantBookTable());
                    intent.putExtra("R_Getway",feedItem.getRestaurantGetway());


                    //  intent.putExtra("ProductOldNew",feedItem.getProductOldNew());

                    Log.i("VideoCLick--","Click");
                    mContext.startActivity(intent);
                }
            });
            //Log.i("Adv:",Ad);

            //Setting text view title
//            if(feedItem.getAdsSellRent().compareTo("Sell")==0)
//            {
//                customViewHolder.AdsRentSell.setImageResource(R.drawable.sell_tag);
//            }
//            else
//            {
//                customViewHolder.AdsRentSell.setImageResource(R.drawable.rent_tag);
//            }


        } catch (Exception e) {
            Log.i("Yes", e.getMessage());
        }
    }

//    public void ShowFullAd()
//    {
//        mAd=new InterstitialAd(mContext);
//
//        mAd.setAdUnitId("ca-app-pub-7919542238309223/7609061780");
//        mAd.loadAd(new AdRequest.Builder().addTestDevice("E382009FE0B2B673EE51746088ACC0CD").build());
//        if (mAd.isLoaded()) {
//            mAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
//        mAd.setAdListener(new com.google.android.gms.ads.AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                if (mAd.isLoaded()) {
//                    mAd.show();
//                } else {
//                    Log.d("TAG", "The interstitial wasn't loaded yet.");
//                }
//                Log.i("Ads", "onAdLoaded");
//            }
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                Log.i("Ads", "onAdFailedToLoad");
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//                Log.i("Ads", "onAdOpened");
//            }
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the ad is displayed.
//
//
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//                int JokeCount=pref.getInt("JokeCount",0);
//
//                if(JokeCount==10)
//                {
//                  //  UpdateUserBalance("4","Joke");
//                    Toast.makeText(mContext,"Please Wait for 20 Sec", Toast.LENGTH_SHORT).show();
//
//                    editor.putInt("JokeCount",0).commit();
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Do something after 20 seconds
//                            UpdateUserBalance("4","Joke");
//                            //  handler.postDelayed(this, 2000);
//                        }
//                    }, 20000);  //the time is in miliseconds
//
//                }
//                else {
//
//                }
//            }
//
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the interstitial ad is closed.
//                Log.i("Ads", "onAdClosed");
//            }
//        });
//
//    }


    public void downloadFile(String uRl) throws IOException {
//        URL url = null;
//        try {
//            url = new URL(uRl);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        InputStream input = null;
//            input = url.openStream();
//
//        try {
//            String root = Environment.getExternalStorageDirectory().toString();
//            File myDir = new File(root + "/Download");
//            myDir.mkdirs();
//            OutputStream output = new FileOutputStream(myDir, Boolean.parseBoolean("myImage.png"));
//            try {
//                byte[] buffer = new byte[1024];
//                int bytesRead = 0;
//                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
//                    output.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                output.close();
//            }
//        }
//
//        finally {
//            input.close();
//        }

        Log.i("InDoenloadinfle","Yes");
        Picasso.with(mContext)
                .load(uRl)
                .into(new Target() {
                          @Override
                          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                              try {
                                  String root = Environment.getExternalStorageDirectory().toString();
                                  File myDir = new File(root + "/Download");

                                  if (!myDir.exists()) {
                                      myDir.mkdirs();
                                  }

                                  String name = new Date().toString() + ".jpg";
                                  myDir = new File(myDir, name);
                                  FileOutputStream out = new FileOutputStream(myDir);
                                  bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                                  out.flush();
                                  out.close();
                              } catch(Exception e){
                                  // some action
                              }
                          }

                          @Override
                          public void onBitmapFailed(Drawable errorDrawable) {
                          }

                          @Override
                          public void onPrepareLoad(Drawable placeHolderDrawable) {
                          }
                      }
                );


    }
    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public void filter(String text) {
        feedItemList = ShowRestaurant.feedsList;
        if (text.length() > 0) {
            List<FeedItem> filterd = new ArrayList<FeedItem>();
            for (int i = 0; i < feedItemList.size(); i++) {
                if (feedItemList.get(i).getRestaurantAddress().toLowerCase().startsWith(text.toLowerCase())) {
                    filterd.add(feedItemList.get(i));
                }
            }
            feedItemList = filterd;
            filterd = null;


        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}


