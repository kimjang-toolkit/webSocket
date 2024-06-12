package kimjang.toolkit.solsol.message;

import kimjang.toolkit.solsol.customer.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * 엔티티는 불변해야하니까 final로 설정
 */
@Getter
@RequiredArgsConstructor
public class ChatMessage {
    private final Long id;
    private final Long roomId; // 어디서 보냈는지
    private final String content; // 뭘 보냈는지
    private final LocalDateTime createDate; // 언제 보냈는지
    private final Customer customer; // 누가 보냈는지
}
