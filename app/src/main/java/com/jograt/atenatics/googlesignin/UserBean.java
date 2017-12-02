package com.jograt.atenatics.googlesignin;

/**
 * Created by John on 2017/12/02.
 */

public class UserBean {
    private String user;
    private String email;
    private int cash;
    private String id;
    private String url;

    public UserBean(String user, String email, int cash, String id, String url){
        this.user = user;
        this.email = email;
        this.cash = cash;
        this.id = id;
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
