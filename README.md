PhoneGap WifiManagerPlugin
Author: Suman Jana
License - The MIT License


Import File System to folder src, select WifiInfoPlugin.java
Edit res\xml\config.xml, add <plugin name="WifiInfoPlugin" value="org.apache.cordova.plugin.WifiInfoPlugin"/> into <plugins> </plugins>
add 
 <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
into AndroidManifest.xml
Import WifiInfoPlugin.js into .html
API

Example 

window.wifi.managewifi(function(wifi){ console.log(wifi); });
