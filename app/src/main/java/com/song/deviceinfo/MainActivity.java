package com.song.deviceinfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.geetest.sdk.GT3ConfigBean;
import com.geetest.sdk.GT3ErrorBean;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.GT3Listener;
import com.geetest.sdk.utils.GT3ServiceNode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.song.deviceinfo.activity.SettingsActivity;
import com.song.deviceinfo.utils.Constants;
import com.song.deviceinfo.utils.HttpUtils;
import com.song.deviceinfo.utils.LanguageUtils;
import com.song.deviceinfo.utils.LogUtils;

import org.json.JSONObject;

import java.util.Locale;

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

    private static final String TAG = Constants.TAG;

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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_net, R.id.nav_thermal, R.id.nav_battery, R.id.nav_system,
                R.id.nav_partitions, R.id.nav_store, R.id.nav_applications, R.id.nav_codecs,
                R.id.nav_input, R.id.nav_usb, R.id.nav_soc, R.id.nav_emulator, R.id.nav_virtual,
                R.id.nav_debug, R.id.nav_root, R.id.nav_hook, R.id.nav_device, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        permissionHandler();
        initGeetest();
    }

    private GT3GeetestUtils gt3GeetestUtils;
    private GT3ConfigBean gt3ConfigBean;

    private void initGeetest(){
        gt3GeetestUtils = new GT3GeetestUtils(this);
        // 配置bean文件，也可在oncreate初始化
        gt3ConfigBean = new GT3ConfigBean();
        // 设置验证模式，1：bind，2：unbind
        gt3ConfigBean.setPattern(1);
        // 设置点击灰色区域是否消失，默认不消失
        // 设置验证服务集群节点，默认为中国节点，使用其他节点需要相应配置，否则无法使用验证
        gt3ConfigBean.setGt3ServiceNode(GT3ServiceNode.NODE_CHINA);
        // 设置回调监听
        gt3ConfigBean.setListener(new GT3Listener() {

            /**
             * 验证码加载完成
             * @param duration 加载时间和版本等信息，为json格式
             */
            @Override
            public void onDialogReady(String duration) {
                Log.e(TAG, "GT3BaseListener-->onDialogReady-->" + duration);
            }

            /**
             * 图形验证结果回调
             * @param code 1为正常 0为失败
             */
            @Override
            public void onReceiveCaptchaCode(int code) {
                Log.e(TAG, "GT3BaseListener-->onReceiveCaptchaCode-->" + code);
            }

            /**
             * 自定义api2回调
             * @param result，api2请求上传参数
             */
            @Override
            public void onDialogResult(String result) {
                Log.e(TAG, "GT3BaseListener-->onDialogResult-->" + result);
                // 开启自定义api2逻辑
                new RequestAPI2().execute(result);
            }

            /**
             * 统计信息，参考接入文档
             * @param result
             */
            @Override
            public void onStatistics(String result) {
                Log.e(TAG, "GT3BaseListener-->onStatistics-->" + result);
            }

            /**
             * 验证码被关闭
             * @param num 1 点击验证码的关闭按钮来关闭验证码, 2 点击屏幕关闭验证码, 3 点击返回键关闭验证码
             */
            @Override
            public void onClosed(int num) {
                Log.e(TAG, "GT3BaseListener-->onClosed-->" + num);
            }

            /**
             * 验证成功回调
             * @param result
             */
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "GT3BaseListener-->onSuccess-->" + result);
            }

            /**
             * 验证失败回调
             * @param errorBean 版本号，错误码，错误描述等信息
             */
            @Override
            public void onFailed(GT3ErrorBean errorBean) {
                Log.e(TAG, "GT3BaseListener-->onFailed-->" + errorBean.toString());
            }

            /**
             * 自定义api1回调
             */
            @Override
            public void onButtonClick() {
                new RequestAPI1().execute();
            }
        });
        gt3GeetestUtils.init(gt3ConfigBean);
    }

    // api1，需替换成自己的服务器URL
    private static String captchaURL = "https://www.geetest.com/demo/gt/register-slide";
    // api2，需替换成自己的服务器URL
    private static String validateURL = "https://www.geetest.com/demo/gt/validate-slide";

    /**
     * 请求api1
     */
    class RequestAPI1 extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject = null;
            try {
                String string = HttpUtils.requestGet(captchaURL + "?t=" + System.currentTimeMillis());
                Log.e(TAG, "doInBackground: " + string);
                jsonObject = new JSONObject(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject parmas) {
            // 继续验证
            Log.i(TAG, "RequestAPI1-->onPostExecute: " + parmas);
            // SDK可识别格式为
            // {"success":1,"challenge":"06fbb267def3c3c9530d62aa2d56d018","gt":"019924a82c70bb123aae90d483087f94","new_captcha":true}
            // TODO 设置返回api1数据，即使为null也要设置，SDK内部已处理
            gt3ConfigBean.setApi1Json(parmas);
            // 继续api验证
            gt3GeetestUtils.getGeetest();
        }
    }

    /**
     * 请求api2
     */
    class RequestAPI2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                if (!TextUtils.isEmpty(params[0])) {
                    return HttpUtils.requestPost(validateURL+ "?t=" + System.currentTimeMillis(), params[0]);
                } else {
                    return null;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "RequestAPI2-->onPostExecute: " + result);
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.optString("result");
                    if (TextUtils.isEmpty(status)){
                        status = jsonObject.optString("status");
                    }
                    if ("success".equals(status)) {
                        gt3GeetestUtils.showSuccessDialog();
                    } else {
                        gt3GeetestUtils.showFailedDialog();
                    }
                } catch (Exception e) {
                    gt3GeetestUtils.showFailedDialog();
                    e.printStackTrace();
                }
            } else {
                gt3GeetestUtils.showFailedDialog();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Locale locale = getResources().getConfiguration().locale;
        LogUtils.d("Resources Language: " + locale.getLanguage() + ", DisplayLanguage: " + locale.getDisplayLanguage());
    }

    private void permissionHandler() {
        // 存储和定位权限申请
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE}, 1);
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
//                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//                navController.navigate(R.id.nav_about);
                gt3GeetestUtils.startCustomFlow();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
