package com.example.simpleble;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class PermissionSupport {
    public final static int MULTIPLE_PERMISSION = 1;
    private final Context context;
    private final Activity activity;
    private final String[] permissions;

    public PermissionSupport(Context context, Activity activity){
        this.context = context;
        this.activity = activity;

        //권한 할당
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            permissions.add(Manifest.permission.BLUETOOTH);
            permissions.add(Manifest.permission.BLUETOOTH_SCAN);
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            permissions.add(Manifest.permission.BLUETOOTH);
        }
        //String[]로 변환
        this.permissions =permissions.toArray(new String[0]);
    }

    //권한 확인
    public void checkPermissions(){
        for(String permission : permissions){
            if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                    Toast.makeText(context,"OK",Toast.LENGTH_LONG).show();
                }else{
                    ActivityCompat.requestPermissions(activity,permissions,MULTIPLE_PERMISSION);
                }
            }
        }
    }

}

