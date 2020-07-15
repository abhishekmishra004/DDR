package com.android.ddr;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private Context context;
    private static User mInstance;
    private String SHARED_PREF_NAME = "My_shared_pref";
    private String KEY_USERID = "userid";
    private String KEY_USERNAME = "username";
    private String KEY_PASSWORD = "Password";
    private String KEY_EMAIL = "Email";
    private String KEY_REGNO = "Regd_no";
    private String KEY_USERTYPE = "Usertype";



    public User(Context context){
        this.context = context;

    }

    public static synchronized User getInstance(Context context){
        if(mInstance == null){
            mInstance = new User(context);
        }
        return mInstance;
    }

    public boolean userLogin(String userid,String name,String email,String password,String regdno,String usertype)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERID,userid);
        editor.putString(KEY_USERNAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_REGNO,regdno);
        editor.putString(KEY_USERTYPE,usertype);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public  boolean logout()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getusertype()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERTYPE, "");
    }
    public String getuserid()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERID, "");
    }
}
