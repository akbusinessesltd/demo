package com.movi.global;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    SharedPreferences prefConfi;
    Context mContext;

    public SharePref(Context mContext) {
        this.mContext = mContext;
        prefConfi = mContext.getSharedPreferences("MyPref",
                mContext.MODE_PRIVATE);
    }

    public void setString(String key,String value){
        SharedPreferences.Editor editorConfi = prefConfi.edit();
        editorConfi.putString(key, value);
        editorConfi.commit();
    }

    public String getString(String key){
        return prefConfi.getString(key, "");
    }

    public void setBoolean(String key, Boolean value) {
        SharedPreferences.Editor editorConfi = prefConfi.edit();
        editorConfi.putBoolean(key, value);
        editorConfi.commit();
    }

    public boolean getBoolean(String key) {
        return prefConfi.getBoolean(key, false);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editorConfi = prefConfi.edit();
        editorConfi.putInt(key, value);
        editorConfi.commit();
    }

    public int getInt(String key) {
        return prefConfi.getInt(key, 0);
    }

}
