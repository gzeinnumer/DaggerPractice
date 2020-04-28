package com.gzeinnumer.daggerpractice.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gzeinnumer.daggerpractice.BaseActivity;
import com.gzeinnumer.daggerpractice.R;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: created");
        Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();
    }
}
