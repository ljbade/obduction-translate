package com.obductiongame.translate.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CustomFilter implements javax.servlet.Filter {  

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");

		if (response instanceof HttpServletResponse) {
			/*HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
			httpResponse.setHeader("X-Frame-Options", "DENY");
			httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
			httpResponse.setHeader("X-Content-Type-Options", "nosniff");*/
			/*httpResponse.setHeader("Content-Security-Policy", "sandbox; default-src 'none'; report-uri /cspReport");*/
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
