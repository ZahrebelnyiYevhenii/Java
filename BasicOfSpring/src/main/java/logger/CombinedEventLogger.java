package logger;

import entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CombinedEventLogger implements EventLogger {
    @Resource(name = "combinedLoggers")
    private List<EventLogger> loggers;

    @Override
    public void logEvent(Event event) {
        writeEventToDiffLogger(event);
    }

    private void writeEventToDiffLogger(Event event) {
        loggers.forEach(logger -> logger.logEvent(event));
    }

    public CombinedEventLogger() {
    }

    public List<EventLogger> getLoggers() {
        return loggers;
    }

    public void setLoggers(List<EventLogger> loggers) {
        this.loggers = loggers;
    }
}
