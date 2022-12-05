package com.hdcompany.hocandroid.healthy_ictu.model;

import android.hardware.lights.LightState;

import java.util.List;

public class UserModel {
    private boolean succes;
    private String mess;
    private List<User> result;

    public boolean isSucces() {
        return succes;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public List<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
        this.result = result;
    }
}
