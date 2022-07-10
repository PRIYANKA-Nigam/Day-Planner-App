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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UndoActivity extends AppCompatActivity {
    ListView listView;  ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter; EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undo);
        listView=(ListView)findViewById(R.id.l);
        arrayList=new ArrayList<>();
        editText2=(EditText)findViewById(R.id.ed);
       arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);arrayAdapter.notifyDataSetChanged();
        try {
            Bundle bundle = getIntent().getExtras();
            String s = bundle.getString("item");
            arrayList.add(s);    arrayAdapter.notifyDataSetChanged();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder a=new AlertDialog.Builder(UndoActivity.this);
                a.setMessage("do you want this item to Share to Another App ?....").setCancelable(true).setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        shareItem(arrayList.get(position));
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
                new AlertDialog.Builder(UndoActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are You Sure?")
                        .setMessage("Do You Want to delete this note").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sh = getApplicationContext().getSharedPreferences("undo", Context.MODE_PRIVATE);
                        HashSet<String> set = new HashSet<>(arrayList);
                        sh.edit().putStringSet("progress", set).apply();
                    }
                }).setNegativeButton("No", null).show();
                return true;
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
        loadData();
    }
    private  void shareItem(String s){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,null));
    }
    private void loadData() {
        SharedPreferences sh = getSharedPreferences("undo", MODE_PRIVATE);
        Set<String> set = sh.getStringSet("notes", new HashSet<String>());
        for (String i : set) {
            arrayList.add(i);
            arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.save){
            SharedPreferences sh=getApplicationContext().getSharedPreferences("undo",MODE_PRIVATE);
            HashSet<String> set=new HashSet<>(arrayList);
            sh.edit().putStringSet("notes",set).apply();
            Toast.makeText(this,"Data Saved",Toast.LENGTH_LONG).show();
            return true;
        }
        if (item.getItemId()==R.id.pick) {
            AlertDialog.Builder a=new AlertDialog.Builder(UndoActivity.this);
            a.setMessage("do you want ot close this app!!!").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) { finish(); }
            })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); }});
            AlertDialog alert= a.create();alert.setTitle("Alert !!!");alert.show();
        }
        if (item.getItemId()==R.id.home){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        int id=item.getItemId();
        if(id==R.id.sort1){
            Collections.sort(arrayList);
            arrayAdapter.notifyDataSetChanged();
        }
        else if(id==R.id.sort2){
            Collections.sort(arrayList,Collections.<String>reverseOrder());
            arrayAdapter.notifyDataSetChanged();
        }
        return false;
    }
}