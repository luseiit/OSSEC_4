package com.example.os2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Check_Root_Emulator myapp = new Check_Root_Emulator();

        if (myapp.isProbablyAnEmulator()) { // emulator detection
            if (myapp.isRooted() && myapp.findSuperuserFile() && myapp.checkDirectoryAccessControl()) { //rooting detection
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("This device is emulator and rooted. can't run this app");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ok, Sorry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return;
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("This device is emulator. and not rooted. can't run this app");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ok, Sorry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moveTaskToBack(true);
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        }

        // rooting detection (1)(2)(3)(4) : using su command, checking build tags, checking rooting files, checking access control
        // not emulator, but rooted device.
        if (myapp.isRooted()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("This device is not emulator, but rooted device. can't run this app");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ok, Sorry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moveTaskToBack(true);
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("This device is not emulator and not rooted device. Welcome!");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
