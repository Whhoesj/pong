package nl.sjtek.smartmobile.libpong.ui;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Some utilities for use with Pong.
 */
public class Utils {

    /**
     * Scale a value in a range.
     * @param x Value to change
     * @param in_min Lowest value in old range
     * @param in_max Highest value in old range
     * @param out_min Lowest value in new range
     * @param out_max Highest value in new range
     * @return Scaled value
     */
    public static float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    /**
     * Apply exponential smoothing to a value
     * <p>
     *     Nice for sensor input
     * </p>
     * @param input Input value
     * @param output Previous output value
     * @param alpha Smoothing constant
     * @return
     */
    public static float exponentialSmoothing(float input, float output, float alpha) {
        return (output + alpha * (input - output));
    }

    /**
     * Get the current IP address on the connected wifi network as a {@link java.lang.String}.
     * @param context Context to get the data from
     * @return IP address
     */
    public static String getIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipString =
                String.format("%d.%d.%d.%d",
                        (ip & 0xff),
                        (ip >> 8 & 0xff),
                        (ip >> 16 & 0xff),
                        (ip >> 24 & 0xff));
        Log.d("getIpAddress()", "Current IP: " + ipString);
        return ipString;
    }
}
