package kimjang.toolkit.solsol.room.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kimjang.toolkit.solsol.customer.dto.CustomerDto;
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
    private CustomerDto customer;
    private String firstChat;
    private CustomerDto maker;

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("방 번호 : ").append(roomId)
                .append("첫 챗팅 : ").append(firstChat)
                .append("생성자 : ").append(maker.getName());
        return sb.toString();
    }
}
