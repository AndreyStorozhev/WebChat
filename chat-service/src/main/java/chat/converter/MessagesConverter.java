package chat.converter;

import chat.dto.MessageDto;
import chat.entity.Message;

import java.util.List;
import java.util.Set;

public interface MessagesConverter {
    List<MessageDto> convertToListDtoMessage(Set<Message> messageSet);
    Message convertToMessageFromDto(MessageDto message);
}
