package com.innocrutrs.restaurant_app;

import android.content.Context;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    private  Context context;
    String img1,img2,img3;
    public MainSliderAdapter(Context context,String img1,String img2,String img3)
    {
        this.context=context;
        this.img1=img1;
        this.img2=img2;
        this.img3=img3;
    }
    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide(img1);
                break;
            case 1:
                viewHolder.bindImageSlide(img2);
                break;
            case 2:
                viewHolder.bindImageSlide(img3);
                break;
        }
    }

}
