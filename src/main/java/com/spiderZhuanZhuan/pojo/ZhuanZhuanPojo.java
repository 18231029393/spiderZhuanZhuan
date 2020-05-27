package com.spiderZhuanZhuan.pojo;

public class ZhuanZhuanPojo {
    private String title;
    private String infoId;
    private String metricValue;
    private int salePrice;
    private int originPrice;
    private String url;

    public String getUrl() {
        return "https://m.zhuanzhuan.com/u/c2c/ndetail.html?webview=zzn&infoId=" +
                infoId + "&metric=" +
                metricValue + "&needHideShare=1&needHideHead=1";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
    }

    @Override
    public String toString() {
        return  "title='" + title + '\'' +
                ",现价='" + salePrice + '\'' +
                ",原价='" + originPrice +'\'' +
                ", url=" +"  "+ "https://m.zhuanzhuan.com/u/c2c/ndetail.html?webview=zzn&infoId=" +
                infoId + "&metric=" +
                metricValue + "&needHideShare=1&needHideHead=1";
    }
}


























