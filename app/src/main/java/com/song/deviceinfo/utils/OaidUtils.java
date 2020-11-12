package com.song.deviceinfo.utils;

import android.content.Context;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.IIdentifierListener;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.supplier.IdSupplier;

import java.util.List;

import androidx.core.util.Pair;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class OaidUtils {

    public void getOaidsInfo(Context context, List<Pair<String, String>> list) {

        int code = MdidSdkHelper.InitSdk(context, true, new IIdentifierListener() {
            @Override
            public void OnSupport(boolean b, IdSupplier idSupplier) {
                LogUtils.d("MdidSdkHelper isSupported: " + b);
                if (b) {
                    list.add(new Pair<>("OAID", idSupplier.getOAID()));
                    list.add(new Pair<>("VAID", idSupplier.getVAID()));
                    list.add(new Pair<>("AAID", idSupplier.getAAID()));
                    idSupplier.shutDown();
                }
            }
        });
        LogUtils.d("MdidSdkHelper Code: " + code);
    }

    private String descriptionCode(int code) {
        switch (code) {
            case ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT:
                return "DEVICE_NOSUPPORT";
            case ErrorCode.INIT_ERROR_LOAD_CONFIGFILE:
                return "LOAD_CONFIGFILE";
            case ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT:
                return "MANUFACTURER_NOSUPPORT";
            case ErrorCode.INIT_ERROR_RESULT_DELAY:
                return "RESULT_DELAY";
            case ErrorCode.INIT_HELPER_CALL_ERROR:
                return "HELPER_CALL_ERROR";
            default:
                return "SUCCESS";
        }
    }
}
