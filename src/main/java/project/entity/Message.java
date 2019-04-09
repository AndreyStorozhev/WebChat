package project.entity;

import java.io.Serializable;

public class Message implements Serializable {
    private String msg;
    private String name;
    private String formatDate;
    private Conversation conversation; // один ко многим

    public Message(String name, String msg, String formatDate) {
        this.msg = msg;
        this.name = name;
        this.formatDate = formatDate;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public Message() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
