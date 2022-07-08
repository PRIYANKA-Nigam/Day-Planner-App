package com.example.marqueetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DateActivity extends AppCompatActivity {
    ListView listView;  ArrayList<String> arrayList;
  ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        listView=(ListView)findViewById(R.id.ll);
        final Intent intent=getIntent(); Bundle bundle=intent.getBundleExtra("bun");
        int n1=bundle.getInt("1",0);
        int n2=bundle.getInt("2",0);
        int n3=bundle.getInt("3",0);
        //  int a=intent.getIntExtra("1",0); int b=intent.getIntExtra("2",0); int c=intent.getIntExtra("3",0);
        String s="";
        s=n1+"/"+n2+"/"+n3;
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add(s);
        arrayList=new ArrayList<>(hashSet);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = arrayList.get(position);
                final Bundle bundle = new Bundle();
                bundle.putString("item", s);
                Intent intent=new Intent(DateActivity.this,PlannedActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(DateActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are You Sure?")
                        .setMessage("Do You Want to delete this note").setPositiveButton("Yes", new DialogInterface.OnClickListener() {@Override
                public void onClick(DialogInterface dialog, int which) {
                    arrayList.remove(position);adapter.notifyDataSetChanged();
                    SharedPreferences sh=getApplicationContext().getSharedPreferences("com.example.marqueetext", Context.MODE_PRIVATE);
                    HashSet<String> set=new HashSet<>(arrayList);sh.edit().putStringSet("notes",set).apply();
                }
                }).setNegativeButton("No",null).show();
                return true;
            }
        });
        loadData();

    }

    private void loadData() {
        SharedPreferences sh = getSharedPreferences("com.example.datepickerdialog", MODE_PRIVATE);
        Set<String> set = sh.getStringSet("notes", new HashSet<String>());
        for (String i : set) {
            arrayList.add(i);
            adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_all,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.save){
            SharedPreferences sh=getApplicationContext().getSharedPreferences("com.example.datepickerdialog",MODE_PRIVATE);
            HashSet<String> set=new HashSet<>(arrayList);
            sh.edit().putStringSet("notes",set).apply();
            Toast.makeText(this,"Data Saved",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}