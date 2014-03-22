package com.gxx.record.entities.wedisle;

/**
 * »ÆµÀ¼ªÈÕ±í
 * Create by Gxx
 * Time: 2014-2-13 10:09
 */
public class WedisleGoodDay
{
    String date;
    String gongli;
    String nongli;
    String chong;
    String yi;
    String ji;
    String wuxing;
    String cisui;
    String pengzubaiji;

    public WedisleGoodDay(String date, String gongli, String nongli, String chong, String yi,
                          String ji, String wuxing, String cisui, String pengzubaiji) {
        this.date = date;
        this.gongli = gongli;
        this.nongli = nongli;
        this.chong = chong;
        this.yi = yi;
        this.ji = ji;
        this.wuxing = wuxing;
        this.cisui = cisui;
        this.pengzubaiji = pengzubaiji;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGongli() {
        return gongli;
    }

    public void setGongli(String gongli) {
        this.gongli = gongli;
    }

    public String getNongli() {
        return nongli;
    }

    public void setNongli(String nongli) {
        this.nongli = nongli;
    }

    public String getChong() {
        return chong;
    }

    public void setChong(String chong) {
        this.chong = chong;
    }

    public String getYi() {
        return yi;
    }

    public void setYi(String yi) {
        this.yi = yi;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public String getWuxing() {
        return wuxing;
    }

    public void setWuxing(String wuxing) {
        this.wuxing = wuxing;
    }

    public String getCisui() {
        return cisui;
    }

    public void setCisui(String cisui) {
        this.cisui = cisui;
    }

    public String getPengzubaiji() {
        return pengzubaiji;
    }

    public void setPengzubaiji(String pengzubaiji) {
        this.pengzubaiji = pengzubaiji;
    }
}
