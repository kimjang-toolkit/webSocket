package kimjang.toolkit.solsol.message;

import kimjang.toolkit.solsol.customer.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class ChatMessage {
    private Long id;
    private String content; // 뭘 보냈는지
    private Date createDate; // 언제 보냈는지
    private Customer customer; // 누가 보냈는지
}
