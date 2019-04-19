package project.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message", schema = "mydbtest")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "msg")
    private String msg;

    @Column(name = "user_name")
    private String name;

    @Column(name = "date_msg")
    private String formatDate;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
