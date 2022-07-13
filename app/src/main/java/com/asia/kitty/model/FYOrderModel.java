package com.asia.kitty.model;

public class FYOrderModel {

    private String payPrice;// 支付价格
    private String commodityKindNumber;
    private String orderImages;// 商品图片

    private String payType;
    private String payTime;
    private String payEndTime;
    private String orderTime;
    private String payStatus;

    private String companyName;

    //private String name;

//    public FYOrderModel(String name) {
//        this.name = name;
//    }

    public FYOrderModel() {

    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    //    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getCommodityKindNumber() {
        return commodityKindNumber;
    }

    public void setCommodityKindNumber(String commodityKindNumber) {
        this.commodityKindNumber = commodityKindNumber;
    }

    public String getOrderImages() {
        return orderImages;
    }

    public void setOrderImages(String orderImages) {
        this.orderImages = orderImages;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }


    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(String payEndTime) {
        this.payEndTime = payEndTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
