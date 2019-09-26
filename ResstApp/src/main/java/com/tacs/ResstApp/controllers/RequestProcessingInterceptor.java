package com.tacs.ResstApp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tacs.ResstApp.services.impl.UserTokenService;

@Component
public class RequestProcessingInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserTokenService tokenService = new UserTokenService();
		String url = request.getRequestURI();
		System.out.println("la url es");
		System.out.println(url);
		switch (url) {
		case "/login":
			return true;
		case "/logout":
			return tokenService.validateLogged(request.getHeader("token"));
		case "/users":
			if (request.getMethod().equals("GET")) {
				return tokenService.validateAdmin(request.getHeader("token"));
			}
			return true;
		case "/users/**":
		default:
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
	}
}
