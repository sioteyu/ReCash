package com.jograt.atenatics.googlesignin;

/**
 * Created by John on 2017/12/02.
 */

public class TransactionBean {
    private double latitude;
    private double longitude;
    private String user;
    private String price;
    private String type;

    public TransactionBean(double latitude, double longitude, String user, String price, String type){
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.price = price;
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
