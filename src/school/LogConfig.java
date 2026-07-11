package school;

import java.util.logging.*;

public class LogConfig {
    
    private LogConfig() {}
 
    public static void setup() {
        try {
            // Remove every existing handler from the root logger
            Logger rootLogger = LogManager.getLogManager().getLogger("");
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }
 
            // Write logs to file only
            FileHandler fileHandler = new FileHandler("school.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.ALL);
 
        } catch (Exception e) {
            System.out.println("Warning: Could not configure log file. Logging disabled.");
        }
    }
    
}
