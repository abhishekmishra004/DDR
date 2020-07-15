package com.android.ddr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Each_Semester extends AppCompatActivity {

    Button first,second,third,fourth,fifth,sixth;
    int choice;
    String Tablename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each__semester);
        Intent intent = getIntent();
        choice = intent.getIntExtra("choice",0);

        first = findViewById(R.id.firstsem);
        second = findViewById(R.id.secondsem);
        third = findViewById(R.id.thirdsem);
        fourth = findViewById(R.id.fourthsem);
        fifth = findViewById(R.id.fifthsem);
        sixth = findViewById(R.id.sixsem);
        Toast.makeText(Each_Semester.this,"choice = "+choice,Toast.LENGTH_LONG).show();

        if(choice == 1) Tablename = "resourctable";
        else if(choice == 2) Tablename = "ebook";
        else if(choice == 3) Tablename = "syllabus_assignment";
        else if(choice == 4) Tablename = "project_table";
        else if(choice == 5) Tablename = "notes_table";

        clickhandler();
    }

    public void clickhandler()
    {
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("table",Tablename);
                bundle.putInt("sem",1);
                Intent intent = new Intent(Each_Semester.this,Sem_result.class);
                intent.putExtra("res",bundle);
                startActivity(intent);
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("table",Tablename);
                bundle.putInt("sem",2);
                Intent intent = new Intent(Each_Semester.this,Sem_result.class);
                intent.putExtra("res",bundle);
                startActivity(intent);
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("table",Tablename);
                bundle.putInt("sem",3);
                Intent intent = new Intent(Each_Semester.this,Sem_result.class);
                intent.putExtra("res",bundle);
                startActivity(intent);
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("table",Tablename);
                bundle.putInt("sem",4);
                Intent intent = new Intent(Each_Semester.this,Sem_result.class);
                intent.putExtra("res",bundle);
                startActivity(intent);
            }
        });
        fifth.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("table",Tablename);
            bundle.putInt("sem",5);
            Intent intent = new Intent(Each_Semester.this,Sem_result.class);
            intent.putExtra("res",bundle);
            startActivity(intent);
        }
        });
        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("table",Tablename);
                bundle.putInt("sem",6);
                Intent intent = new Intent(Each_Semester.this,Sem_result.class);
                intent.putExtra("res",bundle);
                startActivity(intent);
            }
        });

    }
}
