package com.example.marqueetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
private TextView textView,textView2;
FloatingActionButton floatingActionButton,floatingActionButton1,floatingActionButton2,floatingActionButton3,floatingActionButton4;
 Button button,button2;
String[] items;boolean[] checkedItems;
ArrayList<Integer> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text);  textView2=(TextView)findViewById(R.id.textView3);
        textView.setSelected(true);
        button=(Button)findViewById(R.id.button2);
        button2=(Button)findViewById(R.id.button3);
        floatingActionButton =(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton1 =(FloatingActionButton)findViewById(R.id.fab1);
        floatingActionButton2 =(FloatingActionButton)findViewById(R.id.fab2);
        floatingActionButton3 =(FloatingActionButton)findViewById(R.id.fab3);
        floatingActionButton4 =(FloatingActionButton)findViewById(R.id.fab4);
        ObjectAnimator animator=ObjectAnimator.ofFloat(floatingActionButton,"translationX",1000f);
        animator.setDuration(5000);
        animator.start();
        animator=ObjectAnimator.ofFloat(floatingActionButton1,"translationY",1000f);
        animator.setDuration(7000);
        animator.start();
        animator=ObjectAnimator.ofFloat(floatingActionButton2,"translationY",1000f);
        animator.setDuration(5000);
        animator.start();
        animator=ObjectAnimator.ofFloat(floatingActionButton3,"translationX",1000f);
        animator.setDuration(7000);
        animator.start();
        animator=ObjectAnimator.ofFloat(floatingActionButton4,"translationX",1000f);
        animator.setDuration(5000);
        animator.start();

items=getResources().getStringArray(R.array.Shopping_Item);
checkedItems=new boolean[items.length];
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Daily Activities ...");
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!arrayList.contains(position)){
                        arrayList.add(position);
                    }else {
                        arrayList.remove(position);
                    }
                }
            }
        });

        builder.setCancelable(true);
        final String[] finalS = {""};
        builder.setPositiveButton("Todo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                String s="";
                for (int i=0;i<arrayList.size();i++){
                    s=s+items[arrayList.get(i)];
                    if (i!=arrayList.size()-1){
                        s+=" ,";
                    }
                }
                finalS[0] =s;
                final Bundle bundle = new Bundle();
                bundle.putString("list", finalS[0]);
                Intent intent=new Intent(MainActivity.this,TodoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Moving item to Todo Activity", Toast.LENGTH_LONG).show();
                textView2.setText(s);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=0;i<items.length;i++){
                    checkedItems[i]=false;
                    arrayList.clear();
                    textView2.setText("");
                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
});

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Bundle bundle=new Bundle();
        bundle.putInt("1",year);bundle.putInt("2",month+1);bundle.putInt("3",dayOfMonth);
        Intent intent=new Intent(this,DateActivity.class);
        intent.putExtra("bun",bundle);
        startActivity(intent);
    }
    public void ShowPicker(View view){
        final DatePickerDialog datePickerDialog=new DatePickerDialog(this,MainActivity.this,2016,12,01);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "See Existing Dates", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              onDateSet(null,0,-1,0);
            }
        });
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.set_alarm,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.alarm){
            startActivity(new Intent(getApplicationContext(),AlarmActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}