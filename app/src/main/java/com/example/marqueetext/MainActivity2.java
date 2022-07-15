package com.example.marqueetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity2 extends AppCompatActivity {
ListView listView;  ArrayList<String> arrayList;
ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=(ListView)findViewById(R.id.l);
        Intent intent=getIntent(); arrayList=new ArrayList<>();
        arrayList=intent.getStringArrayListExtra("send");
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter); arrayAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = arrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("item", s);
                Intent intent = new Intent(getApplicationContext(), UndoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Moving item to In -Unable To Do Activity", Toast.LENGTH_LONG).show();
            }
        });

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
        if (item.getItemId()==R.id.home){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        if (item.getItemId()==R.id.note){
            startActivity(new Intent(getApplicationContext(),Note1.class));
        }
        if (item.getItemId()==R.id.event){
            startActivity(new Intent(getApplicationContext(),EventActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}