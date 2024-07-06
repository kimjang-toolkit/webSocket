package kimjang.toolkit.solsol.room.dto;

import kimjang.toolkit.solsol.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomDto {
    private List<Long> participants;
    private String roomName;
//    private String firstChat;
//    private String creator;
}
