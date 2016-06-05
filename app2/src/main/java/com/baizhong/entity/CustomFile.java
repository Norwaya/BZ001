package com.baizhong.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/30.
 */
@Table(name="filetest")
public class CustomFile extends Model implements Serializable {
//    @Column(name = "id")
//    private int id;
    @Column(name="name")
    private String name;
    @Column(name="path")
    private String path;
    public CustomFile() {
    }

    public CustomFile(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
