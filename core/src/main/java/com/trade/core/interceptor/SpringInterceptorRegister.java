package com.trade.core.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**  
 * Spring 拦截器
 */
@Configuration
public class SpringInterceptorRegister extends WebMvcConfigurerAdapter {
	@Autowired
	private Environment env;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
	}

	/**
	 * 图片访问映射（通过地址访问本地图片）
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/notice/**").addResourceLocations("file:d://upload_dir/trade/dev/notice/");
//        registry.addResourceHandler("/d://upload_dir/**").addResourceLocations("file:d://upload_dir/");
//        registry.addResourceHandler("/"+env.getProperty("cfg.upload_dir")+"/**").addResourceLocations("file:"+env.getProperty("cfg.upload_dir"));
        registry.addResourceHandler(String.format("/%s/**",env.getProperty("cfg.upload_dir"))).addResourceLocations(String.format("file:%s",env.getProperty("cfg.upload_dir")));
        super.addResourceHandlers(registry);
    }
}