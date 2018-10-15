package com.yeykai.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yeykai.pojo.Comments;
import com.yeykai.pojo.Goods;
import com.yeykai.pojo.GoodsImg;
import com.yeykai.pojo.Users;
import com.yeykai.service.GoodsService;
import com.yeykai.service.UserService;
import com.yeykai.utils.IMoocJSONResult;
import com.yeykai.utils.PagedResult;
import com.yeykai.utils.RedisOperator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="二手商品相关业务的接口",tags= {"二手商品相关业务的接口"})
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private RedisOperator redis;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private Sid sid;

	//通过登录态查询用户OpenId
	public String getUserOpenId(String thirdSession) {
		if (StringUtils.isBlank(thirdSession)) {
			return null;
		}

        String value = (String) redis.get("Wxuser-redis-session:"+thirdSession);
        System.out.println(value);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        //解析json格式的str
        JSONObject json = JSONObject.parseObject(value);
        String openId = json.getString("openid");
		return openId;
	}
	
	@ApiOperation(value="用户发布商品",notes="用户发布商品的接口")
//	@ApiImplicitParam(name="userId",value="用户id",required=true,
//						dataType="String",paramType="query")
	@GetMapping("/uploadGoods")
	public IMoocJSONResult uploadFace(String thirdSession,String goodsName,
			String goodsDesc,double goodsPrice,String goodsPhone,
			int goodsNum,String goodsAddress) throws Exception {
		

        String openId = getUserOpenId(thirdSession);
		
        String goodsId = sid.nextShort();
		
		Goods goods = new Goods();
		goods.setId(goodsId);
		goods.setSellerId(openId);
		goods.setSellerPhone(goodsPhone);
		goods.setAddress(goodsAddress);
		goods.setGoodsDesc(goodsDesc);
		goods.setLikeCounts(0);
		goods.setGoodsName(goodsName);
		goods.setPrice(goodsPrice);
		goods.setCreateTime(new Date());
		goods.setGoodsNum(goodsNum);
		
		goodsService.saveGoods(goods);
		
		return IMoocJSONResult.ok(goodsId);
	
	}
	
	
	@ApiOperation(value="上传商品图片",notes="用户发布商品的接口")
//	@ApiImplicitParam(name="userId",value="用户id",required=true,
//						dataType="String",paramType="query")
	@PostMapping(value="/uploadGoodsImg" ,headers="content-type=multipart/form-data")
	public IMoocJSONResult uploadGoodsImg(String goodsId,@RequestParam("file") MultipartFile[] files) throws Exception {
	
			//文件保存的命名空间
			String fileSpace="D:/cunjin-xianyu-test";
			//保存到数据库的相对路径
			String uploadPathDB="/"+goodsId+"/img";
			FileOutputStream fileOutputStream=null;
			InputStream inputStream= null;
			try {
				if(files != null && files.length>0) {
					String fileName= files[0].getOriginalFilename();
					if (StringUtils.isNotBlank(fileName)) {
						//文件保存的最终路径
						String finalPath = fileSpace + uploadPathDB + "/" + fileName;
					    //设置数据库保存的路径
						uploadPathDB+=("/"+fileName);
						
						File outFile=new File(finalPath);
						
						if (outFile.getParentFile()!=null || !outFile.getParentFile().isDirectory()) {
							//创建父文件夹
							outFile.getParentFile().mkdirs();
						}
						fileOutputStream = new FileOutputStream(outFile);
						inputStream = files[0].getInputStream();
						IOUtils.copy(inputStream, fileOutputStream);
					}else {
						return IMoocJSONResult.errorMsg("上传出错");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return IMoocJSONResult.errorMsg("上传出错");
			}finally {
				if(fileOutputStream!=null) {
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			}
			
			GoodsImg goodsImg = new GoodsImg();
			goodsImg.setGoodsId(goodsId);
			goodsImg.setImg(uploadPathDB);
			goodsService.saveGoodsImg(goodsImg);
			
			return IMoocJSONResult.ok("上传成功");
	
	}
	
	
	@PostMapping(value="/showAll")
	public IMoocJSONResult showAll(Integer page,Integer pageSize){
		
		if (page==null) {
			page=1;
		}
		
		if (pageSize==null) {
			pageSize = 6;
		}
		
		PagedResult result = goodsService.getAllGoods(page, pageSize);
		return IMoocJSONResult.ok(result);
			
	}
	
	@PostMapping(value="/queryImgList")
	public IMoocJSONResult queryImgList(String goodsId){
		
		List<String> imgList = goodsService.queryGoodsImg(goodsId);
		System.out.println(imgList);
		
		return IMoocJSONResult.ok(imgList);
			
	}
	
	@PostMapping(value="/userLike")
	public IMoocJSONResult userLike(String thirdSession,String goodsId,String sellerId) throws Exception{	
        String userId = getUserOpenId(thirdSession);
		goodsService.userLikeVideo(userId, goodsId, sellerId);
		return IMoocJSONResult.ok();	
	}
	
	@PostMapping(value="/userUnLike")
	public IMoocJSONResult userUnLike(String thirdSession,String goodsId,String sellerId) throws Exception{	
        String userId = getUserOpenId(thirdSession);
 		goodsService.userUnLikeVideo(userId, goodsId, sellerId);
		return IMoocJSONResult.ok();	
	}
	
	@PostMapping("/queryUserLikeGoods")
	public IMoocJSONResult queryUserLikeGoods(String thirdSession,String goodsId) throws Exception {
	    String userId = getUserOpenId(thirdSession);
		boolean islike = userService.isUserLikeGoods(userId, goodsId);
		return IMoocJSONResult.ok(islike);
	}
	
	@PostMapping("/saveComment")
	public IMoocJSONResult saveComment(@RequestBody Comments comment, String thirdSession,
			String fatherCommentId, String toUserId) throws Exception {
	    
		String userId = getUserOpenId(thirdSession);
		comment.setFromUserId(userId);
	    
		comment.setFatherCommentId(fatherCommentId);
		comment.setToUserId(toUserId);
		
		goodsService.saveComment(comment);
		System.out.println(comment.getComment());
		return IMoocJSONResult.ok();
	}
	
	@PostMapping("/getGoodsComments")
	public IMoocJSONResult getGoodsComments(String goodsId, Integer page, Integer pageSize) throws Exception {
		
		if (StringUtils.isBlank(goodsId)) {
			return IMoocJSONResult.ok();
		}
		
		// 分页查询视频列表，时间顺序倒序排序
		if (page == null) {
			page = 1;
		}

		if (pageSize == null) {
			pageSize = 10;
		}
		
		PagedResult list = goodsService.getAllComments(goodsId, page, pageSize);
		
		return IMoocJSONResult.ok(list);
	}
}
