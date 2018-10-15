package com.yeykai.mapper;

import com.yeykai.pojo.Goods;
import com.yeykai.utils.MyMapper;

public interface GoodsMapper extends MyMapper<Goods> {
	
	public void addGoodsLikeCount(String goodsId);
	
	public void reduceGoodsLikeCount(String goodsId);
}