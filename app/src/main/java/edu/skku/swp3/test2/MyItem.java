package edu.skku.swp3.test2;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class MyItem implements Serializable, Comparable<MyItem>{

    private String category;
    @SerializedName("Name")
    public String name;
    @SerializedName("DeviceID")
    private String serial;
    @SerializedName("Code")
    private String code;
    private String people_num;
    private int order = 0;
    // Serializable Object Management UID
    private static final long serialVersionUID = 123212321L;

    public MyItem(){

    }

    public MyItem(MyItem oneItem){
        serial = oneItem.getSerial();
        name = oneItem.getName();
        people_num = oneItem.getPeople();
        category = oneItem.getCategory();
        code = oneItem.getCode();
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeople() {
        return people_num;
    }

    public void setPeople(String people) {
        this.people_num = people;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void updateOrder() {
        this.order = 1;
    }

    @Override
    public int compareTo(MyItem o) {
        if (this.order > o.order){
            return -1;
        }
        else if (this.order < o.order){
            return 1;
        }
        else return 0;
    }
}

