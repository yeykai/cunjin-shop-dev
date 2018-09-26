package com.yeykai;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.imooc.service.UserService;
//import com.imooc.service.impl.UserServiceImpl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;


@Configuration
public class WxMaConfiguration {
	
    @Bean
    @ConditionalOnMissingBean
    public WxMaService wxMaService(WxMaConfig config) {
    		return new WxMaServiceImpl();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public WxMaConfig config() {
        return new WxMaInMemoryConfig();
    }
    


}
