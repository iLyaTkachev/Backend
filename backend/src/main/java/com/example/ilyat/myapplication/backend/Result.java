package com.example.ilyat.myapplication.backend;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("r")
    private Float result;

    @SerializedName("e")
    private String error;

    public float getResult() {
        return result;
    }

    public void setResult(final float pResult) {
        result = pResult;
    }

    public String getError() {
        return error;
    }

    public void setError(final String pError) {
        error = pError;
    }
}
