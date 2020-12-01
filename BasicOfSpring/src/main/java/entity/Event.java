package entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.util.Date;

@Component
public class Event {
    private Long id;
    private String msg;
    private Date date;
    private DateFormat df;

    public Event() {
    }

    @Autowired
    public Event(Date date, DateFormat df) {
        SecureRandom random = new SecureRandom();
        this.id = (long) random.nextInt(10);
        this.date = date;
        this.df = df;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + df.format(date) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DateFormat getDf() {
        return df;
    }

    public void setDf(DateFormat df) {
        this.df = df;
    }
}
