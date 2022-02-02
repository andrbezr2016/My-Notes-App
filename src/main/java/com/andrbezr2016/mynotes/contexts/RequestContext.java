package com.andrbezr2016.mynotes.contexts;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@RequestScope
@Component
public class RequestContext {

    private Long userId;
    private String accessToken;
}
