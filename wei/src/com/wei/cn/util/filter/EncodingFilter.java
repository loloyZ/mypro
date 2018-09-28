package com.wei.cn.util.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter" , urlPatterns = "/*")
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        servletRequest.setCharacterEncoding("UTF-8") ;
        chain.doFilter(servletRequest, servletResponse);
        servletResponse.setCharacterEncoding("UTF-8");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
