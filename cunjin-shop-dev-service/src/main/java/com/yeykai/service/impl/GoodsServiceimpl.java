package com.yeykai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yeykai.mapper.GoodsImgMapper;
import com.yeykai.mapper.GoodsMapper;
import com.yeykai.pojo.Goods;
import com.yeykai.pojo.GoodsImg;
import com.yeykai.service.GoodsService;

@Service
public class GoodsServiceimpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsImgMapper goodsImgMapper;
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveGoods(Goods good) {
		// TODO Auto-generated method stub
		goodsMapper.insertSelective(good);
		System.out.println("商品信息上传成功");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveGoodsImg(GoodsImg goodsImg) {
		// TODO Auto-generated method stub
		goodsImgMapper.insertSelective(goodsImg);
		System.out.println("商品图片上传成功");
	}

}
