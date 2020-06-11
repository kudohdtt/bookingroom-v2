package com.learnadroid.myfirstapp.dangnhap;

import com.learnadroid.myfirstapp.actor.User;


public class AccountManager {

    private static AccountManager instance;


    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public User user;

    public void InitAccount(int id, String email, String fullName, String numberPhone, String sex, String birthDay, String userName, String passWord){
        user = new User(id, email, fullName, numberPhone, sex, birthDay, userName, passWord);
    }


}
