package com.movi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoModel {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("response")
    @Expose
    private List<Response> response = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

}