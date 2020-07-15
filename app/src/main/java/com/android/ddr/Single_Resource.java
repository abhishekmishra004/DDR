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

public class Single_Resource extends AppCompatActivity {

    TextView title,sem,sub,feat,desc,pdfname;
    String Title,Sem,Sub,Feat,Desc,Pdfname;
    Button download;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__resource);

        title = findViewById(R.id.resviewtxttitle);
        sem = findViewById(R.id.resviewtxtsem);
        sub= findViewById(R.id.resviewtxtsubject);
        desc = findViewById(R.id.resviewtxtdesc);
        pdfname = findViewById(R.id.resviewtxtpdfname);
        download = findViewById(R.id.resbuttondownload);
        feat = findViewById(R.id.resviewfeaturess);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("res");
        Title = bundle.getString("title");
        Sem = bundle.getString("sem");
        Sub = bundle.getString("sub");
        Desc = bundle.getString("desc");
        Feat = bundle.getString("feat","");
        Pdfname = bundle.getString("name");
        progressDialog.setTitle("Downloading");
        progressDialog.setProgressStyle(0);

        title.setText(Title);
        sem.setText(Sem);
        sub.setText(Sub);
        desc.setText(Desc);
        feat.setText(Feat);
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

                            Toast.makeText(Single_Resource.this, "Downloaded Successfully"+localFile.getPath(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            // Local temp file has been created
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Single_Resource.this, "Some Error Happens"+exception.getMessage(), Toast.LENGTH_LONG).show();
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
