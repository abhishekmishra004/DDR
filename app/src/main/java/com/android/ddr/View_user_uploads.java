package com.android.ddr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class View_user_uploads extends AppCompatActivity {

    Button addnote,addproject,addassignment,addbook,addresource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_uploads);

        addnote = findViewById(R.id.viewnote);
        addproject = findViewById(R.id.viewproject);
        addassignment = findViewById(R.id.viewassignment);
        addbook = findViewById(R.id.viewbook);
        addresource = findViewById(R.id.viewresource);

        clickhandler();
    }

    public void clickhandler()
    {
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(View_user_uploads.this, Each_Semester.class);
                intent.putExtra("choice",1);
                startActivity(intent);

            }
        });
        addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(View_user_uploads.this, Each_Semester.class);
                intent.putExtra("choice",2);
                startActivity(intent);
            }
        });
        addresource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_user_uploads.this, Each_Semester.class);
                intent.putExtra("choice",3);
                startActivity(intent);
            }
        });
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(View_user_uploads.this, Each_Semester.class);
                intent.putExtra("choice",4);
                startActivity(intent);
            }
        });
        addassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_user_uploads.this, Each_Semester.class);
                intent.putExtra("choice",5);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        View_user_uploads.this.finish();
    }
}
