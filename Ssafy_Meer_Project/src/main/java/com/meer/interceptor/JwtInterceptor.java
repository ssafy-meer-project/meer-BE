package com.meer.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.meer.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	private final String HEADER_AUTH = "access-token";

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 클라이언트는 서버에게 실제 요청을 보내려고 했을 때 사전 요청을 보내서 서버가 현재 효청을 수락할 수 있는 상태인지를 확인하다.
		// OPTIONS 요청이다.

		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}

		String token = request.getHeader(HEADER_AUTH);
		String loginId = jwtUtil.validate(token);

		String userId = request.getParameter("userId");
		
		if (token != null) {
			if (userId.equals(loginId)) {
				return true;
			}
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "올바르지 않은 접근입니다.");			
			return false;
		}
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 접근입니다.");
		return false;

	}

}
