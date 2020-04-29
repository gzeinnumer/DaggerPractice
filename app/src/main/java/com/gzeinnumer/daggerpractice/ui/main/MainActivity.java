package com.gzeinnumer.daggerpractice.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gzeinnumer.daggerpractice.BaseActivity;
import com.gzeinnumer.daggerpractice.R;
import com.gzeinnumer.daggerpractice.ui.main.post.PostFragment;
import com.gzeinnumer.daggerpractice.ui.main.profile.ProfileFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: created");
        Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();

        initFragment();
    }

    private void initFragment() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ProfileFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new PostFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                sessionManager.logOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
