package com.movi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("detail_id")
    @Expose
    private String detailId;
    @SerializedName("mov_id")
    @Expose
    private String movId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("details")
    @Expose
    private String details;

    public Response(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getMovId() {
        return movId;
    }

    public void setMovId(String movId) {
        this.movId = movId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}