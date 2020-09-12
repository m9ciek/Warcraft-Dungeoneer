package com.maciek.warcraftstatstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
public class WarcraftStatsTrackerApplication {

    public static void main(String[] args) {
        /*
        Due to bug in java 11 with SSL Handshake TLSv1.2,TLSv1.1,TLSv1 must be enabled manually
         */
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1");
        SpringApplication.run(WarcraftStatsTrackerApplication.class, args);
    }

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }

}
