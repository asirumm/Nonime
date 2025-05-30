package io.asirum.Service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.PrintWriter;
import java.io.StringWriter;

import static java.lang.String.format;

public class Log {
    private static FileHandle logFile = Gdx.files.local("log/application.log");
    private static boolean writeToFile = true;
    private static boolean writeToConsole = true;
    private static LogLevel minimumLevel = LogLevel.DEBUG;

    public enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }

    static {
        if (!Gdx.files.local("logs").exists()) {
            Gdx.files.local("logs").mkdirs();
        }
    }

    // Konfigurasi utama (sekali saja di awal)
    public static void configure(boolean toFile, boolean toConsole, LogLevel minLevel) {
        writeToFile = toFile;
        writeToConsole = toConsole;
        minimumLevel = minLevel;
    }

    public static void debug(String className, String message, Object... args) {
        log(LogLevel.DEBUG, className, format(message, args), null);
    }


    public static void info(String className, String message, Object... args) {
        log(LogLevel.DEBUG, className, format(message, args), null);
    }

    public static void warn(String className, String message, Object... args) {
        log(LogLevel.DEBUG, className, format(message, args), null);
    }

    public static void error(String className, String message,Exception e,Object... args) {
        log(LogLevel.ERROR, className, format(message,args), e);
    }

    public static void error(String className, Exception e) {
        log(LogLevel.ERROR, className, e.getMessage(), e);
    }

    private static void log(LogLevel level, String className, String message, Exception e) {
        if (level.ordinal() < minimumLevel.ordinal()) {
            return;
        }

        String logEntry = level + " | [" + className + "] " +" - "+ message;

        if (e != null) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logEntry += "\n" + sw.toString();
        }

        // Output ke console jika diaktifkan
        if (writeToConsole) {
            Gdx.app.log(level.toString(), logEntry);
        }

        // Output ke file jika diaktifkan
        if (writeToFile) {
            try {
                logFile.writeString(logEntry + "\n", true); // Format newline disamakan
            } catch (Exception fileEx) {
                Gdx.app.error("LOG", "Gagal menulis ke file log", fileEx);
            }
        }
    }

    public static void clear() {
        if (logFile != null) {
            logFile.writeString("", false);
        }
    }
}
