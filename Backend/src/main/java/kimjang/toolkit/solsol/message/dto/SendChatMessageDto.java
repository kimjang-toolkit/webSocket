package kimjang.toolkit.solsol.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SendChatMessageDto {
    private Long roomId; // 어디서 보냈는지
    private String content; // 뭘 보냈는지
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; // 언제 보냈는지
    private CustomerDto customer; // 누가 보냈는지
    public SendChatMessageDto(){
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("누가 : ").append(customer.getName()).append("\n")
                .append("언제 : ").append(createDate).append("\n")
                .append("어디서 : ").append(roomId).append("\n")
                .append("내용 : ").append(content).append("\n");
        return sb.toString();
    }
}
