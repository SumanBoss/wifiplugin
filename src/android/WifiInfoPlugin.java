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
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

public class WifiInfoPlugin extends CordovaPlugin {

    public static final String ACTION_MANAGE_WIFI = "managewifi";
    public static final String GET_DEVICE = "getdevice";
    public static String imei;
    public static String macadress;
    public static String provider;
    public static String phonenumber;

    public WifiInfoPlugin() {
    }
    private String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
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
                    Toast.makeText(context, "ssid====" + result.SSID, Toast.LENGTH_LONG).show();
                    if ("belkin_cloudlabz".equals(result.SSID)) {
                        WifiConfiguration wc = new WifiConfiguration();
                        wc.SSID = "\"belkin_cloudlabz\"";
                        wc.preSharedKey = "\"84dcd64a\"";
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
                callbackContext.success();
                return true;
            } else if (GET_DEVICE.equals(action)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyManager.getDeviceId();
                phonenumber = telephonyManager.getLine1Number();
                provider = telephonyManager.getNetworkOperatorName();
                if (phonenumber.equals("") || phonenumber == null) {
                    phonenumber = md5(telephonyManager.getDeviceId()).substring(0, 10);
                }
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
            } else {
                callbackContext.error("Invalid action");
                Toast.makeText(context, "Invalid action", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            callbackContext.error(e.getMessage());
            return false;
        }
    }
}
