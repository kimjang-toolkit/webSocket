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
}
