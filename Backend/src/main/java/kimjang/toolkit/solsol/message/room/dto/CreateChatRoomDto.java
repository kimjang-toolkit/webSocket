package kimjang.toolkit.solsol.message.room.dto;

import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomDto {
    private List<CustomerDto> participants;
    private String roomName;
    private String firstChat;
    private CustomerDto maker;
}
