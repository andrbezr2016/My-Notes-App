package com.andrbezr2016.mynotes.filters;


import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.entities.UserToken;
import com.andrbezr2016.mynotes.exceptions.AuthorizationException;
import com.andrbezr2016.mynotes.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.andrbezr2016.mynotes.constants.ApiConstants.*;

@RequiredArgsConstructor
@Component
public class TokenFilter implements Filter {

    private final AuthService authService;
    private final RequestContext requestContext;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        try {
            if (!uri.startsWith(API_AUTH_PATH) || uri.equals(API_AUTH_PATH + "/logout")) {
                String accessToken = request.getHeader("Token");
                UserToken userToken = authService.checkUserToken(accessToken);
                requestContext.setUserId(userToken.getUserId());
                requestContext.setAccessToken(userToken.getAccessToken());
            }
            filterChain.doFilter(request, response);
        } catch (AuthorizationException exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
