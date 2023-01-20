package com.example.simpleble;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
//여기서 연결을 시도 및 무언가를 할듯
public class BluetoothLeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
