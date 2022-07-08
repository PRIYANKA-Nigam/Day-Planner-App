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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IllegalFormatCodePointException;
import java.util.Set;

public class TodoActivity extends AppCompatActivity {
    EditText editText;
    ListView listView; static ArrayList<String> notes=new ArrayList<>();
    Button button;static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        editText = (EditText) findViewById(R.id.editTextTextMultiLine);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.ll);
        final HashSet<String> set=new HashSet<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add(editText.getText().toString());
                editText.setText(" ");
//                HashSet<String> set=(HashSet<String>)sh.getStringSet("notes",null);
//                if (set==null){ notes.add("Example Note");
//                }else { notes=new ArrayList<>(set); }
                arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, notes);
                listView.setAdapter(arrayAdapter);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder a=new AlertDialog.Builder(TodoActivity.this);
                a.setMessage("do you want this item to OnGoing Activity or Share to Another App ?....").setCancelable(true).setPositiveButton("OnGoing Task", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        String s = notes.get(position);
                        final Bundle bundle = new Bundle();
                        bundle.putString("item", s);
                        Intent intent=new Intent(TodoActivity.this,OnGoingActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Moving item to In-Progress Activity", Toast.LENGTH_LONG).show();
                        finish(); }
                }).setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shareItem(notes.get(position));
                    }
                })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); }});
                AlertDialog alert= a.create();alert.setTitle("Move or Share this Item");alert.show();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(TodoActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are You Sure?")
                        .setMessage("Do You Want to delete this note").setPositiveButton("Yes", new DialogInterface.OnClickListener() {@Override
                public void onClick(DialogInterface dialog, int which) {
                    notes.remove(position);arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sh=getApplicationContext().getSharedPreferences("com.example.marqueetext", Context.MODE_PRIVATE);
                    HashSet<String> set=new HashSet<>(notes);sh.edit().putStringSet("notes",set).apply();
                }
                }).setNegativeButton("No",null).show();
                return true;
            }
        });

        while(notes.size()>0){
            notes.remove(notes.get(0)); arrayAdapter  =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,notes);
        }
        listView.setAdapter(arrayAdapter);
        try {
            Bundle bundle = getIntent().getExtras();
            String s = bundle.getString("list");
            set.add(s); notes=new ArrayList<>(set);
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
            listView.setAdapter(arrayAdapter);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        loadData();

    }
    private  void shareItem(String s){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,null));
    }
    private void loadData() {
        SharedPreferences sh = getSharedPreferences("com.example.marqueetext", MODE_PRIVATE);
        Set<String> set = sh.getStringSet("notes", new HashSet<String>());
        for (String i : set) {
            notes.add(i);
            arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, notes);
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
            SharedPreferences sh=getApplicationContext().getSharedPreferences("com.example.marqueetext",MODE_PRIVATE);
            HashSet<String> set=new HashSet<>(notes);
            sh.edit().putStringSet("notes",set).apply();
            Toast.makeText(this,"Data Saved",Toast.LENGTH_LONG).show();
            return true;
        }
        if (item.getItemId()==R.id.pick){  String s="";
//            for(int i=0;i<notes.size();i++) {
//                 s = notes.get(i) + ":";
//            }
            Intent intent=new Intent(TodoActivity.this,MainActivity2.class);
            intent.putStringArrayListExtra("send",notes); startActivity(intent);
            Toast.makeText(getApplicationContext(),"Pick Task which you are unable to do",Toast.LENGTH_LONG).show();
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