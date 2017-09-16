package com.andryyu.toggle;

/**
 * Created by yufei on 2017/9/16.
 */

public class AudioModel {
    private String name;
    private String length;
    private boolean status = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
