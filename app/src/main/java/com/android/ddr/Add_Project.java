package com.android.ddr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Add_Project extends AppCompatActivity {

    Spinner projecttype,projectsem,projecttechnology;
    String[] Sem = new String[]{"Select Semester","1st","2nd","3rd","4th","5th","6th"};
    String[] ptype = new String[]{"Select Type","Major","Minor","Individual"};
    String[] ptechno = new String[]{"Select Technology","Machine Learning","Artificial Intelligence","Deep Learning",
            "Robotics","Virtual Reality","Data Science/Data Analytics","Web Development","Android Development","Other"};
    EditText title,desc;
    Button selectpdf,submit;
    public int PDF_REQ_CODE = 1;
    String filename="",userid="";
    Uri uri;
    TextView pdfname;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__project);
        projecttype = findViewById(R.id.projectspinnertype);
        projectsem = findViewById(R.id.projectSpinnerSemester);
        projecttechnology = findViewById(R.id.projectSpinnertechnology);
        title = findViewById(R.id.edittxtprojecttitle);
        desc = findViewById(R.id.edittxtprojectdescription);
        selectpdf = findViewById(R.id.browse_project_button);
        submit = findViewById(R.id.submit_project_button);
        pdfname = findViewById(R.id.txtpdfprojectname);
        final User user =new User(Add_Project.this);
        userid = user.getuserid();
        progressDialog = new ProgressDialog(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ptype){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        projecttype.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Sem){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        projectsem.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ptechno){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        projecttechnology.setAdapter(adapter2);


        clickhandler();
    }

    public void clickhandler()
    {
        selectpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri!=null){
                    PdfUploadFunction();
                }
                else{
                    Toast.makeText(Add_Project.this,"Please Select a file",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            uri2filename();
            pdfname.setText(filename);
            selectpdf.setText("PDF is Selected");
        }
        else {
            Toast.makeText(Add_Project.this,"Please Select a file",Toast.LENGTH_SHORT).show();
        }
    }

    private void uri2filename() {

        String scheme = uri.getScheme();

        if (scheme.equals("file")) {
            filename = uri.getLastPathSegment();
        }
        else if (scheme.equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }
    }

    public void PdfUploadFunction() {

        progressDialog.setProgressStyle(0);
        progressDialog.setTitle("Uploading File..");

        final StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        if(uri!=null) {
            progressDialog.show();
            storageReference.child("Projects").child(filename).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    insertintodatabase();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Add_Project.this, "Some Error Occurred while uploading", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            });

        }
        else
        {
            Toast.makeText(Add_Project.this, "uri null", Toast.LENGTH_SHORT).show();
        }
    }
    public void insertintodatabase()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constraints.ADD_PROJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Add_Project.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_Project.this,Home.class);
                            Add_Project.this.finish();
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Add_Project.this,"Timeout "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",userid);
                params.put("project_type",projecttype.getSelectedItem().toString());
                params.put("projectSem",projectsem.getSelectedItem().toString());
                params.put("title",title.getText().toString());
                params.put("description",desc.getText().toString());
                params.put("technology",projecttechnology.getSelectedItem().toString());
                params.put("name",filename);
                params.put("type","application/pdf");
                return params;
            }
        };
        RequestHandler.getInstance(Add_Project.this).addToRequestQueue(stringRequest);
        progressDialog.hide();
    }
}
