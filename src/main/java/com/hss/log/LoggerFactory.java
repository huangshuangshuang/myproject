package com.hss.log;

import com.hss.exception.LogInitException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoggerFactory {

    private static Logger logger = null;
    private static Lock lock = new ReentrantLock(true);

    public static Logger getLogger(String className) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = LoggerFactory.class.getClassLoader();
        }
        try {
            Class c = classLoader.loadClass(className);
            return getLogger(c);
        } catch (ClassNotFoundException e) {
            throw new LogInitException(e.getMessage());
        }
    }

    private static Logger getLogger(Class cls) {
        if (logger == null) {
            lock.lock();
            try {
                if (logger == null) {
                    logger = new LoggerImpl(cls);
                }
            } finally {
                lock.unlock();
            }
        }

        return logger;
    }

}
