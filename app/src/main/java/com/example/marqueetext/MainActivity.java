package com.example.marqueetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
private TextView textView;
FloatingActionButton floatingActionButton,floatingActionButton1,floatingActionButton2;
ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text);
        textView.setSelected(true);
        floatingActionButton =(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton1 =(FloatingActionButton)findViewById(R.id.fab1);
        floatingActionButton2 =(FloatingActionButton)findViewById(R.id.fab2);
        imageButton=(ImageButton)findViewById(R.id.img);
        ObjectAnimator animator=ObjectAnimator.ofFloat(floatingActionButton,"translationX",1000f);
        animator.setDuration(5000);
        animator.start();
        animator=ObjectAnimator.ofFloat(floatingActionButton1,"translationY",1000f);
        animator.setDuration(7000);
        animator.start();
        animator=ObjectAnimator.ofFloat(floatingActionButton2,"translationY",1000f);
        animator.setDuration(5000);
        animator.start();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.a){
                            startActivity(new Intent(getApplicationContext(),TodoActivity.class));
                            return  true;
                        }
                        else if(item.getItemId()==R.id.b){
                            startActivity(new Intent(getApplicationContext(),InProgressActivity.class));
                            return  true;
                        }
                        else if (item.getItemId()==R.id.d) {
                            AlertDialog.Builder a=new AlertDialog.Builder(MainActivity.this);
                            a.setMessage("do you want ot close this app!!!").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialogInterface, int i) { finish(); }
                            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); }});
                            AlertDialog alert= a.create();alert.setTitle("Alert !!!");alert.show();
                            return true; }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

}