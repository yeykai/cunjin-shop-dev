package com.yeykai.mapper;

import com.yeykai.pojo.Users;
import com.yeykai.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {
	
	public void addReceiveLikeCount(String openId);
	
	public void reduceReceiveLikeCount(String openId);
}