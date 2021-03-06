package ua.lokha.spigotloggerforbungee.paperspigotclasses;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * this class from paperspigot: org.bukkit.craftbukkit.v1_12_R1.util.ForwardLogHandler
 */
public class ForwardLogHandler
        extends ConsoleHandler
{
    private Map<String, Logger> cachedLoggers = new ConcurrentHashMap<>();

    private Logger getLogger(String name)
    {
        Logger logger = this.cachedLoggers.get(name);
        if (logger == null)
        {
            logger = LogManager.getLogger(name);
            this.cachedLoggers.put(name, logger);
        }
        return logger;
    }

    @Override
    public void publish(LogRecord record)
    {
        try {
            Logger logger = getLogger(String.valueOf(record.getLoggerName()));
            Throwable exception = record.getThrown();
            Level level = record.getLevel();
            String message = getFormatter().formatMessage(record);
            if (level == Level.SEVERE) {
                logger.error(message, exception);
            } else if (level == Level.WARNING) {
                logger.warn(message, exception);
            } else if (level == Level.INFO) {
                logger.info(message, exception);
            } else if (level == Level.CONFIG) {
                logger.debug(message, exception);
            } else {
                logger.trace(message, exception);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flush() {}

    @Override
    public void close()
            throws SecurityException
    {}
}
