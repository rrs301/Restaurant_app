package com.innocrutrs.restaurant_app;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity {

    String NewsUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

//        ImageView imageView=(ImageView)findViewById(R.id.headimage);
//        ImageView imageView1=(ImageView)findViewById(R.id.headimage1);
//        ImageView imageView2=(ImageView)findViewById(R.id.headimage2);


        Intent intent=getIntent();
         NewsUrl=intent.getStringExtra("UrlToLoad").trim();
        Log.i("WebView",NewsUrl);
//        if(NewsUrl.compareTo("http://music.uodoo.com/")==0)
//        {
//            imageView.setVisibility(View.GONE);
//        }
//        else if(NewsUrl.compareTo("http://uccricket.ucweb.com/")==0)
//        {
//            imageView.setVisibility(View.VISIBLE);
//        }
//        else if(NewsUrl.compareTo("https://www.railyatri.in")==0)
//        {
//            imageView1.setVisibility(View.VISIBLE);
//        }
//        else if(NewsUrl.compareTo("http://www.ucnews.in/channel/101")==0)
//        {
//
//            imageView2.setVisibility(View.VISIBLE);
//
//        }

            Toast.makeText(this, "Please Wait...", Toast.LENGTH_LONG).show();
        WebView webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(NewsUrl);

        webView.setOnKeyListener(new View.OnKeyListener() {
                                     @Override
                                     public boolean onKey(View v, int keyCode, KeyEvent event) {
                                         if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                             WebView webView = (WebView) v;

                                             switch (keyCode) {
                                                 case KeyEvent.KEYCODE_BACK:
                                                     if (webView.canGoBack()) {
                                                         webView.goBack();
                                                         return true;
                                                     }
                                                     break;
                                             }
                                         }

                                         return false;
                                     }
                                 }
        );

    }

    public void Download_Menu(View view)
    {
        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(NewsUrl);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        long refid = downloadManager.enqueue(request);
    }




}
