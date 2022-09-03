package com.zxc.config;


import com.zxc.interceptor.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wahaha
 * ·@Configuration：允许在 Spring 上下文中注册额外的 bean 或导入其他配置类（表示这是一个配置类）
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器的具体拦截页面
        registry.addInterceptor(checkTokenInterceptor)
                //购物车
                .addPathPatterns("/shopcart/**")
                //订单页面
                .addPathPatterns("/orders/**")
                //用户地址
                .addPathPatterns("/useraddr/**")
                //用户检查
                .addPathPatterns("/user/check");
    }
}
