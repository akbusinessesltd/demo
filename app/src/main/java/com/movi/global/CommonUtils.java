package com.movi.global;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {

    Context mContext;
    int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    public static final String ERROR_IN_LOAD = "errorINLOAD";
    public static final String BANNER_ADS = "bannerADS";
    public static final String BIG_ADS = "bigADS";
    public static final String NATIVE_ADS = "nativeADS";


    public CommonUtils(Context mContext) {
        this.mContext = mContext;
    }



    //check internet connection
    public boolean isNetworkAvailable() {
        /* getting systems Service connectivity manager */
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConnectivityManager != null) {
            NetworkInfo[] mNetworkInfos = mConnectivityManager.getAllNetworkInfo();
            if (mNetworkInfos != null) {
                for (int i = 0; i < mNetworkInfos.length; i++) {
                    if (mNetworkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //create dialog
    public ProgressDialog createDialog() {
        ProgressDialog progress = new ProgressDialog(mContext, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progress.setMessage(Html.fromHtml("<font color='black'  ><big>"
                + "Loading..." + "</big></font>"));
        progress.setCancelable(false);
        return progress;
    }

    public void showDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            if (!progressDialog.isShowing())
                progressDialog.show();
        }
    }

    public void dismissDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    //show simple message print for bottom of screen
    public void snackbar(View coordinatorLayout, String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public boolean isAboveOrEqualLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    //check GPS or location is on or not
    public boolean canGetLocation() {
        boolean result = true;
        LocationManager lm = null;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }
        try {
            network_enabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (gps_enabled == false || network_enabled == false) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    public boolean isAboveOrEqualAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isAboveOrEqualAndroid21() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void getFacebookId(Context context) {
        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
               //   ("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


}
