package com.tacs.ResstApp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tacs.ResstApp.model.User;

public class AdminFilter implements Filter {

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		if (!"OPTIONS".equals(request.getMethod())) {
			if (!("POST".equals(request.getMethod()) && "/users".equals(request.getServletPath()))) {//Para la creacion de usuarios no hace falta estar logueado
				if (!"POST".equals(request.getMethod()) && "/repositories".equals(request.getServletPath())) { //Solo en post es necesario ser admin
					chain.doFilter(req, res);
					return;
				}
				User user = (User) request.getAttribute("user");
				if (user == null || !user.isAdmin()) {
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
