package com.example.guild;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUserName;
    private String mPassword;
    private String mRole;
    private int mMoney;
    private boolean mIsAdmin;

    public User(String userName, String password, String role, int money, boolean isAdmin) {
        mUserName = userName;
        mPassword = password;
        mRole = role;
        mMoney = 0;
        mIsAdmin = isAdmin;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String mRole) {
        this.mRole = mRole;
    }

    public int getMoney() {
        return mMoney;
    }

    public void setMoney(int mMoney) {
        this.mMoney = mMoney;
    }

    public boolean getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(boolean mIsAdmin) {
        this.mIsAdmin = mIsAdmin;
    }
}