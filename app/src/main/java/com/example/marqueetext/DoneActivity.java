package com.example.marqueetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DoneActivity extends AppCompatActivity {
    EditText editText,editText2;
    ListView listView;
    static ArrayList<String> notes = new ArrayList<>();
    Button button;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        editText = (EditText) findViewById(R.id.editTextTextMultiLine);
        editText2=(EditText)findViewById(R.id.ed);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.ll);
        final HashSet<String> set=new HashSet<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add(editText.getText().toString());
                editText.setText(" ");  set.add(notes.toString());
                arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, notes);
                listView.setAdapter(arrayAdapter);
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder a=new AlertDialog.Builder(DoneActivity.this);
                a.setMessage("do you want this item to Share to Another App ?....").setCancelable(true).setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        shareItem(notes.get(position));
                        finish(); }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); }});
                AlertDialog alert= a.create();alert.setTitle("Share this Item");alert.show();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(DoneActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are You Sure?")
                        .setMessage("Do You Want to delete this note").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sh = getApplicationContext().getSharedPreferences("marquee", Context.MODE_PRIVATE);
                        HashSet<String> set = new HashSet<>(notes);
                        sh.edit().putStringSet("progress", set).apply();
                    }
                }).setNegativeButton("No", null).show();
                return true;
            }
        });
        try {
            Bundle bundle = getIntent().getExtras();
            String s = bundle.getString("item");
            set.add(s); notes=new ArrayList<>(set); arrayAdapter.notifyDataSetChanged();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
//        try {
//            Bundle bundle = getIntent().getExtras();
//            String s = bundle.getString("list");
//            set.add(s); notes=new ArrayList<>(set); arrayAdapter.notifyDataSetChanged();
//        }catch(NullPointerException e){
//            e.printStackTrace();
//        }
        loadData();
    }
//    }
//    //on using sharedpreference key "marqueetext" of OnGoingActivity The data of on-going activity will be stored in this activity too. Hence changed it to "marquee
    private void loadData() {
        SharedPreferences sh=getSharedPreferences("marquee",MODE_PRIVATE);
        Set<String> set=sh.getStringSet("progress",new HashSet<String>());
        for(String i:set){
            notes.add(i);
            arrayAdapter  =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,notes);
            listView.setAdapter(arrayAdapter);
        }
    }
//
 private  void shareItem(String s){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_all,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.save){
            SharedPreferences sh=getApplicationContext().getSharedPreferences("marquee",MODE_PRIVATE);
            HashSet<String> set=new HashSet<>(notes);
            sh.edit().putStringSet("progress",set).apply();
            Toast.makeText(this,"Data Saved",Toast.LENGTH_LONG).show();
            return true;
        }
        if (item.getItemId()==R.id.home){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        int id=item.getItemId();
        if(id==R.id.sort1){
            Collections.sort(notes);
            arrayAdapter.notifyDataSetChanged();
        }
        else if(id==R.id.sort2){
            Collections.sort(notes,Collections.<String>reverseOrder());
            arrayAdapter.notifyDataSetChanged();
        }
        return false;
    }

}