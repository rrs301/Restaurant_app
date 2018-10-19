package com.innocrutrs.restaurant_app;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullMenuView extends AppCompatActivity {

    String R_Menu;
   // PdfView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_menu_view);
        Intent intent=getIntent();
        R_Menu=intent.getStringExtra("R_Menu");
     //   ImageView Res_Menu=(ImageView)findViewById(R.id.MenuImage);
//        Picasso.with(this).load(R_Menu)
//                .error(R.drawable.image_placeholder)
//                .placeholder(R.drawable.image_placeholder)
//                .into(Res_Menu);
        WebView webviewer = new WebView(this);
        webviewer.getSettings().setJavaScriptEnabled(true);
       // webviewer.getSettings().setPl
        setContentView(webviewer);

        webviewer.loadUrl("https://docs.google.com/viewer?url="+R_Menu);
        setContentView(webviewer);


    }

    public void Download_Menu(View view)
    {
        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(R_Menu);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        long refid = downloadManager.enqueue(request);
    }
}
