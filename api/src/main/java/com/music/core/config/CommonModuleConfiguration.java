package com.music.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.music.common")
@EnableJpaRepositories(basePackages = {"com.music.common"})
@ComponentScans({
        @ComponentScan(value = "com.music.common"),
        @ComponentScan(value = "com.music.integration")
})
@Configuration
public class CommonModuleConfiguration {
}
