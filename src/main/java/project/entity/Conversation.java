package project.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "conversation", schema = "mydbtest")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "UIDConversation")
    private int UIDConversation;

    @Column(name = "last_active_chat_date")
    private Date lastActiveChatDate;

    @ManyToMany
    @JoinTable(name = "conversations_users", joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_details_id"))
    private Set<UserDetails> userDetailsSet;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Message> messages;

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
