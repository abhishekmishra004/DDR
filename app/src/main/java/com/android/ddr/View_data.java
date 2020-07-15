package com.android.ddr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class View_data extends AppCompatActivity {
    int choice ;
    RecyclerView recyclerView;
    String data="",userid="";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        Intent intent = getIntent();
        choice = intent.getIntExtra("add",-1);
        recyclerView = findViewById(R.id.rcvviewdata);
        user = new User(this);
        userid = user.getuserid();

        if(choice == 0)
        {
            viewdata("notes_table");
        }
        else if(choice == 1)
        {
            viewdata("project_table");
        }
        else if(choice == 2)
        {
            viewdata("resourctable");
        }
        else if(choice == 3)
        {
            viewdata("ebook");
        }
        else if(choice == 4)
        {
            viewdata("syllabus_assignment");
        }
        else
        {
            Toast.makeText(this,"Choice = -1",Toast.LENGTH_SHORT).show();
        }
    }
    public void viewdata(String table){



    }
}
