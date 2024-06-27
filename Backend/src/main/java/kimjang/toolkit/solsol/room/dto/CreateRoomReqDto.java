package kimjang.toolkit.solsol.room.dto;

import kimjang.toolkit.solsol.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomReqDto {

    private Long roomId;
    private String roomName;
    private UserDto user; // 초대 받는 사람
    private String firstChat;
    private UserDto maker; // 방 만드는 사람

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("방 번호 : ").append(roomId)
                .append("첫 챗팅 : ").append(firstChat)
                .append("생성자 : ").append(maker.getName());
        return sb.toString();
    }
}
