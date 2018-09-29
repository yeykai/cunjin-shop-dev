package com.yeykai.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yeykai.pojo.Users;
import com.yeykai.service.UserService;
import com.yeykai.utils.HttpClientUtil;
import com.yeykai.utils.IMoocJSONResult;
import com.yeykai.utils.RedisOperator;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="微信用户登录注册接口",tags= {"微信用户登录注册的接口"})
@RequestMapping("/wxuser")
public class WXLoginController {
	
	@Resource
	private WxMaService wxService;
	
	@Autowired
	private UserService wxUserService;
	
	@Autowired
	private RedisOperator redis;
	
	@Autowired
	private Sid sid;
	
	@PostMapping("/queryUser")
	@ApiOperation(value="根据OpenId查询用户",notes="hello 测试")
	@ApiImplicitParam(name="openId",value="用户openid",required=true,
	dataType="String",paramType="query")
	public IMoocJSONResult queryUser(String openId) {	
		Users user = new Users();
		wxUserService.queryUserInfo(openId);
		return IMoocJSONResult.ok(user);
	}
	
	
	@GetMapping("/wxLogin")
	public IMoocJSONResult wxLogin(String code,String signature, String rawData, String encryptedData, String iv) {	
		//微信接口地址
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		//将code,appid,appsecret存入map
		Map<String, String> param = new HashMap<>();
		param.put("appid", "wxb26e10c1c68a6016");
		param.put("secret", "bfae89aa85485e63a2106c1ac1e3a1d6");
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");
		
		//使用http客户端工具完成交换请求，获得包含openId和session_key的字符串数组
		String wxResult = HttpClientUtil.doGet(url, param);
		System.out.println("交换到的数据为："+wxResult);
        
		//解析json格式的str
        JSONObject json = JSONObject.parseObject(wxResult);
        String sessionKey = json.getString("session_key");
        System.out.println("session_key:"+sessionKey);
        System.out.println("rawData:"+rawData);
        System.out.println("signature:"+signature);
		
        //随机生成一段随机数
         int hashCodeV = UUID.randomUUID().toString().hashCode();
         if (hashCodeV < 0) {
             hashCodeV = -hashCodeV;
         }
         
         //生成自定义登录态
         String thirdSession = new Random(10).nextInt(8) + 1 + String.format("%015d", hashCodeV);	
		// 存入redis,加入user-redis-session:作为前缀，方便管理。
		redis.set("Wxuser-redis-session:" + thirdSession,wxResult,1000 * 60 * 30);

		
        //身份校验
        if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return IMoocJSONResult.errorMsg("check userInfo fail");
        }
        
        // 解密用户信息,并注册到数据库中
        WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        Users wxuser = wxUserService.queryUserInfo(userInfo.getOpenId());
		if (wxuser == null) {
			String userId = sid.nextShort();
			Users wxUserInfo = new Users();
			wxUserInfo.setId(userId);
			wxUserInfo.setGender(userInfo.getGender());
			wxUserInfo.setOpenid(userInfo.getOpenId());
			wxUserInfo.setNickname(userInfo.getNickName());
			wxUserInfo.setCity(userInfo.getCity());
			wxUserInfo.setRegisterTime(new Date());
			wxUserInfo.setAvatarUrl(userInfo.getAvatarUrl());
			wxUserInfo.setFansCounts(0);
			wxUserInfo.setFollowCounts(0);
			wxUserInfo.setReceiveLikeCounts(0);
			wxUserService.saveUser(wxUserInfo);
			System.out.println("注册成功");
		}else {
			System.out.println("有此用户");
		}
        
		//向小程序返回登录态
		return IMoocJSONResult.ok(thirdSession);
	}
	
	@GetMapping("/wxRegister")
	public IMoocJSONResult wxRegister(String thirdSession, String signature, String rawData, String encryptedData, String iv) {
		
		//1.根据小程序前端传来的thirdSession，在redis中找出对应的openId和session_key
		System.out.println("收到的thirdSession:"+thirdSession);
        String value = (String) redis.get("Wxuser-redis-session:"+thirdSession);
        System.out.println(value);
        if (StringUtils.isBlank(value)) {
            return IMoocJSONResult.errorMsg("session timeout");
        }
        //解析json格式的str
        JSONObject json = JSONObject.parseObject(value);
        String sessionKey = json.getString("session_key");
        
        //身份校验
        if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return IMoocJSONResult.errorMsg("check userInfo fail");
        }
        
        // 解密用户信息
        WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        Users wxuser = wxUserService.queryUserInfo(userInfo.getOpenId());
		if (wxuser == null) {
			String userId = sid.nextShort();
			Users wxUserInfo = new Users();
			wxUserInfo.setId(userId);
			wxUserInfo.setGender(userInfo.getGender());
			wxUserInfo.setOpenid(userInfo.getOpenId());
			wxUserInfo.setNickname(userInfo.getNickName());
			wxUserInfo.setCity(userInfo.getCity());
			wxUserInfo.setRegisterTime(new Date());
			wxUserInfo.setAvatarUrl(userInfo.getAvatarUrl());
			wxUserInfo.setFansCounts(0);
			wxUserInfo.setFollowCounts(0);
			wxUserInfo.setReceiveLikeCounts(0);
			wxUserService.saveUser(wxUserInfo);
		    return IMoocJSONResult.ok("用户注册成功!");
		}else {
			System.out.println("有此用户");
		}
        return IMoocJSONResult.ok("用户信息验证成功");
	}
	
}
