package com.example.marqueetext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class PlannedActivity extends AppCompatActivity {
    ImageButton imageButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned);
        textView=(TextView)findViewById(R.id.textView5);
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("item");
        textView.setText("Planner for Day :"+s);
        imageButton=(ImageButton)findViewById(R.id.img);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(PlannedActivity.this, v);
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
                            startActivity(new Intent(getApplicationContext(),DoneActivity.class));
                            return  true;
                        }
                        else if(item.getItemId()==R.id.c){
                            startActivity(new Intent(getApplicationContext(),OnGoingActivity.class));
                            return  true;
                        }
                        else if (item.getItemId()==R.id.d) {
                            startActivity(new Intent(getApplicationContext(),UndoActivity.class));
                            return  true;
                        }
                        else if (item.getItemId()==R.id.e) {
                            AlertDialog.Builder a=new AlertDialog.Builder(PlannedActivity.this);
                            a.setMessage("Want to exit from current screen !!!").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialogInterface, int i) { finish(); }
                            })
                                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); }});
                            AlertDialog alert= a.create();alert.setTitle("Alert !!!");alert.show();
                            return  true;
                        }
                        return true;
                    }
                });
                popup.show();
        }
    });
    }
}