package com.innocrutrs.restaurant_app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import com.google.android.gms.ads.NativeExpressAdView;
//import com.ramotion.foldingcell.FoldingCell;


/**
 * Created by Rahul on 2/4/2016.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

   // NativeExpressAdView adView;
//    NativeExpressAdView adView ;
//    protected FoldingCell fc;
    protected TextView RestaurantTitle,RestaurantAddress,R_Name,R_SeatBook,R_Time,R_Status;
    ImageView RestaurantImage,Delete_post_img,NewTag;
    RelativeLayout Res_layout;
    public CustomViewHolder(View view) {
        super(view);

        this.RestaurantTitle=(TextView)view.findViewById(R.id.res_title);
        this.RestaurantAddress=(TextView)view.findViewById(R.id.res_address);
        this.RestaurantImage=(ImageView)view.findViewById(R.id.res_image);
        this.Res_layout=(RelativeLayout)view.findViewById(R.id.res_layout);
       // this.SellRent=(TextView) view.findViewById(R.id.P_SellRent);
        //this.Delete_post_img=(ImageView) view.findViewById(R.id.delete_post_img);
        this.NewTag=(ImageView) view.findViewById(R.id.NewTag);

        this.R_Name=(TextView)view.findViewById(R.id.R_Name);
        this.R_SeatBook=(TextView)view.findViewById(R.id.R_SeatBook);
        this.R_Time=(TextView)view.findViewById(R.id.R_BookTime);
        this.R_Status=(TextView)view.findViewById(R.id.R_Status);


    }
}