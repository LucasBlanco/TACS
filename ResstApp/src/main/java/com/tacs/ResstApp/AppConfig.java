package com.tacs.ResstApp;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tacs.ResstApp.filters.TokenFilter;

@Configuration
public class AppConfig {
	@Bean
	public FilterRegistrationBean<TokenFilter> filterRegistrationBean() {
		FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<TokenFilter>();
		TokenFilter tokenFilter = new TokenFilter();

		registrationBean.setFilter(tokenFilter);
		//registrationBean.addUrlPatterns("/users/*");//TODO: Asignar a que urls se hay que autenticar
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
