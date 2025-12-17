package xyz.mynt.bootcamp5.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "flipt")
@Getter
@Setter
public class FliptConfig {

    private String url;

}