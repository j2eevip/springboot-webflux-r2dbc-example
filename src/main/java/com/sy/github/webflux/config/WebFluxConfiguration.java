package com.sy.github.webflux.config;

import com.sy.github.webflux.common.GlobalResponseBodyHandler;
import com.sy.github.webflux.common.KryoDecodeCodec;
import com.sy.github.webflux.common.KryoEncodeCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author Sherlock
 * @since 2021/8/31-21:28
 */
@Configuration
@EnableWebFlux
public class WebFluxConfiguration implements WebFluxConfigurer {
    @Bean
    public GlobalResponseBodyHandler responseWrapper(ServerCodecConfigurer serverCodecConfigurer,
        RequestedContentTypeResolver requestedContentTypeResolver) {
        return new GlobalResponseBodyHandler(serverCodecConfigurer.getWriters(), requestedContentTypeResolver);
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.customCodecs().register(new KryoDecodeCodec());
        configurer.customCodecs().register(new KryoEncodeCodec());
    }

    /**
     * 全局跨域配置，根据各自需求定义
     * @param registry
     */
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowCredentials(true)
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("*")
            .exposedHeaders(HttpHeaders.SET_COOKIE);
    }*/

    /**
     * 也可以继承CorsWebFilter使用@Component注解，效果是一样的
     * @return
     */
    /*@Bean
    CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(corsConfigurationSource);
    }*/
}
