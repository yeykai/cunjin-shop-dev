package com.yeykai.pojo;

import javax.persistence.*;

public class Goods {
    @Id
    private String id;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "goods_name")
    private String goodsName;

    private Double price;

    @Column(name = "like_counts")
    private Integer likeCounts;

    @Column(name = "seller_phone")
    private String sellerPhone;

    private String address;

    private String images;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return seller_id
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return goods_name
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return like_counts
     */
    public Integer getLikeCounts() {
        return likeCounts;
    }

    /**
     * @param likeCounts
     */
    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    /**
     * @return seller_phone
     */
    public String getSellerPhone() {
        return sellerPhone;
    }

    /**
     * @param sellerPhone
     */
    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return images
     */
    public String getImages() {
        return images;
    }

    /**
     * @param images
     */
    public void setImages(String images) {
        this.images = images;
    }
}