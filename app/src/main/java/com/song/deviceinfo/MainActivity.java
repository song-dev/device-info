package com.song.deviceinfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.song.deviceinfo.utils.LanguageUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtils.updateResources(newBase, LanguageUtils.getDefaultLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_net, R.id.nav_thermal, R.id.nav_battery, R.id.nav_system, R.id.nav_build,
                R.id.nav_partitions, R.id.nav_store, R.id.nav_camera, R.id.nav_applications, R.id.nav_codecs,
                R.id.nav_input, R.id.nav_usb, R.id.nav_soc, R.id.nav_emulator, R.id.nav_virtual,
                R.id.nav_debug, R.id.nav_root, R.id.nav_hook, R.id.nav_device, R.id.nav_app,
                R.id.nav_wifi, R.id.nav_maps, R.id.nav_bluetooth, R.id.nav_others, R.id.nav_hardware)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        permissionHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void permissionHandler() {
        // 存储和定位权限申请
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.action_settings:
                navController.navigate(R.id.nav_settings);
                break;
            case R.id.action_about:
                navController.navigate(R.id.nav_about);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
