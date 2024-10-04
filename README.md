# V2ray-Example

A simple Java module with sample source code to implement the v2ray on Android.  
This module is built from a [custom library based on the xray core](https://github.com/dev7dev/AndroidLibXrayLite)  . With this module, you can easily develop your VPN application based on powerful v2ray protocols. This module currently supports v2ray short links (URI) thanks to a custom library (may have bugs).  
In fact, this module provides you a v2ray client with xray core, which you can manage with static functions in [V2rayController](https://github.com/dev7dev/V2ray-Android/blob/main/v2ray/src/main/java/dev/dev7/lib/v2ray/V2rayController.java) .

| :exclamation:  The function of the library to convert uri to json may have a bug. Test before using in production.   |
|----------------------------------------------------------------------------------------------------------------------|

**Sample Application**
*You can download sample release of this module from [Github Releases of this repo](https://github.com/dev7dev/V2ray-Android/releases)*
<div style="text-align:center;  vertical-align:middle;">  
<img width="30%" alt="v2ray android java" src="https://github.com/dev7dev/V2ray-Android/blob/main/connected.jpeg?raw=true">  
<img width="30%" alt="v2ray android java" src="https://raw.githubusercontent.com/dev7dev/V2ray-Android/main/disconnected.jpg?raw=true">  
</div>  

## Build
*Before anything, make sure you are using the latest version of Android Studio.*
*The sample project does not use any special dependencies or tools, so if there are no network or software issues on your system, you can simply clone it and build it in Android Studio with one click.*

## Implementation
- First, check that the version of gradle and build config of your project is compatible with the version used in this source.
- Clone or download this repo.
- Open your project in android studio and navigate to `File` -> `New` -> `Import module`. Then select the source directory to `v2ray` from the cloned repository.
- After Gradle build , Open [setting.gradle](https://github.com/dev7dev/V2ray-Android/blob/main/settings.gradle#L13) file in your project and add this to your Gradle repositories block.
```
flatDir {
        dirs 'v2ray/libs'
}
```
- Add module as a dependency to your main project  by adding this line to app-level [build.gradle](https://github.com/dev7dev/V2ray-Android/blob/main/app/build.gradle#L35).
```
implementation project(path: ':v2ray')
```
- Click on `Rebuild project` from `Build` Tab .

> Note : *The contents of the manifest of your main project will be automatically merged with the contents of the [library monifest](https://github.com/dev7dev/V2ray-Android/blob/main/v2ray/src/main/AndroidManifest.xml), so there is no need to manually copy the contents.*
## Usage
- First, the library needs to be initialized. So call [this function](https://github.com/dev7dev/V2ray-Android/blob/main/v2ray/src/main/java/dev/dev7/lib/v2ray/V2rayController.java#L62) for this purpose in the primary methods of your activity.
```
V2rayController.init(this, R.drawable.ic_launcher, "V2ray Android");
```
> `R.drawable.ic_launcher` is a drawable resource that will be used in vpn notification.
> `"V2ray Android"` is application name
- You can get the version of core with this
```
V2rayController.getCoreVersion()
```
- With this function you will get current status of module
```
V2rayController.getConnectionState()
```
> This will return an enum with three state : CONNECTED,CONNECTING,DISCONNECTED
- For **start a connection **
```
V2rayController.startV2ray(this, "Test Server", "vless:\\test@test", null);
```
> `"Test Server"` will be used as remark in notification.
> `"vless:\\test@test"`  is a string of your config (could be uri or full json)
> `null` means no application package will be bypass the tunnel
-  For **stop  connection**
```
V2rayController.stopV2ray(this);
```
> you should call this function in main thread.

## Credits
- https://github.com/xtls/xray-core
- https://github.com/2dust/AndroidLibXrayLite
- https://github.com/gvcgo/vpnparser


