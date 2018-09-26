package com.yeykai.pojo;

import javax.persistence.*;

@Table(name = "users_like_goods")
public class UsersLikeGoods {
    @Id
    private String id;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 视频
     */
    @Column(name = "goods_id")
    private String goodsId;

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
     * 获取用户
     *
     * @return user_id - 用户
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户
     *
     * @param userId 用户
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取视频
     *
     * @return goods_id - 视频
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 设置视频
     *
     * @param goodsId 视频
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}