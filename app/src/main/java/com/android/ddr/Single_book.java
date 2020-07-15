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

public class Single_book extends AppCompatActivity {

    TextView title,author,sem,sub,desc,pdfname;
    Button download;
    String Title,Author,Sem,Sub,Pdfname,Desc;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book);

        title = findViewById(R.id.viewbooktxttitle);
        sem = findViewById(R.id.viewbooktxtsem);
        sub= findViewById(R.id.viewbooktxtsub);
        author = findViewById(R.id.viewbooktxtauthor);
        desc = findViewById(R.id.viewbooktxtdesc);
        pdfname = findViewById(R.id.viewbooktxtpdf);
        download = findViewById(R.id.viewbookbuttondownload);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(0);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("book");
        Title = bundle.getString("title");
        Sem = bundle.getString("sem");
        Sub = bundle.getString("sub");
        Desc = bundle.getString("desc");
        Pdfname = bundle.getString("pdf");
        Author = bundle.getString("aut");
        progressDialog.setTitle("Downloading");
        progressDialog.setProgressStyle(0);

        title.setText(Title);
        sem.setText(Sem);
        sub.setText(Sub);
        author.setText(Author);
        desc.setText(Desc);
        pdfname.setText(Pdfname);

        clickhandler();
    }
    public  void clickhandler()
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
                storageReference = FirebaseStorage.getInstance().getReference("Uploads").child("Books").child(Pdfname);
                try {
                    if (!rootPath.exists()) {
                        rootPath.mkdirs();
                    }
                    final File localFile = new File(rootPath,Pdfname);
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(Single_book.this, "Downloaded Successfully"+localFile.getPath(), Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            // Local temp file has been created
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Single_book.this, "Some Error Happens"+exception.getMessage(), Toast.LENGTH_LONG).show();
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
