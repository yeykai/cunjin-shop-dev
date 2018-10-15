package com.yeykai.service;

import com.yeykai.pojo.Users;

public interface UserService {

	//根据用户openId查询用户信息
	public Users queryUserInfo(String openId);
	
	//保存用户（微信用户注册）
	public void saveUser(Users user);
	
	//修改用户信息
	public void updateUser(Users user);
	
	public boolean isUserLikeGoods(String userId,String goodsId);
}
