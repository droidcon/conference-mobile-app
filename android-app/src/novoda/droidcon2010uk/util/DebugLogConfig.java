package novoda.droidcon2010uk.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import android.util.Log;

public class DebugLogConfig {
	// Use with DebugLogConfig.enable();

    static DalvikLogHandler activeHandler;

    protected static class DalvikLogHandler extends Handler {

        private static final String LOG_TAG = "HttpClient";

        @Override
        public void close() {
            // do nothing
        }

        @Override
        public void flush() {
            // do nothing
        }

        @Override
        public void publish(LogRecord record) {
            if (record.getLoggerName().startsWith("org.apache")) {
                Log.d(LOG_TAG, record.getMessage());
            }
        }
    }

    public static void enable() {
        try {
            String config = "org.apache.http.impl.conn.level = FINEST\n"
                    + "org.apache.http.impl.client.level = FINEST\n"
                    + "org.apache.http.client.level = FINEST\n" + "org.apache.http.level = FINEST";
            InputStream in = new ByteArrayInputStream(config.getBytes());
            LogManager.getLogManager().readConfiguration(in);
        } catch (IOException e) {
            Log
                    .w(DebugLogConfig.class.getSimpleName(),
                            "Can't read configuration file for logging");
        }
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        activeHandler = new DalvikLogHandler();
        activeHandler.setLevel(Level.ALL);
        rootLogger.addHandler(activeHandler);
    }

}
