package logger;

import entity.Event;

import java.util.List;

public class CombinedEventLogger implements EventLogger {
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

    public CombinedEventLogger(List<EventLogger> loggers) {
        this.loggers = loggers;
    }

    public List<EventLogger> getLoggers() {
        return loggers;
    }

    public void setLoggers(List<EventLogger> loggers) {
        this.loggers = loggers;
    }
}
