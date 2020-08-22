package com.maciek.warcraftstatstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarcraftStatsTrackerApplication {

    public static void main(String[] args) {
        /*
        Due to bug in java 11 with SSL Handshake TLSv1.2,TLSv1.1,TLSv1 must be enabled manually
         */
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1");
        SpringApplication.run(WarcraftStatsTrackerApplication.class, args);
    }

}
