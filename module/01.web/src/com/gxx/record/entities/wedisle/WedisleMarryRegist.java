package com.gxx.record.entities.wedisle;

/**
 * 结婚登记处表
 * Create by Gxx
 * Time: 2014-02-13 17:46
 */
public class WedisleMarryRegist
{
    int id;
    String name;
    String place;
    String phone;
    String time;
    String bigImageSrc;
    String imageSrc;

    /**
     * 新增数据时候用
     * @param name
     * @param place
     * @param phone
     * @param time
     * @param bigImageSrc
     * @param imageSrc
     */
    public WedisleMarryRegist(String name, String place, String phone, String time, String bigImageSrc, String imageSrc) {
        this.name = name;
        this.place = place;
        this.phone = phone;
        this.time = time;
        this.bigImageSrc = bigImageSrc;
        this.imageSrc = imageSrc;
    }

    /**
     * 查询数据时候用
     * @param id
     * @param name
     * @param place
     * @param phone
     * @param time
     * @param bigImageSrc
     * @param imageSrc
     */
    public WedisleMarryRegist(int id, String name, String place, String phone, String time, String bigImageSrc, String imageSrc) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.phone = phone;
        this.time = time;
        this.bigImageSrc = bigImageSrc;
        this.imageSrc = imageSrc;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getBigImageSrc() {
        return bigImageSrc;
    }

    public void setBigImageSrc(String bigImageSrc) {
        this.bigImageSrc = bigImageSrc;
    }
}
