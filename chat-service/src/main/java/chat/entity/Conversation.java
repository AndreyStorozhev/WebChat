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
    private int conversationUID;

    @Column(name = "last_active_chat_date")
    private Date lastActiveChatDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "conversations_users", joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_user_id"))
    private Set<ChatUser> chatUserSet;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Message> messages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversationUID() {
        return conversationUID;
    }

    public void setConversationUID(int conversationUID) {
        this.conversationUID = conversationUID;
    }

    public Date getLastActiveChatDate() {
        return lastActiveChatDate;
    }

    public void setLastActiveChatDate(Date lastActiveChatDate) {
        this.lastActiveChatDate = lastActiveChatDate;
    }

    public Set<ChatUser> getChatUser() {
        return chatUserSet;
    }

    public void setUserDetailsSet(Set<ChatUser> userSet) {
        this.chatUserSet = userSet;
    }

    public Set<Message> getMessages() {
        if (CollectionUtils.isEmpty(messages))
            return new HashSet<>();
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
