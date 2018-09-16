package com.movi.model.ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("big")
    @Expose
    private String big;
    @SerializedName("native")
    @Expose
    private String _native;
    @SerializedName("mov_id")
    @Expose
    private String movId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getNative() {
        return _native;
    }

    public void setNative(String _native) {
        this._native = _native;
    }

    public String getMovId() {
        return movId;
    }

    public void setMovId(String movId) {
        this.movId = movId;
    }

}