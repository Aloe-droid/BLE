package com.example.simpleble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //권한 확인
        permissionCheck();
        //BLE 사용 가능한 휴대폰인지 확인
        checkCanBLE();
        //블루투스 켜기 && BLE 찾기
        setBluetooth();

    }

    public void permissionCheck() {
        PermissionSupport permissionSupport = new PermissionSupport(this, this);
        permissionSupport.checkPermissions();
    }

    public void checkCanBLE() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void setBluetooth() {
        BluetoothConnect bluetoothConnect = new BluetoothConnect(this);
        bluetoothConnect.bluetoothOn();
        bluetoothConnect.scanDevice(bluetoothConnect.getIsEnable());
    }
}