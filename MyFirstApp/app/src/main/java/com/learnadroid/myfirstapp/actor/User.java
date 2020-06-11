package com.learnadroid.myfirstapp.actor;

public class User {
    private int id;
    private String email;
    private String fullName;
    private String numberPhone;
    private String sex;
    private String birthDay;
    private String userName;
    private String passWord;


    public User(int id, String email, String fullName, String numberPhone, String sex, String birthDay, String userName, String passWord) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.numberPhone = numberPhone;
        this.sex = sex;
        this.birthDay = birthDay;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", sex='" + sex + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
