package logger;

import entity.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class FileEventLogger implements EventLogger {
    private String filename;
    private File file;

    public FileEventLogger() {
    }

    @Autowired
    public FileEventLogger(@Value("src/main/resources/logger.txt") String filename) {
        this.filename = filename;
    }
    @PostConstruct
    public void init() throws IOException {
        this.file = new File(filename);
        if (!file.canWrite()) {
            throw new IOException();
        }
    }

    public void logEvent(Event event) {

        try {
            FileUtils.writeStringToFile(file, event.toString(), true);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "file =" + file + "doesn't access");
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
