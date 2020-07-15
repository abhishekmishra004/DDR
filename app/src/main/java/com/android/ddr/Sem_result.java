package com.android.ddr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.ddr.Adapters.BookAdapter;
import com.android.ddr.Adapters.NotesAdapter;
import com.android.ddr.Adapters.ProjectAdapter;
import com.android.ddr.Adapters.ResourceAdapter;
import com.android.ddr.Models.BookModel;
import com.android.ddr.Models.Notesmodel;
import com.android.ddr.Models.Projectmodel;
import com.android.ddr.Models.ResourceModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sem_result extends AppCompatActivity {

    RecyclerView resultrcv;
    String Tablename,sem,ans="";
    int seme;
    List<Notesmodel> listnotemodel;
    List<Projectmodel> projectmodelList;
    List<BookModel> bookModelslist;
    List<ResourceModel> resourceModels;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_result);

        resultrcv = findViewById(R.id.Sem_result);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayout.VERTICAL);
        resultrcv.setLayoutManager(layoutmanager);

        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("res");
        Tablename = bundle.getString("table","");
        seme = bundle.getInt("sem",0);
        //Toast.makeText(Sem_result.this,Tablename+seme,Toast.LENGTH_LONG).show();
        //"1st","2nd","3rd","4th","5th","6th"
        if(seme == 1) sem = "1st";
        else if(seme == 2) sem = "2nd";
        else if(seme == 3) sem = "3rd";
        else if(seme == 4) sem = "4th";
        else if(seme == 5) sem = "5th";
        else if(seme == 6) sem = "6th";

        listnotemodel = new ArrayList<>();
        projectmodelList = new ArrayList<>();
        bookModelslist = new ArrayList<>();
        resourceModels = new ArrayList<>();
        progressDialog = new ProgressDialog(Sem_result.this);


        if(Tablename.equals("notes_table")){
            notes();
        }
        if(Tablename.equals("ebook")){
            book();
        }
        if(Tablename.equals("project_table")){
            project();
        }
        if(Tablename.equals("resourctable")){
            res(Constraints.VIEW_RES_RES);
        }
        if(Tablename.equals("syllabus_assignment")){
            res(Constraints.VIEW_ASS_RES);
        }

    }

    public void notes()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constraints.VIEW_NOTES_RES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Notesmodel getnotemodel = new Notesmodel();
                                try {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    getnotemodel.setId(json.getInt("id"));
                                    getnotemodel.setName(json.getString("name"));
                                    getnotemodel.setDescription(json.getString("description"));
                                    getnotemodel.setSemester(json.getString("semester"));
                                    getnotemodel.setSubject(json.getString("subject"));
                                    getnotemodel.setTitle(json.getString("title"));
                                    getnotemodel.setType(json.getString("type"));
                                    getnotemodel.setUserid(json.getString("userid"));
                                    ans = ans + "sem = " + json.getInt("semester") + "\n" +
                                            "subject = " + json.getInt("subject") + "\n";

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                listnotemodel.add(getnotemodel);
                            }

                            Toast.makeText(Sem_result.this, "Len" + listnotemodel.size() + "json" + ans, Toast.LENGTH_LONG).show();
                            NotesAdapter adapter = new NotesAdapter(Sem_result.this, listnotemodel);
                            resultrcv.setAdapter(adapter);
                            progressDialog.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Sem_result.this,"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sem_result.this,"List is Empty"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sem",sem);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    public void book()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constraints.VIEW_BOOK_RES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BookModel getmodel = new BookModel();
                                try {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    getmodel.setId(json.getInt("id"));
                                    getmodel.setUserid(json.getString("userid"));
                                    getmodel.setTitle(json.getString("title"));
                                    getmodel.setAuthor(json.getString("author"));
                                    getmodel.setSemester(json.getString("semester"));
                                    getmodel.setSubject(json.getString("subject"));
                                    getmodel.setDescription(json.getString("description"));
                                    getmodel.setVersion(json.getString("version"));
                                    getmodel.setPdf(json.getString("pdf"));

                                    ans = ans + "title = " + json.getString("semester") + "\n" +
                                            "subject = " + json.getString("subject") + "\n";

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                bookModelslist.add(getmodel);
                            }

                            //  Toast.makeText(Home.this, "Len" + bookModelslist.size() + "json" + ans, Toast.LENGTH_LONG).show();
                            BookAdapter adapter = new BookAdapter(Sem_result.this, bookModelslist);
                            resultrcv.setAdapter(adapter);
                            progressDialog.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Sem_result.this,"Error "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sem_result.this,"List is Empty"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sem",sem);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void project()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constraints.VIEW_PROJECT_RES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Projectmodel getnotemodel = new Projectmodel();
                                try {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    getnotemodel.setId(json.getInt("id"));
                                    getnotemodel.setUserid(json.getString("userid"));
                                    getnotemodel.setProject_type(json.getString("project_type"));
                                    getnotemodel.setProjectSem(json.getString("projectSem"));
                                    getnotemodel.setTitle(json.getString("title"));
                                    getnotemodel.setDescription(json.getString("description"));
                                    getnotemodel.setTechnology(json.getString("technology"));
                                    getnotemodel.setName(json.getString("name"));
                                    getnotemodel.setType(json.getString("type"));
                                    ans = ans + "id = " + json.getInt("id") + "\n" +
                                            "userid = " + json.getString("userid") + "\n" +
                                            "project_type = " + json.getString("project_type") + "\n"+
                                            "projectSem = " + json.getString("projectSem") + "\n"+
                                            "title = " + json.getString("title") + "\n" +
                                            "description = " + json.getString("description") ;

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                projectmodelList.add(getnotemodel);
                            }

                            // Toast.makeText(Home.this, "Len" + projectmodelList.size() + "json" + ans, Toast.LENGTH_LONG).show();
                            ProjectAdapter adapter = new ProjectAdapter(Sem_result.this, projectmodelList);
                            resultrcv.setAdapter(adapter);
                            progressDialog.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Sem_result.this,"error"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sem_result.this,"List is Empty"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sem",sem);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void res(String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                ResourceModel getnotemodel = new ResourceModel();
                                try {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    getnotemodel.setId(json.getInt("id"));
                                    getnotemodel.setName(json.getString("name"));
                                    getnotemodel.setDescription(json.getString("description"));
                                    getnotemodel.setSemester(json.getString("semester"));
                                    getnotemodel.setSubject(json.getString("subject"));
                                    getnotemodel.setTitle(json.getString("title"));
                                    getnotemodel.setFeature(json.getString("feature"));
                                    getnotemodel.setUserid(json.getString("userid"));
                                    ans = ans + "sem = " + json.getInt("semester") + "\n" +
                                            "subject = " + json.getInt("subject") + "\n";

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                resourceModels.add(getnotemodel);
                            }

                            Toast.makeText(Sem_result.this, "Len" + resourceModels.size() + "json" + ans, Toast.LENGTH_LONG).show();
                            ResourceAdapter adapter = new ResourceAdapter(Sem_result.this, resourceModels);
                            resultrcv.setAdapter(adapter);
                            progressDialog.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Sem_result.this,"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sem_result.this,"List is Empty"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sem",sem);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
