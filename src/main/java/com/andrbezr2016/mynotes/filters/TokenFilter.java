package com.andrbezr2016.mynotes.filters;


import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.ErrorDto;
import com.andrbezr2016.mynotes.entities.UserToken;
import com.andrbezr2016.mynotes.exceptions.AuthorizationException;
import com.andrbezr2016.mynotes.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String uri = httpRequest.getRequestURI();
        try {
            if (!uri.startsWith(API_AUTH_PATH) || uri.equals(API_AUTH_PATH + "/logout")) {
                String accessToken = httpRequest.getHeader("Token");
                UserToken userToken = authService.checkUserToken(accessToken);
                requestContext.setUserId(userToken.getUserId());
                requestContext.setAccessToken(userToken.getAccessToken());
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthorizationException exception) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.getWriter().write(convertObjectToJson(ErrorDto.builder().message(exception.getMessage()).build()));
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}
