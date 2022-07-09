package com.example.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {
ImageView i1,i2,i3,i4,i5;
TextView t; Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        t=(TextView)findViewById(R.id.tt);
        i1=(ImageView)findViewById(R.id.imageView);
        i2=(ImageView)findViewById(R.id.imageView2);
        i3=(ImageView)findViewById(R.id.imageView3);
        i4=(ImageView)findViewById(R.id.imageView4);
        i5=(ImageView)findViewById(R.id.imageView5);
        animation   = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        t.startAnimation(animation);
        i1.startAnimation(animation);
        i2.startAnimation(animation);
        i3.startAnimation(animation);
        i4.startAnimation(animation);
        i5.startAnimation(animation);
        final Intent intent=new Intent(this,MainActivity.class);
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}