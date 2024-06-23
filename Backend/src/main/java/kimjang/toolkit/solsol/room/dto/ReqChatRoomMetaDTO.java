package kimjang.toolkit.solsol.room.dto;

import kimjang.toolkit.solsol.customer.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqChatRoomMetaDTO {
    private Long roomId;
    private UserDto user;
}
