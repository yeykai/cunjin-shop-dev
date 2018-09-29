package com.yeykai.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
import com.yeykai.pojo.Goods;
import com.yeykai.pojo.GoodsImg;
import com.yeykai.pojo.Users;
import com.yeykai.service.GoodsService;
import com.yeykai.utils.IMoocJSONResult;
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
	private Sid sid;

	
	@ApiOperation(value="用户发布商品",notes="用户发布商品的接口")
//	@ApiImplicitParam(name="userId",value="用户id",required=true,
//						dataType="String",paramType="query")
	@GetMapping("/uploadGoods")
	public IMoocJSONResult uploadFace(String thirdSession,String goodsName,
			String goodsDesc,double goodsPrice,String goodsPhone,
			int goodsNum,String goodsAddress) throws Exception {
		
		if (StringUtils.isBlank(thirdSession)) {
			return IMoocJSONResult.errorMsg("thirdSession is none");
		}

        String value = (String) redis.get("Wxuser-redis-session:"+thirdSession);
        System.out.println(value);
        if (StringUtils.isBlank(value)) {
            return IMoocJSONResult.errorMsg("session timeout");
        }
        //解析json格式的str
        JSONObject json = JSONObject.parseObject(value);
        String openId = json.getString("openid");
		
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
	
}
