package com.hss.core;

import com.hss.log.Logger;
import com.hss.log.LoggerFactory;
import com.hss.log.LoggerInit;

import java.time.LocalDateTime;

public class ApplicationStart {

    public static void main(String[] args) {
        LoggerInit.init();
        Logger logger = LoggerFactory.getLogger(ApplicationStart.class.getName());
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusMinutes(5);
        while (LocalDateTime.now().isBefore(localDateTime)) {
            logger.debug(Math.random() * 100000, System.currentTimeMillis());
            logger.info(Math.random() * 100000, System.currentTimeMillis());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            logger.warn(Math.random() * 100000, System.currentTimeMillis());
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
            }
            logger.error(Math.random() * 100000, System.currentTimeMillis());
        }
    }
}
