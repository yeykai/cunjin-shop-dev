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
	
	@ApiOperation(value="用户上传图片",notes="用户上传图片的接口")
//	@ApiImplicitParam(name="userId",value="用户id",required=true,
//						dataType="String",paramType="query")
	@PostMapping("/uploadFace")
	public IMoocJSONResult uploadFace(@RequestParam("file") MultipartFile[] files) throws Exception {
//		
//			if (StringUtils.isBlank(userId)) {
//				return IMoocJSONResult.errorMsg("用户ID不能为空");
//			}
//		
//			System.out.println(userId);
			//文件保存的命名空间
			String fileSpace="/Users/chun/IdeaProjects/shopProject/cunjin-xianyu-test";
			//保存到数据库的相对路径
//			String uploadPathDB="/"+userId+"/face";
			FileOutputStream fileOutputStream=null;
			InputStream inputStream= null;
			try {
				if(files != null && files.length>0) {
					String fileName= files[0].getOriginalFilename();
					if (StringUtils.isNotBlank(fileName)) {
						//文件保存的最终路径
//						String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
						String finalFacePath = fileSpace  + "/" + fileName;
						//设置数据库保存的路径
//						uploadPathDB+=("/"+fileName);
						
						File outFile=new File(finalFacePath);
						
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
				
			return IMoocJSONResult.ok("上传成功");
	
	}
	
}
