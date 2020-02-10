package chat.dto;

public class MessageDto {
    private String name;
    private String msg;
    private String conversationUID;
    private String formatDate;

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public String getConversationUID() {
        return conversationUID;
    }

    public void setConversationUID(String conversationUID) {
        this.conversationUID = conversationUID;
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
