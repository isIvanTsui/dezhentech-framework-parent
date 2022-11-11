package com.dezhentech.common.core.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.*;

/**
 * dzSpringBootApplication工具<br/>
 *
 * @author xu.zhang@dezhentech.com
 * @version 1.0.0
 * @title com.dezhentech.common.core.utils.spring.DzSpringBootApplicationUtil
 * @since 2022/11/10 20:33:49
 **/
@SpringBootApplication(scanBasePackages = {"com.dezhentech"})
@Import(DzSpringUtil.class)
@Slf4j
//@EnableConfigurationProperties({DataSourceConfig.class})
public class DzSpringBootApplicationUtil {


    static SpringApplication springBootApplication = null;
    static SpringApplicationBuilder springApplicationBuilder = null;

    public static synchronized void run(String... args) {
        if (springBootApplication == null) {
            StandardEnvironment standardEnvironment = new StandardEnvironment();
            MutablePropertySources propertySources = standardEnvironment.getPropertySources();
            propertySources.addFirst(new SimpleCommandLinePropertySource(args));
            String startJarPath = DzSpringBootApplicationUtil.class.getResource("/").getPath().split("!")[0];
            String[] activeProfiles = standardEnvironment.getActiveProfiles();
            propertySources.addLast(new MapPropertySource("systemProperties", standardEnvironment.getSystemProperties()));
            propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment", standardEnvironment.getSystemEnvironment()));
            if (springBootApplication == null) {
                springApplicationBuilder = new SpringApplicationBuilder(DzSpringBootApplicationUtil.class);
                // 这里可以通过命令行传入
                springApplicationBuilder.profiles("dev");
                springApplicationBuilder.sources(DzSpringBootApplicationUtil.class).web(WebApplicationType.NONE);
            }
            springBootApplication = springApplicationBuilder.build();
            springBootApplication.run(args);
            
            
        }
    }

}