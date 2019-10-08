package com.tacs.ResstApp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.InvalidTokenException;
import com.tacs.ResstApp.services.impl.UserTokenService;

public class TokenFilter implements Filter {

	@Autowired
	private UserTokenService userTokenService;

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		if(userTokenService==null){
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userTokenService = webApplicationContext.getBean(UserTokenService.class);
        }
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		
		if (!"OPTIONS".equals(request.getMethod())) {
			if (!("POST".equals(request.getMethod()) && "/users".equals(request.getServletPath()))) {//Para la creacion de usuarios no hace falta estar logueado
				final String token = request.getHeader("authorization");
		
				if (token == null) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
		
				try {
					final User user = userTokenService.getUser(token);
					request.setAttribute("user", user);
				} catch (InvalidTokenException e) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}
		}

		chain.doFilter(req, res);

	}

	@Override
	public void destroy() {

	}
}
