package com.gzeinnumer.daggerpractice.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.gzeinnumer.daggerpractice.BaseActivity;
import com.gzeinnumer.daggerpractice.R;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: created");
        Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();

        initFragment();

//        initScopeExample();
    }

    private void initFragment() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManager.logOut();
                return true;
            case android.R.id.home:{
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else{
                    return false;
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profil:
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.main, true).build();
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.profileScreen, null, navOptions);
                break;
            case R.id.nav_posts:
                if(isValidDestination(R.id.postScreen)){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.postScreen);
                }
                break;

        }
        item.setCheckable(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    private boolean isValidDestination(int destination){
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
    }

//    @Inject
//    @Named("app_login")
//    ResponseLogin responseLogin1;
//
//    @Inject
//    @Named("auth_login")
//    ResponseLogin responseLogin2;
//
//    private void initScopeExample() {
//        Log.d(TAG, "initScopeExample: "+responseLogin1);
//        Log.d(TAG, "initScopeExample: "+responseLogin2);
//    }
}
