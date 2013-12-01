package org.apache.cordova.plugin;

import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import java.util.List;
import org.json.JSONObject;
import android.widget.Toast;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

public class WifiInfoPlugin extends CordovaPlugin {

    public static final String ACTION_MANAGE_WIFI = "managewifi";
    public static final String GET_DEVICE = "getdevice";
    public static final String WIFI_INFO = "wifinfo";
    public static final String CHANGE_WIFI = "changewifi";
    public static String imei;
    public static String macadress;
    public static String provider;
    public static String phonenumber;
    public static int wifistatus;

    public WifiInfoPlugin() {
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        Context context = cordova.getActivity().getApplicationContext();
        try {
            if (ACTION_MANAGE_WIFI.equals(action)) {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled() == false) {
                    wifi.setWifiEnabled(true);
                }

                List<ScanResult> mScanResults = wifi.getScanResults();
                for (ScanResult result : mScanResults) {
 Toast.makeText(context, "sdsds====" + data.length(), Toast.LENGTH_LONG).show();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject oneObject = data.getJSONObject(i);
                        String ssid = oneObject.getString("ssid");
                        Toast.makeText(context, "ssid====" + ssid, Toast.LENGTH_LONG).show();
                        String password = oneObject.getString("password");
                        if (ssid.equals(result.SSID)) {
                            WifiConfiguration wc = new WifiConfiguration();
//                            wc.SSID = "\"belkin_cloudlabz\"";
//                            wc.preSharedKey = "\"84dcd64a\"";
                            wc.SSID = ssid;
                            wc.preSharedKey = password;
                            wc.hiddenSSID = true;
                            wc.status = WifiConfiguration.Status.ENABLED;

                            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                            wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                            int res = wifi.addNetwork(wc);
                            boolean es = wifi.saveConfiguration();
                            boolean b = wifi.enableNetwork(res, true);
                            break;
                        }
                    }
                }
                callbackContext.success();
                return true;
            } else if (GET_DEVICE.equals(action)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();
                phonenumber = telephonyManager.getLine1Number();
                provider = telephonyManager.getNetworkOperatorName();
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()) {
                    WifiInfo info = wifiManager.getConnectionInfo();
                    macadress = info.getMacAddress();
                } else {
                    wifiManager.setWifiEnabled(true);
                    WifiInfo info = wifiManager.getConnectionInfo();
                    macadress = info.getMacAddress();
                    wifiManager.setWifiEnabled(false);
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("imei", WifiInfoPlugin.imei);
                jSONObject.put("macadress", WifiInfoPlugin.macadress);
                jSONObject.put("phonenumber", WifiInfoPlugin.phonenumber);
                jSONObject.put("provider", WifiInfoPlugin.provider);
                callbackContext.success(jSONObject);
                return true;
            } else if (WIFI_INFO.equals(action)) {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled() == false) {
                    wifistatus=0;
                } else {
                   wifistatus=1;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("status", WifiInfoPlugin.wifistatus);
                callbackContext.success(jSONObject);
                return true;
            } else if (CHANGE_WIFI.equals(action)) {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled() == false) {
                    wifi.setWifiEnabled(true);
                } else {
                    wifi.setWifiEnabled(false);
                }
                callbackContext.success();
                return true;
            } else {
                callbackContext.error("Invalid action");
                Toast.makeText(context, "Invalid action", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            callbackContext.error(e.getMessage());
            return false;
        }
    }
}
