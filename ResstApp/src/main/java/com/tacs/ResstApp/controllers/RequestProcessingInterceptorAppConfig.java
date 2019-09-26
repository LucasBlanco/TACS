package com.tacs.ResstApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
	public class RequestProcessingInterceptorAppConfig implements WebMvcConfigurer {
	   @Autowired
	   RequestProcessingInterceptor requestProcessingInterceptor;

	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(requestProcessingInterceptor);
	   }
	}
