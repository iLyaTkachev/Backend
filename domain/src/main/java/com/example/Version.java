package com.example;

import com.google.gson.annotations.SerializedName;

public class Version {

    @SerializedName("v")
    private int versionCode;
    @SerializedName("h")
    private boolean hardUpdate;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int pVersionCode) {
        versionCode = pVersionCode;
    }

    public boolean isHardUpdate() {
        return hardUpdate;
    }

    public void setHardUpdate(boolean pHardUpdate) {
        hardUpdate = pHardUpdate;
    }
}
