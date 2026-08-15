
package com.BLCMWEB.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads/galeria}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String directorio = uploadDir.replaceAll("[/\\\\]+$", "");
        registry.addResourceHandler("/uploads/galeria/**")
                .addResourceLocations("file:" + directorio + "/");
    }
}
