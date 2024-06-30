package kimjang.toolkit.solsol.room.dto;

import kimjang.toolkit.solsol.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRoomDto {
    private Long roomId;
    private UserDto leaveUser;
}
