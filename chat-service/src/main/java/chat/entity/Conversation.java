package chat.entity;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "conversation", schema = "public")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "conversation_uid")
    private int ConversationUID;

    @Column(name = "last_active_chat_date")
    private Date lastActiveChatDate;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "conversations_users", joinColumns = @JoinColumn(name = "conversation_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_details_id"))
//    private Set<User> userDetailsSet;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Message> messages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversationUID() {
        return ConversationUID;
    }

    public void setConversationUID(int UIDConversation) {
        this.ConversationUID = UIDConversation;
    }

    public Date getLastActiveChatDate() {
        return lastActiveChatDate;
    }

    public void setLastActiveChatDate(Date lastActiveChatDate) {
        this.lastActiveChatDate = lastActiveChatDate;
    }

//    public Set<User> getUserDetailsSet() {
//        return userDetailsSet;
//    }

//    public void setUserDetailsSet(Set<User> userSet) {
//        this.userDetailsSet = userDetailsSet;
//    }

    public Set<Message> getMessages() {
        if (CollectionUtils.isEmpty(messages))
            return new HashSet<>();
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
