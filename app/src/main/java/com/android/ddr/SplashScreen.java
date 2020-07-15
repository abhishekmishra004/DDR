package com.android.ddr;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Arrays;


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class SplashScreen extends AppCompatActivity {
    private Handler mHandler = new Handler();

    private String[] permissionString = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if (!haspermission(this, (String[]) Arrays.copyOf(this.permissionString, this.permissionString.length))) {
            ActivityCompat.requestPermissions((Activity) this, this.permissionString, 131);
        } else {
            checkuserlogin();
        }


    }




    public void checkuserlogin()
    {
        final User user =new User(SplashScreen.this);

        if(!user.isLoggedIn())
        {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            },1000);

        }
        else
        {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            },1000);

        }
    }


    public boolean haspermission(Context context, String[] permissions) {
        boolean hasallpermission = true;
        int len = permissions.length;

        for (int i = 0; i < len; ++i) {
            String permission = permissions[i];
            int res = context.checkCallingOrSelfPermission(permission);
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasallpermission = false;
            }
        }

        return hasallpermission;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 131:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    checkuserlogin();
                } else {
                    Toast.makeText((Context) this, "please grant all permission", Toast.LENGTH_SHORT).show();
                    this.finish();
                }

                return;
            default:
                Toast.makeText((Context) this, "Something went wrong", Toast.LENGTH_SHORT).show();
                this.finish();
        }
    }

}
