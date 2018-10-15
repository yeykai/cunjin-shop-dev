package com.yeykai.mapper;

import java.util.List;

import com.yeykai.pojo.vo.CommentsVO;
import com.yeykai.pojo.Comments;
import com.yeykai.utils.MyMapper;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	public List<CommentsVO> queryComments(String goodsId);
}
