package com.randomappsinc.bro.Models;

/**
 * Created by alexanderchiou on 8/18/15.
 */
public class Friend
{
    private String name;
    private String phoneNumber;

    public Friend(String name, String phoneNumber)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
}
