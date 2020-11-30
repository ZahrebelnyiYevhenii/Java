package startProject;

import entity.Client;
import entity.Event;
import logger.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    private static String[] xmls = new String[]{"spring.xml", "loggers.xml", "other.xml"};
    private static ConfigurableApplicationContext parent = new ClassPathXmlApplicationContext(xmls);
    private static ConfigurableApplicationContext child = new ClassPathXmlApplicationContext(xmls, parent);
    private Client client;
    private EventLogger eventLogger;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public static void main(String[] args) {
        App app = (App) parent.getBean("app");

        app.logEvent("Some event for user 1");
        app.logEvent("Some event for user 2");

        parent.close();
    }

    private void logEvent(String msg) {
        Event event = (Event) child.getBean("event");

        if (msg.contains(client.getId().toString())) {
            String message = msg.replaceAll(client.getId().toString(), client.getFullName());

            event.setMsg(message);
            eventLogger.logEvent(event);
        } else {
            event.setMsg(msg);
            eventLogger.logEvent(event);
        }
    }
}
