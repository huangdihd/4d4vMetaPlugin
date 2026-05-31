package top.ddddvvvv.viaversion;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ddddvvvvViaBackwardsPlatform implements ViaBackwardsPlatform {
    private static final org.slf4j.Logger slf4j = LoggerFactory.getLogger("ViaBackwards");
    private static final Logger logger = createLogger();

    private static Logger createLogger() {
        Logger l = Logger.getLogger("ViaBackwards");
        l.setUseParentHandlers(false);
        for (Handler h : l.getHandlers()) {
            l.removeHandler(h);
        }
        l.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                String msg = record.getMessage();
                Level level = record.getLevel();
                if (level == Level.SEVERE) {
                    slf4j.error(msg);
                } else if (level == Level.WARNING) {
                    slf4j.warn(msg);
                } else if (level == Level.INFO) {
                    slf4j.info(msg);
                } else {
                    slf4j.debug(msg);
                }
            }
            @Override public void flush() {}
            @Override public void close() {}
        });
        return l;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public File getDataFolder() {
        return new File("viabackwards");
    }

    @Override
    public void disable() {
    }
}
