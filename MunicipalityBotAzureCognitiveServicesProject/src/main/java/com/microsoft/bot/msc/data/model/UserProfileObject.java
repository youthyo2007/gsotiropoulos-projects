package com.microsoft.bot.msc.data.model;




public class UserProfileObject {


    public UserProfileObject(String name) {
        this.name = name;
    }


    public UserProfileObject() {
        this.name = name;
    }


    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}