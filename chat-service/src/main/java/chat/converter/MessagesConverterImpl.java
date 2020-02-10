package chat.converter;

import chat.dto.MessageDto;
import chat.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MessagesConverterImpl implements MessagesConverter {
    @Override
    public List<MessageDto> convertToListDtoMessage(Set<Message> messageSet) {
        return null;
    }

    @Override
    public Message convertToMessageFromDto(MessageDto message) {
        return null;
    }
}
