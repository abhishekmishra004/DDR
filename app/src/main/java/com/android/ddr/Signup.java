package com.android.ddr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText userid,name,redgno,email,pass;
    Button submit;
    ProgressDialog progressDialog;
    String user="",names="",registration="",emailid="",password="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userid = findViewById(R.id.userid);
        name = findViewById(R.id.name);
        redgno = findViewById(R.id.regno);
        email = findViewById(R.id.emailid);
        pass = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        progressDialog = new ProgressDialog(Signup.this);

        click();
    }
    public void click()
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Checking..");
                progressDialog.setProgressStyle(0);
                progressDialog.show();
                user = userid.getText().toString();
                names = name.getText().toString();
                registration = redgno.getText().toString();
                emailid = email.getText().toString();
                password = pass.getText().toString();


                if(password.length() < 6)
                {
                    Toast.makeText(Signup.this,"Password size greater than 6",Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    return;
                }
                if(registration.length() != 10 && registration.charAt(0) != '1' )
                {
                    Toast.makeText(Signup.this,"Enter proper registration number",Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
                    Toast.makeText(Signup.this,"Enter proper Email",Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    return;
                }
                if(user.length() < 4)
                {
                    Toast.makeText(Signup.this,"Enter proper userid length greater than 4 and consist number",Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    return;
                }
                String ans = "user = "+user + "\n names = " +names+ "\n registration = " + registration+ "\n email = " + emailid + "\n password = " + password ;
            //Toast.makeText(Signup.this,ans,Toast.LENGTH_LONG).show();
            insertintodatabase();
            }
        });
    }

    public void insertintodatabase()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constraints.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Signup.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if(jsonObject.getString("message").equals("User Register Succesfully")){
                            Intent intent = new Intent(Signup.this,Login.class);
                            Signup.this.finish();
                            startActivity(intent);}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this,"Timeout "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",user);
                params.put("name",names);
                params.put("email_id",emailid);
                params.put("password",password);
                params.put("regd_no",registration);

                return params;
            }
        };
        RequestHandler.getInstance(Signup.this).addToRequestQueue(stringRequest);

        progressDialog.hide();
    }

}
