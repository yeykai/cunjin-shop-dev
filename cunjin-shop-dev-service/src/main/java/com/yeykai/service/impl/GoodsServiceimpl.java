package com.yeykai.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeykai.pojo.vo.CommentsVO;
import com.yeykai.mapper.CommentsMapper;
import com.yeykai.mapper.CommentsMapperCustom;
import com.yeykai.mapper.GoodsImgMapper;
import com.yeykai.mapper.GoodsMapper;
import com.yeykai.mapper.GoodsMapperCustom;
import com.yeykai.mapper.UsersLikeGoodsMapper;
import com.yeykai.mapper.UsersMapper;
import com.yeykai.pojo.Comments;
import com.yeykai.pojo.Goods;
import com.yeykai.pojo.GoodsImg;
import com.yeykai.pojo.UsersLikeGoods;
import com.yeykai.pojo.vo.GoodsVO;
import com.yeykai.service.GoodsService;
import com.yeykai.utils.PagedResult;
import com.yeykai.utils.TimeAgoUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class GoodsServiceimpl implements GoodsService {

	@Autowired
	private Sid sid;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsMapperCustom goodsMapperCustom;
	
	@Autowired
	private GoodsImgMapper goodsImgMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private CommentsMapper commentsMapper;
	
	@Autowired
	private UsersLikeGoodsMapper usersLikeGoodsMapper;
	
	@Autowired
	private CommentsMapperCustom commentsMapperCustom;
	
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

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userLikeVideo(String userId, String goodsId, String sellerId) {
		// 1.保存用户和商品的收藏关联关系表
		String likeId = sid.nextShort();		
		UsersLikeGoods ulg=new UsersLikeGoods();
		ulg.setId(likeId);
		ulg.setUserId(userId);
		ulg.setGoodsId(goodsId);
		usersLikeGoodsMapper.insert(ulg);
		//2.商品收藏数量累加
		goodsMapper.addGoodsLikeCount(goodsId);
		//3.用户商品被收藏数量累加
		usersMapper.addReceiveLikeCount(sellerId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userUnLikeVideo(String userId, String goodsId, String sellerId) {
		// 1.删除用户和商品的收藏关联关系表	
		Example example = new Example(UsersLikeGoods.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId",userId);
		criteria.andEqualTo("goodsId",goodsId);
		usersLikeGoodsMapper.deleteByExample(example);
		//2.商品收藏数量减少
		goodsMapper.reduceGoodsLikeCount(goodsId);
		//3.用户商品被收藏数量减少
		usersMapper.reduceReceiveLikeCount(sellerId);
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveComment(Comments comment) {
		// TODO Auto-generated method stub
		String id = sid.nextShort();
		comment.setId(id);
		comment.setCreateTime(new Date());
		commentsMapper.insert(comment);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getAllComments(String goodsId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		
		List<CommentsVO> list = commentsMapperCustom.queryComments(goodsId);
		
			for (CommentsVO c : list) {
				System.out.println(c.getAvatar_url());
				String timeAgo = TimeAgoUtils.format(c.getCreateTime());
				c.setTimeAgoStr(timeAgo);
			}
		
		PageInfo<CommentsVO> pageList = new PageInfo<>(list);
		
		PagedResult grid = new PagedResult();
		grid.setTotal(pageList.getPages());
		grid.setRows(list);
		grid.setPage(page);
		grid.setRecords(pageList.getTotal());
		
		return grid;
	}

}
