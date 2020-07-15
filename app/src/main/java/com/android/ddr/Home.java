package com.android.ddr;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ddr.Adapters.BookAdapter;
import com.android.ddr.Adapters.NotesAdapter;
import com.android.ddr.Adapters.ProjectAdapter;
import com.android.ddr.Models.BookModel;
import com.android.ddr.Models.Notesmodel;
import com.android.ddr.Models.Projectmodel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Home extends AppCompatActivity {


    final User user = new User(Home.this);
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView,Ebookrecycler,projectrecycler;
    List<Notesmodel> listnotemodel;
    List<Projectmodel> projectmodelList;
    List<BookModel> bookModelslist;
    ProgressDialog progressBar;
    String ans = "";


    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_notes:
                    progressBar.setTitle("Loading");
                    progressBar.show();
                    listnotemodel.clear();
                    ans = "";
                    recyclerView.setVisibility(View.VISIBLE);
                    Ebookrecycler.setVisibility(View.INVISIBLE);
                    projectrecycler.setVisibility(View.INVISIBLE);
                    JSON_WEB_CALL(Constraints.VIEW_NOTES);

                    return true;
                case R.id.navigation_projects:
                    progressBar.setTitle("Loading");
                    progressBar.show();
                    projectmodelList.clear();
                    ans="";
                    PROJECT_WEB_CALL(Constraints.VIEW_PROJECT);
                    projectrecycler.setVisibility(View.VISIBLE);
                    Ebookrecycler.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);

                    return true;
                case R.id.navigation_ebook:
                    progressBar.setTitle("Loading");
                    progressBar.show();
                    bookModelslist.clear();
                    ans="";
                    BOOK_WEB_CALL(Constraints.VIEW_BOOK);
                    Ebookrecycler.setVisibility(View.VISIBLE);
                    projectrecycler.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);


                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_drawer);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationView = findViewById(R.id.home_navigation);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.home_drawerlayout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toogle);
        toogle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toogle.syncState();

        String Usertype = user.getusertype();
        if (Usertype.equals("student")) {
            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(6).setVisible(false);
        }

        recyclerView = findViewById(R.id.HomeRcv);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);

        listnotemodel = new ArrayList<>();
        projectmodelList = new ArrayList<>();
        bookModelslist = new ArrayList<>();
        progressBar = new ProgressDialog(Home.this);


        Ebookrecycler = findViewById(R.id.Ebookrcv);
        LinearLayoutManager layoutmanager2 = new LinearLayoutManager(this);
        layoutmanager2.setOrientation(LinearLayout.VERTICAL);
        Ebookrecycler.setLayoutManager(layoutmanager2);


        projectrecycler = findViewById(R.id.ProjectRcv);
        LinearLayoutManager layoutmanager1 = new LinearLayoutManager(this);
        layoutmanager1.setOrientation(LinearLayout.VERTICAL);
        projectrecycler.setLayoutManager(layoutmanager1);

        clickhandler();
        progressBar.show();
        JSON_WEB_CALL(Constraints.VIEW_NOTES);
    }

    public void clickhandler() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_additem: {
                        Intent intent = new Intent(Home.this, Add_type.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_resource:{
                        Intent intent = new Intent(Home.this, Each_Semester.class);
                        intent.putExtra("choice",1);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_ebooks:{
                        Intent intent = new Intent(Home.this, Each_Semester.class);
                        intent.putExtra("choice",2);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_assignment:{
                        Intent intent = new Intent(Home.this, Each_Semester.class);
                        intent.putExtra("choice",3);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_projects:{
                        Intent intent = new Intent(Home.this, Each_Semester.class);
                        intent.putExtra("choice",4);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_notes:{
                        Intent intent = new Intent(Home.this, Each_Semester.class);
                        intent.putExtra("choice",5);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_myUpload:{
                        Intent intent = new Intent(Home.this, View_user_uploads.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_changePass:
                    {
                        if(user.isLoggedIn()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Home. this);
                        builder.setTitle("Enter New Password");
                        final EditText input = new EditText(Home.this);
                        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String cpass =input.getText().toString();
                                changepass(cpass);

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        }
                        else
                        {
                          Toast.makeText(Home.this,"User not Logined",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case R.id.nav_logout:
                    {
                        if(user.isLoggedIn()) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                            builder.setMessage("Are you Sure?");
                            builder.setTitle("Confirm");
                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Home.this,SplashScreen.class);
                                    startActivity(intent);
                                    Home.this.finish();
                                    user.logout();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        else
                        {
                            Toast.makeText(Home.this,"User not Logined",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }

                }
                return true;
            }
        });
    }


    public void JSON_WEB_CALL(String Url) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Url,
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

                            //Toast.makeText(Home.this, "Len" + listnotemodel.size() + "json" + ans, Toast.LENGTH_LONG).show();
                            NotesAdapter adapter = new NotesAdapter(Home.this, listnotemodel);
                            recyclerView.setAdapter(adapter);
                            progressBar.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    public void PROJECT_WEB_CALL(String Url) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Url,
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
                            ProjectAdapter adapter = new ProjectAdapter(Home.this, projectmodelList);
                            projectrecycler.setAdapter(adapter);
                            progressBar.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    public void BOOK_WEB_CALL(String Url) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Url,
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
                            BookAdapter adapter = new BookAdapter(Home.this, bookModelslist);
                            Ebookrecycler.setAdapter(adapter);
                            progressBar.hide();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void changepass(final String pass)
    {
        if(pass.length() < 6)
        {
            Toast.makeText(Home.this,"Password size greater than 6",Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constraints.CHANGE_PASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Home.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Timeout"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",user.getuserid());
                params.put("pass",pass);
                return params;
            }
        };
        RequestHandler.getInstance(Home.this).addToRequestQueue(stringRequest);

    }
}

