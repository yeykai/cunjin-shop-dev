package com.yeykai.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yeykai.pojo.Users;
import com.yeykai.utils.IMoocJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户相关业务的接口",tags= {"用户相关业务的接口"})
@RequestMapping("/user")
public class UserController {

	
	@PostMapping("/update")
	public IMoocJSONResult updateUser(String userId,@RequestBody Users user) {
		Users user1=new Users();
		user1.setId(userId);
		return IMoocJSONResult.ok();
	}



}
