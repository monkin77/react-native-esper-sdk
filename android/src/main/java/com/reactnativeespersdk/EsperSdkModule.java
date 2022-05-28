package com.reactnativeespersdk;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import android.util.Log;

import io.esper.devicesdk.EsperDeviceSDK;
import io.esper.devicesdk.exceptions.EsperSDKNotFoundException;
import io.esper.devicesdk.models.EsperDeviceInfo;
import javax.annotation.Nullable;
import com.facebook.react.bridge.Callback;

@ReactModule(name = EsperSdkModule.NAME)
public class EsperSdkModule extends ReactContextBaseJavaModule {
    public static final String NAME = "EsperSdkManager";
    final EsperDeviceSDK sdk;

    public EsperSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.sdk = EsperDeviceSDK.getInstance(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    /**
     *
     * @param accessToken Esper token
     * This promise returns true if its activated. False otherwise.
     */
    @ReactMethod
    private void checkSDKActivation(String accessToken, Promise promise) {
        Log.d("[EsperManager]", "Checking activation...");
        this.sdk.isActivated(new EsperDeviceSDK.Callback<Boolean>() {
            @Override public void onResponse(Boolean active) {
                if (active) {
                    Log.d("[EsperManager]", "SDK is activated");
                    promise.resolve(true);
                } else {
                    activateSDK(accessToken, promise);
                }
            }
            @Override public void onFailure(Throwable t) {
                //There was an issue retrieving activation status
                Log.e("[EsperManager]", "There was an error checking SDK Activation");
                t.printStackTrace();
                promise.resolve(false);
            }
        });
    }

    private void activateSDK(String accessToken, Promise promise) {
        this.sdk.activateSDK(accessToken, new EsperDeviceSDK.Callback<Void>() {
            @Override public void onResponse(Void response) {
                Log.d("[EsperManager]", "SDK Activation was Successful");
                promise.resolve(true);
            }
            @Override public void onFailure(Throwable t) {
                t.printStackTrace();
                promise.resolve(false);
            }
        });
    }

    private void getDeviceInfo() {
        Log.d("[ESPER Manager]", "Fetching Esper Device Info...");

        this.sdk.getEsperDeviceInfo(new EsperDeviceSDK.Callback<EsperDeviceInfo>() {
            @Override
            public void onResponse(@Nullable EsperDeviceInfo esperDeviceInfo) {
                String deviceId = esperDeviceInfo.getDeviceId();
                String serialNo = esperDeviceInfo.getSerialNo();

                Log.d("[ESPER Manager]", deviceId + " " + serialNo + " ");
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * This promise returns the deviceId upon success. Null otherwise.
     */
    @ReactMethod
    public void getDeviceId(Promise promise) {
        Log.d("[ESPER Manager]", "Fetching Esper Device Id...");

        this.sdk.getEsperDeviceInfo(new EsperDeviceSDK.Callback<EsperDeviceInfo>() {
            @Override
            public void onResponse(@Nullable EsperDeviceInfo esperDeviceInfo) {
                // returnVal = esperDeviceInfo.getDeviceId();
                Log.d("[ESPER Manager]", esperDeviceInfo.getDeviceId());
                promise.resolve(esperDeviceInfo.getDeviceId());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                promise.resolve(null);
            }
        });
    }

    /**
     * This promise returns the serialNumber upon success. Null otherwise.
     */
    @ReactMethod
    public void getSerialNumber(Promise promise) {
        Log.d("[ESPER Manager]", "Fetching Esper Serial Number...");

        this.sdk.getEsperDeviceInfo(new EsperDeviceSDK.Callback<EsperDeviceInfo>() {
            @Override
            public void onResponse(@Nullable EsperDeviceInfo esperDeviceInfo) {
                Log.d("[ESPER Manager]", esperDeviceInfo.getSerialNo());
                promise.resolve(esperDeviceInfo.getSerialNo());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                promise.resolve(null);
            }
        });
    }
}
