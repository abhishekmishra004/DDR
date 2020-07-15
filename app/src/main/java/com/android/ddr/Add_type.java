package com.android.ddr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Add_type extends AppCompatActivity {

    Button addnote,addproject,addassignment,addbook,addresource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);

        addnote = findViewById(R.id.addnote);
        addproject = findViewById(R.id.addproject);
        addassignment = findViewById(R.id.addassignment);
        addbook = findViewById(R.id.addbook);
        addresource = findViewById(R.id.addresource);

        clickhandler();
    }
    public void clickhandler()
    {
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Add_type.this, Add_Data.class);
                startActivity(intent);

            }
        });
        addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Add_type.this, Add_Project.class);
                startActivity(intent);
            }
        });
        addresource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_type.this, Add_resource.class);
                intent.putExtra("add",0);
                startActivity(intent);
            }
        });
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Add_type.this, Add_book.class);
                startActivity(intent);
            }
        });
        addassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_type.this, Add_resource.class);
                intent.putExtra("add",1);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Add_type.this.finish();
    }
}
