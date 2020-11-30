package startProject;

import entity.Client;
import entity.Event;
import logger.EventLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    private Client client;
    private EventLogger eventLogger;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public static void main(String[] args) {
        App app = (App) applicationContext.getBean("app");

        app.logEvent("Some event for user 1");
        app.logEvent("Some event for user 2");
    }

    private void logEvent(String msg) {
        Event event = (Event) applicationContext.getBean("event");

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
