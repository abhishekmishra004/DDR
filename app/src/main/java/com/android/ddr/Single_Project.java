package com.android.ddr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Single_Project extends AppCompatActivity {

    TextView title,sem,tech,desc,type,pdfname;
    String Title,Sem,Tech,Type,Desc,Pdfname;
    Button download;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__project);

        title = findViewById(R.id.viewprotxttitle);
        sem = findViewById(R.id.viewprotxtsem);
        tech= findViewById(R.id.viewprotxttech);
        desc = findViewById(R.id.viewprotxtdesc);
        type = findViewById(R.id.viewprotxttype);
        pdfname = findViewById(R.id.viewtxtpdf);
        download = findViewById(R.id.viewbuttondownload);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("project");
        Title = bundle.getString("title");
        Sem = bundle.getString("sem");
        Tech = bundle.getString("tech");
        Type = bundle.getString("type");
        Desc = bundle.getString("desc");
        Pdfname = bundle.getString("name");
        progressDialog.setTitle("Downloading");
        progressDialog.setProgressStyle(0);

        title.setText(Title);
        sem.setText(Sem);
        tech.setText(Tech);
        type.setText(Type);
        desc.setText(Desc);
        pdfname.setText(Pdfname);

        clickhandler();

    }

    public void clickhandler()
    {
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                final File rootPath = new File(Environment.getExternalStorageDirectory(), "DDR");
                if (!rootPath.exists()) {
                    rootPath.mkdirs();
                }

                final StorageReference storageReference;
                storageReference = FirebaseStorage.getInstance().getReference("Uploads").child("Projects").child(Pdfname);
                try {
                    if (!rootPath.exists()) {
                        rootPath.mkdirs();
                    }
                    final File localFile = new File(rootPath,Pdfname);
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(Single_Project.this, "Downloaded Successfully"+localFile.getPath(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            // Local temp file has been created
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Single_Project.this, "Some Error Happens"+exception.getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
