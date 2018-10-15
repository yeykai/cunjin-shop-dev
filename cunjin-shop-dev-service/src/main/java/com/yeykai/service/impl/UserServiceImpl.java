package com.yeykai.service.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yeykai.mapper.GoodsMapper;
import com.yeykai.mapper.UsersLikeGoodsMapper;
import com.yeykai.mapper.UsersMapper;
import com.yeykai.pojo.Users;
import com.yeykai.pojo.UsersLikeGoods;
import com.yeykai.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private UsersLikeGoodsMapper usersLikeGoodsMapper;


	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String openId) {
		// TODO Auto-generated method stub
		Example example=new Example(Users.class);
		Criteria criteria=example.createCriteria();
		criteria.andEqualTo("openid",openId);
		Users user = usersMapper.selectOneByExample(example);
		System.out.println("impl成功");
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		// TODO Auto-generated method stub
//		usersMapper.insert(user);
		usersMapper.insertSelective(user);
		System.out.println("savexixi");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
		Example example=new Example(Users.class);
		Criteria criteria=example.createCriteria();
		criteria.andEqualTo("id",user.getId());
		usersMapper.updateByExampleSelective(user, example);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean isUserLikeGoods(String userId, String goodsId) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(goodsId)) {
			return false;
		}
		
		Example example=new Example(UsersLikeGoods.class);
		Criteria criteria=example.createCriteria();
		criteria.andEqualTo("userId",userId);
		criteria.andEqualTo("goodsId",goodsId);
		List<UsersLikeGoods> list = usersLikeGoodsMapper.selectByExample(example);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
		
	}


}
