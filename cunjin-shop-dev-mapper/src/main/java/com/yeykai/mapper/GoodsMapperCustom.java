package com.yeykai.mapper;

import java.util.List;


import com.yeykai.pojo.Goods;
import com.yeykai.pojo.vo.GoodsVO;
import com.yeykai.utils.MyMapper;

public interface GoodsMapperCustom extends MyMapper<Goods> {
	
	public List<GoodsVO> queryAllGoods();
}