package com.yeykai.service;

import java.util.List;

import com.yeykai.pojo.Comments;
import com.yeykai.pojo.Goods;
import com.yeykai.pojo.GoodsImg;
import com.yeykai.pojo.Users;
import com.yeykai.utils.PagedResult;

public interface GoodsService {

	
	//保存用户发布商品信息
	public void saveGoods(Goods good);
	
	//保存商品图片
	public void saveGoodsImg(GoodsImg goodsImg);
	
	//展示商品列表信息
	public PagedResult getAllGoods(Integer page, Integer pageSize);
	
	//按商品id查询商品图片
	public List<String> queryGoodsImg(String goodsId);
	
	//用户收藏商品
	public void userLikeVideo(String userId,String goodsId,String sellerId);
	
	//用户取消收藏商品
	public void userUnLikeVideo(String userId,String goodsId,String sellerId);
	
	//保存商品留言
	public void saveComment(Comments comment);
	
	//获取商品留言列表
	public PagedResult getAllComments(String goodsId, Integer page, Integer pageSize);
}
