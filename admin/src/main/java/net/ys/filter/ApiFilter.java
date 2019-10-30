package net.ys.filter;

import net.ys.component.SysConfig;
import net.ys.constant.GenResult;
import net.ys.constant.X;
import net.ys.utils.LogUtil;
import net.ys.utils.WebUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*")
public final class ApiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String remoteAddress = WebUtil.getClientIP(request, true);

        LogUtil.debug("access client ip:" + remoteAddress);

        if (SysConfig.accessIpAddress == null || "*".equals(SysConfig.accessIpAddress) || SysConfig.accessIpAddress.indexOf(remoteAddress) > -1) {
            filterChain.doFilter(request, response);
        } else {
            response.setCharacterEncoding(X.ENCODING.U);
            response.setContentType("application/json; charset=" + X.ENCODING.U);
            HttpServletResponse res = (HttpServletResponse) response;
            res.getWriter().write(GenResult.NO_ACCESS_PRIVILEGE.toJson());
        }
    }

    @Override
    public void destroy() {
    }
}