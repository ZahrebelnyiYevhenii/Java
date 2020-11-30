package startProject;

import entity.Client;
import entity.Event;
import entity.EventType;
import logger.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {
    private static String[] xmls = new String[]{"spring.xml", "loggers.xml", "other.xml"};
    private static ConfigurableApplicationContext parent = new ClassPathXmlApplicationContext(xmls);
    private static ConfigurableApplicationContext child = new ClassPathXmlApplicationContext(xmls, parent);
    private Client client;
    private EventLogger eventLogger;
    private Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.eventLogger = eventLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {
        App app = (App) parent.getBean("app");

        app.logEvent("Some event for user 1", EventType.INFO);
        app.logEvent("Some event for user 2", EventType.ERROR);
        app.logEvent("Some event for user 3", null);

        parent.close();
    }

    private void logEvent(String msg, EventType eventType) {
        Event event = (Event) child.getBean("event");
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
