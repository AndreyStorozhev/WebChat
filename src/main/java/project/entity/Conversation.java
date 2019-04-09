package project.entity;

import java.util.Date;
import java.util.Set;

public class Conversation {
    private int id;
    private int UIDConversation;
    private Date lastActiveChatDate;
    private Set<UserDetails> userDetailsSet; // многие ко многим
    private Set<Message> messages; //многие к одному

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUIDConversation() {
        return UIDConversation;
    }

    public void setUIDConversation(int UIDConversation) {
        this.UIDConversation = UIDConversation;
    }

    public Date getLastActiveChatDate() {
        return lastActiveChatDate;
    }

    public void setLastActiveChatDate(Date lastActiveChatDate) {
        this.lastActiveChatDate = lastActiveChatDate;
    }

    public Set<UserDetails> getUserDetailsSet() {
        return userDetailsSet;
    }

    public void setUserDetailsSet(Set<UserDetails> userDetailsSet) {
        this.userDetailsSet = userDetailsSet;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
