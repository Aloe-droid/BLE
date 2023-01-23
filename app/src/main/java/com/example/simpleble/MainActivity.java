package com.example.simpleble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BluetoothConnect bluetoothConnect;

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

        Button button = findViewById(R.id.button);
        //버튼을 누르면 BLE 로 전송
        button.setOnClickListener(v -> {
            if (bluetoothConnect != null) {
                bluetoothConnect.sendBLE("Hello.");
            }
        });
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
        bluetoothConnect = new BluetoothConnect(this);
        bluetoothConnect.bluetoothOn();
        bluetoothConnect.scanDevice(bluetoothConnect.getIsEnable());
    }

    @Override
    protected void onDestroy() {
        bluetoothConnect.closeBLE();
        super.onDestroy();
    }
}