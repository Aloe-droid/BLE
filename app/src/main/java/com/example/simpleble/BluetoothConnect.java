package com.example.simpleble;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BluetoothConnect {
    private final Context context;
    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner leScanner;
    private Boolean isEnable = false;
    private final ScanCallback scanCallback;
    private final Set<BluetoothDevice> bluetoothDevices;


    public BluetoothConnect(Context context) {
        this.context = context;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        bluetoothDevices = new HashSet<>();

        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                List<ParcelUuid> mServiceUuids = result.getScanRecord().getServiceUuids(); //기기들의 UUID 수집
                if (mServiceUuids != null) {
                    for (ParcelUuid uuid : mServiceUuids) {
                        //그중 HM10의 디폴트 UUID 가 보이면 주소를 저장한다.
                        if (uuid.getUuid().toString().equals("0000ffe0-0000-1000-8000-00805f9b34fb")) {
                            bluetoothDevices.add(result.getDevice());
                            Log.d("scanResult", result.getDevice().getAddress());
                        }
                    }
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) throws SecurityException {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        };
    }

    public void bluetoothOn() throws SecurityException {
        //블루투스 켜져있는지 확인 && 켜기
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivity(enableIntent);
            bluetoothAdapter.enable();
        }
        isEnable = true;
    }

    public void scanDevice(final boolean enable) throws SecurityException {
        leScanner = bluetoothAdapter.getBluetoothLeScanner();
        if (enable) {
            //10초간 스캔
            long SCAN_PERIOD = 10000;
            new Handler().postDelayed(() -> {
                leScanner.stopScan(scanCallback);
                alertDialogBluetooth();
            }, SCAN_PERIOD);

            leScanner.startScan(scanCallback);
        } else {
            leScanner.stopScan(scanCallback);
        }
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void alertDialogBluetooth() throws SecurityException {
        if (bluetoothDevices.size() > 0) {
            //메시지 띄우기
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("장치 선택");
            //이름만 가져와서 화면에 띄움
            ArrayList<String> deviceNameList = new ArrayList<>();
            for (BluetoothDevice device : bluetoothDevices) {
                deviceNameList.add(device.getName());
            }
            //동적할당된 리스트를 다시 배열로 변환
            CharSequence[] items = deviceNameList.toArray(new CharSequence[0]);
            builder.setItems(items, (dialog, item) -> connectSelectDevice(items[item].toString()));

            //화면에 보여주기
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void connectSelectDevice(String selectedDeviceName) throws SecurityException {
        //연결
        for (BluetoothDevice device : bluetoothDevices) {
            if (device.getName().equals(selectedDeviceName)) {
                //연결을 시도
                Log.d("연결", "시도");
            }
        }
    }
}
