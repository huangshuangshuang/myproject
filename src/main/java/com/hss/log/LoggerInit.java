package com.hss.log;

import com.hss.common.Constant;
import com.hss.common.RegexUtils;
import com.hss.common.StringUtils;
import com.hss.exception.FileCantLoadException;
import com.hss.exception.LogInitException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPOutputStream;

public class LoggerInit {
    private static LogProperties properties = null;

    private static File file = null;

    private static String logFilePath = null;

    private static boolean fileWrite = false;

    private static boolean consoleWrite = false;

    private static boolean debug = false;

    private static boolean info = false;

    private static boolean warn = false;

    private static boolean error = false;

    private static LocalDate localDate = null;

    static volatile long fileSize = Constant.NONE;

    private static Long size = Constant.DEFAULT_LOG_SIZE;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void init() {
        try {
            setProperties();
            if (properties.getPrintLevel().equals(LogProperties.PrintLevel.FILE)) {
                fileWrite = true;
            } else if (properties.getPrintLevel().equals(LogProperties.PrintLevel.CONSOLE)) {
                consoleWrite = true;
            } else if (properties.getPrintLevel().equals(LogProperties.PrintLevel.BOTH)) {
                fileWrite = true;
                consoleWrite = true;
            }
            if (properties.getLogLevel().getLevel() <= Constant.LOG_LEVEL_DEBUG) {
                debug = true;
            }
            if (properties.getLogLevel().getLevel() <= Constant.LOG_LEVEL_INFO) {
                info = true;
            }
            if (properties.getLogLevel().getLevel() <= Constant.LOG_LEVEL_WARN) {
                warn = true;
            }
            if (properties.getLogLevel().getLevel() <= Constant.LOG_LEVEL_ERROR) {
                error = true;
            }
            if (properties.getSize() != null) {
                size = properties.getSize();
            }
        } catch (Exception e) {
            throw new LogInitException(e.getMessage());
        }
    }

    private static void setProperties() throws IOException {
        String path = getPath();
        File file = new File(path);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new FileCantLoadException("can't load file [" + path + "].");
        }
        if (file.length() <= Constant.NONE) {
            throw new FileCantLoadException("file [" + path + "] size is 0, can't load properties from this.");
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        properties = new LogProperties();
        while ((line = reader.readLine()) != null) {
            propertyOperator(line, properties);
        }
    }

    private static void propertyOperator(String line, LogProperties properties) {
        if (StringUtils.isEmpty(line)) {
            return;
        }
        String[] keyAndValue = line.split(Constant.SEPARATOR);
        String key = keyAndValue[0].trim();
        String value = keyAndValue[1].trim();
        switch (key) {
            case Constant.LOG_PATH:
                properties.setPath(value);
                break;
            case Constant.LOG_LEVEL:
                properties.setLogLevel(LogProperties.LogLevel.transformer(value));
                break;
            case Constant.LOG_PRINT_LEVEL:
                properties.setPrintLevel(LogProperties.PrintLevel.transformer(value));
                break;
            case Constant.LOG_PRINT_TYPE:
                properties.setPrintType(LogProperties.PrintType.transformer(value));
                break;
            case Constant.LOG_SLICER:
                properties.setSlicer(LogProperties.Slicer.transformer(value));
                break;
            case Constant.LOG_SIZE:
                if (StringUtils.isEmpty(value) || !RegexUtils.isLong(value)) {
                    properties.setSize(Constant.DEFAULT_LOG_SIZE);
                } else {
                    properties.setSize(Long.valueOf(value));
                }
                break;
        }
    }

    private static String getPath() {
        String path;
        path = System.getProperty(Constant.LOG_PATH_NAME);
        if (StringUtils.isEmpty(path)) {
            path = System.getProperty(Constant.USER_DIR) + Constant.DEFAULT_PATH;
        }

        return path;
    }

    private static BufferedWriter createLogFile(LogProperties properties) throws IOException {
        String path = properties.getPath();
        if (path == null) {
            path = System.getProperty(Constant.USER_DIR) + Constant.DEFAULT_LOG_FILE;
        }
        file = new File(path);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new FileCantLoadException("can't create file [" + path + "], please make sure you have permission.");
            }
        }
        if (!file.canWrite()) {
            throw new FileCantLoadException("can't create file [" + path + "], please make sure you have permission.");
        }
        fileSize = Constant.NONE;
        logFilePath = path;
        localDate = LocalDate.now();
        boolean isAppend = properties.getPrintType().equals(LogProperties.PrintType.APPEND);

        return new BufferedWriter(new FileWriter(file, isAppend));
    }

    static BufferedWriter getWriter(boolean isChangeFileName) {
        if (isChangeFileName) {
            String path;
            if (properties.getSlicer().equals(LogProperties.Slicer.DATE)) {
                path = logFilePath + localDate.format(formatter);
            } else {
                path = logFilePath + System.currentTimeMillis();
            }
            gZip(file, path);
        }
        try {
            return createLogFile(properties);
        } catch (IOException e) {
            throw new FileCantLoadException("can't create file [" + getPath() + "], please make sure you have permission.");
        }
    }

    static boolean isFileWrite() {
        return fileWrite;
    }

    static boolean isConsoleWrite() {
        return consoleWrite;
    }

    static boolean isDebug() {
        return debug;
    }

    static boolean isInfo() {
        return info;
    }

    static boolean isWarn() {
        return warn;
    }

    static boolean isError() {
        return error;
    }

    static boolean isCanAppend() {
        if (properties.getSlicer().equals(LogProperties.Slicer.DATE)) {
            return localDate.isEqual(LocalDate.now());
        } else {
            return fileSize < size;
        }
    }

    private static void gZip(File file, String newFileName) {
        newFileName += Constant.ZIP_SUFFIX;
        try {
            File zipFile = new File(newFileName);
            if (zipFile.exists()) {
                zipFile.delete();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            GZIPOutputStream zipOutputStream = new GZIPOutputStream(new FileOutputStream(zipFile));
            String line;
            while ((line = reader.readLine()) != null) {
                zipOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
            }
            reader.close();
            zipOutputStream.close();
            file.delete();
        } catch (Exception ignored) {
        }
    }
}
