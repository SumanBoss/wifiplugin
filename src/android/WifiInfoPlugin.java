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
                
                // if (wifi.isWifiEnabled() == false) {
                //     wifi.setWifiEnabled(true);
                // }
                
                WifiConfiguration wc = new WifiConfiguration();
                wc.SSID = "\"mtoll\"";
                wc.hiddenSSID = true;
                wc.status = WifiConfiguration.Status.DISABLED;
                wc.priority = 40;
                wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

                wc.wepKeys[0] = "\"FFFFFFFFFF\"";
                wc.wepTxKeyIndex = 0;
                
                WifiManager  wifiManag = (WifiManager) this.getSystemService(WIFI_SERVICE);
                boolean res1 = wifiManag.setWifiEnabled(true);

                int res = wifi.addNetwork(wc);
                boolean es = wifi.saveConfiguration();
                boolean b = wifi.enableNetwork(res, true);
                
                // List<ScanResult> mScanResults = wifi.getScanResults();
                // for (ScanResult result : mScanResults) {
                //     if ("mtoll".equals(result.SSID)) {
                        
                //         break;
                //     }
                // }
                callbackContext.success();
                return true;
            } else if (GET_DEVICE.equals(action)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();
                phonenumber = telephonyManager.getLine1Number();
                provider = telephonyManager.getNetworkOperatorName();
                JSONObject jSONObject = new JSONObject();
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()) {
                    WifiInfo info = wifiManager.getConnectionInfo();
                    macadress = info.getMacAddress();
                    jSONObject.put("macadress", info.getMacAddress());
                } else {
                    wifiManager.setWifiEnabled(true);
                    WifiInfo info = wifiManager.getConnectionInfo();
                    macadress = info.getMacAddress();
                    jSONObject.put("macadress", info.getMacAddress());
                    wifiManager.setWifiEnabled(false);
                }
                jSONObject.put("imei", telephonyManager.getDeviceId());
                jSONObject.put("phonenumber", telephonyManager.getLine1Number());
                jSONObject.put("provider", telephonyManager.getNetworkOperatorName());
                callbackContext.success(jSONObject);
                return true;
            } else if (WIFI_INFO.equals(action)) {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled() == false) {
                    wifistatus = 0;
                } else {
                    wifistatus = 1;
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
