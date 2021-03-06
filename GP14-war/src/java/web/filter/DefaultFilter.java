package web.filter;

import entity.Admin;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "DefaultFilter", urlPatterns = {"/*"})

public class DefaultFilter implements Filter {

    FilterConfig filterConfig;

    private static final String CONTEXT_ROOT = "/GP14-war";

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");

        if (!excludeLoginCheck(requestServletPath)) {
            if (isLogin == true) {
                String usertype = (String) httpSession.getAttribute("usertype");

                if (checkAccessRight(requestServletPath, usertype)) {
                    chain.doFilter(request, response);
                } else {
                    httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
                }
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }

    private Boolean checkAccessRight(String path, String usertype) {
        if (usertype.equals("Admin")) {
            if (path.equals("/adminFunctionality/subscriptionPlanManagement.xhtml")
                    || path.equals("/adminFunctionality/customerManagement.xhtml")
                    || path.equals("/adminFunctionality/routeReviewsManagement.xhtml")
                    || path.equals("/adminFunctionality/gymManagement.xhtml")) {
                return true;
            } else {
                return false;
            }
        } else if (usertype.equals("Partner Gym")) {
            if (path.equals("/gymFunctionality/gymSlotManagement.xhtml")
                    || path.equals("/gymFunctionality/myBookingManagement.xhtml")
                    || path.equals("/gymFunctionality/myRouteReviewsManagement.xhtml")
                    || path.equals("/gymFunctionality/myRouteManagement.xhtml")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml")
                || path.equals("/accessRightError.xhtml")
                || path.equals("/login.xhtml")
                || path.equals("/getStarted.xhtml")
                || path.equals("/termsOfService.xhtml")
                || path.startsWith("/javax.faces.resource")
                || path.startsWith("/uploadedFiles")) {
            return true;
        } else {
            return false;
        }
    }
}
