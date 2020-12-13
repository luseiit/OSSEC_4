package com.example.os2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.lang.reflect.Method;
import android.annotation.SuppressLint;
import android.telephony.TelephonyManager;
import android.util.Log;

import static android.content.Context.TELEPHONY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public final class Check_Root_Emulator{

    /******************** android.os.SystemProperties는 항상 reflection 방식으로만 접근이 가능하다. */
    public static String get(String key){
        String ret = "";
        try{
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");

            @SuppressWarnings("rawtypes")
            Class[] paramTypes = { String.class };
            Method get = SystemProperties.getMethod("get",paramTypes);
            Object[] params = { key };
            ret = (String)get.invoke(SystemProperties,params);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    /********************/


    public boolean isRooted(){ // 1) su 명령어 사용이 가능한지 아닌지로 rooting 판별 실제 에뮬에서 동작
        boolean flag = false;
        try{
            Runtime.getRuntime().exec("su");
            flag = true;
        }catch(Exception e){
            flag = false;
        }

        if(flag){
            return true;
        }else{
            return false;
        }
    }
    public static boolean checkBuildTag() { // 2) build tag가 test 값인지 확인 // 에뮬레이터에서는 동작 x
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        return false;
    }
    public boolean findSuperuserFile() { // 3) 루팅이 의심되는 파일이 있는지 check , 실제 에뮬에서 동작
        return !(new File("/system/app/Superuser.apk").exists()
                &&
                new File("/system/bin/su").exists()
                &&
                new File("/system/xbin/su").exists()
                &&
                new File("/data/data/com.noshufou.android.su").exists()
                &&
                new File("/data/data/com.devadvance.rootcloak").exists()
                &&
                new File("/data/data/com.deadvance.rootcloakplus").exists()
                &&
                new File("/data/data/com.koushikdutta.superuser").exists()
                &&
                new File("/data/data/com.thirdparty.superuser").exists());
    }
    public boolean checkDirectoryAccessControl(){ // 4) 루팅으로 디렉토리의 접근권한이 바뀌는지 check. 실제 에뮬레이터에서 동작
        return !(new File("/data").canWrite()
                &&
                new File("/").canWrite()
                &&
                new File("/system").canWrite());
    }
    boolean isProbablyAnEmulator(){ // checking Emulator
        return /* get("service.camera.running").equals("") && get("ro.bluetooth.tty").equals("")
                || Build.HARDWARE.contains("vbox")
                || Build.HARDWARE.contains("goldfish"); */
                get("ro.product.cpu.abilist64").equals("");

    }
}
