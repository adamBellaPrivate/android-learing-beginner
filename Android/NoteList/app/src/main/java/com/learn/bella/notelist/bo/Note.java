package com.learn.bella.notelist.bo;

/**
 * Created by adambella on 1/22/18.
 */

public class Note {
    private long _id;
    private String title;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;

    @Override
    public String toString() {
        return String.valueOf(_id) + " " + title + ": " + desc;
    }
}
