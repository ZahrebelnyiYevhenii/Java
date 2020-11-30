package startProject;

import entity.Client;
import logger.EventLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    private Client client;
    private EventLogger eventLogger;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) applicationContext.getBean("app");

        app.logEvent("Some event for user 1");
        app.logEvent("Some event for user 2");
    }

    private void logEvent(String msg) {
        if (msg.contains(client.getId().toString())) {
            String message = msg.replaceAll(client.getId().toString(), client.getFullName());
            eventLogger.logEvent(message);
        } else {
            eventLogger.logEvent(msg);
        }
    }


}
