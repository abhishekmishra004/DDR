package com.android.ddr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {

    TextView signuptxt;
    EditText useridedit;
    EditText password_edit;
    Button buttonsumbit;
    ProgressDialog progressDialog;
    final User user =new User(Login.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        signuptxt = findViewById(R.id.txtnewuser);
        useridedit = findViewById(R.id.editxtuserid);
        password_edit = findViewById(R.id.edittxtpass);
        buttonsumbit = findViewById(R.id.buttonsubmit);
        progressDialog = new ProgressDialog(this);

        onclicklistner();
    }

    public void onclicklistner()
    {
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signup.class);
                Login.this.finish();
                startActivity(intent);
            }
        });

        buttonsumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userid= useridedit.getText().toString();
                final String pass = password_edit.getText().toString();

                progressDialog.setMessage("Checking User...");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constraints.URL_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String useridd = jsonObject.getString("userid");
                                    String name = jsonObject.getString("name");
                                    String Password= jsonObject.getString("password");
                                    String email = jsonObject.getString("email_id");
                                    String regdno = jsonObject.getString("regd_no");
                                    String usertype = jsonObject.getString("usertype");
                                    user.userLogin(useridd,name,email,Password,regdno,usertype);
                                    Toast.makeText(Login.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this,Home.class);
                                    Login.this.finish();
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Timeout"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("userid",userid);
                        params.put("password",pass);
                        return params;
                    }
                };
                RequestHandler.getInstance(Login.this).addToRequestQueue(stringRequest);

            }
        });
    }
}
