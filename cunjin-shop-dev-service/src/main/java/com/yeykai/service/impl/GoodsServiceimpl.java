package com.yeykai.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeykai.mapper.GoodsImgMapper;
import com.yeykai.mapper.GoodsMapper;
import com.yeykai.mapper.GoodsMapperCustom;
import com.yeykai.pojo.Goods;
import com.yeykai.pojo.GoodsImg;
import com.yeykai.pojo.vo.GoodsVO;
import com.yeykai.service.GoodsService;
import com.yeykai.utils.PagedResult;
import com.yeykai.utils.TimeAgoUtils;

@Service
public class GoodsServiceimpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsMapperCustom goodsMapperCustom;
	
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

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getAllGoods(Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		
		PageHelper.startPage(page,pageSize);
		List<GoodsVO> list = goodsMapperCustom.queryAllGoods();
		
		for (GoodsVO c : list) {
			String timeAgo = TimeAgoUtils.format(c.getCreateTime());
			c.setTimeAgoStr(timeAgo);
		}
		
		PagedResult pagedResult = new PagedResult();
		
		PageInfo<GoodsVO> pagelist = new PageInfo<>(list);
		pagedResult.setPage(page);
		pagedResult.setTotal(pagelist.getPages());
		pagedResult.setRows(list);
		pagedResult.setRecords(pagelist.getTotal());
		
		return pagedResult;

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> queryGoodsImg(String goodsId) {
		// TODO Auto-generated method stub
		System.out.println(goodsId);
		GoodsImg goodsImg = new GoodsImg();
		goodsImg.setGoodsId(goodsId);
		
		List<GoodsImg> list = goodsImgMapper.select(goodsImg);
		
		List<String> imgList = new ArrayList<>();
		
		System.out.println(list);
		
		for(int i = 0 ; i<list.size();i++) {
			GoodsImg Img = list.get(i);
			imgList.add(Img.getImg());
		}
		
		return imgList;
	}

}
