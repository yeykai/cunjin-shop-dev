package com.yeykai.pojo.vo;

import java.util.Date;

import javax.persistence.*;

public class GoodsVO {

    private String id;
    private String sellerId;
    private String goodsName;
    private Double price;
    private Integer likeCounts;
    private String sellerPhone;
    private String address;
    private Integer goodsNum;
    private String goodsDesc;
    private Date createTime;
    


	private String goodsImg;
    private String nickName;
    private String avatar_url;
    private String TimeAgoStr;
    
    

    public String getTimeAgoStr() {
		return TimeAgoStr;
	}

	public void setTimeAgoStr(String timeAgoStr) {
		TimeAgoStr = timeAgoStr;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

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
     * @return goods_num
     */
    public Integer getGoodsNum() {
        return goodsNum;
    }

    /**
     * @param goodsNum
     */
    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**
     * @return goods_desc
     */
    public String getGoodsDesc() {
        return goodsDesc;
    }

    /**
     * @param goodsDesc
     */
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
}