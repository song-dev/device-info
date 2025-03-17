package com.song.deviceinfo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.song.deviceinfo.utils.LanguageUtils

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private var mAppBarConfiguration: AppBarConfiguration? = null
    
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageUtils.updateResources(newBase, LanguageUtils.getDefaultLanguage(newBase)))
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
                R.id.nav_net, R.id.nav_thermal, R.id.nav_battery, R.id.nav_system, R.id.nav_build,
                R.id.nav_partitions, R.id.nav_store, R.id.nav_camera, R.id.nav_applications, R.id.nav_codecs,
                R.id.nav_input, R.id.nav_usb, R.id.nav_soc, R.id.nav_emulator, R.id.nav_virtual,
                R.id.nav_debug, R.id.nav_root, R.id.nav_hook, R.id.nav_device, R.id.nav_app,
                R.id.nav_wifi, R.id.nav_maps, R.id.nav_bluetooth, R.id.nav_others, R.id.nav_hardware)
                .setDrawerLayout(drawer)
                .build()
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, mAppBarConfiguration!!)
        navigationView.setupWithNavController(navController)
        
        // 沉浸式状态栏、导航栏。需布局配置 fitsSystemWindows="false"
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        // 高亮状态栏、导航栏图标
        val insetsController = ViewCompat.getWindowInsetsController(window.decorView)
        insetsController?.isAppearanceLightStatusBars = true
        insetsController?.isAppearanceLightNavigationBars = true
        
        // 处理刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        
        permissionHandler()
    }
    
    override fun onResume() {
        super.onResume()
    }
    
    private fun permissionHandler() {
        // 存储、定位、蓝牙等权限申请
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission_group.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission_group.PHONE) != PackageManager.PERMISSION_GRANTED ||
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                    (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED))
        ) {
            // 申请权限
            val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                )
            } else {
                arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA
                )
            }
            requestPermissions(permissions, 1)
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return mAppBarConfiguration?.let { navController.navigateUp(it) } == true || super.onSupportNavigateUp()
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.action_settings -> navController.navigate(R.id.nav_settings)
            R.id.action_about -> navController.navigate(R.id.nav_about)
        }
        return super.onOptionsItemSelected(item)
    }
    
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
