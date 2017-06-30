package com.hss.log;

public interface Logger {
    void debug(Object o);

    void debug(Throwable t);

    void debug(Object o, Throwable t);

    void debug(Object... os);

    void debug(String format, Object ...os);

    void info(Object o);

    void info(Throwable t);

    void info(Object o, Throwable t);

    void info(Object... os);

    void info(String format, Object os);

    void warn(Object o);

    void warn(Throwable t);

    void warn(Object o, Throwable t);

    void warn(Object... os);

    void warn(String format, Object os);

    void error(Object o);

    void error(Throwable t);

    void error(Object o, Throwable t);

    void error(Object... os);

    void error(String format, Object os);
}
