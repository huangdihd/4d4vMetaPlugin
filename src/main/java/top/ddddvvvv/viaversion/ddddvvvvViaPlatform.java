package top.ddddvvvv.viaversion;

import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ddddvvvvViaPlatform implements ViaPlatform<UUID> {
    private static final org.slf4j.Logger slf4j = LoggerFactory.getLogger("ViaVersion");
    private static final Logger logger = createLogger();
    private static final ViaVersionConfig config = new ddddvvvvViaVersionConfig();
    private static final ViaAPI<UUID> api = new ddddvvvvViaAPI();

    private static Logger createLogger() {
        Logger l = Logger.getLogger("ViaVersion");
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
    public String getPlatformName() {
        return "4d4vMetaPlugin";
    }

    @Override
    public String getPlatformVersion() {
        return "1.0.0";
    }

    @Override
    public ViaAPI<UUID> getApi() {
        return api;
    }

    @Override
    public ViaVersionConfig getConf() {
        return config;
    }

    @Override
    public File getDataFolder() {
        return new File("viaversion");
    }
}
