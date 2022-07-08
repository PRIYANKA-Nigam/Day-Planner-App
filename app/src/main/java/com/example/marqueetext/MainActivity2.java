package com.example.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}