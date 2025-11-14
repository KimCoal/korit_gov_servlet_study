package com.korit.korit_gov_servlet_study.ch07;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/ch07/*")
public class ValidFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;

        if (httpReq.getMethod().equalsIgnoreCase("POST") && httpReq.getServletPath().equals("/ch07/users")) {
            // if문으로 요청데이터(Json)에 모든 값이 있는지 확인
            // 있으면 dofilter
            // 없으면 dofilter안타고 return;
        }
    }
}
