package com.baizhong.entity;

/**
 * Created by admin on 2016/6/1.
 */
public class Cpath {
    static int ID = 0;
    public int id;
    public Cpath( boolean isMoved, String path) {
        this.id = ++ID;
        this.isMoved = isMoved;
        this.path = path;
    }

    public boolean isMoved;
    public String path;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
