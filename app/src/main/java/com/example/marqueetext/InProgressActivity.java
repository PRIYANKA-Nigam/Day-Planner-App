package com.example.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class InProgressActivity extends AppCompatActivity {
    EditText editText;
    ListView listView; static ArrayList<String> notes=new ArrayList<>();
    Button button;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress);
        editText = (EditText) findViewById(R.id.editTextTextMultiLine);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.ll);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(arrayAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayAdapter.add(editText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                editText.setText(" ");
            }
        });
    }
}