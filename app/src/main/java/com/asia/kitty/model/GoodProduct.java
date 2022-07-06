package com.asia.kitty.model;
// 商品详情
public class GoodProduct {
    private int img;
    private String title;

    public GoodProduct(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
