package com.innocrutrs.restaurant_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GetUserName extends AppCompatActivity {

    String Username;
    TextView UsernameText;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_name);
        getSupportActionBar().hide();
        pref=getApplicationContext().getSharedPreferences("hotelapp",MODE_PRIVATE);
        editor=pref.edit();
        if(pref.getInt("Login",0)==1)
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        UsernameText=(TextView)findViewById(R.id.username);
    }

    public void NextBtn(View view)
    {
        Username=UsernameText.getText().toString();
        if(Username.length()<=3)
        {
            Toast.makeText(this,"Please Enter Name More Than 3 Words",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=new Intent(this,Facebook_Login.class);
            intent.putExtra("Username",Username);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {

            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

    }
}
