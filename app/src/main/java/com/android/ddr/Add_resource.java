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
import android.widget.AdapterView;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_resource extends AppCompatActivity {

    Spinner semspinner,subspinner;
    String[] Sem = new String[]{"Select Semester","1st","2nd","3rd","4th","5th","6th"};
    Button browsepdf,submit;
    public int PDF_REQ_CODE = 1;
    Uri uri;
    String Fileurl="",userid="";
    TextView pdfname;
    String filename="",child="",url="";
    ProgressDialog progressDialog;
    EditText title,description,feature;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);



        browsepdf = findViewById(R.id.resbrowse_button);
        submit = findViewById(R.id.ressubmit_button);
        pdfname = findViewById(R.id.restxtpdfname);
        progressDialog = new ProgressDialog(this);
        final User user =new User(Add_resource.this);
        userid = user.getuserid();
        title = findViewById(R.id.resedittxttitle);
        description = findViewById(R.id.resedittxtdescription);
        feature = findViewById(R.id.resedittxtfeature);
        Intent intent = getIntent();
        int choice = intent.getIntExtra("add",-1);
        if(choice == 0){child = "res"; url = Constraints.ADD_RES;}
        else if(choice == 1){ child = "ass";url = Constraints.ADD_ASS;}
        else{
            Toast.makeText(this,"Choice invalid",Toast.LENGTH_LONG).show();
        }


        semspinner = findViewById(R.id.resSpinnerSemester);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Sem){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        semspinner.setAdapter(adapter1);

        subspinner = findViewById(R.id.resSpinnerSubject);

        clickhandler();
        
    }


    public  void clickhandler()
    {
        semspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    String[] subject = new String[]{"Problem Solving and Programming Using C","Computer Organization and Architecture","Business Information System",
                            "Computer Oriented Numerical Methods ","Engineering Economics","Business Communication"};
                    ArrayAdapter<String> adapt = new ArrayAdapter<>(Add_resource.this,android.R.layout.simple_spinner_dropdown_item,subject);
                    subspinner.setAdapter(adapt);
                }
                else if(position == 2){
                    String[] subject = new String[]{"Data Structure using C ","Object Oriented Programming using C ++","Operating systems",
                            "Principles and Practice of Management","Green IT","Mathematical Computing"};
                    ArrayAdapter<String> adapt = new ArrayAdapter<>(Add_resource.this,android.R.layout.simple_spinner_dropdown_item,subject);
                    subspinner.setAdapter(adapt);
                }
                else if(position == 3){
                    String[] subject = new String[]{"Design Analysis and Algorithms ","Theory of Computation","Computer Networks ",
                            "Database Management Systems ","Quantitative Techniques","Advance OS"};
                    ArrayAdapter<String> adapt = new ArrayAdapter<>(Add_resource.this,android.R.layout.simple_spinner_dropdown_item,subject);
                    subspinner.setAdapter(adapt);
                }
                else if(position == 4){
                    String[] subject = new String[]{"Programming with Java","Computer Graphics and Multimedia ","Software Engineering ",
                            "Compiler Design and Language Processor","Personality and Soft Skill Development "};
                    ArrayAdapter<String> adapt = new ArrayAdapter<>(Add_resource.this,android.R.layout.simple_spinner_dropdown_item,subject);
                    subspinner.setAdapter(adapt);
                }
                else if(position == 5){
                    String[] subject = new String[]{"Artificial Intelligence & Expert System","Object Oriented Analysis & Design with UML","Internet Technology & Enterprise Java ",
                            "Accounting Information System ","IOT","Distributive System"};
                    ArrayAdapter<String> adapt = new ArrayAdapter<>(Add_resource.this,android.R.layout.simple_spinner_dropdown_item,subject);
                    subspinner.setAdapter(adapt);
                }
                else if(position == 6){
                    String[] subject = new String[]{"Project"};
                    ArrayAdapter<String> adapt = new ArrayAdapter<>(Add_resource.this,android.R.layout.simple_spinner_dropdown_item,subject);
                    subspinner.setAdapter(adapt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        browsepdf.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(Add_resource.this,"Please Select a file",Toast.LENGTH_SHORT).show();
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
            browsepdf.setText("PDF is Selected");
        }
        else {
            Toast.makeText(Add_resource.this,"Please Select a file",Toast.LENGTH_SHORT).show();
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
            storageReference.child(child).child(filename).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    insertintodatabase();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Add_resource.this, "Some Error Occurred while uploading", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            });

        }
        else
        {
            Toast.makeText(Add_resource.this, "uri null", Toast.LENGTH_SHORT).show();
        }
    }


    public void insertintodatabase()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Add_resource.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_resource.this,Home.class);
                            Add_resource.this.finish();
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Add_resource.this,"Timeout "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",userid);
                params.put("subject",subspinner.getSelectedItem().toString());
                params.put("description",description.getText().toString());
                params.put("semester",semspinner.getSelectedItem().toString());
                params.put("title",title.getText().toString());
                params.put("name",filename);
                params.put("feature",feature.getText().toString());
                return params;
            }
        };
        RequestHandler.getInstance(Add_resource.this).addToRequestQueue(stringRequest);
        progressDialog.hide();
    }
    
}
