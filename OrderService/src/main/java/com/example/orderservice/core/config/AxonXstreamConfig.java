package com.example.orderservice.core.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonXstreamConfig {
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);

        return xStream;
    }

//    @Bean
//    public XStream xStream() {
//        XStream xStream = new XStream();
//
//        xStream.allowTypesByWildcard(new String[]{
//                "java.util.**",
//                "tech.gklijs.api.**",
//                "com.example.orderservice.command.**",
//                "com.example.orderservice.core.events.**",
//                "com.example.sagacore.core.events.**",
//                "com.example.sagacore.core.commands.**",
//                "com.example.orderservice.core.saga.**"
//        });
//        return xStream;
//    }
}