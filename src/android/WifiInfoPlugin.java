package org.apache.cordova.plugin;

import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import java.util.List;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

public class WifiInfoPlugin extends CordovaPlugin {

    public static final String ACTION_MANAGE_WIFI = "managewifi";

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_MANAGE_WIFI.equals(action)) {
                Context context = cordova.getActivity().getApplicationContext();
                final WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled() == false) {
                    wifi.setWifiEnabled(true);
                }

                List<ScanResult> mScanResults = wifi.getScanResults();
                for (ScanResult result : mScanResults) {

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
                    }
                }
                callbackContext.success();
                return true;
            } else {
                callbackContext.error("Invalid action");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }
}
