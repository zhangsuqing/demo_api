package cn.yoren.srs.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zsq
 * @create 2019-07-09-16:55
 */
public class LoggerUtils {
    private static Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    public LoggerUtils() {
    }

    public static void infoWithStackTrace(String message) {
        try {
            StackTraceElement[] stackTraceElements = (new Throwable()).getStackTrace();
            StringBuilder stringBuilder = new StringBuilder("===>" + message + "\n");
            if (stackTraceElements != null && stackTraceElements.length > 0) {
                StackTraceElement stackTraceElement = null;

                for(int i = 0; i < stackTraceElements.length; ++i) {
                    stackTraceElement = stackTraceElements[i];
                    if (stackTraceElement.getClassName().startsWith("com.eartplus")) {
                        if (i == stackTraceElements.length - 1) {
                            stringBuilder.append("===>:" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "(" + stackTraceElement.getFileName() + "/" + stackTraceElement.getLineNumber() + ")\n");
                        } else {
                            stringBuilder.append("===>:" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "(" + stackTraceElement.getFileName() + "/" + stackTraceElement.getLineNumber() + ")\n");
                        }
                    }
                }

                logger.info(stringBuilder.toString());
            } else {
                logger.info(message);
            }
        } catch (Exception var5) {
            logger.info(var5.getMessage());
        }

    }
}
