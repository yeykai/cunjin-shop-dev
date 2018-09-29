package com.yeykai.pojo;

import javax.persistence.*;

@Table(name = "goods_img")
public class GoodsImg {
    @Id
    @Column(name = "goods_id")
    private String goodsId;

    private String img;

    /**
     * @return goods_id
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }
}