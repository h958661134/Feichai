package com.example.feichai.myapplication.SQLiteDataBase;

public class User {

    private int id;
    private String user_name;
    private int user_pic = 0;
    private String password;
    private String mail;
    private String user_nearname = null;
    String manage_id;

    public User(){
        super();
    }

    public User(String username, String password, String mail) {
        super();
        this.user_name = username;
        this.password = password;
        this.mail = mail;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUser_nearname() {
        return user_nearname;
    }

    public void setUser_nearname(String user_nearname) {
        this.user_nearname = user_nearname;
    }

    public int getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(int user_pic) {
        this.user_pic = user_pic;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    public String getManage_id() {
        return manage_id;
    }

    public void setManage_id(String manage_id) {
        this.manage_id = manage_id;
    }
}
