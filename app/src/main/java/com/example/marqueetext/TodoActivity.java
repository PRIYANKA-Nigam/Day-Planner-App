package com.example.marqueetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TodoActivity extends AppCompatActivity {
EditText editText;
  ListView listView; static ArrayList<String> notes=new ArrayList<>();
    Button button;static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        editText=(EditText)findViewById(R.id.editTextTextMultiLine);
        button=(Button)findViewById(R.id.button);
        listView=(ListView)findViewById(R.id.ll);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                notes.add(editText.getText().toString());    editText.setText(" ");
                 arrayAdapter  =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,notes);
                 listView.setAdapter(arrayAdapter);

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
        int t=0;
        while(notes.size()>0){
            notes.remove(notes.get(t)); arrayAdapter  =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,notes);
        }
        listView.setAdapter(arrayAdapter);
loadData();
    }

    private void loadData() {

        SharedPreferences sh=getSharedPreferences("com.example.marqueetext",MODE_PRIVATE);
  Set<String> set=sh.getStringSet("notes",new HashSet<String>());
  for(String i:set){
      notes.add(i);
      arrayAdapter  =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,notes);
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
        return false;
    }
}