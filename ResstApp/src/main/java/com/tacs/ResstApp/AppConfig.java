package com.tacs.ResstApp;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tacs.ResstApp.filters.TokenFilter;

@Configuration
@EnableCaching
public class AppConfig {
	@Bean
	public FilterRegistrationBean<TokenFilter> filterRegistrationBean() {
		FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<TokenFilter>();
		TokenFilter tokenFilter = new TokenFilter();

		registrationBean.setFilter(tokenFilter);
		registrationBean.addUrlPatterns("/user/*");//TODO: Asignar a que urls se hay que autenticar
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
	@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("repos");
    }
}
