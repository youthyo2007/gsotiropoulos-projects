package com.socialvideo.filters;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if(ck.getName().toString().equals("trbid")){
                	ck.setValue(""); req.setAttribute("languageCookie", ck.getValue());
                } else {
                	response.addCookie(new Cookie("myCookie", "Cookie From KS Codes"));
                };
            }
            chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub
    }
    public void destroy() {
        // TODO Auto-generated method stub
    }
}