package com.dublikunt.astelfa.helper;

import com.dublikunt.astelfa.Astelfa;
import org.apache.logging.log4j.LogManager;

public class Logger {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Astelfa.MOD_NAME);

    public static void info(String text) {
        if (!(Astelfa.config.logType == LogType.NONE)) {
            LOGGER.info(text);
        }
    }

    public static void debug(String text) {
        if (Astelfa.config.logType == LogType.DEBUG) {
            LOGGER.info(text);
        }
    }
}
