package com.hao.permissiontest;

import android.Manifest;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button camera;
    private Button phone;
    private Button data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = (Button) findViewById(R.id.camera);
        camera.setOnClickListener(this);
        phone = (Button) findViewById(R.id.phone);
        phone.setOnClickListener(this);
        data = (Button) findViewById(R.id.data);
        data.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.camera:
                MainActivityPermissionsDispatcher.clickCameraWithCheck(this);
                break;
            case R.id.phone:
                MainActivityPermissionsDispatcher.clickPhoneWithCheck(this);
                break;
            case R.id.data:
                MainActivityPermissionsDispatcher.clickDataWithCheck(this);
                break;
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void clickCamera() {
        // TODO: 2017/2/21 handle onclick
        Snackbar.make(camera, "相机授权成功", Snackbar.LENGTH_LONG).show();
    }
    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("我们需要相机！")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Snackbar.make(camera, "相机权限被拒绝", Snackbar.LENGTH_LONG)
                .setAction("允许", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivityPermissionsDispatcher.clickCameraWithCheck(MainActivity.this);
                    }
                }).show();
    }


    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void clickPhone() {
        // TODO: 2017/2/21 handle onclick
        Snackbar.make(camera, "电话授权成功", Snackbar.LENGTH_LONG).show();
    }
    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showRationaleForPhone(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("我们需要电话！")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void showDeniedForPhone() {
        Snackbar.make(phone, "电话权限被拒绝", Snackbar.LENGTH_LONG)
                .setAction("允许", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivityPermissionsDispatcher.clickPhoneWithCheck(MainActivity.this);
                    }
                }).show();
    }


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void clickData() {
        // TODO: 2017/2/21 handle onclick
        Snackbar.make(camera, "读写数据授权成功", Snackbar.LENGTH_LONG).show();
    }
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForData(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("我们需要访问本地数据！")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForData() {
        Snackbar.make(data, "data权限被拒绝", Snackbar.LENGTH_LONG)
                .setAction("允许", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivityPermissionsDispatcher.clickDataWithCheck(MainActivity.this);
                    }
                }).show();
    }


}
