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
public class ShowAdListAdapter_BookSeat extends RecyclerView.Adapter<CustomViewHolder> {
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


    public ShowAdListAdapter_BookSeat(Context context, List<FeedItem> feedItemList, RecyclerView rv) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.recyclerView=rv;
    }
 //   int conditionn=HomePage.condition;
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       // MobileAds.initialize((mContext), String.valueOf(R.string.Admob_App_Id));


            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_history_layout, null);
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

//            Picasso.with(mContext).load(feedItem.getRestaurantImage1())
//            .error(R.drawable.image_placeholder)
//            .placeholder(R.drawable.image_placeholder)
//            .into(customViewHolder.RestaurantImage);

            customViewHolder.R_Name.setText("Restaurant Name: "+feedItem.getR_Name());
            customViewHolder.R_Status.setText("Status: "+feedItem.getR_Status());
            customViewHolder.R_Time.setText("Seat Booking Time: "+feedItem.getR_Time());
            customViewHolder.R_SeatBook.setText("No. Of Seat Booked:"+feedItem.getR_SeatBook());




        } catch (Exception e) {
            Log.i("Yes", e.getMessage());
        }
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


