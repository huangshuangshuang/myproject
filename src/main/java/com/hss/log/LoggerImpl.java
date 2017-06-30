package com.hss.log;

import com.hss.common.Constant;
import com.hss.common.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class LoggerImpl implements Logger {

    private String className;

    private BufferedWriter writer;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    LoggerImpl(Class c) {
        this.className = c.getName();
        this.writer = LoggerInit.getWriter(false);
    }

    @Override
    public synchronized void debug(Object message) {
        if (!LoggerInit.isDebug()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.DEBUG, message));
    }

    @Override
    public synchronized void debug(Throwable t) {
        if (!LoggerInit.isDebug()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.DEBUG, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void debug(Object message, Throwable t) {
        if (!LoggerInit.isDebug()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.DEBUG, message, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void debug(Object... os) {
        if (!LoggerInit.isDebug()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.DEBUG, os));
    }

    @Override
    public void debug(String format, Object... os) {
        if (!LoggerInit.isDebug()) {
            return;
        }
        if (StringUtils.isEmpty(format)) {
            return;
        }
        format = format.replaceAll("\\{}", "%s");
        write(getMessage(LogProperties.LogLevel.DEBUG, String.format(format, os)));
    }

    @Override
    public synchronized void info(Object message) {
        if (!LoggerInit.isInfo()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.INFO, message));
    }

    @Override
    public synchronized void info(Throwable t) {
        if (!LoggerInit.isInfo()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.INFO, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void info(Object message, Throwable t) {
        if (!LoggerInit.isInfo()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.INFO, message, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void info(Object... os) {
        if (!LoggerInit.isInfo()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.INFO, os));
    }

    @Override
    public void info(String format, Object os) {
        if (!LoggerInit.isInfo()) {
            return;
        }
        if (StringUtils.isEmpty(format)) {
            return;
        }
        format = format.replaceAll("\\{}", "%s");
        write(getMessage(LogProperties.LogLevel.INFO, String.format(format, os)));
    }

    @Override
    public synchronized void warn(Object message) {
        if (!LoggerInit.isWarn()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.WARN, message));
    }

    @Override
    public synchronized void warn(Throwable t) {
        if (!LoggerInit.isWarn()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.WARN, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void warn(Object message, Throwable t) {
        if (!LoggerInit.isWarn()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.WARN, message, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void warn(Object... os) {
        if (!LoggerInit.isWarn()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.WARN, os));
    }

    @Override
    public void warn(String format, Object os) {
        if (!LoggerInit.isWarn()) {
            return;
        }
        if (StringUtils.isEmpty(format)) {
            return;
        }
        format = format.replaceAll("\\{}", "%s");
        write(getMessage(LogProperties.LogLevel.WARN, String.format(format, os)));
    }

    @Override
    public synchronized void error(Object message) {
        if (!LoggerInit.isError()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.ERROR, message));
    }

    @Override
    public synchronized void error(Throwable t) {
        if (!LoggerInit.isError()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.ERROR, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void error(Object message, Throwable t) {
        if (!LoggerInit.isError()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.ERROR, message, t.getCause(), t.getMessage()));
    }

    @Override
    public synchronized void error(Object... os) {
        if (!LoggerInit.isError()) {
            return;
        }
        write(getMessage(LogProperties.LogLevel.ERROR, os));
    }

    @Override
    public void error(String format, Object os) {
        if (!LoggerInit.isError()) {
            return;
        }
        if (StringUtils.isEmpty(format)) {
            return;
        }
        format = format.replaceAll("\\{}", "%s");
        write(getMessage(LogProperties.LogLevel.ERROR, String.format(format, os)));
    }

    private String getMessage(LogProperties.LogLevel logLevel, Object... message) {
        StringBuilder builder = new StringBuilder();
        LocalDateTime dateTime = LocalDateTime.now();
        builder.append(dateTime.format(formatter)).append(Constant.BLANK);
        builder.append(logLevel.toString()).append(Constant.BLANK);
        builder.append(className).append(Constant.FSB);
        builder.append(Thread.currentThread().getName()).append(Constant.BSB).append(Constant.END);
        Arrays.stream(message).forEach(msg -> {
            if (msg == null) {
                msg = "null";
            }
            builder.append(msg.toString());
        });

        return builder.toString();
    }

    private void write(String message) {
        if (LoggerInit.isFileWrite()) {
            try {
                if (!LoggerInit.isCanAppend()) {
                    writer = LoggerInit.getWriter(true);
                }
                writer.write(message);
                writer.newLine();
                writer.flush();
                LoggerInit.fileSize += message.length();
            } catch (IOException ignored) {
            }
        }
        if (LoggerInit.isConsoleWrite()) {
            System.out.println(message);
        }
    }
}
