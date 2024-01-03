package com.example.trypower;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;
    DevicePolicyManager dpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        try {
            dpm.lockNow();
            finish();
        } catch (Exception e) {
            Intent it = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this, DeviceAdminReceiver.class));
            startActivityForResult(it, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            dpm.lockNow();
        } else {
            Toast.makeText(this, "Device administrator must be activated to lock the screen", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
