package com.yeykai.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yeykai.mapper.GoodsMapper;
import com.yeykai.mapper.UsersMapper;
import com.yeykai.pojo.Users;
import com.yeykai.service.UserService;

import tk.mybatis.mapper.entity.Example;
//import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;


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

	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
		Example example=new Example(Users.class);
		Criteria criteria=example.createCriteria();
		criteria.andEqualTo("id",user.getId());
		usersMapper.updateByExampleSelective(user, example);
	}


}
