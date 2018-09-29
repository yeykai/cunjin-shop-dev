package com.yeykai.service;

import com.yeykai.pojo.Goods;
import com.yeykai.pojo.GoodsImg;
import com.yeykai.pojo.Users;

public interface GoodsService {

	
	//保存用户发布商品信息
	public void saveGoods(Goods good);
	
	//保存商品图片
	public void saveGoodsImg(GoodsImg goodsImg);
}
