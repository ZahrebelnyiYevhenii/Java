package startProject;

import configuration.AppConfig;
import configuration.LoggerConfig;
import entity.Client;
import entity.Event;
import entity.EventType;
import logger.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class App {
    @Autowired
    private Event event;
    @Autowired
    private Client client;
    @Resource(name = "defaultLogger")
    private EventLogger eventLogger;
    @Resource(name = "loggerMap")
    private Map<EventType, EventLogger> loggers;

    public App() {
    }

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.eventLogger = eventLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class, LoggerConfig.class);
        ctx.scan("");
        ctx.refresh();
        App app = (App) ctx.getBean("app");
        app.logEvent("Some event for user 1", EventType.INFO);
        app.logEvent("Some event for user 2", EventType.ERROR);
        app.logEvent("Some event for user 3", null);

        ctx.close();
    }

    private void logEvent(String msg, EventType eventType) {
        EventLogger logger = loggers.get(eventType);

        if (logger == null) {
            logger = eventLogger;
        }

        if (msg.contains(client.getId().toString())) {
            String message = msg.replaceAll(client.getId().toString(), client.getFullName());

            event.setMsg(message);
            logger.logEvent(event);
        } else {
            event.setMsg(msg);
            logger.logEvent(event);
        }
    }
}
