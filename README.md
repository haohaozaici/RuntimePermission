# RuntimePermission
handle Android 6.0+ runtime permission exercise.


# Android 6.0+运行时权限
>6.0之后都需要控制危险权限，本文使用PermissionsDispatcher来控制，记下此文，快速集成使用。

目录结构：
- 集成依赖库
- 基本使用
- 填坑，使用经验

-------------

## 集成依赖库
访问 [PermissionsDispatcher](https://github.com/hotchemi/PermissionsDispatcher)了解最新信息。

添加dependencies， [`latest.version`](https://bintray.com/hotchemi/maven/permissionsdispatcher/_latestVersion) 当前是 **2.3.1** ，请使用最新版gradle。
``` java
dependencies {
  compile 'com.github.hotchemi:permissionsdispatcher:${latest.version}'
  annotationProcessor 'com.github.hotchemi:permissionsdispatcher-processor:${latest.version}'
}
```

## 基本使用

1.非[危险权限](https://developer.android.com/guide/topics/security/permissions.html?hl=zh-cn#normal-dangerous)直接在 [`manifest`](https://developer.android.com/reference/android/Manifest.permission.html)中添加。

2.在BaseActivity中控制危险权限。

### 危险权限
**以下注解为必须**
``` java
@RuntimePermissions  //添加注解
public class MainActivity extends AppCompatActivity {

//请求指定权限
@NeedsPermission(Manifest.permission.CAMERA) 
    void clickCamera() {
        // TODO: 2017/2/21 handle onclick
        Snackbar.make(camera, "相机授权成功", Snackbar.LENGTH_LONG).show();
    }
}
```
添加完成后，build项目，自动生成Activity相关类，在需要请求权限的位置使用相关方法。
``` java
MainActivityPermissionsDispatcher.clickCameraWithCheck(this);
```


### 人性化控制
其他回调方法，人性化控制。

``` java
    //请求权限前提示消息
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

    //权限被拒绝提醒
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

    //权限被永久拒绝提醒
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        Toast.makeText(this, R.string.permission_camera_neverask, Toast.LENGTH_SHORT).show();
    }
```

### 同时请求多个权限

不建议直接请求多个权限，最好在功能需要时再请求。不同的权限控制方法之间通过`Manifest.permission.CAMERA注解`来区分。
``` java
//以此类推
@NeedsPermission({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
```

## 填坑，使用经验
1. Android 6.0以下权限是在安装时全部申请，manifest中不能忘记添加权限申请，撤销权限的唯一方式是卸载应用。
2. 权限组：如果应用请求危险权限，在同一权限组中已经授予了另一项危险权限，系统会立即授予，无需与用户进行任何交互。


### 其他参考文章
[Android权限机制与适配经验---QQ音乐技术团队](http://mp.weixin.qq.com/s?__biz=MzI1NjEwMTM4OA==&mid=2651232379&idx=1&sn=b606cea54aafdcce30972cec62df45f0&chksm=f1d9e518c6ae6c0e41bbf5c6bed1dbd37ca6b97b2a93d3ec18dc9ca4959e55121e0a89b26d38&mpshare=1&scene=23&srcid=0224Dlm7T6eLuyIw54pqEyWn#rd)



其他特殊使用详情[访问官方usage](https://github.com/hotchemi/PermissionsDispatcher#usage)
