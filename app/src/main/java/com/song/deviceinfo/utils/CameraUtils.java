package com.song.deviceinfo.utils;

import android.hardware.Camera;

public class CameraUtils {

    private static float[] arrays = null;

    public static float[] getViewAngle() {
        if (arrays != null && arrays.length > 0) {
            return arrays;
        }
        try {
            int numberOfCameras = Camera.getNumberOfCameras();
            arrays = new float[numberOfCameras];
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                Camera open = Camera.open(i);
                Camera.Parameters parameters = open.getParameters();
                open.release();
                arrays[i] = parameters.getHorizontalViewAngle();
            }
            return arrays;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
