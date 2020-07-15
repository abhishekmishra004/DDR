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
import java.io.IOException;

public class SingleNote extends AppCompatActivity {

    TextView title,sem,sub,desc,pdfname;
    String Title,Sem,Sub,Desc,Pdfname;
    Button download;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);
        title = findViewById(R.id.viewtxttitle);
        sem = findViewById(R.id.viewtxtsem);
        sub= findViewById(R.id.viewtxtsubject);
        desc = findViewById(R.id.viewtxtdesc);
        pdfname = findViewById(R.id.viewtxtpdfname);
        download = findViewById(R.id.buttondownload);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("note");
        Title = bundle.getString("title");
        Sem = bundle.getString("sem");
        Sub = bundle.getString("sub");
        Desc = bundle.getString("desc");
        Pdfname = bundle.getString("name");
        progressDialog.setTitle("Downloading");
        progressDialog.setProgressStyle(0);

        title.setText(Title);
        sem.setText(Sem);
        sub.setText(Sub);
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
                storageReference = FirebaseStorage.getInstance().getReference("Uploads").child("Notes").child(Pdfname);
                try {
                    if (!rootPath.exists()) {
                        rootPath.mkdirs();
                    }
                    final File localFile = new File(rootPath,Pdfname);
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(SingleNote.this, "Downloaded Successfully"+localFile.getPath(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            // Local temp file has been created
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(SingleNote.this, "Some Error Happens"+exception.getMessage(), Toast.LENGTH_LONG).show();
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
