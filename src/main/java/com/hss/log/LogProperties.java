package com.hss.log;

class LogProperties {
    private String path;

    private LogLevel logLevel;

    private PrintLevel printLevel;

    private PrintType printType;

    private Slicer slicer;

    private Long size;

    String getPath() {
        return path;
    }

    void setPath(String path) {
        this.path = path;
    }

    LogLevel getLogLevel() {
        return logLevel;
    }

    void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    PrintLevel getPrintLevel() {
        return printLevel;
    }

    void setPrintLevel(PrintLevel printLevel) {
        this.printLevel = printLevel;
    }

    Long getSize() {
        return size;
    }

    void setSize(Long size) {
        this.size = size;
    }

    PrintType getPrintType() {
        return printType;
    }

    void setPrintType(PrintType printType) {
        this.printType = printType;
    }

    Slicer getSlicer() {
        return slicer;
    }

    void setSlicer(Slicer slicer) {
        this.slicer = slicer;
    }

    public enum LogLevel {
        DEBUG(1), INFO(2), WARN(3), ERROR(4);

        private int level;

        LogLevel(int level) {
            this.level = level;
        }

        public static LogLevel transformer(String info) {
            for (LogLevel logLevel : values()) {
                if (logLevel.toString().equalsIgnoreCase(info)) {
                    return logLevel;
                }
            }

            return INFO;
        }

        public int getLevel() {
            return level;
        }
    }

    public enum PrintLevel {
        CONSOLE, FILE, BOTH;

        public static PrintLevel transformer(String info) {
            for (PrintLevel printLevel : values()) {
                if (printLevel.toString().equalsIgnoreCase(info)) {
                    return printLevel;
                }
            }

            return CONSOLE;
        }
    }

    public enum PrintType {
        APPEND, COVER;

        public static PrintType transformer(String info) {
            for (PrintType printType : values()) {
                if (printType.toString().equalsIgnoreCase(info)) {
                    return printType;
                }
            }

            return APPEND;
        }
    }

    public enum Slicer {
        DATE, SIZE;

        public static Slicer transformer(String info) {
            for (Slicer slicer : values()) {
                if (slicer.toString().equalsIgnoreCase(info)) {
                    return slicer;
                }
            }

            return DATE;
        }
    }
}
