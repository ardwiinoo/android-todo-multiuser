package com.example.room_database.Database.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String nim;
    private String password;
    private Boolean is_admin;

    public UserModel(int id, String name, String password, String nim, Boolean is_admin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nim = nim;
        this.is_admin = is_admin;
    }

    public UserModel() {}

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
