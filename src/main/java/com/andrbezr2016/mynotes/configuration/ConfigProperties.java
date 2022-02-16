package com.andrbezr2016.mynotes.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Validated
@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
@ConstructorBinding
public class ConfigProperties {

    @PositiveOrZero
    private final int accessExpiredIn;

    @PositiveOrZero
    private final int refreshExpiredIn;

    @NotBlank
    private final String origin;
}
