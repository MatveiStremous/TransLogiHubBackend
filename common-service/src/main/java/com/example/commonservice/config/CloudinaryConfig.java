package com.example.commonservice.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dcpx7xwjo",
                "api_key", "329295223287461",
                "api_secret", "GXmMCZ0sDgqE10VENM93bqxthbk",
                "secure", true));
    }
}
